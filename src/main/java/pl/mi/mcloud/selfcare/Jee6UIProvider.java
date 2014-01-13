/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.mi.mcloud.selfcare;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 *
 * @author bor
 */
public class Jee6UIProvider extends UIProvider {

	@Override 
	public UI createInstance(UICreateEvent event) {
		return ((Jee6VaadinServlet)VaadinServlet.getCurrent()).getUI();
	}
//
	@Override
	public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
		return ((Jee6VaadinServlet)VaadinServlet.getCurrent()).getUI().getClass();
	}

//    @Override
//    public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
