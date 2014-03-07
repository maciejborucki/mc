/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.mi.mcloud.selfcare.view.viewer;

import com.vaadin.ui.Component;

/**
 *
 * @author bor
 */
public class DataObject {
    private DataType dataType;
    private String name;
    private Object value;
    private String fkey_object_name;
    private Component displayer;

    public DataObject(DataType dataType, String name, Object value, Component displayer) {
        this.dataType = dataType;
        this.value = value;
        this.displayer = displayer;
    }
    
    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Component getDisplayer() {
        return displayer;
    }

    public void setDisplayer(Component displayer) {
        this.displayer = displayer;
    }

    public String getFkey_object_name() {
        return fkey_object_name;
    }

    public void setFkey_object_name(String fkey_object_name) {
        this.fkey_object_name = fkey_object_name;
    }
    
}
