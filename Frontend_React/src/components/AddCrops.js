import React from "react";
import { useEffect, useState } from "react";
import { Link, useHistory, useParams, NavLink } from "react-router-dom";
import cropService from "../services/auth.service";
import img1 from "../images/farmer-icon.png";
import img3 from "../images/farmer_field.jpg";
import img2 from "../images/vegetable.png";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Header2 from "./Header2";

const AddCrops = () => {
  const history = useHistory();
  const user = cropService.getCurrentUser();
  const [id, setCropId] = useState("");
  const [cropName, setCropName] = useState("");
  const [quantity, setQuantity] = useState("");
  const [price, setPrice] = useState("");
  const [date, setDate] = useState("");
  const farmerId = user.id;
  const [loading, setLoading] = useState(false);

  // for validation
  const [nameValid, setNameValid] = useState(false);
  const [quantityValid, setQuantityValid] = useState(false);
  const [priceValid, setPriceValid] = useState(false);

  //for touch validation
  const [nameTouched, setNameTouched] = useState(false);
  const [quantityTouched, setQuantityTouched] = useState(false);
  const [priceTouched, setPriceTouched] = useState(false);

  //form handler
  const nameHandler = (e) => {
    setCropName(e.target.value);

    if (e.target.value.trim() !== "") {
      setNameValid(true);
    }
  };

  const quantityHandler = (e) => {
    setQuantity(e.target.value);

    if (e.target.value != 0) {
      setQuantityValid(true);
    }
  };

  const priceHandler = (e) => {
    setPrice(e.target.value);

    if (e.target.value != 0) {
      setPriceValid(true);
    }
  };

  // Blur Handler
  const nameBlurHandler = (event) => {
    // setEnameTouched(true);

    if (cropName.trim() === "") {
      setNameTouched(true);
      setNameValid(false);
    }
  };

  const quantityBlurHandler = (event) => {
    // quantityTouched(true);

    if (quantity == 0) {
      setQuantityTouched(true);
      setQuantityValid(false);
    }
  };

  const priceBlurHandler = (event) => {
    // priceTouched(true);
    if (price == 0) {
      setPriceTouched(true);
      setPriceValid(false);
    }
  };

  const saveCrops = (e) => {
    setLoading(true);
    e.preventDefault();

    // validation Part
    setNameTouched(true);
    setQuantityTouched(true);
    setPriceTouched(true);

    if (cropName.trim() === "") {
      setNameValid(false);
      setLoading(false);
      return;
    }
    setNameValid(true);

    if (quantity == 0) {
      setQuantityValid(false);
      setLoading(false);
      return;
    }
    setQuantityValid(true);

    if (price == 0) {
      setPriceValid(false);
      setLoading(false);
      return;
    }
    setPriceValid(true);

    const crop = {
      id,
      cropName,
      quantity,
      price,
      farmerId,
      date,
    };

    // create
    cropService
      .addCrops(crop, user.id)
      .then((response) => {
        console.log(crop);
        console.log("Crop added successfully", response.data);
        toast.success(response.data);
        history.push("/myCrops");
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
    cropService.logout();
    history.push(`/`);
  };

  const nameInputValid = !nameValid && nameTouched;
  const quantityInputValid = !quantityValid && quantityTouched;
  const priceInputValid = !priceValid && priceTouched;

  const nameStyle = nameInputValid
    ? " form-control col-4 border border-3 border-danger"
    : "form-control col-4";

  const quantityStyle = quantityInputValid
    ? " form-control col-4 border border-3 border-danger"
    : "form-control col-4";

  const priceStyle = priceInputValid
    ? " form-control col-4 border border-3 border-danger"
    : "form-control col-4";

  const cancel = () => {
    history.push("/myCrops");
  };

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
            <h5>Farmer Dashboard</h5>
          </div>
          <div className="w3-bar-block">
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

          <div className="w3-panel" id="Admin">
            <div
              className="w3-row-padding w3-margin-top"
              style={{ margin: "0 -16px" }}
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
                    Add Crop
                  </h3>
                  <hr />
                  <form>
                    <div className="form-group">
                      <input
                        type="text"
                        className={nameStyle}
                        id="name"
                        value={cropName}
                        onChange={nameHandler}
                        onBlur={nameBlurHandler}
                        placeholder="Enter Crop Name"
                      />
                      {nameInputValid && (
                        <p
                          className="text-danger text-center font-weight-bold"
                          style={{ marginLeft: "-200px" }}
                        >
                          Crop Name must not be empty
                        </p>
                      )}
                    </div>
                    <div className="form-group">
                      <input
                        type="number"
                        className={quantityStyle}
                        id="quantity"
                        value={quantity}
                        onChange={quantityHandler}
                        onBlur={quantityBlurHandler}
                        placeholder="Enter Quantity (kg) of Crop"
                      />
                      {quantityInputValid && (
                        <p
                          className="text-danger text-center font-weight-bold"
                          style={{ marginLeft: "-200px" }}
                        >
                          Crop Quantity must not be empty or 0
                        </p>
                      )}
                    </div>
                    <div className="form-group">
                      <input
                        type="number"
                        className="form-control col-4"
                        id="price"
                        value={price}
                        onChange={priceHandler}
                        onBlur={priceBlurHandler}
                        placeholder="Enter Price (/kg) of The Crop"
                      />
                      {priceInputValid && (
                        <p
                          className="text-danger text-center font-weight-bold"
                          style={{ marginLeft: "-200px" }}
                        >
                          Price must not be empty or 0
                        </p>
                      )}
                    </div>

                    <div style={{ width: "80%" }}>
                      <button
                        onClick={(e) => saveCrops(e)}
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
                      <Link className="btn btn-primary" to={"/myCrops"}>
                        Cancel
                      </Link>
                      <div className="d-flex justify-content-center">
                        {loading && (
                          <span
                            style={{
                              width: "3rem",
                              height: "3rem",
                              marginLeft: "120px",
                              marginRight: "0px",
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
        </div>
      </div>
    </div>
  );
};

export default AddCrops;
