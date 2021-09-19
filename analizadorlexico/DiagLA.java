
package analizadorlexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class DiagLA {

    public static int filesize = 0;
    public static char[] linea;
    public static String hola;
    public static boolean fin_archivo = false;
    public static int a_a = 0;
    public static int a_i = 0;
    public static int c, ContRen = 1;
    public static int COMIENZO, ESTADO;
    public static String LEXEMA, MiToken;
    public static String Entrada = "", Salida = "";

    public static void rut_error() {
        System.out.println("\n\n Error: caracter[" + Character.toString((char) c) + "] en la linea" + ContRen
                + "compilacion terminada\n");
        System.exit(4);
    }

    public static boolean es_letra(int X) {
        if (X >= 65 && X <= 90 || X >= 97 && X <= 122) {
            return (true);
        }
        return (false);
    }

    public static boolean es_digito(int X) {
        if (X >= 48 && X <= 57) {
            return (true);
        }
        return (false);
    }

    public static boolean es_delim(int X) {
        if (X == 32 || X == 9 || X == 10 || X == 13) {
            return (true);
        }
        return (false);
    }

    public static String pausa() {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        String nada = null;
        try {
            nada = entrada.readLine();
            return (nada);
        } catch (Exception e) {
            System.err.println(e);
        }
        return ("");
    }

    public static int lee_car() {
        if (a_a <= filesize - 1) {
            if (linea[a_a] == 10) {
                ContRen++;
            }
            return linea[a_a++];
        } else {
            fin_archivo = true;
            return 255;
        }
    }

    public static char[] abreLeeCierra(String xName) {
        File xFile = new File(xName);
        char[] data;
        try {
            FileReader fin = new FileReader(xFile);
            filesize = (int) xFile.length();
            data = new char[filesize + 1];
            fin.read(data, 0, filesize);
            data[filesize] = ' ';
            filesize = filesize + 1;
            fin.close();
            return (data);
        } catch (FileNotFoundException exc) {
        } catch (IOException exc) {
        }
        return null;
    }

    public static String obten_lexema() {
        String xx = "";
        for (int i = a_i; i <= a_a - 1; i++) {
            xx = xx + linea[i];
        }
        return (xx);
    }

    public static File xArchivo(String xName) {
        File xFile = new File(xName);
        return xFile;
    }

    public static boolean creaEscribeArchivo(File xFile, String mensaje) {
        try {
            PrintWriter fileOut = new PrintWriter(new FileWriter(xFile, true));
            fileOut.println(mensaje);
            fileOut.close();
            return true;

        } catch (IOException ex) {
            return false;
        }
    }

    public static int diagrama() {
        a_a = a_i;
        switch (COMIENZO) {
            case 0:
                COMIENZO = 4;
                break;
            case 4:
                COMIENZO = 13;
                break;
            case 13:
                COMIENZO = 18;
                break;
            case 18:
                COMIENZO = 20;
                break;
            case 20:
                COMIENZO = 23;
                break;
            case 23:
                rut_error();
                break;

        }
        return (COMIENZO);
    }

    public static String TOKEN() {
        while (true) {
            switch (ESTADO) {
                case 0:
                    c = lee_car();
                    if (es_letra(c)) {
                        ESTADO = 1;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 1:
                    c = lee_car();
                    if (es_letra(c) || es_digito(c)) {
                        ESTADO = 1;
                    } else {
                        if (c == '_') {
                            ESTADO = 2;
                        } else {
                            ESTADO = 3;
                        }
                    }
                    break;
                case 2:
                    c = lee_car();
                    if (es_letra(c) || es_digito(c)) {
                        ESTADO = 1;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 3:
                    a_a--;
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("id");
                case 4:
                    c = lee_car();
                    if (es_digito(c)) {
                        ESTADO = 5;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 5:
                    c = lee_car();
                    if (es_digito(c)) {
                        ESTADO = 5;
                    } else {
                        switch (c) {
                            case 'E':
                                ESTADO = 9;
                                break;
                            case 'e':
                                ESTADO = 9;
                                break;
                            case '.':
                                ESTADO = 6;
                                break;
                            default:
                                ESTADO = 8;
                                break;
                        }
                    }
                    break;
                case 6:
                    c = lee_car();
                    if (es_digito(c)) {
                        ESTADO = 7;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 7:
                    c = lee_car();
                    if (es_digito(c)) {
                        ESTADO = 7;
                    } else {
                        switch (c) {
                            case 'E':
                                ESTADO = 9;
                                break;
                            case 'e':
                                ESTADO = 9;
                                break;
                            default:
                                ESTADO = 8;
                                break;
                        }
                    }
                    break;

                case 8:
                    a_a--;
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("num");
                case 9:
                    c = lee_car();
                    if (es_digito(c)) {
                        ESTADO = 11;
                    } else {
                        if (c == '+' || c == '-') {
                            ESTADO = 10;
                        } else {
                            ESTADO = diagrama();
                        }
                    }
                    break;
                case 10:
                    c = lee_car();
                    if (es_digito(c)) {
                        ESTADO = 11;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 11:
                    c = lee_car();
                    if (es_digito(c)) {
                        ESTADO = 11;
                    } else {
                        ESTADO = 12;
                    }
                    break;
                case 12:
                    a_a--;
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("num");
                case 13:
                    c = lee_car();
                    switch (c) {
                        case '+':
                            ESTADO = 14;
                            break;
                        case '-':
                            ESTADO = 15;
                            break;
                        case '*':
                            ESTADO = 16;
                            break;
                        case '/':
                            ESTADO = 17;
                            break;
                        default:
                            ESTADO = diagrama();
                            break;
                    }
                    break;

                case 14:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("+");
                case 15:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("-");
                case 16:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("*");
                case 17:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("/");
                case 18:
                    c = lee_car();
                    if (c == 255) {
                        ESTADO = 19;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 19:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("eof");
                case 20:
                    c = lee_car();
                    switch (c) {
                        case '(':
                            ESTADO = 21;
                            break;
                        case ')':
                            ESTADO = 22;
                            break;
                        default:
                            ESTADO = diagrama();
                            break;
                    }
                    break;
                case 21:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("pa");
                case 22:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("pc");
                case 23:
                    c = lee_car();
                    if (es_delim(c)) {
                        ESTADO = 24;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 24:
                    c = lee_car();
                    if (es_delim(c)) {
                        ESTADO = 24;
                    } else {
                        ESTADO = 25;
                    }
                    break;
                case 25:
                    a_a--;
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("basura");

            }

        }
    }

    public static void main(String argumento[]) {

        try {
            Entrada = argumento[0] + ".LA";
        } catch (Exception e) {
            System.out.println("Error en el archivo de entrada");
            System.exit(4);
        }
        if (!xArchivo(Entrada).exists()) {
            System.out.println("El archivo [" + Entrada + "] no existe");
            System.exit(4);
        }
        Salida = argumento[0] + ".LA1";

        linea = abreLeeCierra(Entrada);
        while (!fin_archivo) {
            ESTADO = 0;
            COMIENZO = 0;
            MiToken = TOKEN();

            if (!MiToken.equals("basura")) {
                creaEscribeArchivo(xArchivo(Salida), MiToken);
                creaEscribeArchivo(xArchivo(Salida), LEXEMA);
                creaEscribeArchivo(xArchivo(Salida), ContRen + "");
            }

        }
        creaEscribeArchivo(xArchivo(Salida), "eof");
        creaEscribeArchivo(xArchivo(Salida), "eof");
        creaEscribeArchivo(xArchivo(Salida), "53");
        System.out.println("");

    }

}
