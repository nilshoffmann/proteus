/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.maltcms.io.xml.serialization.api;

import java.beans.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.EventListener;
import org.openide.util.Exceptions;
import sun.reflect.misc.MethodUtil;

/**
 * This code is adapted from DefaultPersistenceDelegate.
 * @author nilshoffmann
 */
public class CustomPersistenceDelegate extends PersistenceDelegate {

    private String[] constructor = new String[0];
    private int beanInfoMode = Introspector.USE_ALL_BEANINFO;

    public CustomPersistenceDelegate() {
        this(new String[0],Introspector.USE_ALL_BEANINFO);
    }

    public CustomPersistenceDelegate(int beanInfoMode) {
        this(new String[0],beanInfoMode);
    }
    
    public CustomPersistenceDelegate(String[] strings) {
        this(strings, Introspector.USE_ALL_BEANINFO);
    }

    public CustomPersistenceDelegate(String[] strings, int beanInfoMode) {
        this.beanInfoMode = beanInfoMode;
        this.constructor = strings;
    }

    /**
     * This default implementation of the
     * <code>instantiate</code> method returns an expression containing the
     * predefined method name "new" which denotes a call to a constructor with
     * the arguments as specified in the
     * <code>DefaultPersistenceDelegate</code>'s constructor.
     *
     * @param oldInstance The instance to be instantiated.
     * @param out The code output stream.
     * @return An expression whose value is
     * <code>oldInstance</code>.
     *
     * @see #DefaultPersistenceDelegate(String[])
     */
    @Override
    protected Expression instantiate(Object oldInstance, Encoder out) {
        int nArgs = this.constructor.length;
        Class type = oldInstance.getClass();
        Object[] constructorArgs = new Object[nArgs];
        for (int i = 0; i < nArgs; i++) {
            try {
                Method method = findMethod(type, this.constructor[i]);
                constructorArgs[i] = MethodUtil.invoke(method, oldInstance, new Object[0]);
            } catch (Exception e) {
                out.getExceptionListener().exceptionThrown(e);
            }
        }
        return new Expression(oldInstance, oldInstance.getClass(), "new", constructorArgs);
    }

    private Method findMethod(Class type, String property) throws IntrospectionException {
        if (property == null) {
            throw new IllegalArgumentException("Property name is null");
        }
        BeanInfo info = Introspector.getBeanInfo(type, beanInfoMode);
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            if (property.equals(pd.getName())) {
                Method method = pd.getReadMethod();
                if (method != null) {
                    return method;
                }
                throw new IllegalStateException("Could not find getter for the property " + property);
            }
        }
        throw new IllegalStateException("Could not find property by the name " + property);
    }

    static void invokeStatement(Object instance, String methodName, Object[] args, Encoder out) {
        out.writeStatement(new Statement(instance, methodName, args));
    }

    private static boolean equals(Object o1, Object o2) {
        return (o1 == null) ? (o2 == null) : o1.equals(o2);
    }

    // This is a workaround for a bug in the introspector.
    // PropertyDescriptors are not shared amongst subclasses.
    private boolean isTransient(Class type, PropertyDescriptor pd) {
        if (type == null) {
            return false;
        }
        // This code was mistakenly deleted - it may be fine and
        // is more efficient than the code below. This should
        // all disappear anyway when property descriptors are shared
        // by the introspector.
        /*
         * Method getter = pd.getReadMethod(); Class declaringClass =
         * getter.getDeclaringClass(); if (declaringClass == type) { return
         * Boolean.TRUE.equals(pd.getValue("transient")); }
         */
        String pName = pd.getName();
        BeanInfo info;
        try {
            info = Introspector.getBeanInfo(type, beanInfoMode);
            PropertyDescriptor[] propertyDescriptors = info.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; ++i) {
                PropertyDescriptor pd2 = propertyDescriptors[i];
                if (pName.equals(pd2.getName())) {
                    Object value = pd2.getValue("transient");
                    if (value != null) {
                        return Boolean.TRUE.equals(value);
                    }
                }
            }
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }

        return isTransient(type.getSuperclass(), pd);
    }

    private void doProperty(Class type, PropertyDescriptor pd, Object oldInstance, Object newInstance, Encoder out) throws Exception {
        Method getter = pd.getReadMethod();
        Method setter = pd.getWriteMethod();

        if (getter != null && setter != null && !isTransient(type, pd)) {
            Expression oldGetExp = new Expression(oldInstance, getter.getName(), new Object[]{});
            Expression newGetExp = new Expression(newInstance, getter.getName(), new Object[]{});
            Object oldValue = oldGetExp.getValue();
            Object newValue = newGetExp.getValue();
            out.writeExpression(oldGetExp);
            if (!equals(newValue, out.get(oldValue))) {
                // Search for a static constant with this value;
                Object e = (Object[]) pd.getValue("enumerationValues");
                if (e instanceof Object[] && Array.getLength(e) % 3 == 0) {
                    Object[] a = (Object[]) e;
                    for (int i = 0; i < a.length; i = i + 3) {
                        try {
                            Field f = type.getField((String) a[i]);
                            if (f.get(null).equals(oldValue)) {
                                out.remove(oldValue);
                                out.writeExpression(new Expression(oldValue, f, "get", new Object[]{null}));
                            }
                        } catch (Exception ex) {
                        }
                    }
                }
                invokeStatement(oldInstance, setter.getName(), new Object[]{oldValue}, out);
            }
        }
    }

    // Write out the properties of this instance.
    private void initBean(Class type, Object oldInstance, Object newInstance, Encoder out) {
        // System.out.println("initBean: " + oldInstance);
        BeanInfo info;
        try {
            info = Introspector.getBeanInfo(type, beanInfoMode);
            // Properties
            PropertyDescriptor[] propertyDescriptors = info.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; ++i) {
                try {
                    doProperty(type, propertyDescriptors[i], oldInstance, newInstance, out);
                } catch (Exception e) {
                    out.getExceptionListener().exceptionThrown(e);
                }
            }

            // Listeners
        /*
             * Pending(milne). There is a general problem with the archival of
             * listeners which is unresolved as of 1.4. Many of the methods
             * which install one object inside another (typically "add" methods
             * or setters) automatically install a listener on the "child"
             * object so that its "parent" may respond to changes that are made
             * to it. For example the JTable:setModel() method automatically
             * adds a TableModelListener (the JTable itself in this case) to the
             * supplied table model.
             *
             * We do not need to explictly add these listeners to the model in
             * an archive as they will be added automatically by, in the above
             * case, the JTable's "setModel" method. In some cases, we must
             * specifically avoid trying to do this since the listener may be an
             * inner class that cannot be instantiated using public API.
             *
             * No general mechanism currently exists for differentiating between
             * these kind of listeners and those which were added explicitly by
             * the user. A mechanism must be created to provide a general means
             * to differentiate these special cases so as to provide reliable
             * persistence of listeners for the general case.
             */
            if (!java.awt.Component.class.isAssignableFrom(type)) {
                return; // Just handle the listeners of Components for now.
            }
            EventSetDescriptor[] eventSetDescriptors = info.getEventSetDescriptors();
            for (int e = 0; e < eventSetDescriptors.length; e++) {
                EventSetDescriptor d = eventSetDescriptors[e];
                Class listenerType = d.getListenerType();


                // The ComponentListener is added automatically, when
                // Contatiner:add is called on the parent.
                if (listenerType == java.awt.event.ComponentListener.class) {
                    continue;
                }

                // JMenuItems have a change listener added to them in
                // their "add" methods to enable accessibility support -
                // see the add method in JMenuItem for details. We cannot
                // instantiate this instance as it is a private inner class
                // and do not need to do this anyway since it will be created
                // and installed by the "add" method. Special case this for now,
                // ignoring all change listeners on JMenuItems.
                if (listenerType == javax.swing.event.ChangeListener.class
                        && type == javax.swing.JMenuItem.class) {
                    continue;
                }

                EventListener[] oldL = new EventListener[0];
                EventListener[] newL = new EventListener[0];
                try {
                    Method m = d.getGetListenerMethod();
                    oldL = (EventListener[]) MethodUtil.invoke(m, oldInstance, new Object[]{});
                    newL = (EventListener[]) MethodUtil.invoke(m, newInstance, new Object[]{});
                } catch (Throwable e2) {
                    try {
                        Method m = type.getMethod("getListeners", new Class[]{Class.class});
                        oldL = (EventListener[]) MethodUtil.invoke(m, oldInstance, new Object[]{listenerType});
                        newL = (EventListener[]) MethodUtil.invoke(m, newInstance, new Object[]{listenerType});
                    } catch (Exception e3) {
                        return;
                    }
                }

                // Asssume the listeners are in the same order and that there are no gaps.
                // Eventually, this may need to do true differencing.
                String addListenerMethodName = d.getAddListenerMethod().getName();
                for (int i = newL.length; i < oldL.length; i++) {
                    // System.out.println("Adding listener: " + addListenerMethodName + oldL[i]);
                    invokeStatement(oldInstance, addListenerMethodName, new Object[]{oldL[i]}, out);
                }

                String removeListenerMethodName = d.getRemoveListenerMethod().getName();
                for (int i = oldL.length; i < newL.length; i++) {
                    invokeStatement(oldInstance, removeListenerMethodName, new Object[]{newL[i]}, out);
                }
            }
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * This default implementation of the
     * <code>initialize</code> method assumes all state held in objects of this
     * type is exposed via the matching pairs of "setter" and "getter" methods
     * in the order they are returned by the Introspector. If a property
     * descriptor defines a "transient" attribute with a value equal to
     * <code>Boolean.TRUE</code> the property is ignored by this default
     * implementation. Note that this use of the word "transient" is quite
     * independent of the field modifier that is used by the
     * <code>ObjectOutputStream</code>. <p> For each non-transient property, an
     * expression is created in which the nullary "getter" method is applied to
     * the
     * <code>oldInstance</code>. The value of this expression is the value of
     * the property in the instance that is being serialized. If the value of
     * this expression in the cloned environment
     * <code>mutatesTo</code> the target value, the new value is initialized to
     * make it equivalent to the old value. In this case, because the property
     * value has not changed there is no need to call the corresponding "setter"
     * method and no statement is emitted. If not however, the expression for
     * this value is replaced with another expression (normally a constructor)
     * and the corresponding "setter" method is called to install the new
     * property value in the object. This scheme removes default information
     * from the output produced by streams using this delegate. <p> In passing
     * these statements to the output stream, where they will be executed, side
     * effects are made to the
     * <code>newInstance</code>. In most cases this allows the problem of
     * properties whose values depend on each other to actually help the
     * serialization process by making the number of statements that need to be
     * written to the output smaller. In general, the problem of handling
     * interdependent properties is reduced to that of finding an order for the
     * properties in a class such that no property value depends on the value of
     * a subsequent property.
     *
     * @param oldInstance The instance to be copied.
     * @param newInstance The instance that is to be modified.
     * @param out The stream to which any initialization statements should be
     * written.
     *
     * @see java.beans.Introspector#getBeanInfo
     * @see java.beans.PropertyDescriptor
     */
    @Override
    protected void initialize(Class<?> type,
            Object oldInstance, Object newInstance,
            Encoder out) {
        // System.out.println("DefulatPD:initialize" + type);
        super.initialize(type, oldInstance, newInstance, out);
        if (oldInstance.getClass() == type) { // !type.isInterface()) {
            //System.out.println("Using custom persistence delegate on "+type.getName());
            initBean(type, oldInstance, newInstance, out);
        }
    }
}
