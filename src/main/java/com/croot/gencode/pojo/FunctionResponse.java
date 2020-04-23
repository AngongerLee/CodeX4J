package com.croot.gencode.pojo;

import com.croot.gencode.annocation.NotThreadSafe;
import lombok.Data;

import java.util.List;

@NotThreadSafe
@Data
public class FunctionResponse {
    private String code;
    private String name;
    private String description;
    private List<Field> fields;
    private Group group;
    private boolean hasGroup;

    /**
     * 是否拥有group
     *
     * @return
     */
    public boolean hasGroup() {
        if (this.group == null) {
            return false;
        }
        if (this.group.getFields() == null) {
            return false;
        }
        if (this.group.getFields().size() == 0) {
            return false;
        }
        return true;
    }
}
