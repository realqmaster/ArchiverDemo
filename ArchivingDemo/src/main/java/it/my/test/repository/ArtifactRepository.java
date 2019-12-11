package it.my.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.my.test.model.Artifact;

public interface ArtifactRepository extends JpaRepository<Artifact, Integer>{

}
