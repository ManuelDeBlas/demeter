package es.mde.util;

public class StringUtils {

  public static String centimosToEurosString(int centimos) {
    int euros = centimos / 100;
    int decimales = centimos % 100;
    return euros + "," + (decimales < 10 ? "0" + decimales : decimales);
  }

}
