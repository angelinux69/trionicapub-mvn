package it.trionica.web.model.dto.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import lombok.ToString;

@ToString(includeFieldNames=true)
public class ProdottoDTO {
	private Long idProdotto;
	private String nomeProdotto;
	private String tipologia;
	private Double gradazione;
	private String marca;
	private String cantina;
	private Integer quantita;
	private Integer quantitaOrdinata;
	private Double prezzoA;
	private Double prezzoV;
	private List<OrdineProdottoDTO> ordineProdotto = new ArrayList<>();
	
	
	//Get & Set
	public Long getIdProdotto() {
		return idProdotto;
	}
	public void setIdProdotto(Long idProdotto) {
		this.idProdotto = idProdotto;
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
	public Integer getQuantitaOrdinata() {
		return quantitaOrdinata;
	}
	public void setQuantitaOrdinata(Integer quantitaOrdinata) {
		this.quantitaOrdinata = quantitaOrdinata;
	}
	
	public List<OrdineProdottoDTO> getOrdineProdotto() {
		return ordineProdotto;
	}
	
	public void setOrdineProdotto(List<OrdineProdottoDTO> ordineProdotto) {
		this.ordineProdotto = ordineProdotto;
	}
	
}
