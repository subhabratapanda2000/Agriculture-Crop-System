import React from "react";
import { useEffect, useState } from "react";
import { Link, useHistory, useParams, NavLink } from "react-router-dom";
import service from "../services/auth.service";
import img1 from "../images/Admin2.png";
import img2 from "../images/crop.png";
import img3 from "../images/farmer_field.jpg";
import dealerImg from "../images/office-worker-icon.png";
import cropImg from "../images/vegetable.png";
import farmerImg from "../images/farmer-icon.png";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "../style/datalist.css";
import OverlayTrigger from "react-bootstrap/OverlayTrigger";
import Tooltip from "react-bootstrap/Tooltip";
import Header2 from "./Header2";

const FarmerDetails = () => {
  const history = useHistory();
  const user = service.getCurrentUser();
  const [allFarmers, setAllFarmers] = useState([]);
  const [findFarmer, setFindFarmer] = useState("");
  const { fid } = useParams();
  const [name, setName] = useState("");
  const [username, setUsername] = useState("");
  const [phone, setPhone_number] = useState("");
  const [location, setLocation] = useState("");
  const [active, setActive] = useState("");
  const [joindate, setJoinDate] = useState("");
  const [primeMember, setPrimeMember] = useState("");
  const [farmerId, setfarmerId] = useState("");
  const [loading, setLoading] = useState(true);
  const [content, setContent] = useState(false);

  const farmerImgHover = (props) => (
    <Tooltip id="button-tooltip" {...props}>
      Click Here to view Farmer Profile and Crop Details of this Farmer
    </Tooltip>
  );
  const init = (id) => {
    service
      .findByFarmerId(id)
      .then((farmer) => {
        console.log(farmer);
        setfarmerId(farmer.data.fid);
        setName(farmer.data.name);
        setUsername(farmer.data.userName);
        setPhone_number(farmer.data.mobileNo);
        setLocation(farmer.data.address);
        setJoinDate(farmer.data.joinDate);
        if (farmer.data.primeMember) {
          setPrimeMember("Yes");
        } else {
          setPrimeMember("No");
        }
        if (farmer.data.active) {
          setActive("Yes");
        } else {
          setActive("No");
        }
        setLoading(false);
        setContent(true);
      })
      .catch((error) => {
        setLoading(false);
        toast.error("Something went wrong");
        toast.error(error.data);
      });
  };

  const logout = () => {
    toast.success("Log Out Successfully");
    service.logout();
    history.push(`/`);
  };

  useEffect(() => {
    init(fid);
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

  const searchFarmer = () => {
    if (findFarmer.trim() !== "") {
      setLoading(true);
      setContent(false);
      service
        .findByFarmerId(findFarmer)
        .then(() => {
          init(findFarmer);
        })
        .catch(() => {
          setLoading(false);
          setContent(true);
          service
            .findByDealerId(findFarmer)
            .then(() => {
              toast.info("It's a dealer Id");
            })
            .catch(() => {
              toast.info("This Id is not valid");
            });
        });
    } else {
      init(fid);
    }
  };

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
                style={{ width: "220px", paddingLeft: "5px", height: "116px" }}
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

            <div className="w3-twothird">
              <div className="input-group">
                <input
                  type="search"
                  className="form-control col-4"
                  style={{
                    display: "inline-block",
                  }}
                  id="search"
                  value={findFarmer}
                  onChange={(s) => setFindFarmer(s.target.value)}
                  placeholder="Search Farmer Id..."
                />

                <button
                  onClick={() => searchFarmer()}
                  style={{ display: "inline-flex", width: "10%" }}
                  className="btn btn-primary"
                >
                  <i
                    style={{ marginTop: "5px", marginLeft: "12px" }}
                    className="fa fa-search w3-large center"
                  ></i>
                </button>
              </div>
              <hr />
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
                      paddingBottom: "5px",
                      marginLeft: "20%",
                      width: "50%",
                      border: "solid",
                      height: "80%",
                      border: "2px solid ",
                      borderRadius: "25px",
                      backgroundColor: "rgba(216, 227, 225, 0.7)",
                    }}
                  >
                    Farmers Details
                  </h5>
                  {/* Code here.. */}
                  <hr></hr>
                  <div>
                    <table
                      className="w3-table-all w3-striped w3-hoverable w3-white"
                      style={{ marginBottom: "50px" }}
                    >
                      <tbody>
                        <tr>
                          <td>
                            <h6>Farmer Id: </h6>
                          </td>
                          <td>
                            <h6>{farmerId}</h6>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <h6>Farmer Name: </h6>
                          </td>
                          <td>
                            <h6>{name}</h6>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <h6>User Name: </h6>
                          </td>
                          <td>
                            <h6>{username}</h6>
                          </td>
                        </tr>

                        <tr>
                          <td>
                            <h6>Mobile No: </h6>
                          </td>
                          <td>
                            <h6>{phone}</h6>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <h6>Location: </h6>
                          </td>
                          <td>
                            <h6>{location}</h6>
                          </td>
                        </tr>

                        <tr>
                          <td>
                            <h6>Join Date: </h6>
                          </td>
                          <td>
                            <h6>{joindate}</h6>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <h6>Active: </h6>
                          </td>
                          <td>
                            <h6>{active}</h6>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <h6>Prime member: </h6>
                          </td>
                          <td>
                            <h6>{primeMember}</h6>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <h6>Crop Details: </h6>
                          </td>
                          <td>
                            <Link to={`/farmerProfile_ad/${farmerId}`}>
                              <OverlayTrigger
                                placement="right"
                                delay={{ show: 50, hide: 300 }}
                                overlay={farmerImgHover}
                              >
                                <img
                                  src={img2}
                                  className="edit_img"
                                  style={{
                                    height: "40px",
                                    width: "60px",
                                    marginLeft: "0px",
                                  }}
                                ></img>
                              </OverlayTrigger>
                            </Link>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FarmerDetails;
