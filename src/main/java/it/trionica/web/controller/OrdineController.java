package it.trionica.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JacksonInject;

import it.trionica.web.model.dto.user.OrdineDTO;
import it.trionica.web.model.dto.user.ProdottoDTO;
import it.trionica.web.model.dto.user.TavoloDTO;
import lombok.extern.log4j.Log4j2;

@ManagedBean(name = "ordineController")
@SessionScoped
@Log4j2
public class OrdineController {
	
	private static final long serialVersionUID = 1L;

	RestTemplate restTemplate = new RestTemplate();
	
	private Long idOrdine;
	private TavoloDTO tavolo;
	private List<ProdottoDTO> prodotto = new ArrayList<>();
	private Integer quantitaOrdine;
	private Double conto;
	
	public void salvaOrdine(){
		OrdineDTO ordine = new OrdineDTO();
		
	}
	
	
	//Get & Set
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
	public List<ProdottoDTO> getProdotto() {
		return prodotto;
	}
	public void setProdotto(List<ProdottoDTO> prodotto) {
		this.prodotto = prodotto;
	}
	public Integer getQuantitaOrdine() {
		return quantitaOrdine;
	}
	public void setQuantitaOrdine(Integer quantitaOrdine) {
		this.quantitaOrdine = quantitaOrdine;
	}
	public Double getConto() {
		return conto;
	}
	public void setConto(Double conto) {
		this.conto = conto;
	}
}
