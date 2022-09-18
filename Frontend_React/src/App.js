import logo from "./logo.svg";
import "./App.css";
import React from "react";
import { ToastContainer, toast } from "react-toastify";
import { BrowserRouter, Switch, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import NotFound from "./components/NotFound";
import Header from "./components/Header";
import Homepage from "./components/Homepage";
import FarmerLogin from "./components/FarmerLogin";
import DealerLogin from "./components/DealerLogin";
import AdminLogin from "./components/AdminLogin";
import About from "./components/About";
import SPanda from "./components/SubhabrataPanda";
import AddFarmer from "./components/AddFarmer";
import UpdateFarmer from "./components/UpdateFarmer";
import UpdateDealer from "./components/UpdateDealer";
import AddDealer from "./components/AddDealer";
import FarmerDashboard from "./components/FarmerDashboard";
import DealerDashboard from "./components/DealerDashboard";
import "react-toastify/dist/ReactToastify.css";
import ChangePwdFarmer from "./components/ChangePwdFarmer";
import PaymentAck from "./components/PaymentAck";
import GetOffer from "./components/GetOffer";
import MyCrops from "./components/MyCrops";
import AddCrops from "./components/AddCrops";
import UpdateCrops from "./components/UpdateCrop";
import AllCrops from "./components/AllCrops";
import FarmerProfile from "./components/FarmerProfile";
import ChangePwdDealer from "./components/ChangePwdDealer";
import FarmerProfile_dl from "./components/FarmerProfile_dl";
import SendOffer from "./components/SendOffer";
import AllCrops_dl from "./components/AllCrops_dl";
import AdminDashboard from "./components/AdminDashboard";
import AllCrops_ad from "./components/AllCrops_ad";
import FarmerProfile_ad from "./components/FarmerProfile_ad";
import AllFarmers from "./components/AllFarmers";
import AllDealers from "./components/AllDealers";
import FarmerDetails from "./components/FarmerDetails";
import DealerDetails from "./components/DealerDetails";
import AllPayments from "./components/AllPayments";
import CustomerPayment from "./components/CustomerPayment";

function App() {
  return (
    <>
      <BrowserRouter>
        <div>
          <div>
            <Switch>
              <Route exact path="/" component={Homepage} />
              <Route path="/addFarmer" component={AddFarmer} />
              <Route path="/updateFarmer" component={UpdateFarmer} />
              <Route path="/changePwdFarmer" component={ChangePwdFarmer} />
              <Route path="/changePwdDealer" component={ChangePwdDealer} />
              <Route path="/updateDealer" component={UpdateDealer} />
              <Route path="/farmerDashboard" component={FarmerDashboard} />
              <Route path="/adminDashboard" component={AdminDashboard} />
              <Route path="/getOffers" component={GetOffer} />
              <Route path="/sendOffers" component={SendOffer} />
              <Route path="/dealerDashboard" component={DealerDashboard} />
              <Route path="/addDealer" component={AddDealer} />
              <Route path="/admin/login" component={AdminLogin} />
              <Route path="/farmerlogin" component={FarmerLogin} />
              <Route path="/dealerlogin" component={DealerLogin} />
              <Route path="/aboutus" component={About} />
              <Route path="/paymentAck" component={PaymentAck} />
              <Route path="/spanda" component={SPanda} />
              <Route path="/error" component={NotFound} />

              <Route path="/myCrops" component={MyCrops} />
              <Route path="/addCrops" component={AddCrops} />
              <Route path="/updateCrop/:id" component={UpdateCrops} />
              <Route path="/allCrops" component={AllCrops} />
              <Route path="/allCrops_dl" component={AllCrops_dl} />
              <Route path="/allCrops_ad" component={AllCrops_ad} />
              <Route path="/farmerProfile/:fid" component={FarmerProfile} />
              <Route
                path="/farmerProfile_dl/:fid"
                component={FarmerProfile_dl}
              />
              <Route
                path="/farmerProfile_ad/:fid"
                component={FarmerProfile_ad}
              />
              <Route path="/farmerDetails/:fid" component={FarmerDetails} />
              <Route path="/dealerDetails/:fid" component={DealerDetails} />
              <Route path="/customerPayment/:id" component={CustomerPayment} />

              <Route path="/allFarmers" component={AllFarmers} />
              <Route path="/allDealers" component={AllDealers} />
              <Route path="/allPayments" component={AllPayments} />
            </Switch>
          </div>
          {/* footer */}

          <div
            className="navbar navbar-expand-sm  navbar-dark fixed-bottom "
            style={{
              backgroundColor: "#bdb76b",
              textAlign: "center",
              maxHeight: "40px",
            }}
          >
            <nav className="navbar">
              <p
                style={{
                  textAlign: "center",
                  paddingLeft: "450px",
                  paddingTop: "15px",
                }}
              >
                &#169;2022,All Rights Reserved to Agriculture Crop System &nbsp;
                &nbsp; &nbsp; &nbsp; &nbsp; @Made by{" "}
                <Link to="/spanda" style={{ color: "#6B0606" }}>
                  {" "}
                  &nbsp; Subhabrata Panda
                </Link>
              </p>
            </nav>
          </div>
        </div>
      </BrowserRouter>
      <ToastContainer position="top-center" />
    </>
  );
}

export default App;
