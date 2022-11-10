package it.trionica.web.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import it.trionica.web.util.Util;
import lombok.extern.log4j.Log4j2;

@ManagedBean(name="personaInsertController")
@ViewScoped
@Log4j2
public class UserController implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{util}")
	private Util util;
	
	@PostConstruct
	public void init() throws Exception{
		log.debug("UserController init");	
	}
	

}
