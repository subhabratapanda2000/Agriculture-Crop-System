import React from "react";
import { useState, useEffect } from "react";
import { Link, useHistory, useParams } from "react-router-dom";
import dealerService from "../services/auth.service";
import img3 from "../images/farmer_field3.jpg";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Header from "./Header";

const AddDealer = () => {
  const [loading, setLoading] = useState(false);
  const [name, setName] = useState("");
  const [userName, setUsername] = useState("");
  const [active, setActive] = useState("");
  const [role, setRole] = useState("");
  const [joinDate, setJoinDate] = useState("");
  const [primeMember, setPrimeMember] = useState("");
  const [password, setPassword] = useState("");
  const [mobileNo, setPhone_number] = useState("");
  const [address, setLocation] = useState("");
  const history = useHistory();
  const { fid } = useParams();

  // for validation
  const [nameValid, setNameValid] = useState(false);
  const [usernameValid, setUsernameValid] = useState(false);
  const [passwordValid, setPasswordValid] = useState(false);
  const [phone_numberValid, setPhone_numberValid] = useState(false);
  const [locationValid, setLocationValid] = useState(false);

  //for touch validation
  const [nameTouched, setNameTouched] = useState(false);
  const [usernameTouched, setUsernameTouched] = useState(false);
  const [passwordTouched, setPasswordTouched] = useState(false);
  const [phone_numberTouched, setPhone_numberTouched] = useState(false);
  const [locationTouched, setLocationTouched] = useState(false);

  //form handler
  const nameHandler = (e) => {
    setName(e.target.value);

    if (e.target.value.trim() !== "") {
      setNameValid(true);
    }
  };

  const usernameHandler = (e) => {
    setUsername(e.target.value);

    if (e.target.value.trim() != "") {
      setUsernameValid(true);
    }
  };

  const passwordHandler = (e) => {
    setPassword(e.target.value);

    if (e.target.value.trim() != "") {
      setPasswordValid(true);
    }
  };

  const phoneHandler = (e) => {
    setPhone_number(e.target.value);
    if (e.target.value.trim() !== "") {
      setPhone_numberValid(true);
    }
  };

  const locationHandler = (e) => {
    setLocation(e.target.value);
    if (e.target.value.trim() !== "") {
      setLocationValid(true);
    }
  };

  // Blur Handler
  const nameBlurHandler = (event) => {
    // setEnameTouched(true);

    if (name.trim() === "") {
      setNameTouched(true);
      setNameValid(false);
    }
  };

  const usernameBlurHandler = (e) => {
    // usernameTouched(true);

    if (userName.trim() == "") {
      usernameTouched(true);
      setUsernameValid(false);
    }
  };

  const passwordBlurHandler = (e) => {
    // passwordTouched(true);
    if (password.trim() == "") {
      passwordTouched(true);
      setPasswordValid(false);
    }
  };

  const phoneBlurHandler = (e) => {
    // setPhone_numberTouched(true);
    if (mobileNo.trim() === "") {
      setPhone_numberTouched(true);
      setPhone_numberValid(false);
    }
  };

  const locationBlurHandler = (e) => {
    // setLocationTouched(true);
    if (address.trim() === "") {
      setLocationTouched(true);
      setLocationValid(false);
    }
  };

  const saveDealer = (e) => {
    setLoading(true);
    e.preventDefault();
    console.log("No data saved " + e);

    // validation Part
    setNameTouched(true);
    setUsernameTouched(true);
    setPasswordTouched(true);
    setPhone_numberTouched(true);
    setPhone_numberTouched(true);
    setLocationTouched(true);

    if (name.trim() === "") {
      setLoading(false);
      setNameValid(false);
      return;
    }
    setNameValid(true);

    if (userName.trim() === "") {
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

    if (mobileNo.trim() === "") {
      setLoading(false);
      setPhone_numberValid(false);
      return;
    }
    setPhone_numberValid(true);

    if (address.trim() === "") {
      setLoading(false);
      setLocationValid(false);
      return;
    }
    setLocationValid(true);

    const dealer = {
      fid,
      name,
      userName,
      password,
      mobileNo,
      address,
      active,
      role,
      joinDate,
      primeMember,
    };

    // create
    dealerService
      .dealerCreate(dealer)
      .then((response) => {
        console.log(dealer);
        console.log("Dealer added successfully", response.data);
        toast.success(response.data);
        history.push("/dealerlogin");
      })
      .catch((error) => {
        setLoading(false);
        var errormsg = error.response.data;
        var array = errormsg.split(":");
        console.log("Something went wrong in Data Create", error);
        toast.error(array[array.length - 1]);
      });
  };

  const nameInputValid = !nameValid && nameTouched;
  const usernameInputValid = !usernameValid && usernameTouched;
  const passwordInputValid = !passwordValid && passwordTouched;
  const phone_numberInputValid = !phone_numberValid && phone_numberTouched;
  const locationInputValid = !locationValid && locationTouched;

  const nameStyle = nameInputValid
    ? " form-control col-4 border border-3 border-danger"
    : "form-control col-4";

  const usernameStyle = usernameValid
    ? " form-control col-4 border border-3 border-danger"
    : "form-control col-4";
  const passwordStyle = passwordValid
    ? " form-control col-4 border border-3 border-danger"
    : "form-control col-4";

  const phone_numberStyle = phone_numberInputValid
    ? " form-control col-4 border border-3 border-danger"
    : "form-control col-4";
  const locationStyle = locationInputValid
    ? " form-control col-4 border border-3 border-danger"
    : "form-control col-4";

  const cancel = () => {
    history.push("/dealerLogin");
  };

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
        {/* <!-- !PAGE CONTENT! --> */}
        <div
          className="w3-main"
          style={{ marginLeft: "300px !important", marginTop: "43px" }}
        />

        <div className="w3-row-padding " style={{ marginLeft: "300px" }}>
          {/* <!--Admin div--> */}

          <div className="w3-panel" id="Farmer">
            <div
              className="w3-row-padding w3-margin-top"
              style={{ margin: "0px" }}
            >
              <div className="w3-twothird">
                {/* write your code here */}
                <div
                  className="container"
                  style={{
                    width: "120%",
                    paddingBottom: "30px",
                    paddingLeft: "50px",
                    border: "2px solid ",
                    borderRadius: "25px",
                    backgroundColor: "rgba(216, 227, 225, 0.7)",
                  }}
                >
                  <h3
                    style={{
                      textAlign: "center",
                      paddingRight: "180px",
                      paddingTop: "20px",
                    }}
                  >
                    Add Dealer
                  </h3>
                  <hr />
                  <form>
                    <div className="form-group">
                      <input
                        type="text"
                        className={nameStyle}
                        id="name"
                        value={name}
                        onChange={nameHandler}
                        onBlur={nameBlurHandler}
                        placeholder="Enter name"
                      />
                      {nameInputValid && (
                        <p
                          className="text-danger text-center font-weight-bold"
                          style={{ marginLeft: "-200px" }}
                        >
                          Name must not be empty
                        </p>
                      )}
                    </div>
                    <div className="form-group">
                      <input
                        type="text"
                        className={usernameStyle}
                        id="username"
                        value={userName}
                        onChange={usernameHandler}
                        onBlur={usernameBlurHandler}
                        placeholder="Enter username"
                      />
                      {usernameInputValid && (
                        <p
                          className="text-danger text-center font-weight-bold"
                          style={{ marginLeft: "-200px" }}
                        >
                          Username must not be empty
                        </p>
                      )}
                    </div>
                    <div className="form-group">
                      <input
                        type="password"
                        className="form-control col-4"
                        id="password"
                        value={password}
                        onChange={passwordHandler}
                        onBlur={passwordBlurHandler}
                        placeholder="Enter password"
                      />
                      {passwordInputValid && (
                        <p
                          className="text-danger text-center font-weight-bold"
                          style={{ marginLeft: "-200px" }}
                        >
                          Password must not be empty
                        </p>
                      )}
                    </div>

                    <div className="form-group">
                      <input
                        type="text"
                        className={phone_numberStyle}
                        id="phone_number"
                        value={mobileNo}
                        onChange={phoneHandler}
                        onBlur={phoneBlurHandler}
                        placeholder="Enter Phone Number"
                      />
                      {phone_numberInputValid && (
                        <p
                          className="text-danger text-center font-weight-bold"
                          style={{ marginLeft: "-200px" }}
                        >
                          Phone Number must not be empty
                        </p>
                      )}
                    </div>
                    <div className="form-group">
                      <input
                        type="text"
                        className={locationStyle}
                        id="location"
                        value={address}
                        onChange={locationHandler}
                        onBlur={locationBlurHandler}
                        placeholder="Enter Location"
                      />
                      {locationInputValid && (
                        <p
                          className="text-danger text-center font-weight-bold"
                          style={{ marginLeft: "-200px" }}
                        >
                          Location must not be empty
                        </p>
                      )}
                    </div>

                    <div style={{ width: "80%" }}>
                      <button
                        onClick={(e) => saveDealer(e)}
                        className="btn btn-primary"
                      >
                        Save
                      </button>
                    </div>
                    <div
                      style={{
                        width: "20%",
                        marginLeft: "0px",
                        margin: "30px",
                      }}
                    >
                      <button
                        onClick={() => cancel()}
                        className="btn btn-primary"
                      >
                        Cancel
                      </button>
                      <div className="d-flex justify-content-center">
                        {loading && (
                          <span
                            style={{
                              width: "3rem",
                              height: "3rem",
                              marginLeft: "120px",
                            }}
                            className="spinner-border spinner-border-large text-primary"
                          ></span>
                        )}
                      </div>
                    </div>
                  </form>
                  <hr />
                </div>
              </div>
            </div>
          </div>
          <hr />
        </div>
      </div>
    </div>
  );
};

export default AddDealer;
