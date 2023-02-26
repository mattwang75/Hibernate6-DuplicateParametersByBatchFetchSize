package com.example.hibernate.repository;

import com.example.hibernate.model.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EncounterRepository extends JpaRepository<Encounter, Long> {
	Optional<Encounter> findEncounterByIdentifier(String identifier);
}
