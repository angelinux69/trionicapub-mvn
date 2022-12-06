package it.trionica.web.controller;

import java.io.Serializable;
import java.lang.System.Logger;
import java.util.Arrays;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.shaded.json.JSONObject;
import org.primefaces.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import it.trionica.web.model.dto.user.UserDTO;
import it.trionica.web.util.Util;
import lombok.extern.log4j.Log4j2;

@ManagedBean(name="userController")
@SessionScoped
@Log4j2
public class UserController implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	RestTemplate restTemplate = new RestTemplate();
	
	private String username = "Utente";

	private String password;
	
	private String logUtente = "false";
	
	
	public void login(){
		if(logUtente.equals("false")){
			logUtente="true";
			System.out.println(username);
			System.out.println("Sono true");
		}
	}
	public void logout(){
		if(logUtente.equals("true")){
			logUtente="false";
			System.out.println("Sono false");
		}
	}
	
	JSONObject jsonObject = null;
	
	@ManagedProperty(value="#{util}")
	private Util util;
	
	@PostConstruct
	public void init() throws Exception{
		
		log.debug("UserController init");	
	}
	
	public void onLoadView(ComponentSystemEvent event) {
	
		log.debug("sono in onloadView");
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> signin() {
		
		UserDTO userBean = new UserDTO();

		userBean.setNome(username);
		userBean.setCognome(password);
		
		log.info("nome:"+userBean.getNome());
		
		//qui vanno in controlli utente e la chiamata al servizio in POST che fa l'inserimento
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		/*
		 	HttpHeaders headers = csrfHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		*/
        
        HttpEntity<UserDTO> request = new HttpEntity<>(userBean, headers);
		String url = "http://localhost:8080/api/auth/login";
		
    	ResponseEntity<?> res = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
    	return res;
		//System.out.println("Errore: "+ e);
		//System.out.println("RestTemplate: "+ restTemplate);
		//System.out.println("Nome Utente: "+nomeUtente);
		//System.out.println("Cognome Utente: "+cognomeUtente);
	}
	/*
	public HttpHeaders basicAuthHeaders() {
        String plainCreds = "user:password";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        return headers;
	}
	
	 public HttpHeaders csrfHeaders() {
	 	CsrfTokenRepository csrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        CsrfToken csrfToken = csrfTokenRepository.generateToken(null);
        HttpHeaders headers = basicAuthHeaders();

        headers.add(csrfToken.getHeaderName(), csrfToken.getToken());
        headers.add("Cookie", "XSRF-TOKEN=" + csrfToken.getToken());

        return headers;
    }
	*/
	
	//Get e Set
	

	public Util getUtil() {
		return util;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setUtil(Util util) {
		this.util = util;
	}

	public String getLogUtente() {
		return logUtente;
	}

	public void setLogUtente(String logUtente) {
		this.logUtente = logUtente;
	}
	public String getUtente() {
		return utente;
	}
	public void setUtente(String utente) {
		this.utente = utente;
	}

	

}
