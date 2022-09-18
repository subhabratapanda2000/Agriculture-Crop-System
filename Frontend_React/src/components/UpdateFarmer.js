import React from "react";
import { useState, useEffect } from "react";
import { Link, useHistory, useParams } from "react-router-dom";
import farmerService from "../services/auth.service";
import img1 from "../images/farmer-icon.png";
import img2 from "../images/vegetable.png";
import img3 from "../images/farmer_field.jpg";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Header2 from "./Header2";

const UpdateFarmer = () => {
  const [loading, setLoading] = useState(false);
  const user = farmerService.getCurrentUser();
  const [name, setName] = useState("");
  const [userName, setUsername] = useState("");
  const [active, setActive] = useState("");
  const [role, setRole] = useState("");
  const [joinDate, setJoinDate] = useState("");
  const [primeMember, setPrimeMember] = useState("");
  const [password, setPassword] = useState("");
  const [mobileNo, setPhone_number] = useState("");
  const [address, setLocation] = useState("");
  const [head, setHead] = useState("Add Employees");
  const history = useHistory();
  const fid = user.id;

  // for validation
  const [nameValid, setNameValid] = useState(false);
  const [usernameValid, setUsernameValid] = useState(false);
  const [phone_numberValid, setPhone_numberValid] = useState(false);
  const [locationValid, setLocationValid] = useState(false);

  //for touch validation
  const [nameTouched, setNameTouched] = useState(false);
  const [usernameTouched, setUsernameTouched] = useState(false);
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

  const saveFarmer = (e) => {
    setLoading(true);
    e.preventDefault();

    // validation Part
    setNameTouched(true);
    setUsernameTouched(true);
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

    const farmer = {
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

    farmerService
      .farmerUpdate(farmer)
      .then((response) => {
        console.log(farmer);
        toast.success(name + " Your Details updated successfully");
        console.log("Employee data updated successfully", response.data);
        history.push("/farmerDashboard");
      })
      .catch((error) => {
        setLoading(false);
        var errormsg = error.response.data;
        var array = errormsg.split(":");
        console.log("Something went wrong in Data Create", error);
        toast.error(array[array.length - 1]);
      });
  };

  useEffect(() => {
    if (fid) {
      console.log(fid);
      farmerService
        .findByFarmerId(fid)
        .then((farmer) => {
          setName(farmer.data.name);
          setUsername(farmer.data.userName);
          setPhone_number(farmer.data.mobileNo);
          setLocation(farmer.data.address);
        })
        .catch((error) => {
          toast.error("Something went wrong");
          toast.error(error.data);
        });
    }

    if (user) {
      if (user.role !== "ROLE_FARMER") {
        history.push(`/error`);
        toast.error("You are not a Farmer");
      }
    } else {
      history.push(`/error`);
      toast.error("You are not a Authenticate User");
    }
  }, []);

  const logout = () => {
    toast.success("Log Out Successfully");
    farmerService.logout();
    history.push(`/`);
  };

  const nameInputValid = !nameValid && nameTouched;
  const usernameInputValid = !usernameValid && usernameTouched;
  const phone_numberInputValid = !phone_numberValid && phone_numberTouched;
  const locationInputValid = !locationValid && locationTouched;

  const nameStyle = nameInputValid
    ? " form-control col-4 border border-3 border-danger"
    : "form-control col-4";

  const usernameStyle = usernameValid
    ? " form-control col-4 border border-3 border-danger"
    : "form-control col-4";

  const phone_numberStyle = phone_numberInputValid
    ? " form-control col-4 border border-3 border-danger"
    : "form-control col-4";
  const locationStyle = locationInputValid
    ? " form-control col-4 border border-3 border-danger"
    : "form-control col-4";

  const cancel = () => {
    history.push("/farmerDashboard");
  };
  return (
    <div>
      <Header2></Header2>
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
            <h5>Farmer Dashboard</h5>
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
              to="/farmerDashboard"
              className="w3-bar-item w3-button w3-padding"
            >
              <i className="fa fa-bullseye fa-fw"></i> Overview
            </Link>
            <Link
              to={`/changePwdFarmer`}
              className="w3-bar-item w3-button w3-padding"
            >
              <i className="fa fa-edit"></i> Change Password
            </Link>
            <Link to="/getOffers" className="w3-bar-item w3-button w3-padding">
              <i className="fa fa-comment"></i> Offers From Dealers
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
                <i className="fa fa-dashboard"></i> My Dashboard
              </b>
            </h5>
          </header>

          <div className="w3-row-padding ">
            <Link to="/farmerDashboard">
              <div className="w3-quarter">
                <div
                  className="w3-container w3-indigo w3-padding-16"
                  style={{ width: "220px" }}
                >
                  <div className="w3-left">
                    <i
                      className="fa fa-user w3-xxxlarge center"
                      style={{ paddingLeft: "70px" }}
                    ></i>
                  </div>
                  <div className="w3-right"></div>
                  <div className="w3-clear"></div>
                  <h4 style={{ textAlign: "center" }}>My Details</h4>
                </div>
              </div>
            </Link>

            <Link to="/myCrops">
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
                      src={img1}
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
                  <h4 style={{ textAlign: "center" }}>My Crop Details</h4>
                </div>
              </div>
            </Link>

            <Link to="/allCrops">
              <div className="w3-quarter">
                <div
                  className="w3-container w3-yellow w3-padding-16"
                  style={{ width: "220px", height: "116px" }}
                >
                  <div className="w3-left">
                    <img
                      src={img2}
                      className="fa fa-user w3-xxxlarge center"
                      style={{
                        height: "40px",
                        width: "200px",
                      }}
                    ></img>
                  </div>
                  <div className="w3-right"></div>
                  <div className="w3-clear"></div>
                  <h4 style={{ textAlign: "center" }}>Others Crop Details</h4>
                </div>
              </div>
            </Link>
          </div>

          <div className="w3-panel" id="Farmer">
            <div
              className="w3-row-padding w3-margin-top"
              style={{ margin: "0-16px" }}
            >
              <div className="w3-twothird">
                {/* write your code here */}
                <h3
                  style={{
                    textAlign: "center",
                    paddingTop: "10px",
                    paddingBottom: "10px",
                    marginLeft: "20%",
                    width: "50%",
                    border: "solid",
                    height: "80%",
                    border: "2px solid ",
                    borderRadius: "25px",
                    backgroundColor: "rgba(216, 227, 225, 0.5)",
                  }}
                >
                  Update Farmer Details
                </h3>
                <hr />
                <div
                  className="container"
                  style={{
                    width: "120%",
                    height: "70%",
                    marginBottom: "50px",
                    paddingLeft: "50px",
                    border: "2px solid ",
                    borderRadius: "25px",
                    backgroundColor: "rgba(216, 227, 225, 0.7)",
                  }}
                >
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
                        onClick={(e) => saveFarmer(e)}
                        className="btn btn-primary"
                      >
                        Update
                      </button>
                    </div>
                    <Link className="btn btn-primary" to={"/farmerDashboard"}>
                      Cancel
                    </Link>
                    <div className="d-flex justify-content-center">
                      {loading && (
                        <span
                          style={{
                            width: "3rem",
                            height: "3rem",
                            marginRight: "140px",
                          }}
                          className="spinner-border spinner-border-large text-primary"
                        ></span>
                      )}
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

export default UpdateFarmer;
