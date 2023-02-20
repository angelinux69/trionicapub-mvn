package it.trionica.web.model.dto.user;

import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

@ToString(includeFieldNames=true)
public class TavoloDTO {
	private Long idTavolo;
	private Integer coperto;
	private Boolean stato;
	private List<PrenotazioneDTO> prenotazioni = new ArrayList<>();
	
	public TavoloDTO() {
		super();
	}
	
	//Get & Set
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
	public List<PrenotazioneDTO> getPrenotazioni() {
		return prenotazioni;
	}
	public void setPrenotazioni(List<PrenotazioneDTO> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	
}
