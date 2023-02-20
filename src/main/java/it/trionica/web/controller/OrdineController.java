package it.trionica.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import it.trionica.web.model.dto.user.OrdineDTO;
import it.trionica.web.model.dto.user.OrdineProdottoDTO;
import it.trionica.web.model.dto.user.ProdottoDTO;
import it.trionica.web.model.dto.user.TavoloDTO;
import it.trionica.web.util.Util;
import lombok.extern.log4j.Log4j2;

@ManagedBean(name = "ordineController")
@SessionScoped
@Log4j2
public class OrdineController {
	
	private static final long serialVersionUID = 1L;

	RestTemplate restTemplate = new RestTemplate();
	
	private Long idOrdine;
	private TavoloDTO tavolo;
	private List<ProdottoDTO> prodotti = new ArrayList<>();
	private List<Integer> quantitaOrdine = new ArrayList<>();
	private Double conto;
	private String msgError = "";
	private String salvaO;
	private ProdottoDTO prodottoSel;
	private List<ProdottoDTO> prodottiXTip = new ArrayList<>();
	private Boolean tab = true;
	private String tipologiaSel;
	private Integer quantitaOrd;
	private List<OrdineProdottoDTO> listaOP = new ArrayList<>();
	private OrdineDTO ordineCreato = new OrdineDTO();
	
	
	@ManagedProperty(value = "#{util}")
	private Util util;

	@PostConstruct
	public void init() {

		log.debug("OrdineController init");
	}

	public void onLoadView(ComponentSystemEvent event) {
		msgError="";
		tab = true;
		log.debug("sono in onloadView");
	}
	
	public void cercaProdotto(){
		Map<String, String> param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Long idProdotto = Long.parseLong(param.get("idProdotto"));
		String url = "http://localhost:8081/api/auth/cercaProdotto";
		try {
			ResponseEntity<ProdottoDTO> res = restTemplate.getForEntity(url + "/" + idProdotto, ProdottoDTO.class);
			prodottoSel = res.getBody();
		} catch (Exception e) {
			msgError = "Prodotto non trovato";
		}	
	}
	
	public void creaOrdineProdotto(){
		OrdineProdottoDTO ord = new OrdineProdottoDTO();
		ord.setOrdine(ordineCreato);
		ord.setProdotto(prodottoSel);
		ord.setQuantitaOrdine(quantitaOrd);
		listaOP.add(ord);
		//prodottoSel.setOrdineProdotto(listaOP);
	}
	
	public void salvaOrdineProdotto(){
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

		HttpEntity<List<OrdineProdottoDTO>> request = new HttpEntity<>(listaOP, headers);
		String url = "http://localhost:8081/api/auth/salvaOrdineProdotto";

		ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.POST, request,String.class);
		msgError = res.getBody();
		listaOP.clear();
	}
	
	public void salvaOrdine() throws Exception{
		cercaTavolo();
		Double totPro = 0.0;
		Double totConto = 0.0;
		OrdineDTO ordine = new OrdineDTO();
		ordine.setTavolo(tavolo);
		ordine.setConto(totConto);
		/*
		for(OrdineProdottoDTO op:listaOP){
			totPro = op.getQuantitaOrdine()*op.getProdotto().getPrezzoV();
			op.setOrdine(ordine);
			totConto += totPro;
		}
		*/
		//ordine.setOrdineProdotto(listaOP);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

		HttpEntity<OrdineDTO> request = new HttpEntity<>(ordine, headers);
		String url = "http://localhost:8081/api/auth/salvaOrdine";
		try {
			ResponseEntity<OrdineDTO> res = restTemplate.exchange(url, HttpMethod.POST, request,OrdineDTO.class);
			ordineCreato = res.getBody();
			salvaO = "true";
			tab = false;
		} catch (Exception e) {
			salvaO = "false";
			tab = true;
			msgError = "Inserire tutti i campi: " + e;
		}
	}
	
	public void cancellaOrdineNuovo(){
		Long idO = ordineCreato.getIdOrdine();
		String url = "http://localhost:8081/api/auth/cancellaOrdineNuovo";
		try {
			ResponseEntity<String> res = restTemplate.getForEntity(url + "/" + idO, String.class);
		} catch (Exception e) {
			msgError = "Errore: " + e;
		}
	}
	
	public TavoloDTO cercaTavolo(){
		String idTavolo;
		Map<String, String> param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		idTavolo = param.get("idTav");
		Long paramId = Long.parseLong(idTavolo);

		String url = "http://localhost:8081/api/auth/cercaTavolo";
		ResponseEntity<TavoloDTO> res = restTemplate.getForEntity(url + "/" + paramId, TavoloDTO.class);
		return tavolo = res.getBody();
	}

	public void prodottiXTipologia(){
		prodottiXTip.clear();
		String url = "http://localhost:8081/api/auth/prodottiXTipologia";
		ResponseEntity<ProdottoDTO[]> res = restTemplate.getForEntity(url + "/" + tipologiaSel, ProdottoDTO[].class);
		for(ProdottoDTO p:res.getBody()){
			prodottiXTip.add(p);
		}
	}
	
	
	
	//Get & Set
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Long getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(Long idOrdine) {
		this.idOrdine = idOrdine;
	}

	public TavoloDTO getTavolo() {
		return tavolo;
	}

	public void setTavolo(TavoloDTO tavolo) {
		this.tavolo = tavolo;
	}

	public List<ProdottoDTO> getProdotti() {
		return prodotti;
	}

	public void setProdotti(List<ProdottoDTO> prodotti) {
		this.prodotti = prodotti;
	}

	public List<Integer> getQuantitaOrdine() {
		return quantitaOrdine;
	}

	public void setQuantitaOrdine(List<Integer> quantitaOrdine) {
		this.quantitaOrdine = quantitaOrdine;
	}

	public Double getConto() {
		return conto;
	}

	public void setConto(Double conto) {
		this.conto = conto;
	}

	public String getMsgError() {
		return msgError;
	}

	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}

	public String getSalvaO() {
		return salvaO;
	}

	public void setSalvaO(String salvaO) {
		this.salvaO = salvaO;
	}

	public ProdottoDTO getProdottoSel() {
		return prodottoSel;
	}

	public void setProdottoSel(ProdottoDTO prodottoSel) {
		this.prodottoSel = prodottoSel;
	}

	public List<ProdottoDTO> getProdottiXTip() {
		return prodottiXTip;
	}

	public void setProdottiXTip(List<ProdottoDTO> prodottiXTip) {
		this.prodottiXTip = prodottiXTip;
	}

	public Boolean getTab() {
		return tab;
	}

	public void setTab(Boolean tab) {
		this.tab = tab;
	}

	public String getTipologiaSel() {
		return tipologiaSel;
	}

	public void setTipologiaSel(String tipologiaSel) {
		this.tipologiaSel = tipologiaSel;
	}

	public Integer getQuantitaOrd() {
		return quantitaOrd;
	}

	public void setQuantitaOrd(Integer quantitaOrd) {
		this.quantitaOrd = quantitaOrd;
	}

	public List<OrdineProdottoDTO> getListaOP() {
		return listaOP;
	}

	public void setListaOP(List<OrdineProdottoDTO> listaOP) {
		this.listaOP = listaOP;
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

	public OrdineDTO getOrdineCreato() {
		return ordineCreato;
	}

	public void setOrdineCreato(OrdineDTO ordineCreato) {
		this.ordineCreato = ordineCreato;
	}
	
	
}
