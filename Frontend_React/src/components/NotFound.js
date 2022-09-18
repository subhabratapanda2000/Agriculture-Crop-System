import React from "react";
import img3 from "../images/farmer_field.jpg";
import img4 from "../images/smily.png";
import Header from "./Header";

const NotFound = () => {
  return (
    <div
      style={{
        backgroundImage: `url(${img3})`,
        backgroundAttachment: "fixed",
        height: "720px",
        width: "100%",
        backgroundRepeat: "no-repeat",
        backgroundSize: "cover",
        backgroundBlendMode: "lighten",
        paddingTop: "5px",
      }}
    >
      <Header></Header>
      <h1 style={{ textAlign: "center", paddingTop: "200px" }}>
        Sorry!<br></br>
        The page you are looking for is not yours, You can't access this page.
      </h1>
      <span>
        <img
          src={img4}
          style={{
            width: "880px",
            hight: "880px",
            paddingLeft: "620px",
            paddingTop: "50px",
          }}
        ></img>
      </span>
    </div>
  );
};

export default NotFound;
