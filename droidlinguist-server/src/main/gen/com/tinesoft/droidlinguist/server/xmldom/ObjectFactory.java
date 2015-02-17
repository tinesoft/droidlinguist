//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.08.31 à 11:24:59 PM CEST 
//


package com.tinesoft.droidlinguist.server.xmldom;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tinesoft.droidlinguist.server.xmldom package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tinesoft.droidlinguist.server.xmldom
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Resources }
     * 
     */
    public Resources createResources() {
        return new Resources();
    }

    /**
     * Create an instance of {@link StringType }
     * 
     */
    public StringType createStringType() {
        return new StringType();
    }

    /**
     * Create an instance of {@link StringArrayType }
     * 
     */
    public StringArrayType createStringArrayType() {
        return new StringArrayType();
    }

    /**
     * Create an instance of {@link PluralsType }
     * 
     */
    public PluralsType createPluralsType() {
        return new PluralsType();
    }

    /**
     * Create an instance of {@link ItemType }
     * 
     */
    public ItemType createItemType() {
        return new ItemType();
    }

}
