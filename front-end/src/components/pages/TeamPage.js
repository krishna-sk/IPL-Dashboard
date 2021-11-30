import { React, useEffect, useState } from "react";
import { MatchDetailCard } from "./MatchDetailCard";
import { MatchSmallCard } from "./MatchSmallCard";

export const TeamPage = () => {
  const intialState = {
    teamName: "",
    latestMatches: []
  };
  const [team, setTeam] = useState(intialState);
  useEffect(() => {
    const fetchMatches = async () => {
      const response = await fetch(
        "http://localhost:8080/ipl/team/Delhi%20Daredevils"
      );
      const data = await response.json();
      setTeam(data);
    };

    fetchMatches();
  }, []);

  return (
    <div className="TeamPage">
      <h1>{team.teamName}</h1>

      <MatchDetailCard match={team.latestMatches[0]} />
      {team.latestMatches.slice(1).map((match) => (
        <MatchSmallCard match={match} />
      ))}
    </div>
  );
};
