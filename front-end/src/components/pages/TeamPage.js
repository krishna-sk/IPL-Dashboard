import { React, useEffect, useState } from "react";
import { useParams } from "react-router";
import { MatchDetailCard } from "./MatchDetailCard";
import { MatchSmallCard } from "./MatchSmallCard";
import { PieChartCard } from "./PieChartCard";

import "./TeamPage.scss";

export const TeamPage = () => {
  const { teamName } = useParams();
  const [team, setTeam] = useState({});
  useEffect(() => {
    const fetchMatches = async () => {
      const response = await fetch(
        `http://localhost:8080/ipl/team/${teamName}`
      );
      const data = await response.json();
      setTeam(data);
    };

    fetchMatches();
  }, [teamName]);

  if (!team || !team.teamName) return <h1>Team Not Found</h1>;

  return (
    <div className="TeamPage">
      <div className="team-name-section">
        <h1 className="team-name">{team.teamName}</h1>
      </div>
      <div className="win-loss-section">
        Wins / Losses
        <PieChartCard Wins = {team.totalWins} Losses = {team.totalMatches-team.totalWins}/>
      </div>

      <div className="match-detail-section">
        <h3>Latest Matches</h3>
        <MatchDetailCard
          teamName={team.teamName}
          match={team.latestMatches[0]}
        />
      </div>
      {team.latestMatches.slice(1).map((match) => (
        <MatchSmallCard teamName={team.teamName} match={match} />
      ))}
      <div className="more-link">
        <a href="#">More ></a>
      </div>
    </div>
  );
};
