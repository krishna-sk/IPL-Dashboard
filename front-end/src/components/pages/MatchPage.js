import { React } from "react";
import { useState, useEffect } from "react";
import { MatchDetailCard } from "./MatchDetailCard";
import { useParams } from "react-router";

export const MatchPage = () => {
  const [matches, setmatches] = useState([]);

  const { teamName, year } = useParams();

  useEffect(() => {
    const fetchMatches = async () => {
      const response = await fetch(
        `http://localhost:8080/ipl/team/${teamName}/matches?year=${year}`
      );
      const data = await response.json();
      setmatches(data);
    };

    fetchMatches();
  }, [teamName, year]);

  return (
    <div className="MatchPage">
      <h1>MatchPage</h1>
      {matches.map((match) => (
        <MatchDetailCard teamName={teamName} match={match} />
      ))}
    </div>
  );
};
