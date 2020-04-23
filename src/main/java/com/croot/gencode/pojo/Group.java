package com.croot.gencode.pojo;


import lombok.Data;

import java.util.List;

@Data
public class Group {
    private String name;
    private String description;
    private List<Field> fields;
    private List<Component> components;
}
