package it.trionica.web.controller;

import java.io.Serializable;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpSession;

import org.apache.http.impl.client.RequestWrapper;
import org.primefaces.shaded.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import it.trionica.web.model.dto.user.LoginUser;
import it.trionica.web.model.dto.user.UserDTO;
import it.trionica.web.util.Util;
import lombok.extern.log4j.Log4j2;

@ManagedBean(name = "userController")
@SessionScoped
@Log4j2
public class UserController implements Serializable {

	private static final long serialVersionUID = 1L;

	RestTemplate restTemplate = new RestTemplate();

	private String username;

	private String nome;

	private String cognome;

	private String email;

	private String password;

	private String logUtente = "false";

	private String utente = "Utente";

	private LoginUser userLog;

	private String msg = "";

	JSONObject jsonObject = null;

	@ManagedProperty(value = "#{util}")
	private Util util;

	@PostConstruct
	public void init() {

		log.debug("UserController init");
	}

	public void onLoadView(ComponentSystemEvent event) {

		log.debug("sono in onloadView");
	}

	public void signin() throws HttpClientErrorException{
		msg = "";
		UserDTO userBean = new UserDTO();
		userBean.setUsername(username);
		userBean.setPassword(password);

		// System.out.println("username:"+userBean.getUsername());

		// qui vanno in controlli utente e la chiamata al servizio in POST che fa
		// l'inserimento
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		/*
		 * HttpHeaders headers = csrfHeaders();
		 */

		HttpEntity<UserDTO> request = new HttpEntity<>(userBean, headers);
		String url = "http://localhost:8081/api/auth/signin";
		try {
			ResponseEntity<LoginUser> res = restTemplate.exchange(url, HttpMethod.POST, request, LoginUser.class);
			if (logUtente.equals("false")) {
				logUtente = "true";
			}
			userLog = res.getBody();
			utente = userLog.getUsername();
			System.out.println("logUtente: " + logUtente);

			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("token", res.getBody().getJwt());

			
		} catch (HttpClientErrorException e) {
			logUtente = "false";
			//msg = "Credenziali errate";
		}
		
	}

	public ResponseEntity<?> registrazione() {

		UserDTO userBean = new UserDTO();
		userBean.setNome(nome);
		userBean.setCognome(cognome);
		userBean.setUsername(username);
		userBean.setEmail(email);
		userBean.setPassword(password);

		log.info("nome:" + userBean.getNome());

		// qui vanno in controlli utente e la chiamata al servizio in POST che fa
		// l'inserimento
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		/*
		 * HttpHeaders headers = csrfHeaders();
		 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		 */

		HttpEntity<UserDTO> request = new HttpEntity<>(userBean, headers);
		String url = "http://localhost:8081/api/auth/registrazione";

		ResponseEntity<?> res = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

		return res;
	}

	public void logoutUtente() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (session != null) {
			session.invalidate();
		}
		logUtente = "false";
		utente = "Utente";
	}
	
	public String navigate(){
		if(logUtente.equals("true")){
			return "index";
		}else {
			msg = "Credenziali errate";
			return "utente";
		}
	}

	/*
	 * public HttpHeaders basicAuthHeaders() { String plainCreds = "user:password";
	 * byte[] plainCredsBytes = plainCreds.getBytes(); byte[] base64CredsBytes =
	 * Base64.getEncoder().encode(plainCredsBytes); String base64Creds = new
	 * String(base64CredsBytes);
	 * 
	 * HttpHeaders headers = new HttpHeaders(); headers.add("Authorization",
	 * "Basic " + base64Creds); return headers; }
	 * 
	 * public HttpHeaders csrfHeaders() { CsrfTokenRepository csrfTokenRepository =
	 * CookieCsrfTokenRepository.withHttpOnlyFalse(); CsrfToken csrfToken =
	 * csrfTokenRepository.generateToken(null); HttpHeaders headers =
	 * basicAuthHeaders();
	 * 
	 * headers.add(csrfToken.getHeaderName(), csrfToken.getToken());
	 * headers.add("Cookie", "XSRF-TOKEN=" + csrfToken.getToken());
	 * 
	 * return headers; }
	 */

	// Get e Set
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public LoginUser getUserLog() {
		return userLog;
	}

	public void setUserLog(LoginUser userLog) {
		this.userLog = userLog;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
