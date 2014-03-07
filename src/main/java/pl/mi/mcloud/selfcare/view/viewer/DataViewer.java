/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mi.mcloud.selfcare.view.viewer;

import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import pl.mi.mcloud.selfcare.view.util.ViewUtils;

/**
 *
 * @author bor
 */
public class DataViewer extends VerticalLayout {

    private final Object object;
    private Map<String, DataObject> map = new HashMap();

    public DataViewer(Object object) throws IllegalArgumentException, IllegalAccessException {
        this.object = object;
        if (object == null) {
            return;
        }
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if(field.get(object)==null) {
                ViewUtils.messageLog(Level.OFF, "field.get(object)==null", "");
                continue;
            }
            Class type = field.getType();
            
            ViewUtils.messageLog(Level.OFF, field.getName() + " " + field.getType().toString(), field.getGenericType().toString());
            if (field.getType().equals(Long.class)) {
                ViewUtils.messageLog(Level.FINEST, "" + field.get(object).toString() + " " + type.toString(), "LONG");
                String name = field.getName();
                Long value = (Long) field.get(object);
                String svalue = String.valueOf(value);
                ViewUtils.messageLog(Level.FINER, name + " " + value + " " + svalue, "!");
                DataObject dataObject = new DataObject(DataType.ID, name,
                        value, 
                        new Label(svalue));
                map.put(field.getName(), dataObject);
            } else if (type.equals(String.class)) {
                ViewUtils.messageLog(Level.FINEST, "" + field.get(object).toString() + " " + type.toString(), "STRING");
                DataObject dataObject = new DataObject(DataType.TEXT, field.getName(),  field.get(object), new Label(String.valueOf(field.get(object))));
                map.put(field.getName(), dataObject);
            } else if (type.equals(Date.class)) {
                ViewUtils.messageLog(Level.FINEST, "" + field.get(object).toString() + " " + type.toString(), "DATE");
                DataObject dataObject = new DataObject(DataType.DATE, field.getName(), field.get(object), new DateField(field.getName(), (Date)field.get(object)));
                map.put(field.getName(), dataObject);
            } else { //if (type.equals(Job.class) || type.equals(type))
                ViewUtils.messageLog(Level.FINEST, "" + field.get(object).toString() + " " + type.toString(), "OBJECT");
                DataObject dataObject = new DataObject(DataType.DATE, field.getName(), field.get(object), new Label(field.get(object).toString()));
                map.put(field.getName(), dataObject);
            }
        }
        for (Map.Entry<String, DataObject> entry : map.entrySet()) {
            HorizontalLayout hl = new HorizontalLayout();
            hl.addComponent(new Label(entry.getKey()));
            hl.addComponent(entry.getValue().getDisplayer());
            this.addComponent(hl);
            ViewUtils.messageLog(Level.FINEST, entry.getKey() + "/" + entry.getValue(), "");            
        }
    }

    public Object getObject() {
        return object;
    }

    public Map<String, DataObject> getMap() {
        return map;
    }

}
