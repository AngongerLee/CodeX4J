package com.croot.gencode.pojo.message;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "Fields")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Fields {
    @XmlElement(name = "Field")
    private List<MessageField> fieldList;
}
