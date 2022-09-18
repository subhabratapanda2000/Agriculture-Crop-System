import React from "react";
import { useEffect, useState } from "react";
import { Link, useHistory, useParams, NavLink } from "react-router-dom";
import farmerService from "../services/auth.service";
import img1 from "../images/farmer-icon.png";
import img3 from "../images/farmer_field.jpg";
import img2 from "../images/vegetable.png";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Dialog from "../components/Dialog";
import Header2 from "./Header2";
import OverlayTrigger from "react-bootstrap/OverlayTrigger";
import Tooltip from "react-bootstrap/Tooltip";

const FarmerDashboard = () => {
  const history = useHistory();
  const user = farmerService.getCurrentUser();
  const [paymentUrl, setPaymentUrl] = useState("");
  const [fid, setFid] = useState("");
  const [name, setName] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [phone, setPhone_number] = useState("");
  const [location, setLocation] = useState("");
  const [active, setActive] = useState("");
  const [joindate, setJoinDate] = useState("");
  const [primeMember, setPrimeMember] = useState("");
  const [role, setRole] = useState("");
  const [loading, setLoading] = useState(true);
  const [content, setContent] = useState(false);

  const primeHover = (props) => (
    <Tooltip id="button-tooltip" {...props}>
      Click here to pay Rs.-50/- only for become a Prime Customer
    </Tooltip>
  );
  const init = () => {
    farmerService
      .findByFarmerId(user.id)
      .then((farmer) => {
        console.log(farmer);
        setFid(farmer.data.fid);
        setName(farmer.data.name);
        setUsername(farmer.data.userName);
        setPassword(farmer.data.password);
        setPhone_number(farmer.data.mobileNo);
        setLocation(farmer.data.address);
        setActive(farmer.data.active);
        setJoinDate(farmer.data.joinDate);
        if (farmer.data.primeMember) {
          setPrimeMember("Yes");
        } else {
          setPrimeMember("No");
        }
        setRole(farmer.data.role);
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
    farmerService.logout();
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

  const deactivate = () => {
    handleDialog("Are you sure want to deactivate?", true, name);
  };

  const areUSureDelete = (choose) => {
    if (choose) {
      farmerService
        .deactivateFarmer(user.id)
        .then((response) => {
          toast.success("Your Account has been deactivate successfully");
          history.push(`/`);
        })
        .catch((error) => {
          var errormsg = error.response.data;
          var array = errormsg.split(":");
          console.log("Something went wrong", error);
          toast.error(array[1]);
        });
      handleDialog("", false);
    } else {
      handleDialog("", false);
    }
  };

  const primeAccount = () => {
    if (primeMember == "Yes") {
      toast.success("You are already a prime member");
    } else {
      setPaymentUrl(`http://localhost:8060/payment/pgredirect/${user.id}`);
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
    <div>
      <Header2 />
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
            <OverlayTrigger
              placement="right"
              delay={{ show: 50, hide: 300 }}
              overlay={primeHover}
            >
              <a
                onClick={() => primeAccount()}
                href={paymentUrl}
                className="w3-bar-item w3-button w3-padding"
              >
                <i className="fa fa-star"></i> Prime Memeber
              </a>
            </OverlayTrigger>
            <Link
              onClick={() => logout()}
              className="w3-bar-item w3-button w3-padding"
            >
              <i className="fa fa-sign-out fa-fw"></i> Log Out
            </Link>
            <Link
              onClick={() => deactivate()}
              className="w3-bar-item w3-button w3-padding"
            >
              <i className="fa fa-trash"></i> Deactivate
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
            <Link to={`/farmerDashboard`}>
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

            <Link to={`/allCrops`}>
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
          <div className="d-flex justify-content-center">
            {loading && (
              <span
                style={{
                  width: "6rem",
                  height: "6rem",
                  marginTop: "200px",
                  marginRight: "300px",
                }}
                className="spinner-border spinner-border-large text-primary"
              ></span>
            )}
          </div>

          {/* <!-- !PAGE CONTENT! --> */}
          {content && (
            <div className="w3-panel" id="Admin">
              <div
                className="w3-row-padding w3-margin-top"
                style={{ margin: "0 -16px" }}
              >
                <div className="w3-twothird">
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
                    My Details
                  </h5>
                  <table className="w3-table-all w3-striped w3-hoverable w3-white">
                    <tbody>
                      <tr>
                        <td>
                          <h6>Farmer Id: </h6>
                        </td>
                        <td>
                          <h6>{fid}</h6>
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
                          <h6>Prime member: </h6>
                        </td>
                        <td>
                          <h6>{primeMember}</h6>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <Link className="btn btn-info" to={`/updateFarmer`}>
                            Update
                          </Link>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          )}
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
    </div>
  );
};

export default FarmerDashboard;
