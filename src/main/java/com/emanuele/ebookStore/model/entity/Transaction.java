package com.emanuele.ebookStore.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

	@Id
	@GeneratedValue(generator = "tid-generator")
	@GenericGenerator(
			name = "tid-generator",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name = "sequence_name", value = "tId_Generator"),
					@Parameter(name = "initial_value", value = "1"),
			        @Parameter(name = "increment_size", value = "1")
				}
			)
	@Column(name = "tid")
	private long id;
	@CreatedDate
	private Date dataRichiesta;
	private Date dataCompletamento;
	private String metodoPagamento;
	@NotNull
	private String status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User userT;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bookId")
	private BooksInStore bookT;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDataRichiesta() {
		return dataRichiesta;
	}
	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	public Date getDataCompletamento() {
		return dataCompletamento;
	}
	public void setDataCompletamento(Date dataCompletamento) {
		this.dataCompletamento = dataCompletamento;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public User getUserT() {
		return userT;
	}
	public void setUserT(User userT) {
		this.userT = userT;
	}
	public BooksInStore getBookT() {
		return bookT;
	}
	public void setBookT(BooksInStore bookT) {
		this.bookT = bookT;
	}
	public String getMetodoPagamento() {
		return metodoPagamento;
	}
	public void setMetodoPagamento(String metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}
	
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", dataRichiesta=" + dataRichiesta + ", dataCompletamento=" + dataCompletamento
				+ ", metodoPagamento=" + metodoPagamento + ", status=" + status + ", userT=" + userT.getId() + ", bookT="
				+ bookT.getId() + "]";
	}
	
	
}
