package com.croot.gencode.pojo.message;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "Field")
public class MessageField {
    @XmlAttribute(name = "Name")
    private String name;
    @XmlAttribute(name = "Number")
    private int number;
    @XmlAttribute(name = "Type")
    private String type;
    @XmlAttribute(name = "Flag")
    private String flag;
    @XmlAttribute(name = "Description")
    private String description;
}
