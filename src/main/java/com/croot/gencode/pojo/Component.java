package com.croot.gencode.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Component {
    private List<Field> fields;
    private List<Group> groups;

}
