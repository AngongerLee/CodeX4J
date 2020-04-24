package com.croot.gencode.pojo;

import com.croot.gencode.pojo.message.Component;
import com.croot.gencode.pojo.message.Group;
import com.croot.gencode.pojo.message.MessageField;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "")
public class FunctionRequest {

    private String code;
    private String name;
    private String description;
    private List<MessageField> messageFields;
    private List<Group> groups;
    private List<Component> components;
}
