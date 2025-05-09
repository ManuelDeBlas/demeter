import axios from "axios";

export const API_HOST =
  "http://localhost:8080/api";

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
  console.log("En apiservice ", url);
  return llamadaAPI("get", null, url);
}

export function post(data, url) {
  return llamadaAPI("post", data, url);
}

export function deleteEntidad(entidad) {
  console.log("En apiservice, antes de delete: ", entidad);
  return llamadaAPI("delete", null, entidad);
}

export function putEntidad(entidad, data) {
  console.log("En apiservice, antes de put: ", entidad);
  return llamadaAPI("put", data, entidad);
}

export function patchEntidad(entidad, data) {
  console.log("En apiservice, antes de patch: ", entidad);
  return llamadaAPI("patch", data, entidad);
}
