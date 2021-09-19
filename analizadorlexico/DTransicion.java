
package analizadorlexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class DTransicion {

    public static int filesize = 0;
    public static char[] linea;
    public static String hola;
    public static boolean fin_archivo = false;
    public static int a_a = 0;
    public static int a_i = 0;
    public static int c, ContRen = 1;
    public static int COMIENZO, ESTADO;
    public static String LEXEMA, MiToken;
    public static String Entrada, Salida;

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

    public static void rut_error() {
        System.out.println("\n\n Error: caracter [" + Character.toString((char) c) + "] en la linea" + ContRen
                + " compilacion terminada\n");
        System.exit(4);
    }

    public static File xArchivo(String xName) {
        File xFile = new File(xName);
        return xFile;
    }

    public static boolean es_let_hex(int X) {
        if (X >= 65 && X <= 70) {
            return (true);
        }
        return (false);
    }

    public static boolean es_letra(int X) {
        if (X >= 65 && X <= 90 || X >= 97 && X <= 122) {
            return (true);
        }
        return (false);
    }

    public static boolean es_numero(int X) {
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

    public static String obten_lexema() {
        String xx = "";
        for (int i = a_i; i <= a_a - 1; i++) {
            xx = xx + linea[i];
        }
        return (xx);
    }

    public static int diagrama() {
        a_a = a_i;
        switch (COMIENZO) {
            case 0:
                COMIENZO = 4;
                break;
            case 4:
                COMIENZO = 8;
                break;
            case 8:
                COMIENZO = 12;
                break;
            case 12:
                COMIENZO = 16;
                break;
            case 16:
                COMIENZO = 18;
                break;
            case 18:
                COMIENZO = 35;
                break;
            case 35:
                COMIENZO = 38;
                break;
            case 38:
                COMIENZO = 42;
                break;
            case 42:
                COMIENZO = 46;
                break;
            case 46:
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
                    if (c == 'M') {
                        ESTADO = 1;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 1:
                    c = lee_car();
                    if (c == 'O') {
                        ESTADO = 2;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 2:
                    c = lee_car();
                    if (c == 'V') {
                        ESTADO = 3;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;

                case 3:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("inst");

                case 4:
                    c = lee_car();
                    if (c == 'A') {
                        ESTADO = 5;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 5:
                    c = lee_car();
                    if (c == 'D') {
                        ESTADO = 6;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 6:
                    c = lee_car();
                    if (c == 'D') {
                        ESTADO = 7;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 7:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("inst");
                case 8:
                    c = lee_car();
                    if (c == 'J') {
                        ESTADO = 9;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 9:
                    c = lee_car();
                    if (c == 'M') {
                        ESTADO = 10;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 10:
                    c = lee_car();
                    if (c == 'P') {
                        ESTADO = 11;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 11:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("inst");
                case 12:
                    c = lee_car();
                    if (c == 'N') {
                        ESTADO = 13;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 13:
                    c = lee_car();
                    if (c == 'O') {
                        ESTADO = 14;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;

                case 14:
                    c = lee_car();
                    if (c == 'P') {
                        ESTADO = 15;

                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 15:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("inst");
                case 16:
                    c = lee_car();
                    if (c == ',') {
                        ESTADO = 17;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 17:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("sep");
                case 18:
                    c = lee_car();
                    switch (c) {
                        case 'A':
                            ESTADO = 19;
                            break;
                        case 'B':
                            ESTADO = 23;
                            break;
                        case 'C':
                            ESTADO = 27;
                            break;
                        case 'D':
                            ESTADO = 31;
                            break;
                        case 'I':
                            ESTADO = 33;
                            break;
                        default:
                            ESTADO = diagrama();

                    }
                    break;
                case 19:
                    c = lee_car();
                    switch (c) {
                        case 'X':
                            ESTADO = 20;
                            break;
                        case 'H':
                            ESTADO = 21;
                            break;
                        case 'L':
                            ESTADO = 22;
                            break;
                        default:
                            ESTADO = diagrama();

                    }
                    break;
                case 20:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("reg");
                case 21:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("reg");
                case 22:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("reg");
                case 23:
                    c = lee_car();
                    switch (c) {
                        case 'X':
                            ESTADO = 24;
                            break;
                        case 'H':
                            ESTADO = 25;
                            break;
                        case 'L':
                            ESTADO = 26;
                            break;
                        default:
                            ESTADO = diagrama();

                    }
                    break;
                case 24:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("reg");
                case 25:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("reg");
                case 26:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("reg");
                case 27:
                    c = lee_car();
                    switch (c) {
                        case 'X':
                            ESTADO = 28;
                            break;
                        case 'H':
                            ESTADO = 29;
                            break;
                        case 'L':
                            ESTADO = 30;
                            break;
                        default:
                            ESTADO = diagrama();

                    }
                    break;
                case 28:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("reg");
                case 29:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("reg");
                case 30:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("reg");

                case 31:
                    c = lee_car();
                    if (c == 'I') {
                        ESTADO = 32;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 32:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("reg");
                case 33:
                    c = lee_car();
                    if (c == 'P') {
                        ESTADO = 34;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 34:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("reg");
                case 35:
                    c = lee_car();
                    if (es_let_hex(c) || es_numero(c)) {
                        ESTADO = 36;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 36:
                    c = lee_car();
                    if (es_let_hex(c) || es_numero(c)) {
                        ESTADO = 36;
                    } else {
                        if (c == 'h') {
                            ESTADO = 37;
                        } else {
                            ESTADO = diagrama();
                        }
                    }
                    break;
                case 37:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("numhex");
                case 38:
                    c = lee_car();
                    if (c == ':') {
                        ESTADO = 39;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 39:
                    c = lee_car();
                    if (es_letra(c)) {
                        ESTADO = 40;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;

                case 40:
                    c = lee_car();
                    if (es_letra(c) || es_numero(c)) {
                        ESTADO = 40;
                    } else {
                        ESTADO = 41;
                    }
                    break;
                case 41:
                    a_a = a_a - 1;
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("ll_etq");
                case 42:
                    c = lee_car();
                    if (c == '(') {
                        ESTADO = 43;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 43:
                    c = lee_car();
                    if (es_letra(c)) {
                        ESTADO = 44;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 44:
                    c = lee_car();
                    if (es_letra(c) || es_numero(c)) {
                        ESTADO = 44;
                    } else {
                        if (c == ')') {
                            ESTADO = 45;
                        } else {
                            ESTADO = diagrama();
                        }
                    }
                    break;
                case 45:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("etq_dest");
                case 46:
                    c = lee_car();
                    if (es_delim(c)) {
                        ESTADO = 47;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;

                case 47:
                    c = lee_car();
                    if (es_delim(c)) {
                        ESTADO = 47;
                    } else {
                        ESTADO = 48;
                    }
                    break;
                case 48:
                    a_a = a_a - 1;
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("nosirve");
                case 49:
                    c = lee_car();
                    if (c == 255) {
                        ESTADO = 50;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 50:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("nosirve");
            }

        }
    }

    /**
     * @param args the command line arguments
     */
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

    public static void main(String argumento[]) {

        try {
            Entrada = argumento[0] + ".asm";
        } catch (Exception e) {
            System.out.println("Error en el archivo de entrada");
            System.exit(4);
        }
        if (!xArchivo(Entrada).exists()) {
            System.out.println("El archivo [" + Entrada + "] no existe");
            System.exit(4);
        }

        Salida = argumento[0] + ".sal";

        new File(Salida).delete();

        linea = abreLeeCierra(Entrada);
        while (!fin_archivo) {
            ESTADO = 0;
            COMIENZO = 0;
            MiToken = TOKEN();
            // System.out.println("Encontre token [" + MiToken + "} con lexema[" + LEXEMA +
            // "]");
            // pausa();
            if (!MiToken.equals("nosirve")) {
                creaEscribeArchivo(xArchivo(Salida), MiToken);
                creaEscribeArchivo(xArchivo(Salida), LEXEMA);
                creaEscribeArchivo(xArchivo(Salida), ContRen + "");
            }
        }

        creaEscribeArchivo(xArchivo(Salida), "eof");
        creaEscribeArchivo(xArchivo(Salida), "eof");
        creaEscribeArchivo(xArchivo(Salida), "" + (ContRen + 1));

        System.out.println("Analisis lexicografico existoso");
    }

}
