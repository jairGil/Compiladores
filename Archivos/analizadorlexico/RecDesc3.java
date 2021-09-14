/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadorlexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author mari2
 */
public class RecDesc3 {
    static int Posicion = 0;
    static String CABEZA, LEXEMA, RENGLON, Entrada;

    public static void sig_cabeza(File xFile) {
        try {
            FileReader fr = new FileReader(xFile);
            BufferedReader br = new BufferedReader(fr);
            long NoSirve = br.skip(Posicion);
            String linea = br.readLine();
            Posicion = Posicion + linea.length() + 2;
            CABEZA = linea;
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
        System.out.println("\nERROR: Error sintactico en el renglon " + RENGLON + ", token [" + CABEZA + "]"
                + ", lexema [" + LEXEMA + "]");
        System.exit(4);
    }

    public static void asocia(String token) {
        if (CABEZA.equals(token)) {
            sig_cabeza(xArchivo(Entrada));
        } else {
            rut_error();
        }
    }
//terminales se asocian , NO terminarles metodo, rutinas
//si derivan al vacio no se llama la rutina de error
    
    public static void ELEM(){
           switch (CABEZA) {
               case "num":
                   asocia("num");
                   break;
               case "id":
                   asocia("id");
                   break;
               case "pa":
                   asocia("pa");
                   LISTA();
                   asocia("pc");
                   break;
               default:
                   rut_error();
                   break;
           }
    }
    //rutina factorizada
    public static void LISTA(){
        if(CABEZA.equals("num")|| CABEZA.equals("id")||CABEZA.equals("pa")){
          ELEM();
          LISTA_P();
        }else{
            rut_error();
        }
    }
    
    public static void LISTA_P(){
        switch (CABEZA) {
            case "+":
                asocia("+");
                LISTA();
                break;
            case "-":
                asocia("-");
                LISTA();
                break;
            case "*":
                asocia("*");
                LISTA();
                break;
            case "/":
                asocia("/");
                LISTA();
                break;
            default:
                break;
        }
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
        //Salida = argumento[0] + ".LA1";
        sig_cabeza(xArchivo(Entrada));
        //Se llama al SIG 
        LISTA();
        if(!CABEZA.equals("eof"))
            rut_error();
        
        System.out.println("");

    }
}
