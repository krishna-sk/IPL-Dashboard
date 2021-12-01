import { React } from "react";
import { Link } from "react-router-dom";

import "./TeamTile.scss";
export const TeamTile = (props) => {
    const { teamName, totalMatches, totalWins } = props.team;
  return (
    <div className="TeamTile">
      <h1 className="heading">
        <Link to={`/teams/${teamName}`}>{teamName}</Link>
      </h1>
      <div className="data">
        <h2 className="total-matches">Total Matches : {totalMatches}</h2>
        <h2 className="total-wins">Total Wins : {totalWins}</h2>
      </div>
    </div>
  );
};
