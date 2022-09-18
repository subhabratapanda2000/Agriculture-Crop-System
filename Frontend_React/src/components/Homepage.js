import React from "react";
import img1 from "../images/office-worker-icon.png";
// import img from './images/Employee.jpeg';
import img2 from "../images/farmer-icon.png";
import img3 from "../images/farmer-field.jpg";
import service from "../services/auth.service";
import LinkCard from "../components/LinkCard";
import { Link, useHistory, useParams } from "react-router-dom";
import Header from "./Header";

const Homepage = () => {
  const history = useHistory();
  if (service.getCurrentUser()) {
    const user = service.getCurrentUser();

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

  return (
    <>
      <div>
        <Header />
        <div
          className="Homepage"
          style={{
            backgroundImage: `url(${img3})`,
            backgroundAttachment: "fixed",
            height: "700px",
            width: "100%",
            backgroundRepeat: "no-repeat",
            backgroundSize: "cover",
            backgroundBlendMode: "lighten",
          }}
        >
          <div
            style={{
              paddingTop: "20px",
              paddingBottom: "0px",
            }}
          >
            <h2
              className="display-4 text-center pb-2"
              style={{
                paddingTop: "20px",
                paddingBottom: "0px",
              }}
            >
              <b>Welcome To</b>
            </h2>
            <h1
              className="display-4 text-center pb-2"
              style={{
                paddingTop: "0px",

                marginBottom: "30px",
                fontSize: "60px",
              }}
            >
              <b> Agriculture Crop System</b>
            </h1>
            <h5
              style={{
                color: "blue",
                textAlign: "center",
                fontSize: "30px",
                paddingTop: "-10px",
                paddingBottom: "30px",
              }}
            >
              To Get The Accurate Price of Crops
            </h5>
          </div>

          <div>
            <p
              className="display-6 text-center text-primary my-2 border-white"
              style={{
                alignContent: "center",
                color: "#1CDFED",
                border: "2px solid ",
                borderRadius: "25px",
                margin: "500px",
                height: "60px",
                backgroundColor: "rgba(216, 227, 225, 0.3)",
              }}
            >
              <b>Explore sections</b>
            </p>
            <div className="d-flex justify-content-center">
              <div className="row">
                <LinkCard
                  imgsrc={img2}
                  path="/farmerlogin"
                  title="Farmer"
                  para="Farmer section"
                />

                <LinkCard
                  imgsrc={img1}
                  path="/dealerlogin"
                  title="Dealer"
                  para="Dealer section"
                />

                {/* <LinkCard imgsrc={img3} path="/supplier/login" title="Supplier" para="Explore Supplier section" /> */}
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default Homepage;
