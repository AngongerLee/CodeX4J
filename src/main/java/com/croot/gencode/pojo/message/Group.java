package com.croot.gencode.pojo.message;


import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "Group")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Group {
    @XmlAttribute(name = "Name")
    private String name;
    @XmlAttribute(name = "Description")
    private String description;
    private List<MessageField> messageFields;
    private List<Component> components;
    @XmlElement(name = "Group")
    private Group group;
}
