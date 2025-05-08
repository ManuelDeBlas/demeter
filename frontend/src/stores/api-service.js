import axios from "axios";

export const API_HOST =
  "http://localhost:8080/api/";

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

export function cambiarHttpPorHttps(enlace) {
  return enlace.replace("http", "https");
}

export function getElementos(nombreColeccion) {
  console.log("En apiservice, nombreColeccion: ", nombreColeccion);
  return llamadaAPI("get", null, API_HOST + nombreColeccion);
}

export function postEnColeccion(data, nombreColeccion) {
  return llamadaAPI("post", data, API_HOST + nombreColeccion);
}

export function deleteEntidad(entidad) {
  console.log("En apiservice, antes de delete: ", entidad);
  return llamadaAPI("delete", null, cambiarHttpPorHttps(entidad));
}

export function putEntidad(entidad, data) {
  console.log("En apiservice, antes de put: ", entidad);
  return llamadaAPI("put", data, cambiarHttpPorHttps(entidad));
}
