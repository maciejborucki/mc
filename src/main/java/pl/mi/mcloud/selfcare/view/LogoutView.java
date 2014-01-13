/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mi.mcloud.selfcare.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.VerticalLayout;
import pl.mi.mcloud.selfcare.util.Const;
import pl.mi.mcloud.selfcare.view.util.ViewUtils;

/**
 *
 * @author bor
 */
class LogoutView extends VerticalLayout implements View {

    final Navigator navigator = ViewUtils.getNavigator();
    
    public LogoutView() {
    }

    public void enter(ViewChangeListener.ViewChangeEvent event) {      
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userLogged", Boolean.FALSE);
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userLogin", null);
        ViewUtils.notification("User logged out");
        //getUI().close();
        navigator.navigateTo(Const.LOGIN_VIEW);
    }
}
