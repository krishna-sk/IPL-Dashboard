import { React } from "react";
import { useState, useEffect } from "react";
import { TeamTile } from "./TeamTile";
import "./HomePage.scss";

export const HomePage = () => {
  const [teams, setTeams] = useState([]);

  useEffect(() => {
    const fetchTeams = async () => {
      const response = await fetch(`http://localhost:8080/ipl/all`);
      const data = await response.json();
      setTeams(data);
    };

    fetchTeams();
  }, []);
  return (
    <div className="HomePage">
      <div className="header-section">
        <h1 className="app-name">IPL Dashboard</h1>
      </div>
      <div className="team-grid">
        {teams.map((team) => (
          <TeamTile key={team.id} team={team} />
        ))}
      </div>
    </div>
  );
};
