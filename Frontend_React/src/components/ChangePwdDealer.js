import React from "react";
import { useEffect, useState } from "react";
import { Link, useHistory, useParams } from "react-router-dom";
import dealerService from "../services/auth.service";
import "../style/CPEmp.css";
import img1 from "../images/password1.png";
import img2 from "../images/office-worker-icon.png";
import img3 from "../images/farmer_field.jpg";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Header2 from "./Header2";

const ChangePwdDealer = () => {
  const user = dealerService.getCurrentUser();
  const [newPassword, setNewPassword] = useState("");
  const [oldPassword, setOldPassword] = useState("");
  const [repassword, setRePassword] = useState("");
  const [loading, setLoading] = useState(false);
  const history = useHistory();

  const [errAlert1, setErrAlert1] = useState(false);
  const [errAlert2, setErrAlert2] = useState(false);
  const [errAlert3, setErrAlert3] = useState(false);

  const updatePassword = () => {
    setLoading(true);
    setErrAlert1(false);
    setErrAlert2(false);
    setErrAlert3(false);

    if (newPassword != repassword) {
      setErrAlert2(true);
      setLoading(false);
      // alert("Please give same password on retype password");
    } else {
      const pass = {
        oldPassword,
        newPassword,
      };
      dealerService
        .changePassDealer(user.id, pass)
        .then(() => {
          toast.success("Password Update Succefully");
          history.push(`/dealerDashboard`);
        })
        .catch((error) => {
          setLoading(false);
          setErrAlert3(true);
          var errormsg = error.response.data;
          var array = errormsg.split(":");
          console.log("Something went wrong in Data Create", error);
          toast.error(array[array.length - 1]);
        });
    }
  };

  useEffect(() => {
    if (user) {
      if (user.role !== "ROLE_DEALER") {
        history.push(`/error`);
        toast.error("You are not a Dealer");
      }
    } else {
      history.push(`/error`);
      toast.error("You are not a Authenticate User");
    }
  });

  let errAlert = false;
  let addAlert = "";
  if (errAlert1 === true) {
    addAlert = "Incorrect old password";
    errAlert = true;
  } else if (errAlert2 === true) {
    addAlert = "Please give same password on retype password";
    errAlert = true;
  } else if (errAlert3 === true) {
    addAlert = "password not changed";
    errAlert = true;
  } else {
    errAlert = false;
  }

  const logout = () => {
    toast.success("Log Out Successfully");
    dealerService.logout();
    history.push(`/`);
  };

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
      <Header2></Header2>

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
              src={img2}
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
          <h5>Dealer Dashboard</h5>
        </div>
        <div className="w3-bar-block">
          <Link
            to="#"
            className="w3-bar-item w3-button w3-padding-16 w3-hide-large w3-dark-grey w3-hover-black"
            //onClick="w3_close()"
            title="close menu"
          >
            <i className="fa fa-remove fa-fw"></i> Close Menu
          </Link>

          <Link
            to="/dealerDashboard"
            className="w3-bar-item w3-button w3-padding"
          >
            <i className="fa fa-bullseye fa-fw"></i> Overview
          </Link>
          <Link
            to={`/changePwdDealer`}
            className="w3-bar-item w3-button w3-padding"
          >
            <i className="fa fa-edit"></i> Change Password
          </Link>
          <Link to="/sendOffers" className="w3-bar-item w3-button w3-padding">
            <i className="fa fa-comment"></i> Send Offers to Farmers
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
      {/* Code Write... */}
      <div style={{ paddingTop: "30px" }}>
        <div id="d1" style={{ border: "solid", height: "100%" }}>
          {errAlert && (
            <div
              className="alert alert-warning alert-dismissible fade show"
              role="alert"
              style={{
                backgroundColor: "red",
                color: "white",
                marginTop: "10px",
              }}
            >
              <strong>{addAlert}</strong>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="alert"
                aria-label="Close"
                onClick={() => {
                  setErrAlert1(false);
                  setErrAlert2(false);
                  setErrAlert3(false);
                }}
              ></button>
            </div>
          )}
          <img src={img1} className="img" path="/emp/password" />
          <p className="p"> Password </p>
          <input
            className="input"
            type="text"
            value={oldPassword}
            onChange={(p) => setOldPassword(p.target.value)}
            placeholder="Old Password"
            required
          />
          <br />
          <br />
          <input
            className="input"
            type="password"
            value={newPassword}
            onChange={(p) => setNewPassword(p.target.value)}
            placeholder="New Password"
            required
          />
          <br />
          <br />
          <input
            className="input2"
            type="password"
            value={repassword}
            onChange={(p) => setRePassword(p.target.value)}
            placeholder="Retype New Password"
            required
          />
          <br />
          <br />
          <button
            onClick={() => updatePassword()}
            className="input1"
            style={{ backgroundColor: "#4db8ff" }}
          >
            Save
          </button>
          <br />
          <Link className="btn btn-primary" to={"/dealerDashboard"}>
            Cancel
          </Link>
          <div className="d-flex justify-content-center">
            {loading && (
              <span
                style={{ width: "3rem", height: "3rem" }}
                className="spinner-border spinner-border-large text-primary"
              ></span>
            )}
          </div>
          <br />
          {/*  <p1>Remeber me </p1>   */}
          {/* &nbsp; <Link to="#" className="a text-center">Forget Password?</Link> */}
          {/*  <br><br><br>
   <p1><center>not a member?</center></p1>
   <button>Create account</button>  */}
        </div>
      </div>
    </div>
  );
};

export default ChangePwdDealer;
