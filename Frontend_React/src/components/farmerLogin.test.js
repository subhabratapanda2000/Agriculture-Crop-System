// import React from "react";
// import ReactDOM from "react-dom";
// import FarmerLogin from "./FarmerLogin";
// import { render, screen } from "@testing-library/react";

// // it("renders without crashing", () => {
// //   const div = document.createElement("div");
// //   ReactDOM.render(<FarmerLogin></FarmerLogin>, div);
// // });

// test("renders learn react link", () => {
//   render(<FarmerLogin />);
//   const linkElement = screen.getByText(/learn react/i);
//   expect(linkElement).toBeInTheDocument();
// });

import React from "react";
import ReactDOM from "react-dom";
import FarmerLogin from "./FarmerLogin";
import About from "./About";

import { render } from "@testing-library/react";
// import "jest-dom/extend-expect";
import "@testing-library/jest-dom";

import renderer from "react-test-renderer";

it("matches snapshot", () => {
  const tree = renderer.create(<About></About>).toJSON();
  console.log("Farmer login");
  console.log(tree);
  expect(tree).toMatchSnapshot();
});
