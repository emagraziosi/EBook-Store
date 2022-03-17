package com.emanuele.ebookStore.model.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {
	
	@Id
	@GeneratedValue(generator = "uid-generator")
	@GenericGenerator(
			name = "uid-generator",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name = "sequence_name", value = "uId_Generator"),
					@Parameter(name = "initial_value", value = "1"),
			        @Parameter(name = "increment_size", value = "1")
				}
			)
	@Column(name = "uid")
	private long id;
	@NotNull
	private String email;
	@NotNull
	private boolean isAdmin;
	@NotNull
	private String logType;
	@CreatedDate
	private Date createdAt;
	private Date lastLogin;
	private boolean enabled;
	
	@OneToOne(mappedBy = "userId", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private UserInfo uInfo;
	
	@OneToMany(mappedBy = "userT", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Set<Transaction> transaction;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", isAdmin=" + isAdmin + ", logType=" + logType + ", createdAt="
				+ createdAt + ", lastLogin=" + lastLogin + "]";
	}
	
	public User(long id) {
		this.id = id;
	}
	
	public User() {
		super();
		this.enabled = false;
	}
}
