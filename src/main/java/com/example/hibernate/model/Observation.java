package com.example.hibernate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Observation extends IdObject {
	@Column(name = "note")
	private String note;
	public String getNote() {
		return this.note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_encounter", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "FK_observation_has_encounter"))
	private Encounter encounter;
	public void setEncounter(Encounter encounter) {
		this.encounter = encounter;
	}
	public Encounter getEncounter() {
		return this.encounter;
	}
}
