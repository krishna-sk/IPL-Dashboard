import { React } from "react";
import {Link} from 'react-router-dom'
import  "./MatchSmallCard.scss";

export const MatchSmallCard = (props) => {
  const { teamName, match } = props;
  if (!match) return null;

  const otherTeam = teamName === match.team1 ? match.team2 : match.team1;
  const {matchWinner,resultMargin,result} = match;
  const otherTeamRoute = `/teams/${otherTeam}`;
  const isMatchWon = teamName === match.matchWinner;
  return (
    <div className={isMatchWon ? 'MatchSmallCard won-card' : 'MatchSmallCard lost-card'}>
      <span>vs</span>
        <h1>
          <Link to={otherTeamRoute}>{otherTeam}</Link>
        </h1>
      <p>{matchWinner} won by {resultMargin} {result}</p>
    </div>
  );
};
