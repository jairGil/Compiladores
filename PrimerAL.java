import java.io.*;

public class PrimerAL {

    public static int filesize = 0;
    public static char[] linea;
    public static boolean fin_archivo = false;
    public static int a_a = 0;

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

    public static char lee_car() {
        if (a_a < filesize) {
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

    public static void main(String args[]) {
        linea = abreLeeCierra("algo.txt");
        for (int i = 0; i <= 50000; i++) {
            System.out.println("Posicion " + i + ": " + linea[i]);
            pausa();
        }
    }
}