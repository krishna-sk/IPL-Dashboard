package com.ipl.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ipl.model.Match;
import com.ipl.model.Team;
import com.ipl.repository.MatchRepository;
import com.ipl.repository.TeamRepository;

@RestController
@CrossOrigin
@RequestMapping("/ipl")
public class TeamController {

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	MatchRepository matchRepository;

	@GetMapping("/team/{teamName}")
	public Team getTeam(@PathVariable String teamName) {
		Team team = teamRepository.findByTeamName(teamName);
		team.setLatestMatches(matchRepository.findLatestMatchesByTeam(teamName, 4));
		return team;
	}

	@GetMapping("/team/{teamName}/matches")
	public List<Match> getMatchesForTeam(@PathVariable String teamName, @RequestParam Integer year) {
		LocalDate startDate = LocalDate.of(year, 1, 1);
		LocalDate endDate = LocalDate.of(year + 1, 1, 1);
		return matchRepository.getMatchesByTeamBetweenDates(teamName, startDate, endDate);
	}

	@GetMapping("/all")
	public List<Team> getAllTeams() {
		return teamRepository.findAll(Sort.by(Sort.Direction.DESC, "totalWins"));
	}
}
