package it.my.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.my.test.model.history.RuneHistory;

public interface RuneHistoryRepository extends JpaRepository<RuneHistory, Integer> {}
