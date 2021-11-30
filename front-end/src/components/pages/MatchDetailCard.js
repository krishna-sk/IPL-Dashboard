import { React } from "react";
import { Link } from "react-router-dom";

export const MatchDetailCard = (props) => {
  const { teamName, match } = props;
  if (!match) return null;

  const { date, venue, matchWinner, resultMargin, result } = match;

  const otherTeam = teamName === match.team1 ? match.team2 : match.team1;
  const otherTeamRoute = `/teams/${otherTeam}`;
  return (
    <div className="MatchDetailCard">
      <h3>Latest Matches</h3>
      <h1>
        vs <Link to={otherTeamRoute}>{otherTeam}</Link>
      </h1>
      <h2>{date}</h2>
      <h3>at {venue}</h3>
      <h3>
        {matchWinner} won by {resultMargin} {result}
      </h3>
    </div>
  );
};
