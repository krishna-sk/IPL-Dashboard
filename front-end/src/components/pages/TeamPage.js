import { React, useEffect, useState } from "react";
import { useParams } from "react-router";
import { MatchDetailCard } from "./MatchDetailCard";
import { MatchSmallCard } from "./MatchSmallCard";

export const TeamPage = () => {
  const { teamName } = useParams();
  const intialState = {
    teamName: "",
    latestMatches: []
  };
  const [team, setTeam] = useState(intialState);
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
      <h1>{team.teamName}</h1>

      <MatchDetailCard teamName={team.teamName} match={team.latestMatches[0]} />
      {team.latestMatches.slice(1).map((match) => (
        <MatchSmallCard teamName={team.teamName} match={match} />
      ))}
    </div>
  );
};
