package com.croot.gencode.pojo.message;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "Message")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Message {
    @XmlAttribute(name = "Name")
    private String name;
    @XmlAttribute(name = "Code")
    private Long code;
    @XmlAttribute(name = "Description")
    private String Description;
    @XmlAttribute(name = "WebService")
    private String webService;
    @XmlElement(name = "Field")
    private List<MessageField> fieldList;
    @XmlElement(name = "Group")
    private List<Group> groupList;
    @XmlElement(name = "Component")
    private List<Component> componentList;
}
