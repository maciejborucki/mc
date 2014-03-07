/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mi.mcloud.selfcare.view.viewer;

import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import pl.mi.mcloud.selfcare.view.util.ViewUtils;

/**
 *
 * @author bor
 */
public class DataViewer<T> extends VerticalLayout {

    private final T object;
    private Map<String, DataObject> map = new HashMap();
    private Map<String, String> alternativeKeyNamesMap = new HashMap();
//    private Map<String, String> alternativeKeyNamesMap = new HashMap();
    private String titleField;

    public DataViewer(T object) throws IllegalArgumentException {
        this.object = object;
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }
    
    public Component getDisplay()  throws IllegalArgumentException, IllegalAccessException {
        if (object == null) {
             throw new IllegalArgumentException();
        }
        Field[] fields = object.getClass().getDeclaredFields();
//        Field[] inheritedFields = object.getClass().getSuperclass().getDeclaredFields();
//        ViewUtils.messageLog(Level.OFF, "Object "+object.getClass()+" has number of fields (inhertied from "+object.getClass().getSuperclass()+"): ", Integer.toString(fields.length), Integer.toString(inheritedFields.length));
        for (Field field : fields) {
//            //DO NOTHING IF FIELD IS INHERITED FROM SUPERCLASSES
//            for (Field inherited : inheritedFields) {
//                if(field.getName().equals(inherited.getName())) {
//                    ViewUtils.messageLog(Level.OFF, "This field is inherited - ommiting ->", field.getName());
//                    continue;
//                }
//            }

            field.setAccessible(true);
            String name = field.getName();

            if (field.getName().startsWith("_")) {
                ViewUtils.messageLog(Level.OFF, "This field is artificially generated - ommiting ->", field.getName());
                continue;
            }
            if (field.get(object) == null) {
                ViewUtils.messageLog(Level.OFF, "field.get(object)==null", "");
                DataObject dataObject = new DataObject(DataType.ID, name,
                        null,
                        new Label(" empty "));
                map.put(field.getName(), dataObject);
                continue;
            }
            Class type = field.getType();

            ViewUtils.messageLog(Level.OFF, field.getName() + " " + field.getType().toString(), field.getGenericType().toString());
            if (field.getType().equals(Long.class)) {
                ViewUtils.messageLog(Level.FINEST, "" + field.get(object).toString() + " " + type.toString(), "LONG");
                Long value = (Long) field.get(object);
                String svalue = String.valueOf(value);
                ViewUtils.messageLog(Level.FINER, name + " " + value + " " + svalue, "!");
                DataObject dataObject = new DataObject(DataType.ID, name,
                        value,
                        new Label(svalue));
                map.put(field.getName(), dataObject);
            } else if (type.equals(String.class)) {
                ViewUtils.messageLog(Level.FINEST, "" + field.get(object).toString() + " " + type.toString(), "STRING");
                DataObject dataObject = new DataObject(DataType.TEXT, field.getName(), field.get(object), new Label(String.valueOf(field.get(object))));
                map.put(field.getName(), dataObject);
            } else if (type.equals(Date.class)) {
                ViewUtils.messageLog(Level.FINEST, "" + field.get(object).toString() + " " + type.toString(), "DATE");
                SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-mm-dd");
                DataObject dataObject = new DataObject(DataType.DATE, field.getName(), field.get(object), new Label(dt1.format(field.get(object))));
                map.put(field.getName(), dataObject);
            } else { //if (type.equals(Job.class) || type.equals(type))
                ViewUtils.messageLog(Level.FINEST, "" + field.get(object).toString() + " " + type.toString(), "OBJECT");
                DataObject dataObject = new DataObject(DataType.FKEY, field.getName(), field.get(object), new Label(field.get(object).toString()));
                map.put(field.getName(), dataObject);
            }
//            map.put(field.getName(), new DataObject(field));
        }
        for (Map.Entry<String, DataObject> entry : map.entrySet()) {
            HorizontalLayout hl = new HorizontalLayout();
            if (alternativeKeyNamesMap.containsKey(entry.getKey())) {
                ViewUtils.messageLog(Level.FINEST, entry.getKey(), "RENAMED");
                hl.addComponent(new Label(alternativeKeyNamesMap.get(entry.getKey())));
            } else {
                hl.addComponent(new Label(entry.getKey()));
            }
            hl.addComponent(new Label(" : "));
            hl.addComponent(entry.getValue().getDisplayer());
            ViewUtils.messageLog(Level.FINEST, " PFF "+  entry.getKey(), titleField);
            if (titleField.equals(entry.getKey())) {
                ViewUtils.messageLog(Level.FINEST, " addComponentAsFirst "+  entry.getKey(), titleField);
                this.addComponentAsFirst(hl);
            } else {
                this.addComponent(hl);
            }
            ViewUtils.messageLog(Level.FINEST, entry.getKey() + "/" + entry.getValue(), "");
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

    public String getTitleField() {
        return titleField;
    }

    public void setTitleField(String titleField) {
        this.titleField = titleField;
    }

}
