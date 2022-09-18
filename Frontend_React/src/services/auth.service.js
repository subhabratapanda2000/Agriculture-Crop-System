import axios from "axios";
import httpClient from "../components/http-common";
import authHeader from "../services/auth-header";

const findByFarmerId = (fid) => {
  return httpClient.get(`findByIdOfFarmer/${fid}`, { headers: authHeader() });
};

const farmerUpdate = (data) => {
  return httpClient.put(`farmer/updateFarmer/${data.fid}`, data, {
    headers: authHeader(),
  });
};
const changePassFarmer = (fid, data) => {
  return httpClient.put(`farmer/changePasswordOfFarmer/${fid}`, data, {
    headers: authHeader(),
  });
};

const deactivateFarmer = (fid) => {
  return httpClient.get(`farmer/deactivateFarmer/${fid}`, {
    headers: authHeader(),
  });
};

const getOfferFromDealer = () => {
  return httpClient.get("farmer/getOffers", {
    headers: authHeader(),
  });
};

const getPaymentAckById = (id) => {
  return httpClient.get(`anyRole/findLastPaymentsByCustomerId/${id}`, {
    headers: authHeader(),
  });
};

const getPaymentById = (id) => {
  return httpClient.get(`anyRole/findPaymentsByCustomerId/${id}`, {
    headers: authHeader(),
  });
};

//Dealers

const findByDealerId = (did) => {
  return httpClient.get(`findByIdOfDealer/${did}`, { headers: authHeader() });
};

const dealerUpdate = (data) => {
  return httpClient.put(`dealer/updateDealer/${data.fid}`, data, {
    headers: authHeader(),
  });
};

const changePassDealer = (did, data) => {
  return httpClient.put(`dealer/changePasswordOfDealer/${did}`, data, {
    headers: authHeader(),
  });
};

const deactivateDealer = (did) => {
  return httpClient.get(`dealer/deactivateDealer/${did}`, {
    headers: authHeader(),
  });
};

const sendOfferToFarmers = (data, did) => {
  return httpClient.post(`dealer/sendOfferToFarmers/${did}`, data, {
    headers: authHeader(),
  });
};

//Crops
const findAllCropsOfOne = (fid) => {
  return httpClient.get(`anyRole/findAllCropsByFid/${fid}`, {
    headers: authHeader(),
  });
};

const addCrops = (data, fid) => {
  return httpClient.post(`farmer/addCrop/${fid}`, data, {
    headers: authHeader(),
  });
};

const updateCrops = (data, cid, fid) => {
  return httpClient.put(`farmer/updateCrop/${cid}/${fid}`, data, {
    headers: authHeader(),
  });
};

const deleteCrops = (cid, fid) => {
  return httpClient.delete(`farmer/deleteCrop/${cid}/${fid}`, {
    headers: authHeader(),
  });
};

const findAllCrops = () => {
  return httpClient.get("anyRole/findAllCrops", {
    headers: authHeader(),
  });
};

const findCropsByCId = (id) => {
  return httpClient.get(`anyRole/findCropById/${id}`, {
    headers: authHeader(),
  });
};

const findFarmerInfoWithCrops = (fid) => {
  return httpClient.get(`anyRole/findFarmerDetailsWithCrops/${fid}`, {
    headers: authHeader(),
  });
};

//Admin
const findAllFarmers = () => {
  return httpClient.get("admin/findAllFarmers", {
    headers: authHeader(),
  });
};

const findAllActiveFarmers = () => {
  return httpClient.get("admin/findAllActiveFarmers", {
    headers: authHeader(),
  });
};

const findAllPrimeFarmers = () => {
  return httpClient.get("admin/findAllPrimeFarmers", {
    headers: authHeader(),
  });
};

const findAllDealers = () => {
  return httpClient.get("admin/findAllDealers", {
    headers: authHeader(),
  });
};

const findAllActiveDealers = () => {
  return httpClient.get("admin/findAllActiveDealers", {
    headers: authHeader(),
  });
};

const findAllPrimeDealers = () => {
  return httpClient.get("admin/findAllPrimeDealers", {
    headers: authHeader(),
  });
};

const findAllPayments = () => {
  return httpClient.get("admin/findAllPayments", {
    headers: authHeader(),
  });
};

//Crops SEarch
const findCropsByCName = (name) => {
  return httpClient.get(`anyRole/findCropsByCropName/${name}`, {
    headers: authHeader(),
  });
};

const findCropsByCNameandQuantity = (name, qty) => {
  return httpClient.get(
    `anyRole/findCropsByCropNameAndQuantity/${name}/${qty}`,
    {
      headers: authHeader(),
    }
  );
};

const findCropsByCNameandPrice = (name, price) => {
  return httpClient.get(
    `anyRole/findCropsByCropNameAndPrice/${name}/${price}`,
    {
      headers: authHeader(),
    }
  );
};

const findCropsByCNameandPriceandQnty = (name, price, qty) => {
  return httpClient.get(
    `anyRole/findCropsByCropNameAndPriceAndQuantity/${name}/${price}/${qty}`,
    {
      headers: authHeader(),
    }
  );
};

//Payment

const farmerCreate = (data) => {
  return httpClient.post("createFarmer", data);
};

const dealerCreate = (data) => {
  return httpClient.post("createDealer", data);
};

const login = (username, password) => {
  return httpClient
    .post("authenticate", {
      username,
      password,
    })
    .then((response) => {
      if (response.data.jwt) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }
      return response.data;
    });
};
const logout = () => {
  localStorage.removeItem("user");
};
const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};
const AuthService = {
  login,
  logout,
  getCurrentUser,
  farmerCreate,
  dealerCreate,
  farmerUpdate,
  findByFarmerId,
  deactivateFarmer,
  changePassFarmer,
  getOfferFromDealer,
  findFarmerInfoWithCrops,

  dealerUpdate,
  findByDealerId,
  deactivateDealer,
  changePassDealer,
  sendOfferToFarmers,

  getPaymentAckById,
  getPaymentById,

  findAllCropsOfOne,
  addCrops,
  updateCrops,
  deleteCrops,
  findAllCrops,
  findCropsByCId,
  findCropsByCName,
  findCropsByCNameandPrice,
  findCropsByCNameandQuantity,
  findCropsByCNameandPriceandQnty,

  findAllFarmers,
  findAllActiveFarmers,
  findAllPrimeFarmers,
  findAllDealers,
  findAllActiveDealers,
  findAllPrimeDealers,
  findAllPayments,
};
export default AuthService;
