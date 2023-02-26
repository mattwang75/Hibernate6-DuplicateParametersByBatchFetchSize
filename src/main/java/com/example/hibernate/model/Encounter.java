package com.example.hibernate.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Encounter extends IdObject {
	@Column(name = "identifier")
	private String identifier;
	public String getIdentifier() {
		return this.identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "encounter")
	private Set<Observation> observations = new HashSet<>();
	public Set<Observation> getObservations() {
		return this.observations;
	}
	public void setObservations(Set<Observation> observations) {
		this.observations = observations;
	}
}

