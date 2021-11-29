package com.ipl.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String teamName;

	private long totalMatches;

	private long totalWins;
	
	@Transient
	private List<Match> latestMatches;

	public Team(String teamName, Long totalMatches) {
		this.teamName = teamName;
		this.totalMatches = totalMatches;
	}

}
