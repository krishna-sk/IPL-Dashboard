import { React } from "react";
import { useState, useEffect } from "react";
import { MatchDetailCard } from "./MatchDetailCard";
import { useParams } from "react-router";
import "./MatchPage.scss";
import { YearSelector } from "./YearSelector";

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
      <div className="year-selector">
        <h4>Select Year</h4>
        <YearSelector teamName={teamName}/>
      </div>
      <div>
        <h1 className="page-heading">{teamName} matches in {year}</h1>
        {matches.length !== 0 || (
          <div style={{ marginTop: "15px", fontSize: "3rem" }}>
            {teamName} doesn't played any matches in the {year}
          </div>
        )}
        {matches.length !== 0 &&
          matches.map((match) => (
            <MatchDetailCard teamName={teamName} match={match} />
          ))}
      </div>
    </div>
  );
};
