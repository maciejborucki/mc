/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.mi.mcloud.selfcare;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import javax.inject.Inject;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author bor
 */
@PreserveOnRefresh
@WebServlet(urlPatterns = "/*", 
	initParams = { 
		@WebInitParam(name = "UIProvider", value = "pl.mi.mcloud.selfcare.Jee6UIProvider") })
public class Jee6VaadinServlet extends VaadinServlet { 
	
	@Inject private UI ui;
	
	public UI getUI() {
		return ui;
	}
}
