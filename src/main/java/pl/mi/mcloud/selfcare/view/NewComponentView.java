/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mi.mcloud.selfcare.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import mcloud.integration.ldap.client.LdapUserClient;
import pl.mi.mcloud.selfcare.util.Const;
import pl.mi.mcloud.selfcare.view.util.ViewUtils;

/**
 *
 * @author bor
 */
class NewComponentView extends VerticalLayout implements View {

    
    String username;
    final Navigator navigator = ViewUtils.getNavigator();
    final HorizontalLayout menuBar = new MenuView();
    final AbsoluteLayout footer = new AbsoluteLayout();
    final AbsoluteLayout header = new AbsoluteLayout();
    final VerticalLayout layout = new VerticalLayout();
    final LdapUserClient userAPI = Const.USER_API;
    final TextField componentNameField = new TextField("Component Unique Name");

    final ListSelect componentTypeSelect = new ListSelect("Component Type");

    Button componentCreateButton = new Button("Create component");

    final Map<String, ValidComponent> validComponentMap1 = new HashMap<String, ValidComponent>();

    final GridLayout grid = new GridLayout(5, 6);

    public NewComponentView() {
        initComponents();
    }

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        ViewUtils.checkAuthorization(getUI());
    }

    private void initComponents() {

        Panel light = new Panel("Component Creation", grid);
        light.addStyleName(Runo.PANEL_LIGHT);
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponent(light);
        layout.setSpacing(true);
        grid.setSpacing(true);

        componentTypeSelect.setNullSelectionAllowed(false);
        componentTypeSelect.addItem(Const.COMPONENT_TYPE_1);
        componentTypeSelect.select(Const.COMPONENT_TYPE_1);
        componentTypeSelect.setRows(1);
        
        componentCreateButton.setStyleName("alignButtonDown");
        componentCreateButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                ViewUtils.tripleMessage(Level.INFO, footer, "Component has been created - ", 
                        componentTypeSelect.getValue().toString(), " ",
                        componentNameField.getValue().toString());
            }
        });
        grid.addComponent(componentNameField);
//        armTextField(componentNameField, validComponentMap1, componentCreateButton, 
//                "Provide a unique component name - 4-32 alphanumeric characters required", 
//                grid, new RegexpValidator("^[0-9a-zA-Z-_]{4,32}$", "!"));
        grid.addComponent(componentTypeSelect);
        grid.addComponent(componentCreateButton);
        grid.addComponent(new Label(""));
        grid.addComponent(new Label(""));

        light.setWidth(grid.getWidth(), Sizeable.Unit.PIXELS);

        ViewUtils.generateHeaderFooter(header, footer);

        this.setSizeFull();
        layout.setWidth(100, Sizeable.Unit.PERCENTAGE);
        ViewUtils.attachHeader(this, header);
        this.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        this.addComponent(menuBar);
        this.setExpandRatio(menuBar, 5);

        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        this.addComponent(layout);
        this.setExpandRatio(layout, 75);
        ViewUtils.attachFooter(this, footer);
    }
    
    
}
