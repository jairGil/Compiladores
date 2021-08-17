import java.io.*;

public class SegundoAL {

    // VARIABLES DEL PROGRAMA
    public static int filesize = 0; // Tamaño del archivo
    public static char[] linea; // Datos
    public static boolean fin_archivo = false; // Bandera de fin de archivo

    public static int a_i = 0; // Apuntador de inicio
    public static int a_a = 0; // Apuntador de avance

    public static int comienzo; // Auxiliar para cambios de diagramas
    public static int estado; // Auxiliar para verificacion de estados

    public static int c; // Auxiliar para encontrar tokens

    public static String LEXEMA;
    public static String MiToken;
    public static String entrada;

    // Imprime mensaje de error y sale del programa
    public static void rut_error() {
        System.out.println("\n\nERROR: Caracter [" + Character.toString(c) + "], compilacion terminada!!");
        System.exit(4);
    }

    // Genera una pausa del programa al presionar la tecla ENTER
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

    // Leer caracter a caracter de un arreglo
    public static char lee_car() {
        if (a_a < filesize) {
            return linea[a_a++];
        } else {
            fin_archivo = true;
            return 255;
        }
    }

    public static String obten_lexema() {
        String xx = "";
        for (int i = a_i; i <= a_a - 1; i++)
            xx = xx + linea[i];
        return xx;
    }

    // Abrir una archivo de datos
    public static char[] abreLeeCierra(String xName) {
        File xFile = new File(xName);
        char[] data;

        try {
            FileReader fin = new FileReader(xFile);
            filesize = (int) xFile.length();
            data = new char[filesize + 1];
            fin.read(data, 0, filesize);
            data[filesize] = ' ';
            filesize++;
            fin.close();
            return data;
        } catch (FileNotFoundException exc) {

        } catch (IOException exc) {

        }
        return null;
    }

    // Validar una letra hexadecimal
    public static boolean es_let_hex(int x) {
        if (x >= 60 && x <= 70)
            return true;
        return false;
    }

    // Validar un numero
    public static boolean es_num(int x) {
        if (x >= 48 && x <= 57)
            return true;
        return false;
    }

    public static boolean es_letra(int x) {
        if (x >= 65 && x <= 90 || x >= 97 && x <= 122)
            return true;
        return false;
    }

    public static boolean es_letra_mayuscula(int x) {
        if (x >= 65 && x <= 90)
            return true;
        return false;
    }

    public static boolean es_delim(int x) {
        if (x == 9 || x == 10 || x == 13 || x == 32)
            return true;
        return false;
    }

    // Genera los cambios de diagrama
    public static int diagrama() {
        a_a = a_i;
        switch (comienzo) {
            case 0:
                comienzo = 2;
                break;
            case 2:
                comienzo = 11;
                break;
            case 11:
                comienzo = 14;
                break;
            case 14:
                comienzo = 17;
                break;
            case 17:
                comienzo = 21;
                break;
            case 21:
                comienzo = 25;
                break;
            case 25:
                comienzo = 28;
                break;
            case 28:
                comienzo = 30;
                break;
            case 30:
                rut_error();
                break;
        }
        return comienzo;
    }

    public static void cambio(int car, int est) {
        c = lee_car();
        if (c == car)
            estado = est;
        else
            estado = diagrama();
    }

    public static void cambio(boolean b, int est) {
        if (b)
            estado = est;
        else
            estado = diagrama();
    }

    public static void cambio(int[] car, int[] est) {
        c = lee_car();

        for (int i = 0; i < car.length; i++) {
            if (c == car[i]) {
                estado = est[i];
                return;
            }
        }

        estado = diagrama();
    }

    public static void cambio(int[] car, int est) {
        c = lee_car();

        for (int i = 0; i < car.length; i++) {
            if (c == car[i]) {
                estado = est;
                return;
            }
        }

        estado = diagrama();
    }

    // Encuentra un token y cambiar de estado
    public static String token() {
        int camino = -1;
        while (true) {
            // Print path
            if (camino != estado) {
                camino = estado;
                System.out.println(camino);

            } else {
                camino = estado;
            }

            switch (estado) {
                case 0:
                    cambio(',', 1);
                    break;
                case 1:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("sep");
                case 2:
                    cambio(new int[] { 'A', 'B', 'C', 'D', 'I' }, new int[] { 3, 3, 3, 7, 9 });
                    break;
                case 3:
                    cambio(new int[] { 'X', 'H', 'L' }, new int[] { 4, 5, 6 });
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
                    cambio('I', 8);
                    break;
                case 8:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("reg");
                case 9:
                    cambio('P', 10);
                    break;
                case 10:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("reg");
                case 11:
                    c = lee_car();
                    cambio(es_letra_mayuscula(c), 12);
                    break;
                case 12:
                    c = lee_car();
                    if (es_letra_mayuscula(c))
                        estado = 12;
                    else
                        estado = 13;
                    break;
                case 13:
                    a_a--;
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("string");
                case 14:
                    c = lee_car();
                    cambio(es_let_hex(c) || es_num(c), 15);
                    break;
                case 15:
                    c = lee_car();
                    if (es_let_hex(c) || es_num(c))
                        estado = 15;
                    else if (c == 'h')
                        estado = 16;
                    else
                        estado = diagrama();
                    break;
                case 16:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("numhex");
                case 17:
                    cambio(':', 18);
                    break;
                case 18:
                    c = lee_car();
                    cambio(es_letra(c), 19);
                    break;
                case 19:
                    c = lee_car();
                    if (es_letra(c) || es_num(c))
                        estado = 19;
                    else
                        estado = 20;
                    break;
                case 20:
                    a_a--;
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("ll_etq");
                case 21:
                    cambio('(', 22);
                    break;
                case 22:
                    c = lee_car();
                    cambio(es_letra(c), 23);
                    break;
                case 23:
                    c = lee_car();
                    if (es_letra(c) || es_num(c))
                        estado = 23;
                    else if (c == ')')
                        cambio(true, 24);
                    break;
                case 24:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("etq_dest");
                case 25:
                    c = lee_car();
                    cambio(es_delim(c), 26);
                    break;
                case 26:
                    c = lee_car();
                    if (es_delim(c))
                        estado = 26;
                    else
                        estado = 27;
                    break;
                case 27:
                    a_a--;
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("nosirve");
                case 28:
                    cambio(255, 29);
                    break;
                case 29:
                    LEXEMA = obten_lexema();
                    a_i = a_a;
                    return ("nosirve");
            }
        }
    }

    public static File xArchivo(String s) {
        File xFile = new File(s);
        return xFile;
    }

    public static void main(String args[]) {
        /*
         * linea = abreLeeCierra("algo.txt"); for (int i = 0; i <= 50000; i++) {
         * System.out.println("Posicion " + i + ": " + linea[i]); pausa(); }
         * 
         * 
         * System.out.println(es_let_hex('F')); System.out.println(es_num('A'));
         */

        try {
            entrada = args[0] + ".asm";
        } catch (Exception e) {
            System.out.println("Error en el archivo de entrada");
            System.exit(4);
        }

        if (!xArchivo(entrada).exists()) {
            System.out.println("El archivo [" + entrada + "] no existe...");
            System.exit(4);
        }

        linea = abreLeeCierra(entrada);

        while (!fin_archivo) {
            estado = 0;
            comienzo = 0;
            MiToken = token();

            System.out.println("Encontre el token [" + MiToken + "] con lexema [" + LEXEMA + "]");
            pausa();
        }
    }
}
