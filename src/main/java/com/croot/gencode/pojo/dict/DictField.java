package com.croot.gencode.pojo.dict;

import com.croot.gencode.enums.TypeEnum;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "Field")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class DictField{
    @XmlAttribute(name = "Name")
    private String name;
    @XmlAttribute(name = "Description")
    private String description;
    @XmlAttribute(name = "Number")
    private int number;
    @XmlAttribute(name = "Type")
    private String type;
    private TypeEnum typeEnum;


}
