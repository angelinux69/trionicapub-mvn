package it.trionica.web.model.dto.user;

import lombok.ToString;

@ToString(includeFieldNames=true)
public class OrdineProdottoDTO {
	
	private Long id;
	private OrdineDTO ordine;
	private ProdottoDTO prodotto;
	private Integer quantitaOrdine;
	
	// Get & Set
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public OrdineDTO getOrdine() {
		return ordine;
	}
	public void setOrdine(OrdineDTO ordine) {
		this.ordine = ordine;
	}
	public ProdottoDTO getProdotto() {
		return prodotto;
	}
	public void setProdotto(ProdottoDTO prodotto) {
		this.prodotto = prodotto;
	}
	public Integer getQuantitaOrdine() {
		return quantitaOrdine;
	}
	public void setQuantitaOrdine(Integer quantitaOrdine) {
		this.quantitaOrdine = quantitaOrdine;
	}
	
}
