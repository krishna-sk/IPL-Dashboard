package com.ipl.batch_processing;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ipl.model.Team;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private final EntityManager entityManager;

	@Autowired
	public JobCompletionNotificationListener(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");

			Map<String, Team> teamData = new HashMap<>();

			entityManager.createQuery("select m.team1, count(*) from Match m group by m.team1", Object[].class)
					.getResultList().stream().map(e -> new Team((String) e[0], (Long) e[1]))
					.forEach(team -> teamData.put(team.getTeamName(), team));

			entityManager.createQuery("select m.team2, count(*) from Match m group by m.team2", Object[].class)
					.getResultList().stream().forEach(e -> {
						Team team = teamData.get((String) e[0]);
						team.setTotalMatches(team.getTotalMatches() + (Long) e[1]);
					});

			entityManager
					.createQuery("select m.matchWinner, count(*) from Match m group by m.matchWinner", Object[].class)
					.getResultList().stream().forEach(e -> {
						Team team = teamData.get((String) e[0]);
						if (team != null)
							team.setTotalWins((Long) e[1]);
					});

			teamData.values().forEach(team -> entityManager.persist(team));
		}
	}
}