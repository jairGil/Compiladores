import java.io.*;
import java.nio.file.InvalidPathException;

public class ALLenguajeA {

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

    public static int contLinea = 1;

    // Imprime mensaje de error y sale del programa
    public static void rut_error() {
        System.out.println("\n\nERROR: Linea [" + contLinea + "] Caracter [" + Character.toString(c)
                + "], compilacion terminada!!");
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

    public static boolean es_delim(int x) {
        if (x == 9 || x == 10 || x == 13 || x == 32)
            return true;
        return false;
    }

    // Validar un numero
    public static boolean es_digito(int x) {
        if (x >= 48 && x <= 57)
            return true;
        return false;
    }

    public static boolean es_letra(int x) {
        if (x >= 65 && x <= 90 || x >= 97 && x <= 122)
            return true;
        return false;
    }

    // Leer caracter a caracter de un arreglo
    public static char lee_car() {
        if (a_a < filesize) {
            if (linea[a_a] == '\n') {
                contLinea++;
                System.out.println((int) linea[a_a]);

            }
            return linea[a_a++];
        } else {
            fin_archivo = true;
            return 255;
        }
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
        } catch (InvalidPathException exc) {
        } catch (IOException exc) {
        }
        return null;
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

    // Genera los cambios de diagrama
    public static int diagrama() {
        a_a = a_i;
        switch (comienzo) {
            case 0:
                comienzo = 4;
                break;
            case 4:
                comienzo = 13;
                break;
            case 13:
                comienzo = 18;
                break;
            case 18:
                comienzo = 20;
                break;
            case 20:
                comienzo = 23;
                break;
            case 23:
                rut_error();
                break;
        }
        return comienzo;
    }

    // Encuentra un token y cambiar de estado
    public static String token() {
        // int camino = -1;
        while (true) {
            /*
             * // Print path if (camino != estado) { camino = estado;
             * System.out.println(camino);
             * 
             * } else { camino = estado; }
             */

            if (estado == 0) {
                c = lee_car();
                cambio(es_letra(c), 1);
            } else if (estado == 1) {
                c = lee_car();
                if (es_letra(c) || es_digito(c))
                    estado = 1;
                else if (c == '_')
                    estado = 2;
                else
                    estado = 3;
            } else if (estado == 2) {
                c = lee_car();
                cambio(es_letra(c) || es_digito(c), 1);
            } else if (estado == 3) {
                a_a--;
                LEXEMA = obten_lexema();
                a_i = a_a;
                return ("id");
            } else if (estado == 4) {
                c = lee_car();
                cambio(es_digito(c), 5);
            } else if (estado == 5) {
                c = lee_car();
                if (es_digito(c))
                    estado = 5;
                else if (c == 'e' || c == 'E')
                    estado = 9;
                else if (c == '.')
                    estado = 6;
                else
                    estado = 8;
            } else if (estado == 6) {
                c = lee_car();
                cambio(es_digito(c), 7);
            } else if (estado == 7) {
                c = lee_car();
                if (es_digito(c))
                    estado = 7;
                else if (c == 'e' || c == 'E')
                    estado = 9;
                else
                    estado = 8;
            } else if (estado == 8) {
                a_a--;
                LEXEMA = obten_lexema();
                a_i = a_a;
                return ("num");
            } else if (estado == 9) {
                c = lee_car();
                if (c == '+' || c == '-')
                    estado = 10;
                else if (es_digito(c))
                    estado = 11;
                else
                    estado = diagrama();
            } else if (estado == 10) {
                c = lee_car();
                cambio(es_digito(c), 11);
            } else if (estado == 11) {
                c = lee_car();
                if (es_digito(c))
                    estado = 11;
                else
                    estado = 12;
            } else if (estado == 12) {
                a_a--;
                LEXEMA = obten_lexema();
                a_i = a_a;
                return ("num");
            } else if (estado == 13) {
                cambio(new int[] { '+', '-', '*', '/' }, new int[] { 14, 15, 16, 17 });
            } else if (estado == 14) {
                LEXEMA = obten_lexema();
                a_i = a_a;
                return ("+");
            } else if (estado == 15) {
                LEXEMA = obten_lexema();
                a_i = a_a;
                return ("-");
            } else if (estado == 16) {
                LEXEMA = obten_lexema();
                a_i = a_a;
                return ("*");
            } else if (estado == 17) {
                LEXEMA = obten_lexema();
                a_i = a_a;
                return ("/");
            } else if (estado == 18) {
                c = lee_car();
                cambio(255, 19);
            } else if (estado == 19) {
                LEXEMA = obten_lexema();
                a_i = a_a;
                return ("eof");
            } else if (estado == 20) {
                cambio(new int[] { '(', ')' }, new int[] { 21, 22 });
            } else if (estado == 21) {
                LEXEMA = obten_lexema();
                a_i = a_a;
                return ("(");
            } else if (estado == 22) {
                LEXEMA = obten_lexema();
                a_i = a_a;
                return (")");
            } else if (estado == 23) {
                c = lee_car();
                cambio(es_delim(c), 24);
            } else if (estado == 24) {
                c = lee_car();
                if (es_delim(c))
                    estado = 24;
                else
                    estado = 25;
            } else if (estado == 25) {
                a_a--;
                LEXEMA = obten_lexema();
                a_i = a_a;
                return ("basura");
            }

            // Monumento a la mierda de estructura de control que es el switch
            switch (estado) {
                case 0:

                    break;
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
                case 7:

                    break;
                case 8:

                case 9:

                    break;
                case 10:

                    break;
                case 11:

                    break;
                case 12:

                case 13:

                    break;
                case 14:

                case 15:

                case 16:

                case 17:

                case 18:

                    break;
                case 19:

                case 20:

                    break;
                case 21:

                case 22:

                case 23:

                    break;
                case 24:

                case 25:

            }
        }
    }

    public static String obten_lexema() {
        String xx = "";
        for (int i = a_i; i < a_a; i++)
            xx = xx + linea[i];
        return xx;
    }

    public static File xArchivo(String s) {
        File xFile = new File(s);
        return xFile;
    }

    public static boolean creaEscribeArchivo(File xFile, String mensaje) {
        try {
            PrintWriter fileOut = new PrintWriter(xFile);
            fileOut.println(mensaje);
            fileOut.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public static void main(String args[]) {

        try {
            entrada = args[0] + ".LA";
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

            System.out.println("Token [" + MiToken + "] Lexema [" + LEXEMA + "] Linea [" + contLinea + "]");
        }
    }
}