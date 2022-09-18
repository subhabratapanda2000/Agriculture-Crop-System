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
import OverlayTrigger from "react-bootstrap/OverlayTrigger";
import Tooltip from "react-bootstrap/Tooltip";
import Header2 from "./Header2";

const AllFarmers = () => {
  const history = useHistory();
  const user = service.getCurrentUser();
  const [allFarmers, setAllFarmers] = useState([]);
  const [findFarmer, setFindFarmer] = useState("");
  const [ms, setMs] = useState("All");
  const [loading, setLoading] = useState(true);
  const [content, setContent] = useState(false);
  const cropImgHover = (props) => (
    <Tooltip id="button-tooltip" {...props}>
      Click Here to view Crop Details of this Farmer
    </Tooltip>
  );

  const init = () => {
    service
      .findAllFarmers()
      .then((farmers) => {
        console.log(farmers);
        setAllFarmers(farmers.data);
        setLoading(false);
        setContent(true);
        if (farmers.data.length == 0) {
          setContent(false);
          toast.error("There have not any farmers.");
        }
        setMs("All");
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
    service.logout();
    history.push(`/`);
  };

  useEffect(() => {
    init();
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

  const handleAllFarmers = () => {
    init();
  };
  const handleAllActiveFarmers = () => {
    setLoading(true);
    setContent(false);
    service
      .findAllActiveFarmers()
      .then((farmers) => {
        console.log(farmers);
        setAllFarmers(farmers.data);
        setLoading(false);
        setContent(true);
        if (farmers.data.length == 0) {
          toast.error("There have not any farmers.");
          init();
        } else {
          setMs("All Active");
        }
      })
      .catch((error) => {
        init();
        setLoading(false);
        setContent(true);
        var errormsg = error.response.data;
        var array = errormsg.split(":");
        console.log("Something went wrong", error);
        toast.error(array[array.length - 1]);
      });
  };
  const handleAllPrimeFarmers = () => {
    setLoading(true);
    setContent(false);
    service
      .findAllPrimeFarmers()
      .then((farmers) => {
        console.log(farmers);
        setAllFarmers(farmers.data);
        setLoading(false);
        setContent(true);
        if (farmers.data.length == 0) {
          toast.error("There have not any farmers.");
          init();
        } else {
          setMs("All Prime");
        }
      })
      .catch((error) => {
        init();
        setLoading(false);
        setContent(true);
        var errormsg = error.response.data;
        var array = errormsg.split(":");
        console.log("Something went wrong", error);
        toast.error(array[array.length - 1]);
      });
  };

  const searchFarmer = () => {
    if (findFarmer.trim() !== "") {
      setLoading(true);
      setContent(false);
      service
        .findByFarmerId(findFarmer)
        .then(() => {
          history.push(`/farmerDetails/${findFarmer}`);
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
      init();
      setLoading(false);
      setContent(true);
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
            onClick={() => handleAllFarmers()}
            className="w3-bar-item w3-button w3-padding"
          >
            <i className="fa fa-users fa-fw"></i> All Farmers
          </Link>
          <Link
            onClick={() => handleAllActiveFarmers()}
            className="w3-bar-item w3-button w3-padding"
          >
            <i className="fa fa-users fa-fw"></i> All Active Farmers
          </Link>
          <Link
            onClick={() => handleAllPrimeFarmers()}
            className="w3-bar-item w3-button w3-padding"
          >
            <i className="fa fa-star fa-fw"></i> All Prime Farmers
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
                      paddingBottom: "10px",
                      marginLeft: "20%",
                      width: "50%",
                      border: "solid",
                      height: "80%",
                      border: "2px solid ",
                      borderRadius: "25px",
                      backgroundColor: "rgba(216, 227, 225, 0.7)",
                    }}
                  >
                    {ms} Farmers Details
                  </h5>
                  {/* Code here.. */}
                  <hr></hr>
                  <div>
                    <table
                      className="table table-bordered table-striped"
                      style={{
                        backgroundColor: "white",
                        width: "130%",
                        marginBottom: "60px",
                      }}
                    >
                      <thead className="thead-dark">
                        <tr>
                          <th>Farmer Id</th>
                          <th> Name</th>
                          <th> User Name</th>
                          <th> Mobile No</th>
                          <th> Addrss</th>
                          <th> Join Date</th>
                          <th> Crop Details</th>
                        </tr>
                      </thead>
                      <tbody>
                        {allFarmers.map((farmer) => (
                          <tr key={farmer.fid}>
                            <td>{farmer.fid}</td>
                            <td>{farmer.name}</td>
                            <td>{farmer.userName}</td>
                            <td>{farmer.mobileNo}</td>
                            <td>{farmer.address}</td>
                            <td>{farmer.joinDate}</td>
                            <td>
                              <Link to={`/farmerProfile_ad/${farmer.fid}`}>
                                <OverlayTrigger
                                  placement="right"
                                  delay={{ show: 50, hide: 300 }}
                                  overlay={cropImgHover}
                                >
                                  <img
                                    src={img2}
                                    className="edit_img"
                                    style={{
                                      height: "40px",
                                      width: "40px",
                                      marginBottom: "0px",
                                    }}
                                  ></img>
                                </OverlayTrigger>
                              </Link>
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
    </div>
  );
};

export default AllFarmers;
