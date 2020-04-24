package com.croot.gencode.pojo;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "")
public class FunctionRequest {

    private String code;
    private String name;
    private String description;
    private List<Field> fields;
    private List<Group> groups;
    private List<Component> components;
}
