package pl.mi.mcloud.selfcare;

import pl.mi.mcloud.selfcare.util.MockSessionBean;
import pl.mi.mcloud.selfcare.util.Const;
import pl.mi.mcloud.selfcare.view.util.ViewUtils;
import pl.mi.mcloud.selfcare.view.*;
import com.vaadin.annotations.PreserveOnRefresh;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import pl.mi.mcloud.selfcare.ejb.*;

@SessionScoped
@PreserveOnRefresh
//@Theme("mytheme")
@Theme("theme1")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI
{
    @Inject private MockSessionBean mockBean;
    
    @Inject AccessGroupFacade accessGroupFacade;
    @Inject ComplaintFacade complaintFacade;
    @Inject ComplaintHistoryFacade complaintHistoryFacade;
    @Inject JobFacade jobFacade;
    @Inject JobHistoryFacade jobHistoryFacade;
    @Inject PlatformComponentFacade platformComponentFacade;
    @Inject PlatformModuleFacade platformModuleFacade;
    @Inject PlatformServiceFacade platformServiceFacade;
    @Inject PlatformUserFacade platformUserFacade;
    @Inject PriorityFacade priorityFacade;
    @Inject StatusFacade statusFacade;
    
    final Navigator navigator = new Navigator(this, this);

    private void attachEJBHandlers() {
        EJBBus.accessGroupFacade = accessGroupFacade;
        EJBBus.complaintFacade = complaintFacade;
        EJBBus.complaintHistoryFacade = complaintHistoryFacade;
        EJBBus.jobFacade = jobFacade;
        EJBBus.jobHistoryFacade = jobHistoryFacade;
        EJBBus.platformComponentFacade = platformComponentFacade;
        EJBBus.platformModuleFacade = platformModuleFacade;
        EJBBus.platformServiceFacade = platformServiceFacade;
        EJBBus.platformUserFacade = platformUserFacade;
        EJBBus.priorityFacade = priorityFacade;
        EJBBus.statusFacade = statusFacade;
    }

//    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "pl.mi.mcloud.selfcare.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        attachEJBHandlers();
        
        getPage().setTitle(mockBean.businessMethod()+jobFacade.count() );
        
        ViewUtils.setNavigator(navigator);
//        
//        navigator.addView(Const.REGISTER_VIEW, new RegisterView());
//        navigator.addView(Const.LOGIN_VIEW, new LoginView());
        
        //TODO - protect resource
        
        navigator.addView(Const.NEW_REQUEST_VIEW, new NewRequestView());
        navigator.addView(Const.LIST_REQUEST_VIEW, new ListRequestView());
        navigator.addView(Const.EDIT_REQUEST_VIEW, new EditRequestView());
        
        navigator.addView(Const.NEW_COMPLAINT_VIEW, new NewComplaintView());
        navigator.addView(Const.LIST_COMPLAINT_VIEW, new ListComplaintView());
        navigator.addView(Const.EDIT_COMPLAINT_VIEW, new EditComplaintView());
//
        navigator.navigateTo(Const.NEW_REQUEST_VIEW);
    }
    

}
