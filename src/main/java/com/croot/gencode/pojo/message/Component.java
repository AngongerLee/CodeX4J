package com.croot.gencode.pojo.message;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "Component")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Component {
    @XmlAttribute(name = "Name")
    private String name;
    @XmlAttribute(name = "WebService")
    private String webService;
    @XmlAttribute(name = "Description")
    private String description;
    @XmlElement(name = "Field")
    private List<MessageField> messageFields;
    @XmlElement(name = "Group")
    private List<Group> groups;

}
