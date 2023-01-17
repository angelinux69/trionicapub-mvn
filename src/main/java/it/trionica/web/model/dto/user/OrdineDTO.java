package it.trionica.web.model.dto.user;

import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

@ToString(includeFieldNames=true)
public class OrdineDTO {
	private Long idOrdine;
	private TavoloDTO tavolo;
	private List<ProdottoDTO> prodotto = new ArrayList<>();
	private Integer quantitaOrdine;
	private Double conto;
	
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
