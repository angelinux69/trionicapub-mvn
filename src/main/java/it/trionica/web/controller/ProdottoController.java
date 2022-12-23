package it.trionica.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import it.trionica.web.model.dto.user.ProdottoDTO;
import it.trionica.web.util.Util;
import lombok.extern.log4j.Log4j2;

@ManagedBean(name = "prodottoController")
@ViewScoped
@Log4j2
public class ProdottoController {

	private static final long serialVersionUID = 1L;

	RestTemplate restTemplate = new RestTemplate();

	private String nomeProdotto;
	private String tipologia;
	private Double gradazione;
	private String marca;
	private String cantina;
	private Integer quantita;
	private Double prezzoA;
	private Double prezzoV;

	private ProdottoDTO prod;
	private List<ProdottoDTO> magazzino = new ArrayList<>();

	@ManagedProperty(value = "#{util}")
	private Util util;

	@PostConstruct
	public void init() {

		log.debug("ProdottoController init");
	}

	public void onLoadView(ComponentSystemEvent event) {

		log.debug("sono in onloadView di prodotto controller");
		this.loadMagazzino();

	}

	public void salvaProdotto() {

		ProdottoDTO prodotto = new ProdottoDTO();
		prodotto.setNomeProdotto(nomeProdotto);
		prodotto.setTipologia(tipologia);
		prodotto.setGradazione(gradazione);
		;
		prodotto.setMarca(marca);
		prodotto.setCantina(cantina);
		prodotto.setQuantita(quantita);
		prodotto.setPrezzoA(prezzoA);
		prodotto.setPrezzoV(prezzoV);

		log.info("nome:" + prodotto.getNomeProdotto());

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

		HttpEntity<ProdottoDTO> request = new HttpEntity<>(prodotto, headers);
		String url = "http://localhost:8081/api/auth/salvaProdotto";

		ResponseEntity<ProdottoDTO> res = restTemplate.exchange(url, HttpMethod.POST, request, ProdottoDTO.class);
		prod = res.getBody();

		return;
	}

	public void loadMagazzino() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("X-COM-PERSIST", "NO");
		headers.set("X-COM-LOCATION", "USA");

		HttpEntity<ProdottoDTO> request = new HttpEntity<>(headers);
		String url = "http://localhost:8081/api/auth/magazzino";
		ResponseEntity<ProdottoDTO[]> res = restTemplate.exchange(url, HttpMethod.GET, request, ProdottoDTO[].class);

		for (ProdottoDTO x : res.getBody()) {
			magazzino.add(x);
		}
	}

	// Get & Set
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getNomeProdotto() {
		return nomeProdotto;
	}

	public void setNomeProdotto(String nomeProdotto) {
		this.nomeProdotto = nomeProdotto;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public Double getGradazione() {
		return gradazione;
	}

	public void setGradazione(Double gradazione) {
		this.gradazione = gradazione;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getCantina() {
		return cantina;
	}

	public void setCantina(String cantina) {
		this.cantina = cantina;
	}

	public Integer getQuantita() {
		return quantita;
	}

	public void setQuantita(Integer quantita) {
		this.quantita = quantita;
	}

	public Double getPrezzoA() {
		return prezzoA;
	}

	public void setPrezzoA(Double prezzoA) {
		this.prezzoA = prezzoA;
	}

	public Double getPrezzoV() {
		return prezzoV;
	}

	public void setPrezzoV(Double prezzoV) {
		this.prezzoV = prezzoV;
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

	public ProdottoDTO getProd() {
		return prod;
	}

	public void setProd(ProdottoDTO prod) {
		this.prod = prod;
	}

	public List<ProdottoDTO> getMagazzino() {
		return magazzino;
	}

	public void setMagazzino(List<ProdottoDTO> magazzino) {
		this.magazzino = magazzino;
	}

}
