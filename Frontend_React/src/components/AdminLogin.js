import React from "react";
import img1 from "../images/Admin2.png";
import "../style/farmerlogin.css";
import { useState, useEffect } from "react";
import { Link, useHistory, useParams } from "react-router-dom";
import "../style/adminlogin.css";
import img3 from "../images/farmer_field.jpg";
import adminService from "../services/auth.service";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Header from "./Header";

const AdminLogin = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const history = useHistory();

  const [usernameValid, setUsernameValid] = useState(false);
  const [passwordValid, setPasswordValid] = useState(false);

  const [usernameTouched, setUsernameTouched] = useState(false);
  const [passwordTouched, setPasswordTouched] = useState(false);
  const [loading, setLoading] = useState(false);
  const [errAlert, setErrAlert] = useState(false);

  if (adminService.getCurrentUser()) {
    const user = adminService.getCurrentUser();

    if (user.role === "ROLE_FARMER") {
      history.push("/farmerDashboard");
    } else if (user.role === "ROLE_DEALER") {
      history.push("/dealerDashboard");
    } else if (user.role === "ROLE_ADMIN") {
      history.push("/adminDashboard");
    } else {
      history.push("/error");
    }
  }
  //handler
  const usernameHandler = (e) => {
    setUsername(e.target.value);
    if (e.target.value.trim() !== "") {
      setUsernameValid(true);
    }
  };

  const passwordHandler = (e) => {
    setPassword(e.target.value);
    if (e.target.value.trim() !== "") {
      setPasswordValid(true);
    }
  };

  // blur handler
  const usernameBlurHandler = (e) => {
    if (username.trim() == "") {
      setUsernameTouched(true);
      setUsernameValid(false);
      return;
    }
  };

  const passwordBlurHandler = (e) => {
    if (password.trim() == "") {
      setPasswordTouched(true);
      setPasswordValid(false);
      return;
    }
  };

  const saveAdmin = (e) => {
    setLoading(true);
    setErrAlert(false);
    setUsernameTouched(true);
    setPasswordTouched(true);

    if (username.trim() === "") {
      setLoading(false);
      setUsernameValid(false);
      return;
    }
    setUsernameValid(true);

    if (password.trim() === "") {
      setLoading(false);
      setPasswordValid(false);
      return;
    }
    setPasswordValid(true);
    adminService
      .login(username, password)
      .then(() => {
        const user = adminService.getCurrentUser();
        console.log(user.jwt);
        console.log(user.id);
        toast.success("Login Sussessful");
        if (user.role == "ROLE_ADMIN") {
          history.push(`/adminDashboard`);
          // window.location.reload(false);
        } else {
          history.push(`/error`);
          toast.error("You are not a Admin");
        }
      })
      .catch((error) => {
        setErrAlert(true);
        setLoading(false);
        toast.error("Wrong Username or Password");

        // alert("Wrong UserName or Password");
      });
    console.log(username, password);
  };

  const addAlert = "Wrong UserName or Password";
  const usernameInputValid = !usernameValid && usernameTouched;
  const passwordInputValid = !passwordValid && passwordTouched;

  const usernameStyle = usernameInputValid
    ? " form-control col-8 border border-3 border-danger "
    : "";
  const passwordStyle = passwordInputValid
    ? " form-control col-8 border border-3 border-danger"
    : "";

  return (
    <div>
      <Header />
      <div
        style={{
          backgroundImage: `url(${img3})`,
          minHeight: "720px",
          height: "100%",
          width: "100%",
          backgroundRepeat: "no-repeat",
          backgroundSize: "cover",
          backgroundBlendMode: "lighten",
          paddingTop: "5px",
        }}
      >
        <div
          id="d1"
          style={{
            paddingTop: "5px",
            paddingBottom: "20px",
            height: "100%",
            border: "2px solid ",
            borderRadius: "25px",
            backgroundColor: "rgba(216, 227, 225, 0.7)",
          }}
        >
          {errAlert && (
            <div
              class=" alert alert-warning alert-dismissible fade show"
              role="alert"
              style={{
                backgroundColor: "red",
                color: "white",
                marginTop: "15px",
              }}
            >
              <strong>{addAlert}</strong>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="alert"
                aria-label="Close"
                onClick={() => {
                  setErrAlert(false);
                }}
              ></button>
            </div>
          )}
          <img src={img1} className="img" path="/admin" />
          <p className="p5">ADMIN LOGIN</p>
          <input
            className={usernameStyle}
            type="text"
            id="username"
            value={username}
            onChange={usernameHandler}
            onBlur={usernameBlurHandler}
            placeholder="USERNAME"
            required
          />
          {usernameInputValid && (
            <p className="text-danger text-center font-weight-bold">
              Username must not be empty
            </p>
          )}
          <br />
          <br />
          <input
            className={passwordStyle}
            type="password"
            id="password"
            value={password}
            onChange={passwordHandler}
            onBlur={passwordBlurHandler}
            placeholder="PASSWORD"
            required
          />
          {passwordInputValid && (
            <p className="text-danger text-center font-weight-bold">
              Password must not be empty
            </p>
          )}
          <br />
          <br />
          <br />
          <input
            type="submit"
            onClick={(e) => saveAdmin(e)}
            value="Login Now"
          />
          <br />
          <br />
          <div className="d-flex justify-content-center">
            {loading && (
              <span
                style={{ width: "2rem", height: "2rem" }}
                className="spinner-border spinner-border-large text-primary"
              ></span>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminLogin;
