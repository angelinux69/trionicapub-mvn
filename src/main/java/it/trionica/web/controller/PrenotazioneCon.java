package it.trionica.web.controller;

import java.sql.Date;
import java.time.LocalTime;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.clock.Clock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import it.trionica.web.model.dto.user.PrenotazioneDTO;
import it.trionica.web.model.dto.user.TavoloDTO;
import it.trionica.web.util.Util;
import lombok.extern.log4j.Log4j2;

@ManagedBean(name="prenotazioneCon")
@SessionScoped
@Log4j2
public class PrenotazioneCon {
	
	private static final long serialVersionUID = 1L;
	
	RestTemplate restTemplate = new RestTemplate();
	HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	
	private Long idPrenotazione;
	private TavoloDTO tavolo;
	private String nome;
	private Integer cell;
	private String ora;
	private Integer coperti;
	private Long idTavoloPre;
	
	
	@ManagedProperty(value="#{util}")
	private Util util;
	
	@PostConstruct
	public void init(){
		
		log.debug("PrenotazioneCon init");
	}
	
	public void onLoadView(ComponentSystemEvent event) {
	
		log.debug("sono in onloadView");
		this.idTavoloPren();
	}
	
	public void idTavoloPren(){
		idTavoloPre = Long.parseLong(request.getParameter("id"));
		coperti = Integer.parseInt(request.getParameter("coperti"));
		Date data = Date.valueOf(request.getParameter("data"));
		System.out.println(data);
	}
	
	public void salvaPrenotazione(){
		
		System.out.println("------");
		System.out.println(idTavoloPre);
		this.cercaTavolo();
		PrenotazioneDTO prenotazione = new PrenotazioneDTO();
		prenotazione.setTavolo(tavolo);
		prenotazione.setNome(nome);
		prenotazione.setOra(ora);
		prenotazione.setCell(cell);
		
		log.info("nome:"+prenotazione.getNome());
		
		//qui vanno in controlli utente e la chiamata al servizio in POST che fa l'inserimento
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        
        
        HttpEntity<PrenotazioneDTO> request = new HttpEntity<>(prenotazione, headers);
		String url = "http://localhost:8080/api/auth/salvaPrenotazione";
		
    	ResponseEntity<PrenotazioneDTO> res = restTemplate.exchange(url, HttpMethod.POST, request, PrenotazioneDTO.class);
	}
	
	public void cercaTavolo(){
		
		Long param = idTavoloPre;

		String url = "http://localhost:8080/api/auth/cercaTavolo";
		ResponseEntity<TavoloDTO> res = restTemplate.getForEntity(url + "/"+param, TavoloDTO.class);
		tavolo = res.getBody();
	}

	//Get & Set
	public Long getIdPrenotazione() {
		return idPrenotazione;
	}

	public void setIdPrenotazione(Long idPrenotazione) {
		this.idPrenotazione = idPrenotazione;
	}

	public TavoloDTO getTavolo() {
		return tavolo;
	}

	public void setTavolo(TavoloDTO tavolo) {
		this.tavolo = tavolo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCell() {
		return cell;
	}

	public void setCell(Integer cell) {
		this.cell = cell;
	}

	public String getOra() {
		return ora;
	}

	public void setOra(String ora) {
		this.ora = ora;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public static org.apache.logging.log4j.Logger getLog() {
		return log;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getCoperti() {
		return coperti;
	}

	public void setCoperti(Integer coperti) {
		this.coperti = coperti;
	}

	public Long getIdTavoloPre() {
		return idTavoloPre;
	}

	public void setIdTavoloPre(Long idTavoloPre) {
		this.idTavoloPre = idTavoloPre;
	}
	
}
