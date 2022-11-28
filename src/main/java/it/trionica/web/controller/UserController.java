package it.trionica.web.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

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
@ViewScoped
@Log4j2
public class UserController implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	RestTemplate restTemplate = new RestTemplate();
	
	private String nomeUtente;

	private String cognomeUtente;
		
	
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
	public String signin() {
		
		UserDTO userBean = new UserDTO();

		userBean.setNome(nomeUtente);
		userBean.setCognome(cognomeUtente);
		
		log.info("nome:"+userBean.getNome());
		
		//qui vanno in controlli utente e la chiamata al servizio in POST che fa l'inserimento
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		/*
		 	HttpHeaders headers = csrfHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		*/
        
        
        HttpEntity<UserDTO> request = new HttpEntity<>(userBean, headers);
		
        try {
        	
        	return restTemplate.exchange("http://localhost:8080/api/auth/signin", HttpMethod.POST, request, String.class).getBody();
        } catch (Exception e) {
			System.out.println("Errore: "+ e);
			System.out.println("RestTemplate: "+ restTemplate);
			System.out.println("Nome Utente: "+nomeUtente);
			System.out.println("Cognome Utente: "+cognomeUtente);
		}
		return "E' tutto vuoto";
        
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
	public String getNomeUtente() {
		return nomeUtente;
	}

	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public String getCognomeUtente() {
		return cognomeUtente;
	}

	public void setCognomeUtente(String cognomeUtente) {
		this.cognomeUtente = cognomeUtente;
	}

	

}
