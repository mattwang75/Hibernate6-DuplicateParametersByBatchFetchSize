package com.example.hibernate;

import com.example.hibernate.model.Encounter;
import com.example.hibernate.model.Observation;
import com.example.hibernate.repository.EncounterRepository;
import com.example.hibernate.repository.ObservationRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class HibernateApplicationTests {
	@Autowired EncounterRepository encounterRepository;
	@Autowired ObservationRepository observationRepository;

	@Test
	@Rollback(false)
	void createEncounterObservations() {
		Encounter encounter = makeEncounter();
		Set<Observation> observations = addObservations(encounter);
		encounterRepository.save(encounter);
	}

	@Test
	void findEncounterObservations() {
		System.out.println("===== Loading encounter =====");
		Encounter encounter = encounterRepository.findEncounterByIdentifier("Medical-Record-Number-123456").orElse(null);
		assertThat(encounter).isNotNull();
		assertThat(encounter.getId()).isNotNull();

		System.out.println("===== Accessing observations =====");
		System.out.println(
			"If default_batch_fetch_size is set to 50 (in application.properties), then the log shows that "
			+ "this single encounter id is repeatedly bound as a parameter to the SQL query 50 times!!! "
			+ "That's utterly unnecessary in terms of loading this one-to-many collection and in fact causes our "
			+ "PostgreSQL database significantly longer to execute the query.  "
			+ "The default_batch_fetch_size tuning has its use such as when doing FetchMode.SELECT to reduce "
			+ "round-trips, but it's not applicable here.  Please fix!"
		);
		System.out.println("==================================");
		Set<Observation> observations = encounter.getObservations();
		assertThat(observations).hasSize(3);

		System.out.println("===== Completed =====");
	}

	//
	// private helper functions
	//

	private Encounter makeEncounter() {
		final Encounter encounter = new Encounter();
		encounter.setIdentifier("Medical-Record-Number-123456");
		return encounter;
	}

	private Set<Observation> addObservations(Encounter encounter) {
		Observation observation1 = new Observation();
		observation1.setEncounter(encounter);
		encounter.getObservations().add(observation1);
		observation1.setNote("BMI 30");

		Observation observation2 = new Observation();
		observation2.setEncounter(encounter);
		encounter.getObservations().add(observation2);
		observation2.setNote("Height 6 ft");

		Observation observation3 = new Observation();
		observation3.setEncounter(encounter);
		encounter.getObservations().add(observation3);
		observation3.setNote("Weight 200 lb");

		return Set.of(observation1, observation2, observation3);
	}
}
