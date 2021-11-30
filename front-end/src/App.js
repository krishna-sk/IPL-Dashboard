import "./App.scss";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { TeamPage } from "./components/pages/TeamPage";
import { MatchPage } from "./components/pages/MatchPage";

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/teams/:teamName" element={<TeamPage />} />
          <Route path="/teams/:teamName/matches/:year" element={<MatchPage />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
