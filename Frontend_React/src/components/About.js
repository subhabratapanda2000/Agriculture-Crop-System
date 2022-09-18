import React from "react";

import img1 from "../images/about_us_crop.png";
import Header from "./Header";
import { Link } from "react-router-dom";

const About = () => {
  return (
    <div>
      <Header />
      <div
        style={{
          backgroundImage: `url(${img1})`,
          height: "700px",
          width: "100%",
          backgroundRepeat: "no-repeat",
          backgroundSize: "cover",
          backgroundBlendMode: "lighten",
        }}
      >
        <p
          className="display-1 text-center pb-1"
          style={{
            paddingTop: "40px",
            paddingLeft: "40px",
            paddingRight: "40px",
            fontSize: "24px",
            border: "2px solid ",
            borderRadius: "25px",
            backgroundColor: "rgba(216, 227, 225, 0.5)",
            height: "220px",
          }}
        >
          This <b>Agriculture Crop System</b> act as a bridge between the Farmer
          and the Dealer (Crop purchaser). There is no need for the farmer to
          carry the crop till the market, paying unexpected commissions and wait
          for proper price from the Dealers. Whenever farmer want to sell the
          vegetables/fruits at the Farm itself, he just wants to select the type
          of Crop, quantity available, input the location/address and publish
          the information to Dealers. Whoever(Dealer) is interested in
          purchasing will connect with the Farmer, will reach the location,
          checks the quality of the crop, negotiates the price and buy the crop.
        </p>
      </div>
    </div>
  );
};

export default About;
