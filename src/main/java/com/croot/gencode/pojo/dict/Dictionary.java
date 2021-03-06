package com.croot.gencode.pojo.dict;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "Dictionary")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Dictionary {
    @XmlElement(name = "Field")
    private List<DictField> dictFieldList;
}
