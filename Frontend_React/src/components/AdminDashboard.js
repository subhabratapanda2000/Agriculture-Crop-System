import React from "react";
import { useEffect, useState } from "react";
import { Link, useHistory, useParams, NavLink } from "react-router-dom";
import adminService from "../services/auth.service";
import img1 from "../images/Admin2.png";
import img3 from "../images/farmer_field.jpg";
import dealerImg from "../images/office-worker-icon.png";
import cropImg from "../images/vegetable.png";
import farmerImg from "../images/farmer-icon.png";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Header2 from "./Header2";

const AdminDashboard = () => {
  const history = useHistory();
  const user = adminService.getCurrentUser();

  const logout = () => {
    toast.success("Log Out Successfully");
    adminService.logout();
    history.push(`/`);
    window.location.reload(false);
  };

  useEffect(() => {
    if (user) {
      if (user.role !== "ROLE_ADMIN") {
        history.push(`/error`);
        toast.error("You are not a Admin");
      }
    } else {
      history.push(`/error`);
      toast.error("You are not a Authenticate User");
    }
  }, []);

  return (
    <div>
      <Header2 />
      <div
        style={{
          backgroundImage: `url(${img3})`,
          backgroundAttachment: "fixed",
          minHeight: "720px",
          height: "100%",
          width: "100%",
          backgroundRepeat: "no-repeat",
          backgroundSize: "cover",
          backgroundBlendMode: "lighten",
          paddingTop: "5px",
        }}
      >
        <header></header>

        {/* <!-- Sidebar/menu --> */}
        <nav
          className="w3-sidebar w3-collapse w3-white w3-animate-left"
          style={{
            zIndex: "3",
            width: "300px",
            backgroundColor: "rgba(216, 227, 225, 0.7)",
          }}
          id="mySidebar"
        >
          <br />
          <div className="w3-container w3-row">
            <div className="w3-col s4">
              <img
                src={img1}
                className="w3-circle w3-margin-right"
                style={{ width: "80px" }}
              />
            </div>
            <div className="w3-col s8 w3-bar">
              <span>
                Welcome
                <br />
                {user.name}
              </span>
              <br />
            </div>
          </div>
          <hr />
          <div className="w3-container">
            <h5>Admin Dashboard</h5>
          </div>
          <div className="w3-bar-block">
            <Link
              to="/adminDashboard"
              className="w3-bar-item w3-button w3-padding"
            >
              <i className="fa fa-bullseye fa-fw"></i> Overview
            </Link>

            <Link
              onClick={() => logout()}
              className="w3-bar-item w3-button w3-padding"
            >
              <i className="fa fa-sign-out fa-fw"></i> Log Out
            </Link>
            <br />
            <br />
          </div>
        </nav>

        {/* <!-- Overlay effect when opening sidebar on small screens --> */}
        <div
          className="w3-overlay w3-hide-large w3-animate-opacity"
          style={{ cursor: "pointer" }}
          title="close side menu"
          id="myOverlay"
        ></div>

        {/* <!-- !PAGE CONTENT! --> */}
        <div
          className="w3-main"
          style={{ marginLeft: "300px", marginTop: "43px" }}
        >
          {/* <!-- Header --> */}
          <header className="w3-container" style={{ paddingTop: "22px" }}>
            <h5>
              <b>
                <i className="fa fa-dashboard"></i> Admin Dashboard
              </b>
            </h5>
          </header>

          <div className="w3-row-padding ">
            <Link to="/allFarmers">
              <div className="w3-quarter">
                <div
                  className="w3-container w3-aqua w3-padding-16"
                  style={{ width: "220px", height: "116px" }}
                >
                  <div className="w3-left">
                    <img
                      src={farmerImg}
                      className="fa fa-user w3-xxxlarge center"
                      style={{
                        paddingLeft: "70px",
                        height: "60px",
                        width: "130px",
                      }}
                    ></img>
                  </div>
                  <div className="w3-right"></div>
                  <div className="w3-clear"></div>
                  <h4 style={{ textAlign: "center" }}>Farmer Details</h4>
                </div>
              </div>
            </Link>

            <Link to="/allDealers">
              <div className="w3-quarter">
                <div
                  className="w3-container w3-green w3-padding-16"
                  style={{
                    width: "220px",
                    paddingLeft: "5px",
                    height: "116px",
                  }}
                >
                  <div className="w3-left">
                    <img
                      src={dealerImg}
                      className="fa fa-user w3-xxxlarge center"
                      style={{
                        paddingLeft: "70px",
                        height: "60px",
                        width: "130px",
                      }}
                    ></img>
                  </div>
                  <div className="w3-right"></div>
                  <div className="w3-clear"></div>
                  <h4 style={{ textAlign: "center" }}>Dealer Details</h4>
                </div>
              </div>
            </Link>

            <Link to="/allCrops_ad">
              <div className="w3-quarter">
                <div
                  className="w3-container w3-yellow w3-padding-16"
                  style={{ width: "220px", height: "116px" }}
                >
                  <div className="w3-left">
                    <img
                      src={cropImg}
                      className="fa fa-user w3-xxxlarge center"
                      style={{
                        height: "40px",
                        width: "200px",
                      }}
                    ></img>
                  </div>
                  <div className="w3-right"></div>
                  <div className="w3-clear"></div>
                  <h4 style={{ textAlign: "center" }}>Crop Details</h4>
                </div>
              </div>
            </Link>
            <Link to="/allPayments">
              <div className="w3-quarter">
                <div
                  className="w3-container w3-pink w3-padding-16"
                  style={{ width: "220px" }}
                >
                  <div className="w3-left">
                    <i
                      className="fa fa-money w3-xxxlarge center"
                      style={{ paddingLeft: "70px" }}
                    ></i>
                  </div>
                  <div className="w3-right"></div>
                  <div className="w3-clear"></div>
                  <h4 style={{ textAlign: "center" }}>Payment Details</h4>
                </div>
              </div>
            </Link>
          </div>

          <div className="w3-panel" id="Admin">
            <div
              className="w3-row-padding w3-margin-top"
              style={{ margin: "0 -16px" }}
            >
              {/* Write Code... */}

              <div
                style={{
                  backgroundColor: "#ffff99",
                  height: "100%",
                  width: "1000px",
                }}
              >
                <h5
                  style={{
                    paddingTop: "30px",
                    paddingLeft: "20px",
                    textAlign: "left",
                  }}
                >
                  Hello Admin,
                </h5>

                <p
                  style={{
                    paddingLeft: "130px",
                    textAlign: "left",
                    fontFamily: "Serif",
                    fontSize: "17px",
                  }}
                >
                  You are now the most responsible person of our organization.
                  You can access out total Database. We have given you the
                  responsibiity of total database. So, whatever you will do, do
                  it properly and carefully. Otherwise a small mistake can do
                  extreme damage to our Database.
                </p>
                <p
                  style={{
                    textAlign: "center",
                    fontFamily: "Serif",
                    fontSize: "17px",
                  }}
                >
                  If you face any problem in this system then contact to the
                  developer<br></br>
                  <Link to="/spanda"> Subhabrata Panda</Link>
                  <br />
                  <br />
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminDashboard;
