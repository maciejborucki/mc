package pl.mi.mcloud.selfcare;

import pl.mi.mcloud.selfcare.util.MockSessionBean;
import pl.mi.mcloud.selfcare.util.Const;
import pl.mi.mcloud.selfcare.view.util.ViewUtils;
import pl.mi.mcloud.selfcare.view.LoginView;
import pl.mi.mcloud.selfcare.view.RegisterView;
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
import pl.mi.mcloud.selfcare.ejb.JobFacade;

@SessionScoped
@PreserveOnRefresh
@Theme("theme1")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI
{
    @Inject private MockSessionBean mockBean;
    
    @Inject JobFacade facadeLocal;
    
    final Navigator navigator = new Navigator(this, this);

//    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "pl.mi.mcloud.selfcare.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        
//        JobJpaController j; 
        
//        getSession().getSession().invalidate();
        
        getPage().setTitle(mockBean.businessMethod()+facadeLocal.count() );
        
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);
        
        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                layout.addComponent(new Label("Thank you for clicking"));
            }
        });
        layout.addComponent(button);
        
        ViewUtils.setNavigator(navigator);
//        
        navigator.addView(Const.REGISTER_VIEW, new RegisterView());
        navigator.addView(Const.LOGIN_VIEW, new LoginView());
//
        navigator.navigateTo(Const.REGISTER_VIEW);
    }
    

}
