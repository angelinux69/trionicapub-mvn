package it.trionica.web.controller;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.tabview.TabView;

import it.trionica.web.util.Util;

public class BaseController {

	@ManagedProperty(value="#{util}")
	private Util util;

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public int getPollingSeconds(){
		return new Integer(util.getConfigValue("POLLING_SECONDS")).intValue();
	}

	public void resetPaginazione(String idDataTable){

		final DataTable d = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("schemaDati:"+idDataTable);
		if(d!=null)
			d.setFirst(0);

	}
	
	public void resetSelezione(String idDataTable){

		final DataTable d = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("schemaDati:"+idDataTable);
		if(d!=null)
			d.setSelection(null);

	}
	
	public void resetTabIndex(String idTabView){

		final TabView d = (TabView) FacesContext.getCurrentInstance().getViewRoot().findComponent("schemaDati:"+idTabView);
		if(d!=null)
			d.setActiveIndex(0);

	}
}
