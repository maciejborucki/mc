package sc.main.view;

///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package pl.mi.mcloud.selfcare.view;
//
//import com.vaadin.data.Property;
//import com.vaadin.data.util.BeanItemContainer;
//import com.vaadin.navigator.Navigator;
//import com.vaadin.navigator.View;
//import com.vaadin.navigator.ViewChangeListener;
//import com.vaadin.server.Sizeable;
//import com.vaadin.server.VaadinService;
//import com.vaadin.ui.AbsoluteLayout;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.DateField;
//import com.vaadin.ui.GridLayout;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.ListSelect;
//import com.vaadin.ui.Panel;
//import com.vaadin.ui.PopupDateField;
//import com.vaadin.ui.TextArea;
//import com.vaadin.ui.VerticalLayout;
//import com.vaadin.ui.themes.Runo;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.logging.Level;
//import sc.main.EJB;
//import pl.mi.mcloud.selfcare.entity.Complaint;
//import pl.mi.mcloud.selfcare.entity.PlatformUserX;
//import pl.mi.mcloud.selfcare.entity.Priority;
//import pl.mi.mcloud.selfcare.entity.Status;
//import pl.mi.mcloud.selfcare.util.Const;
//import pl.mi.mcloud.selfcare.view.util.EJBBus;
//import pl.mi.mcloud.selfcare.view.util.ViewUtils;
//
///**
// *
// * @author bor
// */
//public class NewComplaintView extends VerticalLayout implements View {
//
//    final AbsoluteLayout footer = new AbsoluteLayout();
//    final AbsoluteLayout header = new AbsoluteLayout();
//    final VerticalLayout layout = new VerticalLayout();
//    final HorizontalLayout menuBar = new MenuView();
//    final HorizontalLayout horizontalMainLayout = new HorizontalLayout();
//    final VerticalLayout verticalMenuLayout = new VerticalLayout();
//    final VerticalLayout verticalMainLayout = new VerticalLayout();
//    final Navigator navigator = ViewUtils.getNavigator();
//    final GridLayout grid = new GridLayout(5, 6);
//
//    final Button createComplaint = new Button("Create");
//    final TextArea contents = new TextArea("Content");
//    final DateField dateCreated = new PopupDateField("Complaint creation date");
//    final DateField dateAcknowledged = new PopupDateField("Complaint assigned date");
//    final DateField dateDue = new PopupDateField("Complaint deadline");
//    final DateField dateClosed = new PopupDateField("Complaint closing date");
//    final ListSelect status = new ListSelect("Complaint status");
//    final ListSelect priority = new ListSelect("Complaint priority");
//    final ListSelect creator = new ListSelect("Complaint created by");
//
//    @Override
//    public void enter(ViewChangeListener.ViewChangeEvent event) {
//    }
//
//    public NewComplaintView() {
//        //TODO should be moved
//        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userLogged", Boolean.TRUE);
//        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userLogin", Const.AUTOLOGIN_USERNAME);
//        initComponents();
//    }
//
//    private void initComponents() {
//        VerticalLayout vl = new VerticalLayout(grid);
//        Panel light = new Panel("New complaint", vl);
//        light.addStyleName(Runo.PANEL_LIGHT);
//        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
//        layout.addComponent(light);
//        layout.setSpacing(true);
//
//        grid.setSpacing(true);
//
//        grid.addComponent(dateCreated);
//        dateCreated.setEnabled(false);
//        dateCreated.setDateFormat("yyyy-MM-dd");
//        dateCreated.setValue(new Date());
//        dateAcknowledged.setEnabled(false);
//        grid.addComponent(dateAcknowledged);
//        dateDue.setEnabled(false);
//        dateDue.setDateFormat("yyyy-MM-dd");
//        Date dt = new Date();
//        dateDue.setValue(dt);
//        grid.addComponent(dateDue);
//        dateClosed.setEnabled(false);
//        grid.addComponent(dateClosed);
//        
//        status.setEnabled(false);
//        grid.addComponent(status);
//        
//        populateLists();
//
//        grid.addComponent(priority);
//        
//        priority.setImmediate(true);
//        priority.addValueChangeListener(new Property.ValueChangeListener() {
//
//            @Override
//            public void valueChange(Property.ValueChangeEvent event) {
//                Calendar c = Calendar.getInstance();
//                Date dt = new Date();
//                c.setTime(dt);
//                Priority selectedPriority = (Priority) priority.getValue();
//                Integer d = EJB.priorityFacade.find(selectedPriority.getId()).getComplaintDeadlineDays();
//                c.add(Calendar.DATE, d);
//                dt = c.getTime();
//                dateDue.setValue(dt); 
//                dateDue.markAsDirty();
//                ViewUtils.messageLog(Level.FINEST, "NewComplaintView PriorityValueChanged ", selectedPriority.getPriorityName(), d.toString(), dt.toString());
//            }
//        });
//        
//        creator.setEnabled(false);
//        grid.addComponent(creator);
//
//        contents.setWidth(1100, Unit.PIXELS);
//        contents.setRows(10);
//        contents.setWordwrap(true);
//
//        createComplaint.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                Complaint complaint = new Complaint();
//                complaint.setDateCreated(dateCreated.getValue());
//
//                Status s = (Status) status.getValue();
//                complaint.setStatus(s);
//                ViewUtils.messageLog(Level.INFO, status.getValue().toString(), "");
//                ViewUtils.messageLog(Level.INFO, s.getId().toString(), "");
//                complaint.setPriority((Priority) priority.getValue());
//                complaint.setCreator((PlatformUserX) creator.getValue());
//                ViewUtils.messageLog(Level.WARNING, creator.getValue().toString(), " ");
////                ViewUtils.messageLog(Level.INFO, ((PlatformUser) creator.getValue()).getUsername(), " ");
//                complaint.setContents(contents.getValue() + " /");
//                complaint.setTrackList("/");
//
//                try {
//                    EJB.complaintFacade.create(complaint);
//                } catch (Exception e) {
//                    ViewUtils.tripleMessage(Level.WARNING, footer, "Create complaint failed", "");
//                }
//                ViewUtils.tripleMessage(Level.WARNING, footer, "complaint created", "");
//            }
//        });
//        vl.addComponent(contents);
//        vl.addComponent(createComplaint);
//        createComplaint.setStyleName("alignButtonDown");
//
//        grid.addComponent(footer);
//        light.setWidth(grid.getWidth(), Sizeable.Unit.PIXELS);
//        ViewUtils.generateHeaderFooter(header, footer);
//        this.setSizeFull();
//        layout.setWidth(100, Sizeable.Unit.PERCENTAGE);
//        ViewUtils.attachHeader(this, header);
//        this.setDefaultComponentAlignment(Alignment.TOP_LEFT);
//        this.addComponent(menuBar);
//        this.setExpandRatio(menuBar, 5);
//        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
//        this.addComponent(layout);
//        this.setExpandRatio(layout, 75);
//        ViewUtils.attachFooter(this, footer);
//    }
//
//    private void populateLists() throws IllegalArgumentException {
//        List<Status> statusList = new ArrayList<Status>();
//        statusList.addAll(EJB.statusFacade.findAll());
//        BeanItemContainer<Status> statusContainer = new BeanItemContainer(Status.class, statusList);
//        status.setContainerDataSource(statusContainer);
//        Status defaultStatus = EJB.statusFacade.findByStatusName(Const.DEFAULT_STATUS_STRING);
//        status.select(statusList.indexOf(defaultStatus));
//        status.setNullSelectionAllowed(false);
//        status.setRows(1);
//
//        List<Priority> priorityList = new ArrayList<Priority>();
//        priorityList.addAll(EJB.priorityFacade.findAll());
//        BeanItemContainer<Priority> priorityContainer = new BeanItemContainer(Priority.class, priorityList);
//        priority.setContainerDataSource(priorityContainer);
//        Priority defaultPriority = EJB.priorityFacade.findByPriorityName(Const.DEFAULT_PRIORITY_STRING);
//        priority.select(priorityList.indexOf(defaultPriority));
//        priority.setNullSelectionAllowed(false);
//        priority.setRows(1);
//        ViewUtils.messageLog(Level.FINEST, "NewComplaintView populated priority with default value ", defaultPriority.getPriorityName());
//        Calendar c = Calendar.getInstance();
//        Date dt = new Date();
//        c.setTime(dt);
//        Integer d = defaultPriority.getComplaintDeadlineDays();
//        c.add(Calendar.DATE, d);
//        dt = c.getTime();
//        dateDue.setValue(dt);
//        dateDue.markAsDirty();
//        ViewUtils.messageLog(Level.FINEST, "NewComplaintView Set dueDate according to default priority  ", defaultPriority.getPriorityName(), d.toString(), dt.toString());
//
//        String username = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userLogin").toString();
//
//        List<PlatformUserX> creatorList = new ArrayList<PlatformUserX>();
//        creatorList.addAll(EJBBus.platformUserFacade.findByUsername(username));
//        ViewUtils.messageLog(Level.INFO, username, " ");
//        ViewUtils.messageLog(Level.INFO, Integer.toString(creatorList.size()), " ");
//        BeanItemContainer<PlatformUserX> creatorContainer = new BeanItemContainer(PlatformUserX.class, creatorList);
//        creator.setContainerDataSource(creatorContainer);
//        creator.select(creatorList.get(0));
//        creator.setNullSelectionAllowed(false);
//        creator.setRows(1);
//    }
//}

import pl.mi.mcloud.selfcare.view.*;

