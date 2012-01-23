/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification;

import java.beans.*;

/**
 *
 * @author nilshoffmann
 */
public class IdentificationBeanInfo extends SimpleBeanInfo {

    // Bean descriptor information will be obtained from introspection.//GEN-FIRST:BeanDescriptor
    private static BeanDescriptor beanDescriptor = null;
    private static BeanDescriptor getBdescriptor(){//GEN-HEADEREND:BeanDescriptor


    // Here you can add code for customizing the BeanDescriptor.

         return beanDescriptor;     }//GEN-LAST:BeanDescriptor


    // Property identifiers//GEN-FIRST:Properties
    private static final int PROPERTY_abbreviation = 0;
    private static final int PROPERTY_accession = 1;
    private static final int PROPERTY_coverage = 2;
    private static final int PROPERTY_difference = 3;
    private static final int PROPERTY_gendbId = 4;
    private static final int PROPERTY_gendbProject = 5;
    private static final int PROPERTY_id = 6;
    private static final int PROPERTY_keggNumbers = 7;
    private static final int PROPERTY_method = 8;
    private static final int PROPERTY_name = 9;
    private static final int PROPERTY_piValue = 10;
    private static final int PROPERTY_proteinMolecularWeight = 11;
    private static final int PROPERTY_score = 12;
    private static final int PROPERTY_source = 13;

    // Property array 
    /*lazy PropertyDescriptor*/
    private static PropertyDescriptor[] getPdescriptor(){
        PropertyDescriptor[] properties = new PropertyDescriptor[14];
    
        try {
            properties[PROPERTY_abbreviation] = new PropertyDescriptor ( "abbreviation", de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification.Identification.class, "getAbbreviation", "setAbbreviation" ); // NOI18N
            properties[PROPERTY_accession] = new PropertyDescriptor ( "accession", de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification.Identification.class, "getAccession", "setAccession" ); // NOI18N
            properties[PROPERTY_coverage] = new PropertyDescriptor ( "coverage", de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification.Identification.class, "getCoverage", "setCoverage" ); // NOI18N
            properties[PROPERTY_difference] = new PropertyDescriptor ( "difference", de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification.Identification.class, "getDifference", "setDifference" ); // NOI18N
            properties[PROPERTY_gendbId] = new PropertyDescriptor ( "gendbId", de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification.Identification.class, "getGendbId", "setGendbId" ); // NOI18N
            properties[PROPERTY_gendbProject] = new PropertyDescriptor ( "gendbProject", de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification.Identification.class, "getGendbProject", "setGendbProject" ); // NOI18N
            properties[PROPERTY_id] = new PropertyDescriptor ( "id", de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification.Identification.class, "getId", null ); // NOI18N
            properties[PROPERTY_keggNumbers] = new PropertyDescriptor ( "keggNumbers", de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification.Identification.class, "getKeggNumbers", "setKeggNumbers" ); // NOI18N
            properties[PROPERTY_method] = new PropertyDescriptor ( "method", de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification.Identification.class, "getMethod", "setMethod" ); // NOI18N
            properties[PROPERTY_name] = new PropertyDescriptor ( "name", de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification.Identification.class, "getName", "setName" ); // NOI18N
            properties[PROPERTY_piValue] = new PropertyDescriptor ( "piValue", de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification.Identification.class, "getPiValue", "setPiValue" ); // NOI18N
            properties[PROPERTY_proteinMolecularWeight] = new PropertyDescriptor ( "proteinMolecularWeight", de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification.Identification.class, "getProteinMolecularWeight", "setProteinMolecularWeight" ); // NOI18N
            properties[PROPERTY_score] = new PropertyDescriptor ( "score", de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification.Identification.class, "getScore", "setScore" ); // NOI18N
            properties[PROPERTY_source] = new PropertyDescriptor ( "source", de.unibielefeld.gi.kotte.laborprogramm.proteomik.spi.identification.Identification.class, "getSource", "setSource" ); // NOI18N
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

