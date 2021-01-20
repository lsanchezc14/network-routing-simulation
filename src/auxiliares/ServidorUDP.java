package auxiliares;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * @author Luis Carlos Sanchez Cespedes - A65285
 */
public class ServidorUDP extends Thread{
        
    Tablas tablas = new Tablas(); // Instancia las tablas estaticas
    DatagramSocket datagramSocket; // Parametro para la clase
    int nodo; // Parametro para la clase
    
    Boolean primerTimer = true;
    

    public ServidorUDP(DatagramSocket DatagramSocket, int nodo) {
        this.nodo = nodo;
        this.datagramSocket=DatagramSocket;
    }

    @Override
    public void run() {
        
        try{
           
            String temporalString; //String temporal para ubicar los bytes leidos y luego convertidos en arreglo
            String[] temporalArray;
            DatosTabla temporalDatos;

            //Valores iniciales en cada nodo (Red que le corresponde)
            switch(nodo){
                case 1:tablas.addNodo1(new DatosTabla("10.0.1.0", "255.255.255.0" , "10.0.1.1", 1, 1, 1));
                       break;
                case 2:tablas.addNodo2(new DatosTabla("10.0.3.0", "255.255.255.0" , "10.0.3.1", 1, 2, 2));
                       break;
                case 3://NO HAY SUBREDES ASOCIADAS A ESTE NODO
                       break;                       
                case 4:tablas.addNodo4(new DatosTabla("10.0.2.0", "255.255.255.0" , "10.0.2.1", 1, 4, 4));
                       break;
                case 5:tablas.addNodo5(new DatosTabla("10.0.4.0", "255.255.255.0" , "10.0.4.1", 1, 5, 5));
                       break;
            }           
            
            while(true){
                
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(packet);
                
                temporalString = new String(buffer,0, packet.getLength()); //String del tamano exacto. Elimina campos por excedentes en bytes.
                temporalArray = temporalString.split("-");
                                
                for (int j=0;j<temporalArray.length/6;j++){
                    
                    temporalDatos = new DatosTabla(temporalArray[6*j+0],temporalArray[6*j+1],temporalArray[6*j+2],Integer.valueOf(temporalArray[6*j+3]),
                            Integer.valueOf(temporalArray[6*j+4]),Integer.valueOf(temporalArray[6*j+5]));
               
                    switch (nodo){

                        case 1: revisarInalcanzabilidad1("192.168.0.2");
                                recibirMensajesNodo1(3, temporalDatos, "192.168.0.2");
                                break;
                        case 2: revisarInalcanzabilidad2("192.168.0.18");
                                recibirMensajesNodo2(3, temporalDatos, "192.168.0.18");
                                break;

                        case 3: if(tablas.isTimerConvergencia() == false){
                                recibirMensajesNodo3(1, temporalDatos, "192.168.0.1");
                                recibirMensajesNodo3(2, temporalDatos, "192.168.0.17");
                                recibirMensajesNodo3(4, temporalDatos, "192.168.0.6");
                                recibirMensajesNodo3(5, temporalDatos, "192.168.0.14");
                                }
                                break;

                        case 4: revisarInalcanzabilidad4("192.168.0.5");
                                recibirMensajesNodo4(3, temporalDatos, "192.168.0.5");
                                recibirMensajesNodo4(5, temporalDatos, "192.168.0.10");
                                break;

                        case 5: revisarInalcanzabilidad5("192.168.0.13");
                                recibirMensajesNodo5(3, temporalDatos, "192.168.0.13");
                                recibirMensajesNodo5(4, temporalDatos, "192.168.0.9");
                                break;
                    }
                    
                }                

            }
        } catch (IOException e){
            System.out.println("Mensaje de error:"+e);
        }        
        
    }

        
    //Inicio de funciones 
    //Nodo 1
    public void recibirMensajesNodo1 (int nodoPermitido, DatosTabla entradaNueva, String SiguienteSalto){
        
        int subredExistente = 0;
        int tempDistancia = 0;
        
        if(nodoPermitido == entradaNueva.nodoAnterior){ //Evalua que sea nodo vecino
            if(nodo!=entradaNueva.nodoOriginal){ //Evalua que sea diferente a quien le enseno la ruta - Split Horizon
                if(entradaNueva.distancia!=16 && primerTimer == true){ //Evalua inalcanzabilidad
                    
                    subredExistente=  tablas.retornoIndiceNodo1(entradaNueva.subred);
                    
                    if(subredExistente!=-1){ //Existe la subred?
                        if(entradaNueva.distancia < tablas.getDistanciaNodo1(subredExistente)){ //Evalua si la nueva distancia es menor y le suma 1
                            
                            tempDistancia = entradaNueva.distancia + 1;
                            entradaNueva.setDistancia(tempDistancia);
                            entradaNueva.setNodoAnterior(nodo);
                            entradaNueva.setSiguienteSalto(SiguienteSalto);
                            tablas.setNodo1(subredExistente, entradaNueva);
                            
                        }else{
                        
                        }
                    
                    }else{ //Si la subred no existe en tabla se agrega y le suma 1 a la distancia
                        
                        tempDistancia = entradaNueva.distancia + 1;
                        entradaNueva.setDistancia(tempDistancia);
                        entradaNueva.setNodoAnterior(nodo);
                        entradaNueva.setSiguienteSalto(SiguienteSalto);
                        tablas.addNodo1(entradaNueva);
                    }
                    
                }
            }
        }
    }

    public void revisarInalcanzabilidad1 (String SiguienteSalto){
        
        if(tablas.isTimerConvergencia() == true && primerTimer == true){
 
            try {
                System.out.println("Nodo "+nodo+" inicia Hold Down time");
                tablas.setDistanciaNodo1(SiguienteSalto); //Asigna 16 y el hilo de Cliente lo envia a sus vecinos - Poison Reverse
                Thread.sleep(20000); //Hold down time
                primerTimer=false;
                System.out.println("Nodo "+nodo+" termina Hold Down time");
            } catch (InterruptedException ex) {
                Logger.getLogger(ServidorUDP.class.getName()).log(Level.SEVERE, null, ex);
            }
  
        }
    
    }
    
    //Nodo 2
    public void recibirMensajesNodo2 (int nodoPermitido, DatosTabla entradaNueva, String SiguienteSalto){
        
        int subredExistente = 0;
        int tempDistancia = 0;
        
        if(nodoPermitido == entradaNueva.nodoAnterior){ //Evalua que sea nodo vecino
            if(nodo!=entradaNueva.nodoOriginal){ //Evalua que sea diferente a quien le enseno la ruta - Split Horizon -
                if(entradaNueva.distancia!=16 && primerTimer == true){ //Evalua inalcanzabilidad
                    
                    subredExistente=  tablas.retornoIndiceNodo2(entradaNueva.subred);
                    
                    if(subredExistente!=-1){ //Existe la subred?
                        if(entradaNueva.distancia < tablas.getDistanciaNodo2(subredExistente)){ //Evalua si la nueva distancia es menor y le suma 1
                            
                            tempDistancia = entradaNueva.distancia + 1;
                            entradaNueva.setDistancia(tempDistancia);
                            entradaNueva.setNodoAnterior(nodo);
                            entradaNueva.setSiguienteSalto(SiguienteSalto);
                            tablas.setNodo2(subredExistente, entradaNueva);
                            
                        }else{
                        
                        }
                    
                    }else{ //Si la subred no existe en tabla se agrega y le suma 1 a la distancia
                        
                        tempDistancia = entradaNueva.distancia + 1;
                        entradaNueva.setDistancia(tempDistancia);
                        entradaNueva.setNodoAnterior(nodo);
                        entradaNueva.setSiguienteSalto(SiguienteSalto);
                        tablas.addNodo2(entradaNueva);
                    }
                    
                }
            }
        }
    }

    public void revisarInalcanzabilidad2 (String SiguienteSalto){
        
        if(tablas.isTimerConvergencia() == true && primerTimer == true){
 
            try {
                System.out.println("Nodo "+nodo+" inicia Hold Down time");
                tablas.setDistanciaNodo2(SiguienteSalto); //Asigna 16 y el hilo de Cliente lo envia a sus vecinos - Poison Reverse
                Thread.sleep(20000); //Hold down time
                primerTimer=false;
                System.out.println("Nodo "+nodo+" termina Hold Down time");
            } catch (InterruptedException ex) {
                Logger.getLogger(ServidorUDP.class.getName()).log(Level.SEVERE, null, ex);
            }
  
        }
    
    }    
    
    //Nodo 3
    public void recibirMensajesNodo3 (int nodoPermitido, DatosTabla entradaNueva, String SiguienteSalto){
        
        int subredExistente = 0;
        int tempDistancia = 0;
        
        if(nodoPermitido == entradaNueva.nodoAnterior){ //Evalua que sea nodo vecino
            if(nodo!=entradaNueva.nodoOriginal){ //Evalua que sea diferente a quien le enseno la ruta - Split Horizon -
                if(entradaNueva.distancia!=16 && primerTimer == true){ //Evalua inalcanzabilidad
                    
                    subredExistente=  tablas.retornoIndiceNodo3(entradaNueva.subred);
                    
                    if(subredExistente!=-1){ //Existe la subred?
                        if(entradaNueva.distancia < tablas.getDistanciaNodo3(subredExistente)){ //Evalua si la nueva distancia es menor y le suma 1
                            
                            tempDistancia = entradaNueva.distancia + 1;
                            entradaNueva.setDistancia(tempDistancia);
                            entradaNueva.setNodoAnterior(nodo);
                            entradaNueva.setSiguienteSalto(SiguienteSalto);
                            tablas.setNodo3(subredExistente, entradaNueva);
                            
                        }else{
                        
                        }
                    
                    }else{ //Si la subred no existe en tabla se agrega y le suma 1 a la distancia
                        
                        tempDistancia = entradaNueva.distancia + 1;
                        entradaNueva.setDistancia(tempDistancia);
                        entradaNueva.setNodoAnterior(nodo);
                        entradaNueva.setSiguienteSalto(SiguienteSalto);
                        tablas.addNodo3(entradaNueva);
                    }
                    
                }
            }
        }
    }

    public void revisarInalcanzabilidad3 (String SiguienteSalto){
        
        if(tablas.isTimerConvergencia() == true && primerTimer == true){
 
            try {
                System.out.println("Nodo "+nodo+" inicia Hold Down time");
                tablas.setDistanciaNodo3(SiguienteSalto); //Asigna 16 y el hilo de Cliente lo envia a sus vecinos - Poison Reverse
                Thread.sleep(20000); //Hold down time
                primerTimer=false;
                System.out.println("Nodo "+nodo+" termina Hold Down time");
            } catch (InterruptedException ex) {
                Logger.getLogger(ServidorUDP.class.getName()).log(Level.SEVERE, null, ex);
            }
  
        }
    
    }    
    
    //Nodo 4
    public void recibirMensajesNodo4 (int nodoPermitido, DatosTabla entradaNueva, String SiguienteSalto){
        
        int subredExistente = 0;
        int tempDistancia = 0;
        
        if(nodoPermitido == entradaNueva.nodoAnterior){ //Evalua que sea nodo vecino
            if(nodo!=entradaNueva.nodoOriginal){ //Evalua que sea diferente a quien le enseno la ruta - Split Horizon
                if(entradaNueva.distancia!=16 && primerTimer == true){ //Evalua inalcanzabilidad
                    
                    subredExistente=  tablas.retornoIndiceNodo4(entradaNueva.subred);
                    
                    if(subredExistente!=-1){ //Existe la subred?
                        if(entradaNueva.distancia < tablas.getDistanciaNodo4(subredExistente)){ //Evalua si la nueva distancia es menor y le suma 1
                            
                            tempDistancia = entradaNueva.distancia + 1;
                            entradaNueva.setDistancia(tempDistancia);
                            entradaNueva.setNodoAnterior(nodo);
                            entradaNueva.setSiguienteSalto(SiguienteSalto);
                            tablas.setNodo4(subredExistente, entradaNueva);
                            
                        }else{
                        
                        }
                    
                    }else{ //Si la subred no existe en tabla se agrega y le suma 1 a la distancia
                        
                        tempDistancia = entradaNueva.distancia + 1;
                        entradaNueva.setDistancia(tempDistancia);
                        entradaNueva.setNodoAnterior(nodo);
                        entradaNueva.setSiguienteSalto(SiguienteSalto);
                        tablas.addNodo4(entradaNueva);
                    }
                    
                }
            }
        }
    
    }

    public void revisarInalcanzabilidad4 (String SiguienteSalto){
        
        if(tablas.isTimerConvergencia() == true && primerTimer == true){
 
            try {
                System.out.println("Nodo "+nodo+" inicia Hold Down time");
                tablas.setDistanciaNodo4(SiguienteSalto); //Asigna 16 y el hilo de Cliente lo envia a sus vecinos - Poison Reverse
                Thread.sleep(20000); //Hold down time
                primerTimer=false;
                System.out.println("Nodo "+nodo+" termina Hold Down time");
            } catch (InterruptedException ex) {
                Logger.getLogger(ServidorUDP.class.getName()).log(Level.SEVERE, null, ex);
            }
  
        }
    
    }    
    
    //Nodo 5
    public void recibirMensajesNodo5 (int nodoPermitido, DatosTabla entradaNueva, String SiguienteSalto){
        
        int subredExistente = 0;
        int tempDistancia = 0;
        
        if(nodoPermitido == entradaNueva.nodoAnterior){ //Evalua que sea nodo vecino
            if(nodo!=entradaNueva.nodoOriginal){ //Evalua que sea diferente a quien le enseno la ruta - Split Horizon
                if(entradaNueva.distancia!=16 && primerTimer == true){ //Evalua inalcanzabilidad
                    
                    subredExistente=  tablas.retornoIndiceNodo5(entradaNueva.subred);
                    
                    if(subredExistente!=-1){ //Existe la subred?
                        if(entradaNueva.distancia < tablas.getDistanciaNodo5(subredExistente)){ //Evalua si la nueva distancia es menor y le suma 1
                            
                            tempDistancia = entradaNueva.distancia + 1;
                            entradaNueva.setDistancia(tempDistancia);
                            entradaNueva.setNodoAnterior(nodo);
                            entradaNueva.setSiguienteSalto(SiguienteSalto);
                            tablas.setNodo5(subredExistente, entradaNueva);
                            
                        }else{
                        
                        }
                    
                    }else{ //Si la subred no existe en tabla se agrega y le suma 1 a la distancia
                        
                        tempDistancia = entradaNueva.distancia + 1;
                        entradaNueva.setDistancia(tempDistancia);
                        entradaNueva.setNodoAnterior(nodo);
                        entradaNueva.setSiguienteSalto(SiguienteSalto);
                        tablas.addNodo5(entradaNueva);
                    }
                    
                }
            }
        }
    }

    public void revisarInalcanzabilidad5 (String SiguienteSalto){
        
        if(tablas.isTimerConvergencia() == true && primerTimer == true){
 
            try {
                System.out.println("Nodo "+nodo+" inicia Hold Down time");
                tablas.setDistanciaNodo5(SiguienteSalto); //Asigna 16 y el hilo de Cliente lo envia a sus vecinos - Poison Reverse
                Thread.sleep(20000); //Hold down time
                primerTimer=false;
                System.out.println("Nodo "+nodo+" termina Hold Down time");
            } catch (InterruptedException ex) {
                Logger.getLogger(ServidorUDP.class.getName()).log(Level.SEVERE, null, ex);
            }
  
        }
    
    }
  

}
