/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mi.mcloud.selfcare.view;

//import com.vaadin.addon.jpacontainer.JPAContainer;
//import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.event.Action;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;
import mcloud.integration.ldap.client.LdapUserClient;
import pl.mi.mcloud.selfcare.util.Const;
//import pl.mi.ejb.PlatformUser;
import pl.mi.mcloud.selfcare.view.util.ViewUtils;

/**
 *
 * @author bor
 */
class ComponentListView extends VerticalLayout implements View {

    String username;
    final Navigator navigator = ViewUtils.getNavigator();
    final HorizontalLayout menuBar = new MenuView();
    final AbsoluteLayout footer = new AbsoluteLayout();
    final AbsoluteLayout header = new AbsoluteLayout();
    final VerticalLayout layout = new VerticalLayout();
    final LdapUserClient userAPI = Const.USER_API;
    Table sample = new Table();
//    final GridLayout grid = new GridLayout(5, 6);

    public ComponentListView() {
        initComponents();
    }

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        ViewUtils.checkAuthorization(getUI());
    }

    private void initComponents() {

//        JPAContainer<PlatformUser> users;
//        users = JPAContainerFactory.make(PlatformUser.class,"DB1PU");
//
//        Table personTable = new Table(null, users);
        
        Panel light = new Panel("Component list", sample);
        light.addStyleName(Runo.PANEL_LIGHT);

        layout.setWidth(90, Sizeable.Unit.PERCENTAGE);
        light.setWidth(90, Sizeable.Unit.PERCENTAGE);
        sample.setWidth(90, Sizeable.Unit.PERCENTAGE);

        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponent(light);
        layout.setSpacing(true);
//        grid.setSpacing(true);

        sample.setSizeFull();
        sample.setSelectable(true);
//        sample.setMultiSelect(true);
        sample.setImmediate(true);
//
//        List<User> users = userAPI.findAll(User.class, null, null);

        
        
//        sample.setContainerDataSource(new BeanItemContainer<User>(users));
//        sample.setVisibleColumns(new Object[] {
//                ExampleUtil.iso3166_PROPERTY_NAME,
//                ExampleUtil.iso3166_PROPERTY_SHORT });
//
        sample.setColumnReorderingAllowed(true);
        sample.setColumnCollapsingAllowed(true);
//
//        sample.setColumnHeaders(new String[] { "Country", "Code" });
//
//        sample.setColumnAlignment(ExampleUtil.iso3166_PROPERTY_SHORT,
//                Align.CENTER);
//
//        sample.setColumnExpandRatio(ExampleUtil.iso3166_PROPERTY_NAME, 1);
//        sample.setColumnWidth(ExampleUtil.iso3166_PROPERTY_SHORT, 70);
////
//        sample.setRowHeaderMode(RowHeaderMode.ICON_ONLY);
//        sample.setItemIconPropertyId(ExampleUtil.iso3166_PROPERTY_FLAG);
//
        final Action actionMark = new Action("Mark");
        final Action actionUnmark = new Action("Unmark");
//
        sample.setPageLength(3);

        //Filter filter = new SimpleStringFilter("name",
        //        "Douglas", true, false);
        //sample.addContainerFilter(filter);
//        sample.addActionHandler(new Action.Handler() {
//            @Override
//            public Action[] getActions(final Object target, final Object sender) {
//                if (markedRows.contains(target)) {
//                    return new Action[] { actionUnmark };
//                } else {
//                    return new Action[] { actionMark };
//                }
//            }
//
//            @Override
//            public void handleAction(final Action action, final Object sender,
//                    final Object target) {
//                if (actionMark == action) {
//                    markedRows.add(target);
//                } else if (actionUnmark == action) {
//                    markedRows.remove(target);
//                }
//                sample.markAsDirtyRecursive();
//                Notification.show("Marked rows: " + markedRows,
//                        Type.TRAY_NOTIFICATION);
//            }
//
//        });
//
//        sample.setCellStyleGenerator(new CellStyleGenerator() {
//            @Override
//            public String getStyle(final Table source, final Object itemId,
//                    final Object propertyId) {
//                String style = null;
//                if (propertyId == null && markedRows.contains(itemId)) {
//                    // no propertyId, styling a row
//                    style = "marked";
//                }
//                return style;
//            }
//
//        });
// 
//
//        sample.addValueChangeListener(new ValueChangeListener() {
//            @Override
//            public void valueChange(final ValueChangeEvent event) {
//                final String valueString = String.valueOf(event.getProperty()
//                        .getValue());
//                Notification.show("Value changed:", valueString,
//                        Type.TRAY_NOTIFICATION);
//            }
//        });
//        light.setWidth(sample.getWidth(), Sizeable.Unit.PIXELS);
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
