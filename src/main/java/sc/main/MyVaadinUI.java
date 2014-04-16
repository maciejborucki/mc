package sc.main;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import pl.mi.mcloud.selfcare.util.MockSessionBean;
import sc.main.ejb.AccessGroupFacade;
import sc.main.ejb.ComplaintFacade;
import sc.main.ejb.ComplaintHistoryFacade;
import sc.main.ejb.JobFacade;
import sc.main.ejb.JobHistoryFacade;
import sc.main.ejb.PlatformComponentFacade;
import sc.main.ejb.PlatformModuleFacade;
import sc.main.ejb.PlatformServiceFacade;
import sc.main.ejb.PlatformUserFacade;
import sc.main.ejb.PriorityFacade;
import sc.main.ejb.StatusFacade;
import sc.main.entity.AccessGroup;
import sc.main.entity.PlatformUser;
import sc.main.util.Const;
import sc.main.util.Utils;
import sc.main.view.LoginView;
import sc.main.view.RegisterView;

@SessionScoped
@PreserveOnRefresh
//@Theme("mytheme")
@Theme("theme1")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {

    @Inject
    private MockSessionBean mockBean;

    @Inject
    AccessGroupFacade accessGroupFacade;
    @Inject
    ComplaintFacade complaintFacade;
    @Inject
    ComplaintHistoryFacade complaintHistoryFacade;
    @Inject
    JobFacade jobFacade;
    @Inject
    JobHistoryFacade jobHistoryFacade;
    @Inject
    PlatformComponentFacade platformComponentFacade;
    @Inject
    PlatformModuleFacade platformModuleFacade;
    @Inject
    PlatformServiceFacade platformServiceFacade;
    @Inject
    PlatformUserFacade platformUserFacade;
    @Inject
    PriorityFacade priorityFacade;
    @Inject
    StatusFacade statusFacade;

//    final Navigator navigator = new Navigator(this, this);
    private void attachEJBHandlers() {
        EJB.accessGroupFacade = accessGroupFacade;
        EJB.complaintFacade = complaintFacade;
        EJB.complaintHistoryFacade = complaintHistoryFacade;
        EJB.jobFacade = jobFacade;
        EJB.jobHistoryFacade = jobHistoryFacade;
        EJB.platformComponentFacade = platformComponentFacade;
        EJB.platformModuleFacade = platformModuleFacade;
        EJB.platformServiceFacade = platformServiceFacade;
        EJB.platformUserFacade = platformUserFacade;
        EJB.priorityFacade = priorityFacade;
        EJB.statusFacade = statusFacade;
    }

    private void checkInitialData() {

        AccessGroup developerGroup = accessGroupFacade.findByGroupName(Const.DEVELOPER_GROUP);
        AccessGroup customerGroup = accessGroupFacade.findByGroupName(Const.CUSTOMER_GROUP);
        try {
            if (developerGroup == null) {
                AccessGroup accessGroup = new AccessGroup();
                accessGroup.setGroupName(Const.DEVELOPER_GROUP);
                accessGroup.setAccessType(Const.DEVELOPER_GROUP);
                accessGroup.setAccessResources(Const.DEVELOPER_GROUP);
                accessGroupFacade.create(accessGroup);
            }
            if (customerGroup == null) {
                AccessGroup accessGroup = new AccessGroup();
                accessGroup.setGroupName(Const.CUSTOMER_GROUP);
                accessGroup.setAccessType(Const.CUSTOMER_GROUP);
                accessGroup.setAccessResources(Const.CUSTOMER_GROUP);
                accessGroupFacade.create(accessGroup);
            }
        } catch (Exception e) {
            Utils.logSevere("MyVaadinUI checkInitialData exception", false);
        }
        
        if(Const.TEST_ENABLED) {
                PlatformUser pu = new PlatformUser();
                pu.setUsername("testuser");
                pu.setAddress("Fajna 6");
                pu.setCity("Kosovo");
                pu.setCode("7821");
                pu.setEmail("test@test.tst");
                pu.setFirstname("Igor");
                pu.setLastname("Abramov");
                pu.setPhone("19231123231");
                pu.setPassword("Emlajf123$%^");
                pu.setAccessGroup116(Const.DEVELOPER_GROUP);
                pu.setIsPhoneConfirmed("12345678");
                pu.setIsEmailConfirmed("12345678");
                pu.setCountry("Poland");
                pu.setId(99999L);
            try {
                platformUserFacade.create(pu);
            } catch (Exception ex) {
                Utils.logInfo("TEST_ENABLED MyVaadinUI checkInitialData create not possible", false);
            }
        }
    }

//    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "sc.main.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle(mockBean.businessMethod());
        this.attachEJBHandlers();
        Utils.setNavigator(new Navigator(this, this));
        Utils.getNavigator().addView(Const.REGISTER_VIEW, new RegisterView());
        Utils.getNavigator().addView(Const.LOGIN_VIEW, new LoginView());
        Utils.getNavigator().navigateTo(Const.LOGIN_VIEW);
        this.checkInitialData();
    }

}
