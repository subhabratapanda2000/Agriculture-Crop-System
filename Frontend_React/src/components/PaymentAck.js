import React from "react";
import { useEffect, useState } from "react";
import { Link, useHistory, useParams, NavLink } from "react-router-dom";
import service from "../services/auth.service";
import img1 from "../images/farmer-icon.png";
import img3 from "../images/farmer_field.jpg";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Dialog from "../components/Dialog";
import Header2 from "./Header2";

const PaymentAck = () => {
  const history = useHistory();
  const user = service.getCurrentUser();
  const [loading, setLoading] = useState(true);
  const [content, setContent] = useState(false);
  const [orderId, setOrderrId] = useState("");
  const [customerId, setCustomerId] = useState("");
  const [result, setResult] = useState("");
  const [bankName, setBankName] = useState("");
  const [bankTxnId, setBankTxnId] = useState("");
  const [currency, setCurrency] = useState("");
  const [paymentMode, setPaymentMode] = useState("");
  const [respcode, setRespcode] = useState("");
  const [respmsg, setRespmsg] = useState("");
  const [status, setStatus] = useState("");
  const [txnAmount, setTxnAmount] = useState("");
  const [txnDate, setTxnDate] = useState("");
  const [txnId, setTxnId] = useState("");
  const [role, setRole] = useState("");
  const url = "/" + role + "Dashboard";

  const init = () => {
    service
      .getPaymentAckById(user.id)
      .then((payment) => {
        console.log(payment);
        setOrderrId(payment.data.orderId);
        setCustomerId(payment.data.customerId);
        setResult(payment.data.result);
        setBankName(payment.data.transactionDeatils.BANKNAME);
        setBankTxnId(payment.data.transactionDeatils.BANKTXNID);
        setCurrency(payment.data.transactionDeatils.CURRENCY);
        setPaymentMode(payment.data.transactionDeatils.PAYMENTMODE);
        setRespcode(payment.data.transactionDeatils.RESPCODE);
        setRespmsg(payment.data.transactionDeatils.RESPMSG);
        setStatus(payment.data.transactionDeatils.STATUS);
        setTxnAmount(payment.data.transactionDeatils.TXNAMOUNT);
        setTxnDate(payment.data.transactionDeatils.TXNDATE);
        setTxnId(payment.data.transactionDeatils.TXNID);
        if (user.role == "ROLE_FARMER") {
          setRole("farmer");
        } else if (user.role == "ROLE_DEALER") {
          setRole("dealer");
        }
        if (payment.data.result == "Payment Successful") {
          toast.success(user.name + " Your payment has been successfully");
          toast.success(user.name + " Now you are a prime user");
        } else {
          toast.error(payment.data.result);
        }
        setLoading(false);
        setContent(true);
      })
      .catch((error) => {
        setLoading(false);
        var errorcode = error.response.status;
        console.log("Something went wrong", error);
        toast.error("Something went wrong");
      });
  };

  useEffect(() => {
    init();
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
        alignContent: "center",
        paddingLeft: "400px",
        paddingBottom: "50px",
      }}
    >
      <Header2></Header2>
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
              <h3
                style={{
                  textAlign: "center",
                  paddingTop: "20px",
                  paddingBottom: "5px",
                }}
              >
                Payment Receipt
              </h3>
              <p
                style={{
                  textAlign: "center",
                  paddingBottom: "5px",
                }}
              >
                (Take a Screenshot of this payment receipt or note down the
                details)
              </p>
              <table
                className="w3-table-all w3-striped "
                style={{ paddingBottom: "100px", marginBottom: "50px" }}
              >
                <tbody>
                  <tr>
                    <td>
                      <h6>Order Id: </h6>
                    </td>
                    <td>
                      <h6>{orderId}</h6>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <h6>{role} Id: </h6>
                    </td>
                    <td>
                      <h6>{customerId}</h6>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <h6>{role} Name: </h6>
                    </td>
                    <td>
                      <h6>{user.name}</h6>
                    </td>
                  </tr>

                  <tr>
                    <td>
                      <h6>Payment Result </h6>
                    </td>
                    <td>
                      <h6>{result}</h6>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <h6>Bank Name: </h6>
                    </td>
                    <td>
                      <h6>{bankName}</h6>
                    </td>
                  </tr>

                  <tr>
                    <td>
                      <h6>Bank Transaction Id: </h6>
                    </td>
                    <td>
                      <h6>{bankTxnId}</h6>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <h6>Currency: </h6>
                    </td>
                    <td>
                      <h6>{currency}</h6>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <h6>Payment Mode: </h6>
                    </td>
                    <td>
                      <h6>{paymentMode}</h6>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <h6>Response Code: </h6>
                    </td>
                    <td>
                      <h6>{respcode}</h6>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <h6>Response Message: </h6>
                    </td>
                    <td>
                      <h6>{respmsg}</h6>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <h6>Payment Status: </h6>
                    </td>
                    <td>
                      <h6>{status}</h6>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <h6>Transaction Amount: </h6>
                    </td>
                    <td>
                      <h6>{txnAmount}</h6>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <h6>Transaction Date: </h6>
                    </td>
                    <td>
                      <h6>{txnDate}</h6>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <h6>Transaction Id: </h6>
                    </td>
                    <td>
                      <h6>{txnId}</h6>
                    </td>
                  </tr>
                </tbody>
              </table>
              <button
                className="btn btn-info"
                onClick={() => history.push(url)}
              >
                Back to Dashboard
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default PaymentAck;
