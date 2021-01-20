package auxiliares;

import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * @author Luis Carlos Sanchez Cespedes - A65285
 */
public class Simulacion extends Thread{

    Tablas tablas = new Tablas(); // Instancia las tablas estaticas
    
    @Override
    public void run() {
        
        long tiempoInicio = System.currentTimeMillis();
        long tiempoFinal = System.currentTimeMillis();
        
        while(true){
            
            if((tiempoFinal-tiempoInicio)>300000){ //Cuando el tiempo supera 5 minutos
                
                if (tablas.isTimerConvergencia()==false){ // Solo se ejecuta la primera vez
                    System.out.println("Desconexion del nodo 3");
                    tablas.setTimerConvergencia(true);
                }

            }
                
            tiempoFinal = System.currentTimeMillis();
            System.out.println("*********************************************************");
            System.out.println("Tiempo igual:");
            System.out.println((tiempoFinal-tiempoInicio)/1000+" s\n");
            tablas.imprimirNodo1();
            tablas.imprimirNodo2();
            tablas.imprimirNodo3();
            tablas.imprimirNodo4();
            tablas.imprimirNodo5();            
            
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Simulacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
        
}
