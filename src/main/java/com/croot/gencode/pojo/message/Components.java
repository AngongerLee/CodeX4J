package com.croot.gencode.pojo.message;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "Components")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Components {
    @XmlElement(name = "Component")
    private List<Component> components;
}
