 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mi.mcloud.selfcare.view.viewer; 

import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.mi.mcloud.selfcare.view.util.ViewUtils;

/**
 *
 * @author bor
 */
public class DataViewer<T> extends VerticalLayout {

    private final T object;
    private Map<String, DataObject> map = new HashMap();
    private Map<String, String> alternativeKeyNamesMap = new HashMap();
    private Map<String, Integer> alternativeDataPositions = new HashMap();
//    private Map<String, Boolean> alternativeVisibility = new HashMap();
//    private String titleField;

    public DataViewer(T object) throws IllegalArgumentException {
        this.object = object;
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }

    public Component getDisplay() {
        if (object == null) {
            throw new IllegalArgumentException();
        }
        Field[] fields = object.getClass().getDeclaredFields();
//        Field[] inheritedFields = object.getClass().getSuperclass().getDeclaredFields();
//        ViewUtils.messageLog(Level.OFF, "Object "+object.getClass()+" has number of fields (inhertied from "+object.getClass().getSuperclass()+"): ", Integer.toString(fields.length), Integer.toString(inheritedFields.length));
        for (Field field : fields) {
            try {
                //            //DO NOTHING IF FIELD IS INHERITED FROM SUPERCLASSES
//            for (Field inherited : inheritedFields) {
//                if(field.getName().equals(inherited.getName())) {
//                    ViewUtils.messageLog(Level.OFF, "This field is inherited - ommiting ->", field.getName());
//                    continue;
//                }
//            }

                map.put(field.getName(), new DataObject(field, object));
            } catch (IllegalAccessException ex) {
                ViewUtils.messageLog(Level.WARNING, "", "IllegalAccessException");
            }
        }
        for (Map.Entry<String, DataObject> entry : map.entrySet()) {
            if (entry.getValue().isVisible()) {
                GridLayout grid = new GridLayout(2, map.size());
                int row = 0;
//                HorizontalLayout hl = new HorizontalLayout();
                if (alternativeKeyNamesMap.containsKey(entry.getKey())) {
                    ViewUtils.messageLog(Level.FINEST, entry.getKey(), "RENAMED");
                    grid.addComponent(new Label(alternativeKeyNamesMap.get(entry.getKey())),0,row);
                    
                } else {
                    grid.addComponent(new Label(entry.getKey()),0,row);
                }
//                grid.addComponent(new Label(" : "),1,row);
//            if (entry.getValue().isVisible()) {
//                ViewUtils.messageLog(Level.FINEST, entry.getKey(), " VISIBLE");
                grid.addComponent(entry.getValue().getDisplayer(),1,row);
//            } else {
//                ViewUtils.messageLog(Level.FINEST, entry.getKey(), " INVISIBLE");
//            }
//                ViewUtils.messageLog(Level.FINEST, " PFF " + entry.getKey(), titleField);
//                if (titleField.equals(entry.getKey())) {
//                    ViewUtils.messageLog(Level.FINEST, " addComponentAsFirst " + entry.getKey(), titleField);
//                    this.addComponentAsFirst(hl);
//                } else {
                    this.addComponent(grid);
//                }
                ViewUtils.messageLog(Level.FINEST, entry.getKey() + "/" + entry.getValue(), "");
                row++;
            }
            
        }
        return this;
    }

    public Object getObject() {
        return object;
    }

    public Map<String, DataObject> getMap() {
        return map;
    }

    public Map<String, String> getAlternativeKeyNamesMap() {
        return alternativeKeyNamesMap;
    }

    public void setAlternativeKeyNamesMap(Map<String, String> alternativeKeyNamesMap) {
        this.alternativeKeyNamesMap = alternativeKeyNamesMap;
    }

//    public String getTitleField() {
//        return titleField;
//    }
//
//    public void setTitleField(String titleField) {
//        this.titleField = titleField;
//    }

    public Map<String, Integer> getAlternativeDataPositions() {
        return alternativeDataPositions;
    }

    public void setAlternativeDataPositions(Map<String, Integer> alternativeDataPositions) {
        this.alternativeDataPositions = alternativeDataPositions;
    }

    
}
