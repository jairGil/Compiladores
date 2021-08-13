import java.io.*;

public class PrimerAL {

    // VARIABLES DEL PROGRAMA
    public static int filesize = 0; // Tamaño del archivo
    public static char[] linea; // Datos
    public static boolean fin_archivo = false; // Bandera de fin de archivo

    public static int a_i = 0; // Apuntador de inicio
    public static int a_a = 0; // Apuntador de avance

    public static int comienzo; // Auxiliar para cambios de diagramas
    public static int estado; // Auxiliar para verificacion de estados
    
    public static int c; // Auxiliar para encontrar tokens

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
            return(nada);
        } catch (Exception e){
            System.err.println(e);
        }
        return("");
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
            filesize = (int)xFile.length();
            data = new char[filesize + 1];
            fin.read(data, 0, filesize);
            data[filesize] = ' ';
            filesize += 1;
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
                comienzo = 4;
                break;
            case 4:
                comienzo = 8;
                break;
            case 8:
                comienzo = 12;
                break;
            case 12:
                comienzo = 16;
                break;
            case 16:
                comienzo = 18;
                break;
            case 18:
                comienzo = 35;
                break;
            case 35:
                comienzo = 37;
                break;
            case 37:
                comienzo = 41;
                break;
            case 41:
                comienzo = 45;
                break;
            case 45:
                rut_error();
                break;
        }
        return comienzo;
    }

    public static void cambio (int car, int est) {
        c = lee_car();
        if (c == car) estado = est;
        else estado = diagrama();
    }

    public static void cambio (int[] car, int[] est) {
        c = lee_car();
        boolean encontrado = false;
        for (int i = 0; i < car.length; i++) {
            if (c == car[i]){
                estado = est[i];
                encontrado = true;
                break;
            } 
        }

        if (!encontrado)
            estado = diagrama();
    }

    // Encuentra un token y cambiar de estado
    public static String token() {
        while (true) {
            switch (comienzo) {
                case 0:
                    cambio('M', 1);
                    break;
                case 1:
                    cambio('O', 2);
                    break;
                case 2:
                    cambio('V', 3);
                    break;
                case 4:
                    cambio('A', 5);
                    break;
                case 5:
                    cambio('D', 6);
                    break;
                case 6:
                    cambio('D', 7);
                    break;
                case 8:
                    cambio('J', 9);
                    break;
                case 9:
                    cambio('M', 10);
                    break;
                case 10:
                    cambio('P', 11);
                    break;
                case 12:
                    cambio('N', 13);
                    break;
                case 13:
                    cambio('O', 14);
                    break;
                case 14:
                    cambio('P', 15);
                    break;
                case 16:
                    cambio(',', 17);
                    break;
                case 18:
                    cambio(new int[]{'A', 'B', 'C', 'D', 'I'}, new int[]{19, 23, 27, 31, 33});
                    break;
                case 19:
                    cambio(new int[]{'X', 'H', 'L'}, new int[]{20, 21, 22});
                    cambio('X', 20);
                    cambio('H', 21);
                    cambio('L', 22);
                    break;
                case 23:
                    cambio(new int[]{'X', 'H', 'L'}, new int[]{24, 25, 26});
                    break;
                case 27:
                    cambio(new int[]{'X', 'H', 'L'}, new int[]{28, 29, 30});
                    break;
                case 31:
                    cambio('I', 32);
                    break;
                case 33:
                    cambio('P', 34);
                    break;
                case 35:
                    break;
            }
        }
    }

    public static void main(String args[]) {
        /*
        linea = abreLeeCierra("algo.txt");
        for (int i = 0; i <= 50000; i++) {
            System.out.println("Posicion " + i + ": " + linea[i]);
            pausa();
        }
        */

        System.out.println(es_let_hex('F'));
        System.out.println(es_num('A'));
    }
}