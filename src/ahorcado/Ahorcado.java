/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahorcado;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
/**
 *
 * @author David Madrigal Buendía
 * @author David Arturo Oxaca Pérez
 */
public class Ahorcado {
    public static void main(String[] args) {
        String dir= "192.168.0.7";
        int puerto= 1234;
        try {
            Socket cl= new Socket(dir, puerto);
            System.out.println("Conexión establecida con: " + dir + ":" + puerto);
            
            BufferedReader teclado= new BufferedReader(new InputStreamReader(System.in));
            BufferedReader conexion_lectura= new BufferedReader(new InputStreamReader(cl.getInputStream()));
            System.out.println("Ingresa la dificultad:");
            System.out.println("1 - Fácil");
            System.out.println("2 - Medio");
            System.out.println("3 - Dífícil");
            String opcion= (String) teclado.readLine();
            
            PrintWriter conexion_escritura= new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
            conexion_escritura.println(opcion);
            conexion_escritura.flush();
            
            String palabra= (String) conexion_lectura.readLine();
            System.out.println("Palabra a encontrar:");
            colocarPalabra(palabra);
            
            boolean palabra_encontrada= false;
            int intentos= 0;
            do {
                System.out.println("Ingresa un carácter:");
                char caracter= teclado.readLine().charAt(0);
                if(palabra.contains(caracter + "")) {
                    System.out.println("La palabra contiene: " + caracter);
                    colocarPalabra(palabra, caracter);
                }else {
                    System.out.println("La palabra no contiene: " + caracter + ", te quedan: " + intentos + " intentos");
                    colocarPalabra(palabra, caracter);
                }
                intentos++;
                if(intentos > 6) {
                    System.out.println("Perdiste, se te acabaron los intentos.");
                    break;
                }
            }while(!palabra_encontrada);
            
            if(palabra_encontrada) {
                System.out.println("Ganaste.");
            }
            
            conexion_lectura.close();
            conexion_escritura.close();
            cl.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void colocarPalabra(String palabra) {
        int tamanio= palabra.length();
        int i;
        for(i= 0; i < tamanio; i++) {
            System.out.print("_ ");
        }
        System.out.println("");
    }
    
    public static void colocarPalabra(String palabra, char caracter) {
        int tamanio= palabra.length();
        int i;
        for(i= 0; i < tamanio; i++) {
            if(palabra.charAt(i) == caracter) {
                System.out.println(caracter + " ");
            }else {
                System.out.println("_ ");
            }
        }
    }
}
