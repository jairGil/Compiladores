/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadorlexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author mari2
 */
public class Analizador3 {

    public static int filesize = 0;
    public static char[] linea;
    static String[] pr = new String[100];
    public static String hola;
    public static boolean fin_archivo = false;
    public static int a_a = 0;
    public static int a_i = 0;
    public static int c;
    public static int COMIENZO, ESTADO;
    public static String LEXEMA, MiToken;
    public static String Entrada;

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
            return linea[a_a++];
        } else {
            // System.out.println("Llegue al final");
            // pausa();
            fin_archivo = true;
            return 255;
        }
    }

    public static void rut_error() {
        System.out.println("\n\n Error: caracter[" + Character.toString((char) c) + "]compilacion terminada\n");
        System.exit(4);
    }

    public static File xArchivo(String xName) {
        File xFile = new File(xName);
        return xFile;
    }

    // Usando codigo ascii
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

    // Solo da los valores del simbolo o numero
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
    // Inicio de la instrucción Mov, Add, Jmp, Nop

    public static boolean es_inicio(int X) {
        if (X == 77 || X == 65 || X == 74 || X == 78) {
            return (true);
        }
        return false;
    }
    // Cuerpo de la instrucción mOV, aDD, jMP, nOP

    public static boolean es_cuerpo(int X) {
        if (X == 79 || X == 86 || X == 68 || X == 77 || X == 80) {
            return true;
        }
        return false;

    }

    public static boolean es_mayuscula(int X) {
        if (X >= 65 && X <= 90) {
            return true;
        }
        return false;
    }

    // Concatena lo que se tenga almacenado del estado
    public static String obten_lexema() {
        String xx = "";
        for (int i = a_i; i <= a_a - 1; i++) {
            xx = xx + linea[i];
        }
        return (xx);
    }

    // Objetivo: cuando no se pasa por un estado, se invoca y regresa el apuntador
    // de avance al inicio y hay cambio de diagrama, envia el valor del comienzo
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

    // Busca cambiar de un estado a otro teniendo en cuenta los 4 tipos de estados
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
                    if (es_inicio(c)) {
                        ESTADO = 12;
                    } else {
                        ESTADO = diagrama();
                    }

                    break;
                case 12:

                    if (es_cuerpo(c)) {
                        ESTADO = 12;
                    } else {
                        ESTADO = 13;
                    }
                    break;
                // estado de aceptación con asterisco el apuntador avance retrocede
                case 13:
                    a_a = a_a - 1;
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("inst");
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
                // Estado que pasa por otro
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
                // Estado que pasa por otro
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
                // fin de archivo
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
        // pausa();
        linea = abreLeeCierra(Entrada);
        while (!fin_archivo) {
            ESTADO = 0;
            COMIENZO = 0;
            MiToken = TOKEN();
            System.out.println("Encontre token [" + MiToken + "] con lexema[" + LEXEMA + "]");
            pausa();
        }
        System.out.println("Analisis lexicografico existoso");
    }

}
