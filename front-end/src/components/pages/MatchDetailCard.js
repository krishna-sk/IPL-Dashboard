import { React } from "react";
import { Link } from "react-router-dom";
import "./MatchDetailCard.scss";

export const MatchDetailCard = (props) => {
  const { teamName, match } = props;
  if (!match) return null;

  const { date, venue, matchWinner, resultMargin, result } = match;

  const otherTeam = teamName === match.team1 ? match.team2 : match.team1;
  const otherTeamRoute = `/teams/${otherTeam}`;
  const isMatchWon = teamName === match.matchWinner;
  return (
    <div className={isMatchWon ? 'MatchDetailCard won-card' : 'MatchDetailCard lost-card'}>
      <div>
        <span>vs</span>
        <h1>
          <Link to={otherTeamRoute}>{otherTeam}</Link>
        </h1>
        <h2 className="match-date">{date}</h2>
        <h3 className="match-venue">at {venue}</h3>
        <h3 className="match-result">
          {matchWinner} won by {resultMargin} {result}
        </h3>
      </div>
      <div className="additional-detail">
        <h3>First Innings</h3>
        <p>{match.team1}</p>
        <h3>Second Innings</h3>
        <p>{match.team2}</p>
        <h3>Man of the match</h3>
          <p>{match.playerOfMatch}</p>
          <h3>Umpires</h3>
          <p>{match.umpire1}, {match.umpire2}</p>
      </div>
    </div>
  );
};
