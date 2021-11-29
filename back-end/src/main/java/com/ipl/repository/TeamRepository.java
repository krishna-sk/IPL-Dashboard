package com.ipl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ipl.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

	Team findByTeamName(String teamName);

}
