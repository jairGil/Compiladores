
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ParseLL1 {

    static int Posicion = 0;
    static String a, LEXEMA, RENGLON, Entrada, X;
    static String nt[] = { "LISTA", "LISTA_P", "ELEM" };
    static String t[] = { "+", "-", "*", "/", "num", "id", "pa", "pc", "eof" };
    static String pd[] = { "ELEM LISTA_P", "+ LISTA", "- LISTA", "* LISTA", "/ LISTA", "epsilon", "num", "id",
            "pa LISTA pc" };
    static int m[][] = { { 0, 0, 0, 0, 1, 1, 1, 0, 0 }, { 2, 3, 4, 5, 0, 0, 0, 6, 6 }, { 0, 0, 0, 0, 7, 8, 9, 0, 0 } };
    static String pila[] = new String[10000];
    static int tope = 0;

    public static void lee_token(File xFile) {
        try {
            FileReader fr = new FileReader(xFile);
            BufferedReader br = new BufferedReader(fr);
            long NoSirve = br.skip(Posicion);
            String linea = br.readLine();
            Posicion = Posicion + linea.length() + 2;
            a = linea;
            linea = br.readLine();
            Posicion = Posicion + linea.length() + 2;
            LEXEMA = linea;
            linea = br.readLine();
            Posicion = Posicion + linea.length() + 2;
            RENGLON = linea;
            fr.close();
            System.out.println(".");
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public static File xArchivo(String xName) {
        File xFile = new File(xName);
        return xFile;
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

    public static void rut_error() {
        System.out.println("\nERROR: Error sintactico en el renglon " + RENGLON + ", token [" + a + "]" + ", lexema ["
                + LEXEMA + "]");
        System.exit(4);
    }

    public static int no_terminal(String x) {
        for (int i = 0; i < nt.length; i++) {
            if (nt[i].equals(x)) {
                return (i);
            }
        }
        return (-1);

    }

    public static int terminal(String x) {
        for (int i = 0; i < t.length; i++) {
            if (t[i].equals(x)) {
                return (i);
            }
        }
        return (-1);

    }

    public static void push(String x) {
        if (tope >= 9999) {
            System.out.println("Error pila llena");
            System.exit(4);
        }
        if (!x.equals("epsilon")) {
            pila[++tope] = x;
        }
    }

    public static void print_pila() {
        System.out.println("PILA ==>");
        for (int i = 0; i <= tope; i++) {
            System.out.println(pila[i] + " ");
        }
        System.out.println("");
    }

    public static String pop() {
        if (tope < 0) {
            System.out.println("Error pila vacia");
            System.exit(4);
        }
        return (pila[tope--]);
    }

    public static void main(String argumento[]) {
        try {
            Entrada = argumento[0] + ".LA1";
        } catch (Exception e) {
            System.out.println("\7Error en el archivo de entrada");
            System.exit(4);
        }
        if (!xArchivo(Entrada).exists()) {
            System.out.println("\7El archivo [" + Entrada + "] no existe");
            System.exit(4);
        }

        push("eof");
        push("LISTA");
        print_pila();
        pausa();
        lee_token(xArchivo(Entrada));
        do {
            X = pop();

            if (a.equals("eof") && X.equals("eof")) {
                System.out.println("Compilacion exitosa");
            } else {
                if (terminal(X) >= 0) {
                    if (X.equals(a)) {
                        System.out.println("\tPop y leer siguiente");
                        print_pila();
                        pausa();
                        lee_token(xArchivo(Entrada));
                    } else {
                        rut_error();
                    }
                } else {
                    if (m[no_terminal(X)][terminal(a)] != 0) {
                        System.out.println("\tProduccion=" + m[no_terminal(X)][terminal(a)]);
                        pausa();
                        String[] Y = pd[m[no_terminal(X)][terminal(a)]].split(" ");
                        for (int i = Y.length - 1; i >= 0; i--) {
                            push(Y[i]);
                        }
                        print_pila();
                        pausa();
                    } else {
                        rut_error();
                    }
                }
            }
        } while (!X.equals("eof"));
        System.out.println("Termino el parse LL1");

    }
}
