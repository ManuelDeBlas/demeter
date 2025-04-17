import reservistasJSON from "@/assets/reservistas.json";
import { crearStore } from "@/stores/fabricaStore";

export const useReservistasStore = crearStore(
  "reservistas",
  reservistasJSON._embedded.reservistas,
  "http://api-demeter/api/reservistas"
);