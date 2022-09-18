import React from "react";
import "../style/farmerlogin.css";
import { useState, useEffect } from "react";
import { Link, useHistory, useParams } from "react-router-dom";
import img1 from "../images/farmer-icon.png";
import farmerService from "../services/auth.service";
import img3 from "../images/farmer_field2.jpg";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Header from "./Header";

const FarmerLogin = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const history = useHistory();
  const [loading, setLoading] = useState(false);

  const [usernameValid, setUsernameValid] = useState(false);
  const [passwordValid, setPasswordValid] = useState(false);

  const [usernameTouched, setUsernameTouched] = useState(false);
  const [passwordTouched, setPasswordTouched] = useState(false);
  const [errAlert, setErrAlert] = useState(false);

  if (farmerService.getCurrentUser()) {
    const user = farmerService.getCurrentUser();

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

  const save = () => {
    history.push("/addFarmer");
  };

  const saveFarmer = (e) => {
    setLoading(true);
    setErrAlert(false);
    setUsernameTouched(true);
    setPasswordTouched(true);

    if (username.trim() === "") {
      setUsernameValid(false);
      setLoading(false);
      return;
    }
    setUsernameValid(true);

    if (password.trim() === "") {
      setLoading(false);
      setPasswordValid(false);
      return;
    }
    setPasswordValid(true);
    farmerService
      .login(username, password)
      .then((response) => {
        const user = farmerService.getCurrentUser();
        console.log(user.jwt);
        console.log(user.id);
        toast.success("Login Sussessful");
        if (user.role == "ROLE_FARMER") {
          history.push(`/farmerDashboard`);
          // window.location.reload(false);
        } else {
          history.push(`/error`);
          toast.error("You are not a Farmer");
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
    ? " form-control col-8 border border-3 border-danger"
    : "";
  const passwordStyle = passwordInputValid
    ? " form-control col-8 border border-3 border-danger"
    : "";

  const sty = ``;
  return (
    <div>
      <Header />

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
        <div
          id="d1"
          style={{
            paddingTop: "5px",
            border: "solid",
            height: "85%",
            border: "2px solid ",
            borderRadius: "25px",
            backgroundColor: "rgba(216, 227, 225, 0.7)",
          }}
        >
          {errAlert && (
            <div
              class="alert alert-warning alert-dismissible fade show"
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
          <img src={img1} className="img" path="/admin/login" />
          <p className="p7">Farmer LOGIN</p>
          <input
            className={usernameStyle}
            type="text"
            id="username"
            value={username}
            // onChange={(e) => setUsername(e.target.value)}
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
          <p>
            <input
              className={passwordStyle}
              type="password"
              id="password"
              value={password}
              // onChange={(e) => setPassword(e.target.value)}
              onChange={passwordHandler}
              onBlur={passwordBlurHandler}
              placeholder="PASSWORD"
              required
            />
          </p>
          {passwordInputValid && (
            <p className="text-danger text-center font-weight-bold">
              Password must not be empty
            </p>
          )}
          <br />

          <input
            type="submit"
            onClick={(e) => saveFarmer(e)}
            value="Login Now"
          />

          {/*  <p1>Remeber me </p1> */}
          <p
            style={{
              textAlign: "center",
              paddingLeft: "10px",
              paddingTop: "10px",
              color: "black",
            }}
          >
            New Farmer?
            <Link onClick={() => save()}> &nbsp; &nbsp; Sign Up</Link>
          </p>
          <div className="d-flex justify-content-center">
            {loading && (
              <span
                style={{ width: "3rem", height: "3rem" }}
                className="spinner-border spinner-border-large text-primary"
              ></span>
            )}
          </div>
          {/*  <br><br><br>
   <p1><center>not a member?</center></p1>
   <button>Create account</button>  */}
        </div>

        {/* <marquee direction="right">
        <div>
          <img
            src={img5}
            style={{ width: "140px", height: "120px", paddingBottom: "10px" }}
          />
        </div>
      </marquee> */}
      </div>
    </div>
  );
};

export default FarmerLogin;
