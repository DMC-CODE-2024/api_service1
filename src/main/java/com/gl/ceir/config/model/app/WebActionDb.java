package com.gl.ceir.config.model.app;



import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;



@Entity
@Table(name = "web_action_db")
public class WebActionDb implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	@Column(name="created_on")
	LocalDateTime createdOn;

	@UpdateTimestamp
	@Column(name="modified_on")
	LocalDateTime modifiedOn;

	@Column(name="feature")
	private String feature ;

	@Column(name="sub_feature")
	private String subFeature;

	@Column(name="txn_id")
	private String txnId;

	@Column(name="state")
	private int state;

	@Column(name="data")
	private String data;


	public WebActionDb() {
	}
	
	public WebActionDb(String feature, String subFeature, int state, String txnId) {
		this.feature = feature;
		this.subFeature = subFeature;
		this.state = state;
		this.txnId = txnId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getSubFeature() {
		return subFeature;
	}

	public void setSubFeature(String subFeature) {
		this.subFeature = subFeature;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "WebActionDb{" +
				"id=" + id +
				", feature='" + feature + '\'' +
				", subFeature='" + subFeature + '\'' +
				", txnId='" + txnId + '\'' +
				", state=" + state +
				", data='" + data + '\'' +
				'}';
	}
}
