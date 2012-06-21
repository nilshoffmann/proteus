/*
 *  Copyright (C) 2008-2012 Nils Hoffmann
 *  Nils.Hoffmann A T CeBiTec.Uni-Bielefeld.DE
 *
 *  This file is part of Maui.
 *
 *  Maui is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Maui is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Maui.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations;

import java.beans.*;

/**
 *
 * @author nilshoffmann
 */
public class SpotAnnotationBeanInfo extends SimpleBeanInfo {

    // Bean descriptor information will be obtained from introspection.//GEN-FIRST:BeanDescriptor
    private static BeanDescriptor beanDescriptor = null;
    private static BeanDescriptor getBdescriptor(){//GEN-HEADEREND:BeanDescriptor





    // Here you can add code for customizing the BeanDescriptor.

         return beanDescriptor;     }//GEN-LAST:BeanDescriptor


    // Property identifiers//GEN-FIRST:Properties
    private static final int PROPERTY_displacementX = 0;
    private static final int PROPERTY_displacementY = 1;
    private static final int PROPERTY_drawSpotBox = 2;
    private static final int PROPERTY_drawSpotId = 3;
    private static final int PROPERTY_fillAlpha = 4;
    private static final int PROPERTY_fillColor = 5;
    private static final int PROPERTY_font = 6;
    private static final int PROPERTY_lineColor = 7;
    private static final int PROPERTY_payload = 8;
    private static final int PROPERTY_position = 9;
    private static final int PROPERTY_selected = 10;
    private static final int PROPERTY_selectedFillColor = 11;
    private static final int PROPERTY_selectedStrokeColor = 12;
    private static final int PROPERTY_selectionCrossColor = 13;
    private static final int PROPERTY_shape = 14;
    private static final int PROPERTY_strokeAlpha = 15;
    private static final int PROPERTY_strokeColor = 16;
    private static final int PROPERTY_textColor = 17;

    // Property array 
    /*lazy PropertyDescriptor*/
    private static PropertyDescriptor[] getPdescriptor(){
        PropertyDescriptor[] properties = new PropertyDescriptor[18];
    
        try {
            properties[PROPERTY_displacementX] = new PropertyDescriptor ( "displacementX", de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation.class, "getDisplacementX", "setDisplacementX" ); // NOI18N
            properties[PROPERTY_displacementY] = new PropertyDescriptor ( "displacementY", de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation.class, "getDisplacementY", "setDisplacementY" ); // NOI18N
            properties[PROPERTY_drawSpotBox] = new PropertyDescriptor ( "drawSpotBox", de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation.class, "isDrawSpotBox", "setDrawSpotBox" ); // NOI18N
            properties[PROPERTY_drawSpotId] = new PropertyDescriptor ( "drawSpotId", de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation.class, "isDrawSpotId", "setDrawSpotId" ); // NOI18N
            properties[PROPERTY_fillAlpha] = new PropertyDescriptor ( "fillAlpha", de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation.class, "getFillAlpha", "setFillAlpha" ); // NOI18N
            properties[PROPERTY_fillColor] = new PropertyDescriptor ( "fillColor", de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation.class, "getFillColor", "setFillColor" ); // NOI18N
            properties[PROPERTY_font] = new PropertyDescriptor ( "font", de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation.class, "getFont", "setFont" ); // NOI18N
            properties[PROPERTY_lineColor] = new PropertyDescriptor ( "lineColor", de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation.class, "getLineColor", "setLineColor" ); // NOI18N
            properties[PROPERTY_payload] = new PropertyDescriptor ( "payload", de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation.class, "getPayload", "setPayload" ); // NOI18N
            properties[PROPERTY_payload].setHidden ( true );
            properties[PROPERTY_position] = new PropertyDescriptor ( "position", de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation.class, "getPosition", "setPosition" ); // NOI18N
            properties[PROPERTY_position].setHidden ( true );
            properties[PROPERTY_selected] = new PropertyDescriptor ( "selected", de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation.class, "isSelected", "setSelected" ); // NOI18N
            properties[PROPERTY_selected].setHidden ( true );
            properties[PROPERTY_selectedFillColor] = new PropertyDescriptor ( "selectedFillColor", de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation.class, "getSelectedFillColor", "setSelectedFillColor" ); // NOI18N
            properties[PROPERTY_selectedStrokeColor] = new PropertyDescriptor ( "selectedStrokeColor", de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation.class, "getSelectedStrokeColor", "setSelectedStrokeColor" ); // NOI18N
            properties[PROPERTY_selectionCrossColor] = new PropertyDescriptor ( "selectionCrossColor", de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation.class, "getSelectionCrossColor", "setSelectionCrossColor" ); // NOI18N
            properties[PROPERTY_selectionCrossColor].setHidden ( true );
            properties[PROPERTY_shape] = new PropertyDescriptor ( "shape", de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation.class, "getShape", "setShape" ); // NOI18N
            properties[PROPERTY_shape].setHidden ( true );
            properties[PROPERTY_strokeAlpha] = new PropertyDescriptor ( "strokeAlpha", de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation.class, "getStrokeAlpha", "setStrokeAlpha" ); // NOI18N
            properties[PROPERTY_strokeColor] = new PropertyDescriptor ( "strokeColor", de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation.class, "getStrokeColor", "setStrokeColor" ); // NOI18N
            properties[PROPERTY_textColor] = new PropertyDescriptor ( "textColor", de.unibielefeld.gi.kotte.laborprogramm.gelViewer.annotations.SpotAnnotation.class, "getTextColor", "setTextColor" ); // NOI18N
        }
        catch(IntrospectionException e) {
            e.printStackTrace();
        }//GEN-HEADEREND:Properties

    // Here you can add code for customizing the properties array.

        return properties;     }//GEN-LAST:Properties

    // Event set information will be obtained from introspection.//GEN-FIRST:Events
    private static EventSetDescriptor[] eventSets = null;
    private static EventSetDescriptor[] getEdescriptor(){//GEN-HEADEREND:Events

    // Here you can add code for customizing the event sets array.

        return eventSets;     }//GEN-LAST:Events

    // Method information will be obtained from introspection.//GEN-FIRST:Methods
    private static MethodDescriptor[] methods = null;
    private static MethodDescriptor[] getMdescriptor(){//GEN-HEADEREND:Methods

    // Here you can add code for customizing the methods array.
    
        return methods;     }//GEN-LAST:Methods

    private static java.awt.Image iconColor16 = null;//GEN-BEGIN:IconsDef
    private static java.awt.Image iconColor32 = null;
    private static java.awt.Image iconMono16 = null;
    private static java.awt.Image iconMono32 = null;//GEN-END:IconsDef
    private static String iconNameC16 = null;//GEN-BEGIN:Icons
    private static String iconNameC32 = null;
    private static String iconNameM16 = null;
    private static String iconNameM32 = null;//GEN-END:Icons

    private static final int defaultPropertyIndex = -1;//GEN-BEGIN:Idx
    private static final int defaultEventIndex = -1;//GEN-END:Idx

    
//GEN-FIRST:Superclass

    // Here you can add code for customizing the Superclass BeanInfo.

//GEN-LAST:Superclass
	
    /**
     * Gets the bean's <code>BeanDescriptor</code>s.
     * 
     * @return BeanDescriptor describing the editable
     * properties of this bean.  May return null if the
     * information should be obtained by automatic analysis.
     */
    public BeanDescriptor getBeanDescriptor() {
	return getBdescriptor();
    }

    /**
     * Gets the bean's <code>PropertyDescriptor</code>s.
     * 
     * @return An array of PropertyDescriptors describing the editable
     * properties supported by this bean.  May return null if the
     * information should be obtained by automatic analysis.
     * <p>
     * If a property is indexed, then its entry in the result array will
     * belong to the IndexedPropertyDescriptor subclass of PropertyDescriptor.
     * A client of getPropertyDescriptors can use "instanceof" to check
     * if a given PropertyDescriptor is an IndexedPropertyDescriptor.
     */
    public PropertyDescriptor[] getPropertyDescriptors() {
	return getPdescriptor();
    }

    /**
     * Gets the bean's <code>EventSetDescriptor</code>s.
     * 
     * @return  An array of EventSetDescriptors describing the kinds of 
     * events fired by this bean.  May return null if the information
     * should be obtained by automatic analysis.
     */
    public EventSetDescriptor[] getEventSetDescriptors() {
	return getEdescriptor();
    }

    /**
     * Gets the bean's <code>MethodDescriptor</code>s.
     * 
     * @return  An array of MethodDescriptors describing the methods 
     * implemented by this bean.  May return null if the information
     * should be obtained by automatic analysis.
     */
    public MethodDescriptor[] getMethodDescriptors() {
	return getMdescriptor();
    }

    /**
     * A bean may have a "default" property that is the property that will
     * mostly commonly be initially chosen for update by human's who are 
     * customizing the bean.
     * @return  Index of default property in the PropertyDescriptor array
     * 		returned by getPropertyDescriptors.
     * <P>	Returns -1 if there is no default property.
     */
    public int getDefaultPropertyIndex() {
        return defaultPropertyIndex;
    }

    /**
     * A bean may have a "default" event that is the event that will
     * mostly commonly be used by human's when using the bean. 
     * @return Index of default event in the EventSetDescriptor array
     *		returned by getEventSetDescriptors.
     * <P>	Returns -1 if there is no default event.
     */
    public int getDefaultEventIndex() {
        return defaultEventIndex;
    }

    /**
     * This method returns an image object that can be used to
     * represent the bean in toolboxes, toolbars, etc.   Icon images
     * will typically be GIFs, but may in future include other formats.
     * <p>
     * Beans aren't required to provide icons and may return null from
     * this method.
     * <p>
     * There are four possible flavors of icons (16x16 color,
     * 32x32 color, 16x16 mono, 32x32 mono).  If a bean choses to only
     * support a single icon we recommend supporting 16x16 color.
     * <p>
     * We recommend that icons have a "transparent" background
     * so they can be rendered onto an existing background.
     *
     * @param  iconKind  The kind of icon requested.  This should be
     *    one of the constant values ICON_COLOR_16x16, ICON_COLOR_32x32, 
     *    ICON_MONO_16x16, or ICON_MONO_32x32.
     * @return  An image object representing the requested icon.  May
     *    return null if no suitable icon is available.
     */
    public java.awt.Image getIcon(int iconKind) {
        switch ( iconKind ) {
        case ICON_COLOR_16x16:
            if ( iconNameC16 == null )
                return null;
            else {
                if( iconColor16 == null )
                    iconColor16 = loadImage( iconNameC16 );
                return iconColor16;
            }
        case ICON_COLOR_32x32:
            if ( iconNameC32 == null )
                return null;
            else {
                if( iconColor32 == null )
                    iconColor32 = loadImage( iconNameC32 );
                return iconColor32;
            }
        case ICON_MONO_16x16:
            if ( iconNameM16 == null )
                return null;
            else {
                if( iconMono16 == null )
                    iconMono16 = loadImage( iconNameM16 );
                return iconMono16;
            }
        case ICON_MONO_32x32:
            if ( iconNameM32 == null )
                return null;
            else {
                if( iconMono32 == null )
                    iconMono32 = loadImage( iconNameM32 );
                return iconMono32;
            }
	default: return null;
        }
    }

}

