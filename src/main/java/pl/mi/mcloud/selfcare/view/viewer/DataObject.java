/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mi.mcloud.selfcare.view.viewer;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import pl.mi.mcloud.selfcare.view.util.ViewUtils;

/**
 *
 * @author bor
 */
public class DataObject {

    private boolean visible = true;
    private DataType dataType;
    private String name;
    private Object value;
//    private String fkey_object_name;
    private Component displayer;

    

//    private DataObject(DataType dataType, String name, Object value, Component displayer) {
//        this.dataType = dataType;
//        this.value = value;
//        this.displayer = displayer;
//    }

    public DataObject(Field field, Object object) throws IllegalAccessException {
        field.setAccessible(true);
        name = field.getName();
        if (field.getName().startsWith("_") || field.getName().startsWith("serialVersionUID")) {
            ViewUtils.messageLog(Level.FINE, "This field is artificially generated - ommiting ->", name);
            visible = false;
            return;
        }
        if (field.get(object) == null) {
            ViewUtils.messageLog(Level.FINE, "Field value for this object is null", object.toString(), field.getName());
//            dataType = DataType.UNDEFINED;
            name = field.getName();
            value = "empty";
//            fkey_object_name = null;
            displayer = new Label(value.toString());
            return;
        } else {
//            Class type = field.getType();
            ViewUtils.messageLog(Level.FINE, field.getName() + " " + field.getType().toString(), field.getGenericType().toString());
//            dataType = DataType.UNDEFINED;
            name = field.getName();
            value = field.get(object);
//            fkey_object_name = null;
            displayer = new Label(value.toString());
//            if (field.getType().equals(Long.class)) {
//                
//            }
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        

        
        
//        Class type = field.getType();
//
//        ViewUtils.messageLog(Level.OFF, field.getName() + " " + field.getType().toString(), field.getGenericType().toString());
//        if (field.getType().equals(Long.class)) {
//            ViewUtils.messageLog(Level.FINEST, "" + field.get(object).toString() + " " + type.toString(), "LONG");
//            Long value = (Long) field.get(object);
//            String svalue = String.valueOf(value);
//            ViewUtils.messageLog(Level.FINER, name + " " + value + " " + svalue, "!");
//            DataObject dataObject = new DataObject(DataType.ID, name,
//                    value,
//                    new Label(svalue));
////                map.put(field.getName(), dataObject);
//        } else if (type.equals(String.class)) {
//            ViewUtils.messageLog(Level.FINEST, "" + field.get(object).toString() + " " + type.toString(), "STRING");
//            DataObject dataObject = new DataObject(DataType.TEXT, field.getName(), field.get(object), new Label(String.valueOf(field.get(object))));
////                map.put(field.getName(), dataObject);
//        } else if (type.equals(Date.class)) {
//            ViewUtils.messageLog(Level.FINEST, "" + field.get(object).toString() + " " + type.toString(), "DATE");
//            SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-mm-dd");
//            DataObject dataObject = new DataObject(DataType.DATE, field.getName(), field.get(object), new Label(dt1.format(field.get(object))));
////                map.put(field.getName(), dataObject);
//        } else { //if (type.equals(Job.class) || type.equals(type))
//            ViewUtils.messageLog(Level.FINEST, "" + field.get(object).toString() + " " + type.toString(), "OBJECT");
//            DataObject dataObject = new DataObject(DataType.FKEY, field.getName(), field.get(object), new Label(field.get(object).toString()));
//            map.put(field.getName(), dataObject);
//        }
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
    
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getFkey_object_name() {
//        return fkey_object_name;
//    }
//
//    public void setFkey_object_name(String fkey_object_name) {
//        this.fkey_object_name = fkey_object_name;
//    }

}
