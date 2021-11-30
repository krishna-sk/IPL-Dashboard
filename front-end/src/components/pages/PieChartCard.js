import { React } from "react";
import { PieChart } from "react-minimal-pie-chart";

export const PieChartCard = (props) => {
  return (
    <PieChart
      data={[
        { title: "Loses", value: props.Losses, color: "#a34d5d" },
        { title: "Wins", value: props.Wins, color: "#4da375" }
      ]}
    />
  );
};
