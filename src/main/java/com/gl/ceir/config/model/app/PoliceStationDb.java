package com.gl.ceir.config.model.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "police_station")
public class PoliceStationDb {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "created_on")
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDateTime createdOn;

	@Column(name = "modified_on")
	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDateTime modifiedOn;

	@Column(name = "commune_id")
	private int communeId;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "commune_id", insertable = false, updatable = false)
	@JsonProperty(value = "communeDb", access = JsonProperty.Access.READ_ONLY)
	private CommuneDb communeDb;

	@Column(name = "police")
	private String police;

	@Column(name = "police_km")
	private String policeKm;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}



	public String getPolice() {
		return police;
	}

	public void setPolice(String police) {
		this.police = police;
	}

	public String getPoliceKm() {
		return policeKm;
	}

	public void setPoliceKm(String policeKm) {
		this.policeKm = policeKm;
	}


	public CommuneDb getCommuneDb() {
		return communeDb;
	}

	public void setCommuneDb(CommuneDb communeDb) {
		this.communeDb = communeDb;
	}

	public int getCommuneId() {
		return communeId;
	}

	@Override
	public String toString() {
		return "PoliceStationDb{" +
				"id=" + id +
				", createdOn=" + createdOn +
				", modifiedOn=" + modifiedOn +
				", communeId=" + communeId +
				", communeDb=" + communeDb +
				", police='" + police + '\'' +
				", policeKm='" + policeKm + '\'' +
				'}';
	}
}

