package com.emanuele.ebookStore.model.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


@Entity
public class BooksInStore {

	@Id
	@GeneratedValue(generator = "bid-generator")
	@GenericGenerator(
			name = "bid-generator",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name = "sequence_name", value = "bId_Generator"),
					@Parameter(name = "initial_value", value = "1"),
			        @Parameter(name = "increment_size", value = "1")
				}
			)
	@Column(name = "bid")
	private long id;
	@NotNull
	private String titolo;
	private String copertina;
	@NotNull
	private String descrizione;
	private String genere;
	private String autore;
	@NotNull
	private float prezzo;
	private long numeroAcquisti = 0;
	
	@OneToMany(mappedBy = "bookT", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Set<Transaction> transaction;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getCopertina() {
		return copertina;
	}
	public void setCopertina(String copertina) {
		this.copertina = copertina;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public float getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}
	public String getGenere() {
		return genere;
	}
	public void setGenere(String genere) {
		this.genere = genere;
	}
	public String getAutore() {
		return autore;
	}
	public void setAutore(String autore) {
		this.autore = autore;
	}
	public long getNumeroAcquisti() {
		return numeroAcquisti;
	}
	public void setNumeroAcquisti(long numeroAcquisti) {
		this.numeroAcquisti = numeroAcquisti;
	}
	
	@Override
	public String toString() {
		return "BooksInStore [id=" + id + ", titolo=" + titolo + ", copertina=" + copertina + ", descrizione="
				+ descrizione + ", genere=" + genere + ", autore=" + autore + ", prezzo=" + prezzo + ", numeroAcquisti="
				+ numeroAcquisti + "]";
	}
	
	public BooksInStore(long id) {
		this.id = id;
	}
	
	public BooksInStore() {
		super();
	}
}
