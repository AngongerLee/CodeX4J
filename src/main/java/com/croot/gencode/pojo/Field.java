package com.croot.gencode.pojo;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlSeeAlso({
        DictField.class
})
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement
public class Field {
    @XmlAttribute(name = "Name")
    private String name;
    @XmlAttribute(name = "Description")
    private String description;
}
