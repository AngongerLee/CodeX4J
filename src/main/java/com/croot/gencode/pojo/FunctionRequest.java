package com.croot.gencode.pojo;

import lombok.Data;

import java.util.List;

@Data
public class FunctionRequest {

    private String code;
    private String name;
    private String description;
    private List<Field> fields;
}
