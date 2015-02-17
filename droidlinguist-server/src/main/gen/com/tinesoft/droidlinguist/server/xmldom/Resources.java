//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.08.31 à 11:24:59 PM CEST 
//


package com.tinesoft.droidlinguist.server.xmldom;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="string" type="{}stringType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="string-array" type="{}stringArrayType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="plurals" type="{}pluralsType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "stringOrStringArrayOrPlurals"
})
@XmlRootElement(name = "resources")
public class Resources {

    @XmlElements({
        @XmlElement(name = "string", type = StringType.class),
        @XmlElement(name = "string-array", type = StringArrayType.class),
        @XmlElement(name = "plurals", type = PluralsType.class)
    })
    protected List<Object> stringOrStringArrayOrPlurals;

    /**
     * Gets the value of the stringOrStringArrayOrPlurals property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stringOrStringArrayOrPlurals property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStringOrStringArrayOrPlurals().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StringType }
     * {@link StringArrayType }
     * {@link PluralsType }
     * 
     * 
     */
    public List<Object> getStringOrStringArrayOrPlurals() {
        if (stringOrStringArrayOrPlurals == null) {
            stringOrStringArrayOrPlurals = new ArrayList<Object>();
        }
        return this.stringOrStringArrayOrPlurals;
    }

}
