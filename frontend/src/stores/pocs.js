import pocsJSON from "@/assets/pocs.json";
import { crearStore } from "@/stores/fabricaStore";

export const usePocsStore = crearStore(
  "pocs",
  pocsJSON._embedded.pocs,
  "http://api-demeter/api/pocs"
);