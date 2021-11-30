package com.ipl.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ipl.model.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {

	List<Match> getByTeam1OrTeam2OrderByDateDesc(String team1, String team2, Pageable pageable);

	/*
	 * List<Match>
	 * getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(String team1,
	 * LocalDate startDate, LocalDate endDate, String team2, LocalDate startDate1,
	 * LocalDate endDate1);
	 */

	 @Query("select m from Match m where (m.team1 = :teamName or m.team2 = :teamName) and m.date between :dateStart and :dateEnd order by date desc")
	    List<Match> getMatchesByTeamBetweenDates(
	        @Param("teamName") String teamName, 
	        @Param("dateStart") LocalDate dateStart, 
	        @Param("dateEnd") LocalDate dateEnd
	    );

	default List<Match> findLatestMatchesByTeam(String team, int count) {
		return getByTeam1OrTeam2OrderByDateDesc(team, team, PageRequest.of(0, count));
	}
}
