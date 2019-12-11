package it.my.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import it.my.test.model.Artifact;
import it.my.test.model.history.ArtifactHistory;

public interface ArtifactHistoryRepository extends JpaRepository<ArtifactHistory, Integer>,  JpaSpecificationExecutor<Artifact>  {

}
