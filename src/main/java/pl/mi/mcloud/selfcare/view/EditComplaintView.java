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
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import static pl.mi.mcloud.selfcare.EJBBus.complaintFacade;
import static pl.mi.mcloud.selfcare.EJBBus.platformUserFacade;
import static pl.mi.mcloud.selfcare.EJBBus.priorityFacade;
import static pl.mi.mcloud.selfcare.EJBBus.statusFacade;
import pl.mi.mcloud.selfcare.entity.Complaint;
import pl.mi.mcloud.selfcare.entity.PlatformUser;
import pl.mi.mcloud.selfcare.entity.Priority;
import pl.mi.mcloud.selfcare.entity.Status;
import pl.mi.mcloud.selfcare.view.util.ViewUtils;

/**
 *
 * @author bor
 */
public class EditComplaintView extends VerticalLayout implements View {

//    final LdapUserClient userAPI = new LdapUserClient(Const.BASE_URI, Const.MEDIA_TYPE);
    final AbsoluteLayout footer = new AbsoluteLayout();
    final AbsoluteLayout header = new AbsoluteLayout();
    final VerticalLayout layout = new VerticalLayout();
    final HorizontalLayout menuBar = new MenuView();
    final HorizontalLayout horizontalMainLayout = new HorizontalLayout();
    final VerticalLayout verticalMenuLayout = new VerticalLayout();
    final VerticalLayout verticalMainLayout = new VerticalLayout();
    final Navigator navigator = ViewUtils.getNavigator();

    final GridLayout grid = new GridLayout(5, 6);

    final Button saveComplaint = new Button("Save");
    final TextArea contents = new TextArea("Content");
    final DateField dateCreated = new PopupDateField("Complaint creation date");
    final DateField dateAcknowledged = new PopupDateField("Complaint assigned date");
    final DateField dateDue = new PopupDateField("Complaint deadline");
    final DateField dateClosed = new PopupDateField("Complaint closing date");
    final ListSelect status = new ListSelect("Complaint status");
    final ListSelect priority = new ListSelect("Complaint priority");
    final ListSelect creator = new ListSelect("Complaint created by");

    private Complaint complaintToEdit;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
            Long jid = Long.parseLong(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("complaintId").toString());
            complaintToEdit = complaintFacade.find(jid);
            populateFields(complaintToEdit);
        } catch (NumberFormatException nfe) {
            ViewUtils.tripleMessage(Level.WARNING, footer, "Object does not exist", "");
        }
    }

    private void populateFields(Complaint complaint) {
        contents.setValue(complaint.getContents());
        dateCreated.setValue(complaint.getDateCreated());
        dateAcknowledged.setValue(complaint.getDateAcknowledged());
        dateDue.setValue(complaint.getDateDue());
        dateClosed.setValue(complaint.getDateClosed());
        status.setValue(complaint.getStatus());
        priority.setValue(complaint.getPriority());
        creator.setValue(complaint.getCreator());
    }

    public EditComplaintView() {
        initComponents();
    }

    private void initComponents() {
        VerticalLayout vl = new VerticalLayout(grid);
        Panel light = new Panel("Edit complaint", vl);
        light.addStyleName(Runo.PANEL_LIGHT);
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponent(light);
        layout.setSpacing(true);

        grid.setSpacing(true);

        grid.addComponent(dateCreated);
        dateCreated.setEnabled(false);
        dateCreated.setDateFormat("yyyy-MM-dd");
//        dateCreated.setValue(new Date());
        dateAcknowledged.setEnabled(false);
        grid.addComponent(dateAcknowledged);
        dateDue.setEnabled(false);
        grid.addComponent(dateDue);
        dateClosed.setEnabled(false);
        grid.addComponent(dateClosed);
        
        grid.addComponent(status);

        populateLists();

        grid.addComponent(priority);
        creator.setEnabled(false);
        grid.addComponent(creator);       

        contents.setWidth(1100, Unit.PIXELS);
        contents.setRows(10);
        contents.setWordwrap(true);

        saveComplaint.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                complaintToEdit.setDateCreated(dateCreated.getValue());
                
                Status s = (Status) status.getValue();
                complaintToEdit.setStatus(s);
                ViewUtils.messageLog(Level.INFO, status.getValue().toString(), "");
                ViewUtils.messageLog(Level.INFO, s.getId().toString(), "");
                complaintToEdit.setPriority((Priority) priority.getValue());
                complaintToEdit.setCreator((PlatformUser) creator.getValue());
                ViewUtils.messageLog(Level.WARNING, creator.getValue().toString(), " ");
//                ViewUtils.messageLog(Level.INFO, ((PlatformUser) creator.getValue()).getUsername(), " ");
                complaintToEdit.setContents(contents.getValue() + " /");
                ViewUtils.tripleMessage(Level.WARNING, footer, "Contents to be saved "+contents.getValue(), "Status "+status.getValue());
                complaintToEdit.setTrackList("/");
                try {
                    complaintFacade.edit(complaintToEdit);
                    HistoryService.makeHistoricalEntry(complaintToEdit);
                } catch (Exception e) {
                    ViewUtils.tripleMessage(Level.WARNING, footer, "Edit complaint failed", "");
//                    for (ConstraintViolation cv : e.getConstraintViolations()) {
//                        ViewUtils.messageLog(Level.SEVERE, cv.getConstraintDescriptor().getAnnotation().annotationType().getName(), "");
                }
                ViewUtils.tripleMessage(Level.WARNING, footer, "Complaint saved", "");
            }
        });

        vl.addComponent(contents);
        vl.addComponent(saveComplaint);

        saveComplaint.setStyleName("alignButtonDown");

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
        statusList.addAll(statusFacade.findAll());
        BeanItemContainer<Status> statusContainer = new BeanItemContainer(Status.class, statusList);
        status.setContainerDataSource(statusContainer);
        status.select(statusList.get(0));
        status.setNullSelectionAllowed(false);
        status.setRows(1);

        List<Priority> priorityList = new ArrayList<Priority>();
        priorityList.addAll(priorityFacade.findAll());
        BeanItemContainer<Priority> priorityContainer = new BeanItemContainer(Priority.class, priorityList);
        priority.setContainerDataSource(priorityContainer);
        priority.select(priorityList.get(0));
        priority.setNullSelectionAllowed(false);
        priority.setRows(1);

        String username = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userLogin").toString();

        List<PlatformUser> creatorList = new ArrayList<PlatformUser>();
        creatorList.addAll(platformUserFacade.findByUsername(username));
        ViewUtils.messageLog(Level.INFO, username, " ");
        ViewUtils.messageLog(Level.INFO, Integer.toString(creatorList.size()), " ");
        BeanItemContainer<PlatformUser> creatorContainer = new BeanItemContainer(PlatformUser.class, creatorList);
        creator.setContainerDataSource(creatorContainer);
        creator.select(creatorList.get(0));
        creator.setNullSelectionAllowed(false);
        creator.setRows(1);
    }
}
