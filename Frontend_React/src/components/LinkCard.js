import React from "react";
import { Link } from "react-router-dom";

const LinkCard = (props) => {
  return (
    <div
      className="card admin-card col mx-4 mt-3"
      style={{
        backgroundColor: "rgba(216, 227, 225, 0.5)",
        borderRadius: "20px",
      }}
    >
      <img
        src={props.imgsrc}
        height="130"
        className="card-img-top mt-1"
        alt="Image 1"
      ></img>
      <div className="card-body">
        <h4 className="card-title" style={{ color: "blue" }}>
          {props.title}
        </h4>
        <h6 className="card-text" style={{ color: "blue" }}>
          {props.para}
        </h6>
        <Link
          to={props.path}
          className="btn btn-outline-success"
          style={{ backgroundColor: "white", color: "green" }}
        >
          Go
        </Link>
      </div>
    </div>
  );
};

export default LinkCard;
