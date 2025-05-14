import axios from "axios";

export const API_HOST =
  "https://demeter-72fd40c612e8.herokuapp.com/api";

function llamadaAPI(method, body, path) {
  let config = {
    method: method ?? "get",
    maxBodyLength: Infinity,
    url: path,
    headers: {},
  };
  if (body) {
    (config.data = body), (config.headers["content-Type"] = "application/json");
  }
  return axios.request(config);
}

export function get(url) {
  return llamadaAPI("get", null, url);
}

export function post(data, url) {
  return llamadaAPI("post", data, url);
}

export function deleteEntidad(url) {
  return llamadaAPI("delete", null, url);
}

export function put(objeto, url) {
  return llamadaAPI("put", objeto, url);
}

export function patchEntidad(url, data) {
  return llamadaAPI("patch", data, url);
}
