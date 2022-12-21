package it.trionica.web.model.dto.user;

import java.time.LocalTime;

import org.primefaces.component.clock.Clock;

import lombok.ToString;

@ToString(includeFieldNames=true)
public class PrenotazioneDTO {
	
	private Long idPrenotazione;
	private String nome;
	private Integer cell;
	private String ora;
	private TavoloDTO tavolo;
	
	
	//Get & Set
	public Long getIdPrenotazione() {
		return idPrenotazione;
	}

	public void setIdPrenotazione(Long idPrenotazione) {
		this.idPrenotazione = idPrenotazione;
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

	public TavoloDTO getTavolo() {
		return tavolo;
	}

	public void setTavolo(TavoloDTO tavolo) {
		this.tavolo = tavolo;
	}
	
}
