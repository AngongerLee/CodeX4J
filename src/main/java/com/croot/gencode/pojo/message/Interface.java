package com.croot.gencode.pojo.message;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "Interface")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Interface {
    @XmlElement(name = "Messages")
    private Messages messages;
    @XmlElement(name = "Components")
    private Components components;
    @XmlElement(name = "Fields")
    private Fields fields;
}
