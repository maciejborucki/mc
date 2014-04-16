/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sc.main.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import sc.main.util.Utils;

/**
 *
 * @author bor
 */
public class WelcomeView extends VerticalLayout implements View {

    /*LAYOUT*/
    final VerticalLayout main = new VerticalLayout();
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Utils.logInfo("WelcomeView RegisterView()", false);
        Utils.checkAuthorization();
        Utils.wrapView(this, main);
        Utils.getMenuBar().setEnabled(true);
    }

    public WelcomeView() {
        Utils.logInfo("WelcomeView RegisterView()", false);
        initComponents();
    }

    private void initComponents() {
        Utils.logInfo("WelcomeView initComponents()", false);
        main.addComponent(new Label("Welcome"));
//        Utils.attachFooterHeaderMenu(false);
        
    }
}
