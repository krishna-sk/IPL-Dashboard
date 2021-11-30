import { React } from "react";
import {Link} from 'react-router-dom'
export const MatchSmallCard = (props) => {
  const { teamName, match } = props;
  if (!match) return null;

  const otherTeam = teamName === match.team1 ? match.team2 : match.team1;
  const {matchWinner,resultMargin,result} = match;
  const otherTeamRoute = `/teams/${otherTeam}`;
  return (
    <div className="MatchSmallCard">
      <h3>vs <Link to={otherTeamRoute}>{otherTeam}</Link></h3>
      <p>{matchWinner} won by {resultMargin} {result}</p>
    </div>
  );
};
