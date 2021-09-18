
package analizadorlexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Analizador2 {

    public static int filesize = 0;
    public static char[] linea;
    static String[] pr = new String[100];
    public static String hola;
    public static boolean fin_archivo = false;
    public static int a_a = 0;
    public static int a_i = 0;
    public static int c, ContRen = 1;
    public static int COMIENZO, ESTADO;
    public static String LEXEMA, MiToken;
    public static String Entrada = "", Salida = "";

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
        System.out.println("\n\n Error: caracter[" + Character.toString((char) c) + "] en la linea" + ContRen
                + "compilacion terminada\n");
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

    public static boolean es_mayuscula(int X) {
        if (X >= 65 && X <= 90) {
            return true;
        }
        return false;
    }

    public static boolean es_pr(String X) {
        pr[0] = "MOV";
        pr[1] = "JMP";
        pr[2] = "ADD";
        pr[3] = "NOP";
        for (int i = 0; i <= 3; i++) {
            if (pr[i].equals(X)) {
                return true;
            }
        }
        return false;
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
                COMIENZO = 2;
                break;
            case 2:
                COMIENZO = 11;
                break;
            case 11:
                COMIENZO = 14;
                break;
            case 14:
                COMIENZO = 17;
                break;
            case 17:
                COMIENZO = 21;
                break;
            case 21:
                COMIENZO = 25;
                break;
            case 25:
                COMIENZO = 28;
                break;
            case 28:
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
                    if (c == ',') {
                        ESTADO = 1;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 1:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("sep");
                case 2:
                    c = lee_car();
                    switch (c) {
                        case 'A':
                            ESTADO = 3;
                            break;
                        case 'B':
                            ESTADO = 3;
                            break;
                        case 'C':
                            ESTADO = 3;
                            break;
                        case 'D':
                            ESTADO = 7;
                            break;
                        case 'I':
                            ESTADO = 9;
                            break;
                        default:
                            ESTADO = diagrama();

                    }
                    break;
                case 3:
                    c = lee_car();
                    switch (c) {
                        case 'X':
                            ESTADO = 4;
                            break;
                        case 'H':
                            ESTADO = 5;
                            break;
                        case 'L':
                            ESTADO = 6;
                            break;
                        default:
                            ESTADO = diagrama();
                    }
                    break;
                case 4:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("reg");
                case 5:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("reg");
                case 6:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("reg");
                case 7:
                    c = lee_car();
                    if (c == 'I') {
                        ESTADO = 8;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 8:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("reg");
                case 9:
                    c = lee_car();
                    if (c == 'P') {
                        ESTADO = 10;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 10:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("reg");
                case 11:
                    c = lee_car();
                    if (es_mayuscula(c)) {
                        ESTADO = 12;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 12:
                    c = lee_car();
                    if (es_mayuscula(c)) {
                        ESTADO = 12;
                    } else {
                        ESTADO = 13;
                    }
                    break;

                case 13:
                    a_a = a_a - 1;
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    if (es_pr(LEXEMA)) {
                        a_i = a_a;
                        return ("inst");
                    }
                    ESTADO = diagrama();
                    break;
                case 14:
                    c = lee_car();
                    if (es_let_hex(c) || es_numero(c)) {
                        ESTADO = 15;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 15:
                    c = lee_car();
                    if (es_let_hex(c) || es_numero(c)) {
                        ESTADO = 15;
                    } else if (c == 'h') {
                        ESTADO = 16;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 16:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("numhex");
                case 17:
                    c = lee_car();
                    if (c == ':') {
                        ESTADO = 18;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 18:
                    c = lee_car();
                    if (es_letra(c)) {
                        ESTADO = 19;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;

                case 19:
                    c = lee_car();
                    if (es_letra(c) || es_numero(c)) {
                        ESTADO = 19;
                    } else {
                        ESTADO = 20;
                    }
                    break;
                case 20:
                    a_a = a_a - 1;
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("ll_etq");
                case 21:
                    c = lee_car();
                    if (c == '(') {
                        ESTADO = 22;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 22:
                    c = lee_car();
                    if (es_letra(c)) {
                        ESTADO = 23;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 23:
                    c = lee_car();
                    if (es_letra(c) || es_numero(c)) {
                        ESTADO = 23;
                    } else {
                        if (c == ')') {
                            ESTADO = 24;
                        } else {
                            ESTADO = diagrama();
                        }
                    }
                    break;
                case 24:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("etq_dest");
                case 25:
                    c = lee_car();
                    if (es_delim(c)) {
                        ESTADO = 26;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;

                case 26:
                    c = lee_car();
                    if (es_delim(c)) {
                        ESTADO = 26;
                    } else {
                        ESTADO = 27;
                    }
                    break;
                case 27:
                    a_a = a_a - 1;
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("nosirve");

                case 28:
                    c = lee_car();
                    if (c == 255) {
                        ESTADO = 29;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 29:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("nosirve");
            }

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

        linea = abreLeeCierra(Entrada);
        while (!fin_archivo) {
            ESTADO = 0;
            COMIENZO = 0;
            MiToken = TOKEN();
            System.out.println(ContRen);
            if (!MiToken.equals("nosirve")) {
                creaEscribeArchivo(xArchivo(Salida), MiToken);
                creaEscribeArchivo(xArchivo(Salida), LEXEMA);
                creaEscribeArchivo(xArchivo(Salida), ContRen + "");
            }

        }
        System.out.println("Analisis lexicografico existoso");
    }

}
