package com.croot.gencode.pojo.message;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "Field")
public class MessageField {
    @XmlAttribute(name = "Name")
    private String name;
    @XmlAttribute(name = "Number")
    private Integer number;
    @XmlAttribute(name = "Type")
    private String type;
    @XmlAttribute(name = "Flag")
    private String flag;
    @XmlAttribute(name = "Description")
    private String description;
}
