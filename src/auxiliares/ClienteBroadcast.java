package auxiliares;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * @author Luis Carlos Sanchez Cespedes - A65285
 */
public class ClienteBroadcast extends Thread{

    Tablas tablas = new Tablas(); // Instancia las tablas estaticas
    int nodo; // Parametro para la clase
    String broadcast;
    
    public ClienteBroadcast(int Nodo, String Broadcast) {
        this.nodo = Nodo;
        this.broadcast = Broadcast;
    }

    @Override
    public void run() {

            byte[] buffer="initialize".getBytes();
            
            try {
            InetAddress direccion = InetAddress.getByName(broadcast); //Direccion de broadcast
            DatagramSocket datagramSocket = new DatagramSocket();
            datagramSocket.setBroadcast(true); //Flag de seguridad que permite hacer broadcast         
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, direccion, 5555); //Puerto UDP hard coded
            
                while(true){

                    Thread.sleep(10000);

                    switch (nodo){

                        case 1: buffer = tablaCompleta1().getBytes();
                                packet.setData(buffer);
                                packet.setLength(buffer.length);
                                datagramSocket.send(packet);
                                break;

                        case 2: buffer = tablaCompleta2().getBytes();
                                packet.setData(buffer);
                                packet.setLength(buffer.length);
                                datagramSocket.send(packet);
                                break;
                                
                        case 3: if(tablas.isTimerConvergencia() == false && tablas.getTamanoNodo3()>0){ //Condicion para hacer fallar al nodo 3
                                    buffer = tablaCompleta3().getBytes();
                                    packet.setData(buffer);
                                    packet.setLength(buffer.length);
                                    datagramSocket.send(packet);
                                }else{}
                                
                                break;
                                
                        case 4: buffer = tablaCompleta4().getBytes();
                                packet.setData(buffer);
                                packet.setLength(buffer.length);
                                datagramSocket.send(packet);
                                break;
                                
                        case 5: buffer = tablaCompleta5().getBytes();
                                packet.setData(buffer);
                                packet.setLength(buffer.length);
                                datagramSocket.send(packet);
                                break;
                                
                    }
                }
                       
            } catch (SocketTimeoutException e){ //Catch parar error de timeout para el datagramSocket
                System.out.println("Socket timeout"+e);   
            } catch (IOException ioe){ //Catch para error de mensaje
                System.out.println("Mensaje de error: "+ioe.getMessage());
            } catch (InterruptedException ex) { //Catch para error de Thread.sleep
                Logger.getLogger(ClienteBroadcast.class.getName()).log(Level.SEVERE, null, ex);
            }       
    }
    
    //Inicio de funciones
    
    public String tablaCompleta1 (){
    
        String tablaCompleta="";
        
        for(int i=0; i<tablas.getTamanoNodo1();i++){
            tablaCompleta=tablaCompleta+tablas.getFilaNodo1(i)+"-";
        }
                
    return tablaCompleta;
    }

    public String tablaCompleta2 (){
    
        String tablaCompleta="";
        
        for(int i=0; i<tablas.getTamanoNodo2();i++){
            tablaCompleta=tablaCompleta+tablas.getFilaNodo2(i)+"-";
        }
                
    return tablaCompleta;
    }

    public String tablaCompleta3 (){
    
        String tablaCompleta="";
        
        for(int i=0; i<tablas.getTamanoNodo3();i++){
            tablaCompleta=tablaCompleta+tablas.getFilaNodo3(i)+"-";
        }
                
    return tablaCompleta;
    }

    public String tablaCompleta4 (){
    
        String tablaCompleta="";
        
        for(int i=0; i<tablas.getTamanoNodo4();i++){
            tablaCompleta=tablaCompleta+tablas.getFilaNodo4(i)+"-";
        }
                
    return tablaCompleta;
    } 

    public String tablaCompleta5 (){
    
        String tablaCompleta="";
        
        for(int i=0; i<tablas.getTamanoNodo5();i++){
            tablaCompleta=tablaCompleta+tablas.getFilaNodo5(i)+"-";
        }
                
    return tablaCompleta;
    }    
}

