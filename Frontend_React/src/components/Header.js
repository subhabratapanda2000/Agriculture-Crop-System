import React from "react";
// import { connect } from 'react-redux';
import { useEffect, useState } from "react";
import { Link, useHistory, NavLink, Redirect } from "react-router-dom";
import img2 from "../images/crop.png";
import Timer from "../components/Timer";
import service from "../services/auth.service";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const Header = () => {
  const [dashboard, setDashboard] = useState("");
  const [logout, setLogout] = useState("");
  const [name, setName] = useState("");
  const [urlAd, setUrl] = useState("/");
  const [admin, setAdmin] = useState("Admin");
  const [dealer, setDealer] = useState("Dealer");
  const [farmer, setFarmer] = useState("Farmer");
  const history = useHistory();

  const init = () => {
    if (service.getCurrentUser()) {
      setDashboard("My DashBoard");
      setLogout("Logout");
      const user = service.getCurrentUser();

      if (user.role === "ROLE_FARMER") {
        setUrl("/farmerDashboard");
      } else if (user.role === "ROLE_DEALER") {
        setUrl("/dealerDashboard");
      } else if (user.role === "ROLE_ADMIN") {
        setUrl("/adminDashboard");
      } else {
        setUrl("/error");
      }
      setAdmin("");
      setDealer("");
      setFarmer("");
      setName(user.name);
    }
  };

  const handleLogout = () => {
    service.logout();
    history.push("/");
    toast.success("You have log out successfully");
    window.location.reload(false);
  };

  useEffect(() => {
    init();
  }, []);

  const activeStyle = {
    fontWeight: "bold",
    color: "#0b0040",
  };

  return (
    <nav
      className="navbar navbar-expand-lg navbar-light bg-light"
      // style={{ position: "fixed" }}
    >
      <div
        className="navbar navbar-expand-sm  navbar-dark fixed-top"
        style={{
          backgroundColor: "#FDB059",
          paddingTop: "0px",
          paddingBottom: "0px",
          height: "40px",
        }}
      >
        <div className="container-fluid">
          <div className="navbar-brand">
            <span className="w3-bar-item w3-left mt-1">
              <img src={img2} style={{ width: "40px", hight: "10px" }} />
            </span>
            {/* <img src={img1} style={{width:"170px", hight:"40px"}} /> */}
          </div>
          <button
            className="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div
            className="collapse navbar-collapse justify-content-between"
            id="navbarNav"
          >
            <ul className="navbar-nav">
              <li className="nav-item active">
                <NavLink className="nav-link fw-bold" to={urlAd}>
                  HOME
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink
                  className="nav-link fw-bold"
                  activeStyle={activeStyle}
                  to="/admin/login"
                >
                  {admin}
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink
                  className="nav-link fw-bold"
                  activeStyle={activeStyle}
                  to="/dealerlogin"
                >
                  {dealer}
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink
                  className="nav-link fw-bold"
                  activeStyle={activeStyle}
                  to="/farmerlogin"
                >
                  {farmer}
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink
                  className="nav-link fw-bold"
                  activeStyle={activeStyle}
                  to="/aboutus"
                >
                  ABOUT US
                </NavLink>
              </li>

              <li className="nav-item">
                <NavLink
                  className="nav-link fw-bold"
                  activeStyle={activeStyle}
                  to="/"
                  onClick={() => {
                    handleLogout();
                  }}
                >
                  {logout}
                </NavLink>
              </li>
            </ul>

            <ul className="navbar-nav">
              <li className="nav-item">
                <div className="d-flex">
                  <NavLink
                    style={{
                      margin: "10px",
                      marginTop: "21px",
                      color: "black",
                    }}
                    to={urlAd}
                  >
                    <b>{name}</b>
                  </NavLink>
                </div>
              </li>
              <li className="nav-item">
                <div className="d-flex">
                  <Timer></Timer>
                </div>
              </li>
            </ul>
            {/* {auths === true ? <Redirect to='/' /> : null} */}
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Header;
