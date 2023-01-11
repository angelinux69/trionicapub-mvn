package it.trionica.web.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.expression.spel.support.BooleanTypedValue;
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

@ManagedBean(name = "prenotazioneCon")
@SessionScoped
@Log4j2
public class PrenotazioneCon {

	private static final long serialVersionUID = 1L;

	RestTemplate restTemplate = new RestTemplate();
	
	private Long idPrenotazione;
	private TavoloDTO tavolo;
	private String nome;
	private Integer cell;
	private Date data;
	private String ora;
	private Integer coperti;
	private String idTavoloPre;
	private Boolean tab = true;
	private String dataS;
	private List<PrenotazioneDTO> listaPren = new ArrayList<>();
	private Boolean indietro;
	private String salvaP = "false";
	private String msgError = "";

	@ManagedProperty(value = "#{util}")
	private Util util;

	@PostConstruct
	public void init() {

		log.debug("PrenotazioneCon init");
	}

	public void onLoadView(ComponentSystemEvent event) throws ParseException {

		log.debug("sono in onloadView");
		//this.listaPrenotazioni();
		//this.listaPrenotazioniXTavolo();
	}

	public void idTavoloPren() throws ParseException {
		tab = true;
		Map<String, String> param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		idTavoloPre = param.get("id");
		dataS = param.get("dataDisp");
		data = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US).parse(dataS);
		if (data == null || data.toString().isEmpty()) {
			data = new Date();
		}
		coperti = this.cercaTavolo().getCoperto();
	}

	public void salvaPrenotazione() {
		this.cercaTavolo();
		PrenotazioneDTO prenotazione = new PrenotazioneDTO();
		prenotazione.setTavolo(tavolo);
		prenotazione.setNome(nome);
		prenotazione.setData(data);
		prenotazione.setOra(ora);
		prenotazione.setCell(cell);

		log.info("nome:" + prenotazione.getNome());

		// qui vanno in controlli utente e la chiamata al servizio in POST che fa
		// l'inserimento
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

		HttpEntity<PrenotazioneDTO> request = new HttpEntity<>(prenotazione, headers);
		String url = "http://localhost:8081/api/auth/salvaPrenotazione";
		try {
			ResponseEntity<PrenotazioneDTO> res = restTemplate.exchange(url, HttpMethod.POST, request,PrenotazioneDTO.class);
			tab = false;
			salvaP = "true";
		} catch (Exception e) {
			salvaP = "false";
			msgError = "Inserire tutti i campi";
		}
		
	}

	public TavoloDTO cercaTavolo() {

		Long param = Long.parseLong(idTavoloPre);

		String url = "http://localhost:8081/api/auth/cercaTavolo";
		ResponseEntity<TavoloDTO> res = restTemplate.getForEntity(url + "/" + param, TavoloDTO.class);
		return tavolo = res.getBody();
	}
	
	public List<PrenotazioneDTO> listaPrenotazioniXTavolo() throws ParseException{
		this.idTavoloPren();
		Long id = Long.parseLong(idTavoloPre);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dataString = df.format(data);
		//listaPren.clear(); Perchè non svuota qui e sotto si? chiedere ad angelo
		for(PrenotazioneDTO x : listaPrenotazioni()){
			listaPren.remove(x);
			if(x.getTavolo().getIdTavolo().equals(id) && df.format(x.getData()).equals(dataString)){
				listaPren.add(x);
			}
		}
		indietro = false;
		return listaPren;
	}
	
	public List<PrenotazioneDTO> listaPrenXTavolo() throws ParseException{
		Long id = Long.parseLong(idTavoloPre);
		//listaPren.clear(); Perchè non svuota qui e sotto si? chiedere ad angelo
		for(PrenotazioneDTO x : listaPrenotazioni()){
			listaPren.remove(x);
			if(x.getTavolo().getIdTavolo().equals(id)){
				listaPren.add(x);
			}
		}
		indietro = false;
		return listaPren;
	}
	
	public Set<PrenotazioneDTO> listaPrenotazioni() throws ParseException {
		listaPren.clear();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("X-COM-PERSIST", "NO");
		headers.set("X-COM-LOCATION", "USA");

		HttpEntity<PrenotazioneDTO> request = new HttpEntity<>(headers);
		String url = "http://localhost:8081/api/auth/listaPrenotazioni";
		ResponseEntity<PrenotazioneDTO[]> res = restTemplate.exchange(url, HttpMethod.GET, request, PrenotazioneDTO[].class);
		Set<PrenotazioneDTO> lista = new HashSet<>();
		for (PrenotazioneDTO p : res.getBody()) {
			listaPren.add(p);
			lista.add(p);
		}
		indietro = true;
		return lista;
	}
	
	public String navigate(){
		if(salvaP.equals("true")){
			return "sala";
		}else {
			return "";
		}
	}
	
	// Get & Set
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getIdTavoloPre() {
		return idTavoloPre;
	}

	public void setIdTavoloPre(String idTavoloPre) {
		this.idTavoloPre = idTavoloPre;
	}

	public Boolean getTab() {
		return tab;
	}

	public void setTab(Boolean tab) {
		this.tab = tab;
	}

	public String getDataS() {
		return dataS;
	}

	public void setDataS(String dataS) {
		this.dataS = dataS;
	}

	public List<PrenotazioneDTO> getListaPren() {
		return listaPren;
	}

	public void setListaPren(List<PrenotazioneDTO> listaPren) {
		this.listaPren = listaPren;
	}

	public Boolean getIndietro() {
		return indietro;
	}

	public void setIndietro(Boolean indietro) {
		this.indietro = indietro;
	}

	public String getSalvaP() {
		return salvaP;
	}

	public void setSalvaP(String salvaP) {
		this.salvaP = salvaP;
	}

	public String getMsgError() {
		return msgError;
	}

	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}
	
}
