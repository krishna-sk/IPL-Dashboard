package com.ipl.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ipl.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

	Team findByTeamName(String teamName);

	List<Team> findAll(Sort sort);

}
