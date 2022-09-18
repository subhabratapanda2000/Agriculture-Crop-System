import React from "react";
import { useEffect, useState } from "react";
import { Link, useHistory, useParams, NavLink } from "react-router-dom";
import cropService from "../services/auth.service";
import img1 from "../images/farmer-icon.png";
import img3 from "../images/farmer_field.jpg";
import img2 from "../images/vegetable.png";
import img4 from "../images/edit1.png";
import img5 from "../images/delete1.png";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Dialog from "../components/Dialog";
import "../style/datalist.css";
import OverlayTrigger from "react-bootstrap/OverlayTrigger";
import Tooltip from "react-bootstrap/Tooltip";
import Header2 from "./Header2";

const MyCrops = () => {
  const history = useHistory();
  const user = cropService.getCurrentUser();
  const [myAllCrops, setMyAllCrops] = useState([]);
  const [cropId, setCropId] = useState("");
  const [cropName, setCropName] = useState("");
  const [quantity, setQuantity] = useState("");
  const [price, setPrice] = useState("");
  const [date, setDate] = useState("");
  const [loading, setLoading] = useState(true);
  const [content, setContent] = useState(false);
  const updateHover = (props) => (
    <Tooltip id="button-tooltip" {...props}>
      Click Here to Update your Crop Details
    </Tooltip>
  );
  const deleteHover = (props) => (
    <Tooltip id="button-tooltip" {...props}>
      Click Here to Delete your Crop Details
    </Tooltip>
  );
  const saveHover = (props) => (
    <Tooltip id="button-tooltip" {...props}>
      Click Here to Add your Crop
    </Tooltip>
  );
  const init = () => {
    cropService
      .findAllCropsOfOne(user.id)
      .then((crops) => {
        console.log(crops);
        setMyAllCrops(crops.data);
        setLoading(false);
        setContent(true);
        if (crops.data.length == 0) {
          toast.error("You have not any crops.");
          toast.info("Please! add your crops");
          setContent(false);
        }
      })
      .catch((error) => {
        setLoading(false);
        var errormsg = error.response.data;
        var array = errormsg.split(":");
        console.log("Something went wrong", error);
        toast.error(array[array.length - 1]);
      });
  };

  const logout = () => {
    toast.success("Log Out Successfully");
    cropService.logout();
    history.push(`/`);
  };

  const [dialog, setDialog] = useState({
    message: "",
    isLoading: false,
    //Update
    nameUser: "",
  });

  const handleDialog = (message, isLoading, nameUser) => {
    setDialog({
      message,
      isLoading,
      //Update
      nameUser,
    });
  };

  const handleDelete = (cid, crop) => {
    handleDialog("Are you sure want to delete?", true, crop);
    setCropId(cid);
    setCropName(crop);
  };

  const areUSureDelete = (choose) => {
    if (choose) {
      cropService
        .deleteCrops(cropId, user.id)
        .then((response) => {
          console.log(response);
          toast.success(cropName + " has been deleted successfully");
          window.location.reload(false);
        })
        .catch((error) => {
          var errormsg = error.response.data;
          var array = errormsg.split(":");
          console.log("Something went wrong", error);
          toast.error(array[array.length - 1]);
        });
      handleDialog("", false);
    } else {
      handleDialog("", false);
    }
  };

  useEffect(() => {
    init();
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

  return (
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
                style={{ width: "220px", paddingLeft: "5px", height: "116px" }}
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
              <OverlayTrigger
                placement="right"
                delay={{ show: 50, hide: 300 }}
                overlay={saveHover}
              >
                <Link
                  to="/addCrops"
                  className="btn btn-primary mb-2"
                  style={{ paddingBottom: "5px" }}
                >
                  Add Crops
                </Link>
              </OverlayTrigger>
              <div className="d-flex justify-content-center">
                {loading && (
                  <span
                    style={{
                      width: "6rem",
                      height: "6rem",
                      marginTop: "100px",
                    }}
                    className="spinner-border spinner-border-large text-primary"
                  ></span>
                )}
              </div>

              {/* <!-- !PAGE CONTENT! --> */}
              {content && (
                <div>
                  <h5
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
                    My Crop Details
                  </h5>
                  {/* Code here.. */}
                  <hr></hr>
                  <div>
                    <table
                      className="table table-bordered table-striped"
                      style={{ backgroundColor: "white", width: "100%" }}
                    >
                      <thead className="thead-dark">
                        <tr>
                          <th> Crop Id</th>
                          <th> Crop Name</th>
                          <th> Quantity</th>
                          <th> Price (/kg)</th>
                          <th> Date of post</th>
                          <th>Action</th>
                        </tr>
                      </thead>
                      <tbody>
                        {myAllCrops.map((crops) => (
                          <tr key={crops.id}>
                            <td>{crops.id}</td>
                            <td>{crops.cropName}</td>
                            <td>{crops.quantity} Kg</td>
                            <td>{crops.price}/-</td>
                            <td>{crops.date}</td>
                            <td>
                              <Link to={`/updateCrop/${crops.id}`}>
                                <OverlayTrigger
                                  placement="top-start"
                                  delay={{ show: 50, hide: 300 }}
                                  overlay={updateHover}
                                >
                                  <img
                                    src={img4}
                                    className="edit_img"
                                    style={{ marginTop: "10px" }}
                                  ></img>
                                </OverlayTrigger>
                              </Link>

                              <button
                                onClick={() => {
                                  handleDelete(crops.id, crops.cropName);
                                }}
                                style={{ width: "50px" }}
                              >
                                <OverlayTrigger
                                  placement="right"
                                  delay={{ show: 50, hide: 300 }}
                                  overlay={deleteHover}
                                >
                                  <img src={img5} className="delete_img"></img>
                                </OverlayTrigger>
                              </button>
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
      {dialog.isLoading && (
        <Dialog
          //Update
          nameProduct={dialog.nameUser}
          onDialog={areUSureDelete}
          message={dialog.message}
        />
      )}
    </div>
  );
};

export default MyCrops;
