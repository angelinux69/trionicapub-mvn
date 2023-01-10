package it.trionica.web.controller;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.event.DateViewChangeEvent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import it.trionica.web.model.dto.user.PrenotazioneDTO;
import it.trionica.web.model.dto.user.TavoloDTO;
import it.trionica.web.util.Util;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;

@ManagedBean(name = "tavoloCon")
@ViewScoped
@Log4j2
public class TavoloController {

	private static final long serialVersionUID = 1L;

	RestTemplate restTemplate = new RestTemplate();

	private Long idTavolo;
	private Integer coperto;
	private Boolean stato;
	private String statoTav;
	private TavoloDTO tav;
	private List<TavoloDTO> listaTavoli = new ArrayList<>();
	private Long cercaTav;
	private Date dataT;
	private String msg = "Ciao";

	// HttpServletRequest request = (HttpServletRequest)
	// FacesContext.getCurrentInstance().getExternalContext().getRequest();
	// private Integer id;

	@ManagedProperty(value = "#{util}")
	private Util util;

	@PostConstruct
	public void init() throws ParseException {

	}

	public void onLoadView(ComponentSystemEvent event) throws Exception {
		log.debug("sono in onloadView");
		this.listaTavoliSala(); // QUESTO CI VA SE VUOI CARICARE LA TABELLA
		if(dataT == null){
			dataT = new Date();
		}
	}

	public ResponseEntity<?> salvaTavolo() {

		TavoloDTO tavolo = new TavoloDTO();
		tavolo.setIdTavolo(idTavolo);
		tavolo.setCoperto(coperto);
		if (statoTav.equals("libero")) {
			stato = true;
			tavolo.setStato(stato);
		} else {
			stato = false;
			tavolo.setStato(stato);
		}

		log.info("nome:" + tavolo.getIdTavolo());

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

		HttpEntity<TavoloDTO> request = new HttpEntity<>(tavolo, headers);
		String url = "http://localhost:8081/api/auth/salvaTavolo";

		ResponseEntity<TavoloDTO> res = restTemplate.exchange(url, HttpMethod.POST, request, TavoloDTO.class);
		tav = res.getBody();

		return res;
	}

	public void listaTavoliSala() throws Exception {
		try {
			listaTavoli.clear();
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.set("X-COM-PERSIST", "NO");
			headers.set("X-COM-LOCATION", "USA");

			HttpEntity<TavoloDTO> request = new HttpEntity<>(headers);
			String url = "http://localhost:8081/api/auth/listaTavoli";
			ResponseEntity<TavoloDTO[]> res = restTemplate.exchange(url, HttpMethod.GET, request, TavoloDTO[].class);
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String stringDate = df.format(dataT);
			for (TavoloDTO x : res.getBody()) {
				aggiornaStato(x, true);
				for(PrenotazioneDTO pren : listaPrenotazioniXTavolo(x.getIdTavolo())){
					if(df.format(pren.getData()).equals(stringDate)){
						aggiornaStato(x, false);
					}		
				}
				listaTavoli.add(x);
			}
		} catch (Exception e) {
			
			listaTavoli.clear();
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.set("X-COM-PERSIST", "NO");
			headers.set("X-COM-LOCATION", "USA");

			HttpEntity<TavoloDTO> request = new HttpEntity<>(headers);
			String url = "http://localhost:8081/api/auth/listaTavoli";
			ResponseEntity<TavoloDTO[]> res = restTemplate.exchange(url, HttpMethod.GET, request, TavoloDTO[].class);
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String stringDate = df.format(date);
			for (TavoloDTO x : res.getBody()) {
				aggiornaStato(x, true);
				for(PrenotazioneDTO pren : listaPrenotazioniXTavolo(x.getIdTavolo())){
					if(df.format(pren.getData()).equals(stringDate)){
						aggiornaStato(x, false);
					}		
				}
				listaTavoli.add(x);
			}
		}
			
	}
	
	public void aggiornaStato(TavoloDTO tav, Boolean stat){
		TavoloDTO tavoloAgg = new TavoloDTO();
		tavoloAgg.setIdTavolo(tav.getIdTavolo());
		tavoloAgg.setStato(stat);
		tavoloAgg.setCoperto(tav.getCoperto());
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

		HttpEntity<TavoloDTO> request = new HttpEntity<>(tavoloAgg, headers);
		String url = "http://localhost:8081/api/auth/salvaTavolo";

		ResponseEntity<TavoloDTO> res = restTemplate.exchange(url, HttpMethod.POST, request, TavoloDTO.class);
	}
	
	public List<PrenotazioneDTO> listaPrenotazioniXTavolo(Long idTav){
		String url = "http://localhost:8081/api/auth/listaPrenotazioniXTavolo";
		ResponseEntity<PrenotazioneDTO[]> res = restTemplate.getForEntity(url + "/" + idTav, PrenotazioneDTO[].class);
		List<PrenotazioneDTO> listPre = new ArrayList<>();
		for(PrenotazioneDTO x : res.getBody()){
			listPre.add(x);
		}
		return listPre;
	}
	
	// Get & Set
	public Long getIdTavolo() {
		return idTavolo;
	}

	public void setIdTavolo(Long idTavolo) {
		this.idTavolo = idTavolo;
	}

	public Integer getCoperto() {
		return coperto;
	}

	public void setCoperto(Integer coperto) {
		this.coperto = coperto;
	}

	public Boolean getStato() {
		return stato;
	}

	public void setStato(Boolean stato) {
		this.stato = stato;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public TavoloDTO getTav() {
		return tav;
	}

	public void setTav(TavoloDTO tav) {
		this.tav = tav;
	}

	public List<TavoloDTO> getListaTavoli() {
		return listaTavoli;
	}

	public void setListaTavoli(List<TavoloDTO> listaTavoli) {
		this.listaTavoli = listaTavoli;
	}

	public Long getCercaTav() {
		return cercaTav;
	}

	public void setCercaTav(Long cercaTav) {
		this.cercaTav = cercaTav;
	}

	public String getStatoTav() {
		return statoTav;
	}

	public void setStatoTav(String statoTav) {
		this.statoTav = statoTav;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public static org.apache.logging.log4j.Logger getLog() {
		return log;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getDataT() {
		return dataT;
	}

	public void setDataT(Date dataT) {
		this.dataT = dataT;
	}
	
}
