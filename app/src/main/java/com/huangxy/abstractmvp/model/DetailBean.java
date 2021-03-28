package com.huangxy.abstractmvp.model;

import java.io.Serializable;

public class DetailBean implements Serializable {

    private static final long serialVersionUID = -948256767683905130L;

    public String type;
    public String index;

    public DetailBean() {

    }

    public DetailBean(String type, String index) {
        this.index = index;
        this.type = type;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
