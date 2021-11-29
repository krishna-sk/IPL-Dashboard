package com.ipl.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ipl.model.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {

	List<Match> getByTeam1OrTeam2OrderByDateDesc(String team1, String team2, Pageable pageable);

	default List<Match> findLatestMatchesByTeam(String team, int count) {
		return getByTeam1OrTeam2OrderByDateDesc(team, team, PageRequest.of(0, count));
	}
}
