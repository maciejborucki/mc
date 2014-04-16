/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sc.main.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.VerticalLayout;
import sc.main.util.Const;
import sc.main.util.Utils;

/**
 *
 * @author bor
 */
class LogoutView extends VerticalLayout implements View {

    public LogoutView() {
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userLogged", Boolean.FALSE);
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("username", null);
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userid", null);
        Utils.notification("User logged out");
        Utils.getNavigator().navigateTo(Const.LOGIN_VIEW);
        Utils.getMenuBar().setEnabled(false);
    }
}
