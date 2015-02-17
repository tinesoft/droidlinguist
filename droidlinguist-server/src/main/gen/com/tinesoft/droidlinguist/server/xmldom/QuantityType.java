//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.08.31 à 11:24:59 PM CEST 
//


package com.tinesoft.droidlinguist.server.xmldom;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour quantityType.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="quantityType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="zero"/>
 *     &lt;enumeration value="one"/>
 *     &lt;enumeration value="two"/>
 *     &lt;enumeration value="few"/>
 *     &lt;enumeration value="many"/>
 *     &lt;enumeration value="other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "quantityType")
@XmlEnum
public enum QuantityType {

    @XmlEnumValue("zero")
    ZERO("zero"),
    @XmlEnumValue("one")
    ONE("one"),
    @XmlEnumValue("two")
    TWO("two"),
    @XmlEnumValue("few")
    FEW("few"),
    @XmlEnumValue("many")
    MANY("many"),
    @XmlEnumValue("other")
    OTHER("other");
    private final String value;

    QuantityType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static QuantityType fromValue(String v) {
        for (QuantityType c: QuantityType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
