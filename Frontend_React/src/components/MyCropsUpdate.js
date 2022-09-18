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

const MyCropsUpdate = () => {
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
      })
      .catch((error) => {
        var errorcode = error.response.status;
        console.log("Something went wrong", error);
        if (errorcode == 403) {
          history.push(`/error`);
          toast.error("You are not a Farmer");
        } else {
          toast.error(errorcode + ": Something went wrong");
        }
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
    handleDialog("Are you sure you want to deactivate?", true, name);
  };

  const areUSureDelete = (choose) => {
    if (choose) {
      console.log("Try to deactivate  " + choose);
      farmerService
        .deactivateFarmer(user.id)
        .then((response) => {
          console.log(response);
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
      console.log("Try to not deactivate " + choose);
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
  }, []);

  return (
    <div
      style={{
        backgroundImage: `url(${img3})`,
        backgroundAttachment: "fixed",
        height: "700px",
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
            onClick={() => primeAccount()}
            href={paymentUrl}
            className="w3-bar-item w3-button w3-padding"
          >
            <i className="fa fa-star"></i> Prime Memeber
          </Link>

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

          <Link to="/farmerDashboard">
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

          <Link to="/farmerDashboard">
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
              <h5 style={{ textAlign: "center" }}>My Crop Details</h5>
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

export default MyCropsUpdate;
