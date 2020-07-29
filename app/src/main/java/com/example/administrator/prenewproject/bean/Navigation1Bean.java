package com.example.administrator.prenewproject.bean;

public class Navigation1Bean {


    private String name;

    private boolean isSelect=false;

    public Navigation1Bean(String name, boolean isSelect) {
        this.name = name;
        this.isSelect = isSelect;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
