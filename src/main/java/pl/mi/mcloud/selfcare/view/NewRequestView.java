/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mi.mcloud.selfcare.view;

import com.vaadin.data.Property;
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
import java.util.logging.Logger;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import mcloud.integration.ldap.client.LdapUserClient;
import pl.mi.mcloud.selfcare.EJBBus;
import static pl.mi.mcloud.selfcare.EJBBus.jobFacade;
import pl.mi.mcloud.selfcare.entity.Complaint;
import pl.mi.mcloud.selfcare.entity.Job;
import pl.mi.mcloud.selfcare.entity.PlatformComponent;
import pl.mi.mcloud.selfcare.entity.PlatformModule;
import pl.mi.mcloud.selfcare.entity.PlatformService;
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

    public NewRequestView() {
        //TODO should be moved
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userLogged", Boolean.TRUE);
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userLogin", Const.AUTOLOGIN_USERNAME);

//        navigator.addView(Const.LOGOUT_VIEW, new LogoutView());
//        navigator.addView(Const.PERSONAL_DATA_VIEW, new PersonalDataView());
        initComponents();
    }

    private void initComponents() {
        VerticalLayout vl = new VerticalLayout(grid);
        Panel light = new Panel("New Request", vl);
        light.addStyleName(Runo.PANEL_LIGHT);
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponent(light);
        layout.setSpacing(true);

        grid.setSpacing(true);

        grid.addComponent(dateCreated);
        dateCreated.setEnabled(false);
        dateCreated.setDateFormat("yyyy-MM-dd");
        dateCreated.setValue(new Date());
        dateAcknowledged.setEnabled(false);
        grid.addComponent(dateAcknowledged);
        dateDue.setEnabled(false);
        grid.addComponent(dateDue);
        dateClosed.setEnabled(false);
        grid.addComponent(dateClosed);
        grid.addComponent(isPlannedWork);
        isPlannedWork.setStyleName("margintop15px");
        isPlannedWork.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (isPlannedWork.getValue()) {
                    plannedWorkStart.setEnabled(true);
                    plannedWorkEnd.setEnabled(true);
                } else {
                    plannedWorkStart.setEnabled(false);
                    plannedWorkEnd.setEnabled(false);
                }
            }
        });
        plannedWorkStart.setEnabled(false);
        plannedWorkEnd.setEnabled(false);
        grid.addComponent(plannedWorkStart);
        grid.addComponent(plannedWorkEnd);
        grid.addComponent(status);

        populateLists();

//    ComboBox combo = new ComboBox("Example", statusContainer);
//    combo.setItemCaptionPropertyId("description");
        grid.addComponent(priority);
        grid.addComponent(creator);
        assignee.setEnabled(false);
        grid.addComponent(assignee);
        grid.addComponent(service);
        grid.addComponent(component);
        grid.addComponent(module);
        grid.addComponent(complaint);

        contents.setWidth(1100, Unit.PIXELS);
        contents.setRows(10);
        contents.setWordwrap(true);

        createRequest.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Job job = new Job();
                job.setDateCreated(dateCreated.getValue());
                if (isPlannedWork.getValue()) {
                    job.setPlannedWorkStart(plannedWorkStart.getValue());
                    job.setPlannedWorkEnd(plannedWorkEnd.getValue());
                }
//                Object itemId = event.getProperty().getValue();
//                BeanItem<?> item = (BeanItem<?>) select.getItem(itemId);
                Status s = (Status) status.getValue();
                job.setStatus(s);
                ViewUtils.messageLog(Level.INFO, status.getValue().toString(), "");
                ViewUtils.messageLog(Level.INFO, s.getId().toString(), "");
                job.setPriority((Priority) priority.getValue());
                job.setCreator((PlatformUser) creator.getValue());
                ViewUtils.messageLog(Level.WARNING, creator.getValue().toString(), " ");
//                ViewUtils.messageLog(Level.INFO, ((PlatformUser) creator.getValue()).getUsername(), " ");
                job.setContents(contents.getValue() + " /");
                job.setTrackList("/");
                job.setComplaint((Complaint) complaint.getValue());
                job.setModule((PlatformModule) module.getValue());
                job.setComponent((PlatformComponent) component.getValue());
                job.setService((PlatformService) service.getValue());
//                try {
                try {
                    jobFacade.create(job);
                } catch (Exception e) {
                    ViewUtils.tripleMessage(Level.WARNING, footer, "Create request failed", "");
//                    for (ConstraintViolation cv : e.getConstraintViolations()) {
//                        ViewUtils.messageLog(Level.SEVERE, cv.getConstraintDescriptor().getAnnotation().annotationType().getName(), "");
                    }
//                } catch (Exception e) {
//                    ViewUtils.messageLog(Level.SEVERE, e.getMessage(), "");
//                }
//                } catch (Exception ex) {
//                    Logger.getLogger(NewRequestView.class.getName()).log(Level.SEVERE, null, ex);
//                }
                ViewUtils.tripleMessage(Level.WARNING, footer, "Request created", "");
            }
        });

        vl.addComponent(contents);
        vl.addComponent(createRequest);
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

    private void populateLists() throws IllegalArgumentException {
        List<Status> statusList = new ArrayList<Status>();
        statusList.addAll(EJBBus.statusFacade.findAll());
        BeanItemContainer<Status> statusContainer = new BeanItemContainer(Status.class, statusList);
        status.setContainerDataSource(statusContainer);
        status.select(statusList.get(0));
        status.setNullSelectionAllowed(false);
        status.setRows(1);

        List<Priority> priorityList = new ArrayList<Priority>();
        priorityList.addAll(EJBBus.priorityFacade.findAll());
        BeanItemContainer<Priority> priorityContainer = new BeanItemContainer(Priority.class, priorityList);
        priority.setContainerDataSource(priorityContainer);
        priority.select(priorityList.get(0));
        priority.setNullSelectionAllowed(false);
        priority.setRows(1);

        String username = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userLogin").toString();

        List<PlatformUser> creatorList = new ArrayList<PlatformUser>();
        creatorList.addAll(EJBBus.platformUserFacade.findByUsername(username));
        ViewUtils.messageLog(Level.INFO, username, " ");
        ViewUtils.messageLog(Level.INFO, Integer.toString(creatorList.size()), " ");
        BeanItemContainer<PlatformUser> creatorContainer = new BeanItemContainer(PlatformUser.class, creatorList);
        creator.setContainerDataSource(creatorContainer);
        creator.select(creatorList.get(0));
//        creator.setNullSelectionItemId(Const.NULL_SELECT_TEXT);
        creator.setNullSelectionAllowed(false);
        creator.setRows(1);
//
        List<PlatformUser> assigneeList = new ArrayList<PlatformUser>();
        assigneeList.addAll(EJBBus.platformUserFacade.findAll());
        BeanItemContainer<PlatformUser> assigneeContainer = new BeanItemContainer(PlatformUser.class, assigneeList);
        assignee.setContainerDataSource(assigneeContainer);
        assignee.setNullSelectionItemId(Const.NULL_SELECT_TEXT);
        assignee.setNullSelectionAllowed(true);
        assignee.setRows(1);

        List<PlatformService> serviceList = new ArrayList<PlatformService>();
        serviceList.addAll(EJBBus.platformServiceFacade.findAll());
        BeanItemContainer<PlatformService> serviceContainer = new BeanItemContainer(PlatformService.class, serviceList);
        service.setContainerDataSource(serviceContainer);
        service.setNullSelectionItemId(Const.NULL_SELECT_TEXT);
        service.setNullSelectionAllowed(true);
        service.setRows(1);

        List<PlatformComponent> componentList = new ArrayList<PlatformComponent>();
        componentList.addAll(EJBBus.platformComponentFacade.findAll());
        BeanItemContainer<PlatformComponent> componentContainer = new BeanItemContainer(PlatformComponent.class, componentList);
        component.setContainerDataSource(componentContainer);
        component.setNullSelectionItemId(Const.NULL_SELECT_TEXT);
        component.setNullSelectionAllowed(true);
        component.setRows(1);

        List<PlatformModule> moduleList = new ArrayList<PlatformModule>();
        moduleList.addAll(EJBBus.platformModuleFacade.findAll());
        BeanItemContainer<PlatformModule> moduleContainer = new BeanItemContainer(PlatformModule.class, moduleList);
        module.setContainerDataSource(moduleContainer);
        module.setNullSelectionItemId(Const.NULL_SELECT_TEXT);
        module.setNullSelectionAllowed(true);
        module.setRows(1);

        List<Complaint> complaintList = new ArrayList<Complaint>();
        complaintList.addAll(EJBBus.complaintFacade.findAll());
        BeanItemContainer<Complaint> complaintContainer = new BeanItemContainer(Complaint.class, complaintList);
        complaint.setContainerDataSource(complaintContainer);
        complaint.setNullSelectionItemId(Const.NULL_SELECT_TEXT);
        complaint.setNullSelectionAllowed(true);
        complaint.setRows(1);
//        complaint.add
    }
}
