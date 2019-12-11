package it.my.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.my.test.model.history.GlyphHistory;

public interface GlyphHistoryRepository extends JpaRepository<GlyphHistory, Integer> {}
