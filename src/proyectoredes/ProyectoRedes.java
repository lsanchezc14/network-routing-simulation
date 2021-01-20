package proyectoredes;

import auxiliares.ClienteBroadcast;
import auxiliares.ServidorUDP;
import auxiliares.Simulacion;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * @author Luis Carlos Sanchez Cespedes - A65285
 */
public class ProyectoRedes {
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
        System.out.println("CI-1320-Redes de computadores I - Proyecto\n"
                + "Estudiante: Luis Carlos Sanchez Cespedes\n"
                + "Carnet: A65285\n");
        
        System.out.println("Introduzca el numero de direccion de broadcast para su subred:");
        String broadcast = sc.next();

                
        try {

            //Instancia el datagram socket para el servidor UPD en el puerto 5555
            DatagramSocket datagramSocketServidor = new DatagramSocket(5555); 
            
            //Hilos para servidor UDP por nodo
            Thread nodo1Servidor = new ServidorUDP(datagramSocketServidor,1);
            nodo1Servidor.start();          

            Thread nodo2Servidor = new ServidorUDP(datagramSocketServidor,2);
            nodo2Servidor.start();

            Thread nodo3Servidor = new ServidorUDP(datagramSocketServidor,3);
            nodo3Servidor.start();

            Thread nodo4Servidor = new ServidorUDP(datagramSocketServidor,4);
            nodo4Servidor.start();

            Thread nodo5Servidor = new ServidorUDP(datagramSocketServidor,5);
            nodo5Servidor.start();
            
            Thread.sleep(500); //Un delay para sincronizar un poco los hilos
            
            Thread simulacion = new Simulacion();
            simulacion.start();            
            
            Thread.sleep(500); //Un delay para sincronizar un poco los hilos
            
            //Hilos para cliente UDP por nodo
            Thread nodo1Cliente = new ClienteBroadcast(1, broadcast);
            nodo1Cliente.start();
                        
            Thread nodo2Cliente = new ClienteBroadcast(2, broadcast);
            nodo2Cliente.start();
            
            Thread nodo3Cliente = new ClienteBroadcast(3, broadcast);
            nodo3Cliente.start();
                        
            Thread nodo4Cliente = new ClienteBroadcast(4, broadcast);
            nodo4Cliente.start();
            
            Thread nodo5Cliente = new ClienteBroadcast(5, broadcast);
            nodo5Cliente.start(); 
                     
        } catch (SocketException ex) {
            Logger.getLogger(ProyectoRedes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ProyectoRedes.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
}
