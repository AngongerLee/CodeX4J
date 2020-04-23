package com.croot.gencode.pojo;

import com.croot.gencode.enums.TypeEnum;
import lombok.Data;

@Data
public class DictField extends Field{
    private int number;
    private String typeStr;
    private TypeEnum type;


}
