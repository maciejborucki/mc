/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mi.mcloud.selfcare.view;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.CellStyleGenerator;
import com.vaadin.ui.Table.RowHeaderMode;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import mcloud.integration.ldap.client.LdapUserClient;
import pl.mi.mcloud.selfcare.EJBBus;
import pl.mi.mcloud.selfcare.entity.Job;
import pl.mi.mcloud.selfcare.entity.PlatformUser;
import pl.mi.mcloud.selfcare.util.Const;
import pl.mi.mcloud.selfcare.view.util.ViewUtils;

/**
 *
 * @author bor
 */
//    @Theme("pagedtabletheme")
public class ListRequestView extends VerticalLayout implements View {

    final LdapUserClient userAPI = new LdapUserClient(Const.BASE_URI, Const.MEDIA_TYPE);
    final AbsoluteLayout footer = new AbsoluteLayout();
    final AbsoluteLayout header = new AbsoluteLayout();
    final VerticalLayout layout = new VerticalLayout();
    final HorizontalLayout menuBar = new MenuView();
    final HorizontalLayout horizontalMainLayout = new HorizontalLayout();
    final VerticalLayout verticalMenuLayout = new VerticalLayout();
    final VerticalLayout verticalMainLayout = new VerticalLayout();
    final Navigator navigator = ViewUtils.getNavigator();

//    final GridLayout grid = new GridLayout(5, 6);
    final Button createRequest = new Button("Create");
    final TextArea contents = new TextArea("Content");
    final DateField dateCreated = new PopupDateField("Request creation date");
    final DateField dateAcknowledged = new PopupDateField("Request assigned date");
    final DateField dateDue = new PopupDateField("Request deadline");
    final DateField dateClosed = new PopupDateField("Request closing date");
    final CheckBox isPlannedWork = new CheckBox("Request is a planned work event");
    final DateField plannedWorkStart = new PopupDateField("Planned work start date");
    final DateField plannedWorkEnd = new PopupDateField("Planned work end date");
    final ListSelect status = new ListSelect("Request status");
    final ListSelect priority = new ListSelect("Request priority");
    final ListSelect creator = new ListSelect("Request created by");
    final ListSelect assignee = new ListSelect("Request executor");
    final ListSelect service = new ListSelect("Request is related to specific service");
    final ListSelect component = new ListSelect("Request is related to specific component");
    final ListSelect module = new ListSelect("Request is related to specific module");
    final ListSelect complaint = new ListSelect("Request is tied to complaint");

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //TODO Uncomment this shit on prod
//        ViewUtils.checkAuthorization(getUI());
    }

    public ListRequestView() {
        //TODO should be moved
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userLogged", Boolean.TRUE);
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userLogin", Const.AUTOLOGIN_USERNAME);
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userId", Const.AUTOLOGIN_ID);

//        navigator.addView(Const.LOGOUT_VIEW, new LogoutView());
//        navigator.addView(Const.PERSONAL_DATA_VIEW, new PersonalDataView());
        initComponents();
    }

    private void initComponents() {
        VerticalLayout vl = new VerticalLayout();
        Panel light = new Panel("Request List", vl);
        light.addStyleName(Runo.PANEL_LIGHT);
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponent(light);
        layout.setSpacing(true);
        vl.setSpacing(true);

        vl.addComponent(buildTable());

//        light.setWidth(vl.getWidth(), Sizeable.Unit.PIXELS);
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



    private Table buildTable() {
        final HashSet markedRows = new HashSet<Object>();
        final Table jobTable = new Table();
        jobTable.setSizeFull();
        jobTable.setSelectable(true);
        jobTable.setMultiSelect(true);
        jobTable.setImmediate(true);
        jobTable.setPageLength(10);
        List<Job> jobList = new ArrayList<Job>();
        Long uid = Long.parseLong(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userId").toString());
        PlatformUser platformUser = EJBBus.platformUserFacade.find(uid);
        jobList.addAll(EJBBus.jobFacade.findUserJobs(platformUser));
        BeanItemContainer<Job> jobContainer = new BeanItemContainer(Job.class, jobList);
        jobTable.setContainerDataSource(jobContainer);

//        jobTable.setContainerDataSource(ExampleUtil.getISO3166Container());
//        jobTable.setVisibleColumns(new Object[] {
//                ExampleUtil.iso3166_PROPERTY_NAME,
//                ExampleUtil.iso3166_PROPERTY_SHORT });
        jobTable.setColumnReorderingAllowed(true);
        jobTable.setColumnCollapsingAllowed(true);

//        jobTable.setColumnHeaders(new String[] { "Country", "Code" });
//
//        jobTable.setColumnAlignment(ExampleUtil.iso3166_PROPERTY_SHORT,
//                Align.CENTER);
//
//        jobTable.setColumnExpandRatio(ExampleUtil.iso3166_PROPERTY_NAME, 1);
//        jobTable.setColumnWidth(ExampleUtil.iso3166_PROPERTY_SHORT, 70);
        jobTable.setRowHeaderMode(RowHeaderMode.ICON_ONLY);
//        jobTable.setItemIconPropertyId(ExampleUtil.iso3166_PROPERTY_FLAG);

        final Action actionMark = new Action("Mark");
        final Action actionUnmark = new Action("Unmark");

        jobTable.addActionHandler(new Action.Handler() {
            @Override
            public Action[] getActions(final Object target, final Object sender) {
                if (markedRows.contains(target)) {
                    return new Action[]{actionUnmark};
                } else {
                    return new Action[]{actionMark};
                }
            }

            @Override
            public void handleAction(final Action action, final Object sender,
                    final Object target) {
                if (actionMark == action) {
                    markedRows.add(target);
                } else if (actionUnmark == action) {
                    markedRows.remove(target);
                }
                jobTable.markAsDirtyRecursive();
                Notification.show("Marked rows: " + markedRows,
                        Type.TRAY_NOTIFICATION);
            }

        });

        jobTable.setCellStyleGenerator(new CellStyleGenerator() {
            @Override
            public String getStyle(final Table source, final Object itemId,
                    final Object propertyId) {
                String style = null;
                if (propertyId == null && markedRows.contains(itemId)) {
                    // no propertyId, styling a row
                    style = "marked";
                }
                return style;
            }

        });

        jobTable.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(final ValueChangeEvent event) {
                final String valueString = String.valueOf(event.getProperty()
                        .getValue());
                Notification.show("Value changed:", valueString,
                        Type.TRAY_NOTIFICATION);
            }
        });
        return jobTable;
    }

}
