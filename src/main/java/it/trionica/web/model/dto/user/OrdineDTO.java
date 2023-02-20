package it.trionica.web.model.dto.user;

import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

@ToString(includeFieldNames=true)
public class OrdineDTO {
	private Long idOrdine;
	private TavoloDTO tavolo;
	private Double conto;
	private List<OrdineProdottoDTO> ordineProdotto = new ArrayList<>();
	
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
	public Double getConto() {
		return conto;
	}
	public void setConto(Double conto) {
		this.conto = conto;
	}
	
	public List<OrdineProdottoDTO> getOrdineProdotto() {
		return ordineProdotto;
	}
	
	public void setOrdineProdotto(List<OrdineProdottoDTO> ordineProdotto) {
		this.ordineProdotto = ordineProdotto;
	}
	
}
