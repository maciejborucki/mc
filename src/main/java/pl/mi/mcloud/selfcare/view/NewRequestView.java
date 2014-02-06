/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mi.mcloud.selfcare.view;

import com.vaadin.data.util.BeanItemContainer;
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
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import mcloud.integration.ldap.client.LdapUserClient;
import pl.mi.mcloud.selfcare.EJBBus;
import pl.mi.mcloud.selfcare.entity.PlatformUser;
import pl.mi.mcloud.selfcare.entity.Priority;
import pl.mi.mcloud.selfcare.entity.Status;
import pl.mi.mcloud.selfcare.util.Const;
import pl.mi.mcloud.selfcare.view.util.ViewUtils;
import pl.mlife.mcloud.integration.ldap.entity.Password;
import pl.mlife.mcloud.integration.ldap.entity.User;

/**
 *
 * @author bor
 */
public class NewRequestView extends VerticalLayout implements View {

    final LdapUserClient userAPI = new LdapUserClient(Const.BASE_URI, Const.MEDIA_TYPE);
    final AbsoluteLayout footer = new AbsoluteLayout();
    final AbsoluteLayout header = new AbsoluteLayout();
    final VerticalLayout layout = new VerticalLayout();
    final HorizontalLayout menuBar = new MenuView();
    final HorizontalLayout horizontalMainLayout = new HorizontalLayout();
    final VerticalLayout verticalMenuLayout = new VerticalLayout();
    final VerticalLayout verticalMainLayout = new VerticalLayout();
    final Navigator navigator = ViewUtils.getNavigator();

    final GridLayout grid = new GridLayout(5, 6);

    final Button createRequest = new Button("Create Request");
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

    public NewRequestView() {
        //TODO should be moved
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userLogged", Boolean.TRUE);
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userLogin", Const.AUTOLOGIN_USERNAME);

        navigator.addView(Const.LOGOUT_VIEW, new LogoutView());
        navigator.addView(Const.PERSONAL_DATA_VIEW, new PersonalDataView());
        initComponents();
    }

    private void initComponents() {
        Panel light = new Panel("New Request", grid);
        light.addStyleName(Runo.PANEL_LIGHT);
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponent(light);
        layout.setSpacing(true);

        grid.setSpacing(true);

        grid.addComponent(dateCreated);
        dateCreated.setValue(new Date());
        grid.addComponent(dateAcknowledged);
        grid.addComponent(dateDue);
        grid.addComponent(dateClosed);
        grid.addComponent(isPlannedWork);
        grid.addComponent(plannedWorkStart);
        grid.addComponent(plannedWorkEnd);
        grid.addComponent(status);

        List<Status> statusList = new ArrayList<Status>();
        statusList.addAll(EJBBus.statusFacade.findAll());
        BeanItemContainer<Status> statusContainer = new BeanItemContainer(Status.class, statusList);
        status.setContainerDataSource(statusContainer);
        status.setNullSelectionAllowed(false);
        status.setRows(1);

        List<Priority> priorityList = new ArrayList<Priority>();
        priorityList.addAll(EJBBus.priorityFacade.findAll());
        BeanItemContainer<Priority> priorityContainer = new BeanItemContainer(Priority.class, priorityList);
        priority.setContainerDataSource(priorityContainer);
        priority.setNullSelectionAllowed(false);
        priority.setRows(1);

        String username = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userLogin").toString();
        List<PlatformUser> creatorList = new ArrayList<PlatformUser>();
        creatorList.addAll(EJBBus.platformUserFacade.findByUsername(username));
        BeanItemContainer<PlatformUser> creatorContainer = new BeanItemContainer(PlatformUser.class, creatorList);
        creator.setContainerDataSource(creatorContainer);
        creator.setNullSelectionAllowed(false);
        creator.setRows(1);
//
        List<PlatformUser> assigneeList = new ArrayList<PlatformUser>();
        assigneeList.addAll(EJBBus.platformUserFacade.findAll());
        BeanItemContainer<PlatformUser> assigneeContainer = new BeanItemContainer(PlatformUser.class, assigneeList);
        assignee.setContainerDataSource(assigneeContainer);
        assignee.setNullSelectionAllowed(false);
        assignee.setRows(1);  

//    ComboBox combo = new ComboBox("Example", statusContainer);
//    combo.setItemCaptionPropertyId("description");
        grid.addComponent(priority);
        grid.addComponent(creator);
        grid.addComponent(assignee);
        grid.addComponent(service);
        grid.addComponent(component);
        grid.addComponent(module);
        grid.addComponent(complaint);

        grid.addComponent(contents);
        grid.addComponent(createRequest);
        // Create a DateField with the default style

// Set the date and time to present
        createRequest.setStyleName("alignButtonDown");

        grid.addComponent(footer);

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
