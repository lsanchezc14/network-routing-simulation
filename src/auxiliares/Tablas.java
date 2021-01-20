package auxiliares;

import java.util.ArrayList;
/*
 * @author Luis Carlos Sanchez Cespedes - A65285
 */
public class Tablas {
    
    //Instanciar los objetos que seran usados como tablas en cada nodo (la memoria del nodo)
    public static ArrayList<DatosTabla> tablaNodo1 = new ArrayList();
    public static ArrayList<DatosTabla> tablaNodo2 = new ArrayList();
    public static ArrayList<DatosTabla> tablaNodo3 = new ArrayList();
    public static ArrayList<DatosTabla> tablaNodo4 = new ArrayList();
    public static ArrayList<DatosTabla> tablaNodo5 = new ArrayList();
    
    public static boolean timerConvergencia = false;

    public Tablas() {
    }
    
    // Getters y setters para cada tabla y por nodo. De esta forma evitamos condiciones de carrera en los distintos hilos
    
    public boolean isTimerConvergencia() {
        return timerConvergencia;
    }

    public void setTimerConvergencia(boolean timerConvergencia) {
        Tablas.timerConvergencia = timerConvergencia;
    }
    
    // Nodo 1
    public void addNodo1 (DatosTabla datosTabla){
        
        tablaNodo1.add(datosTabla);
    
    }
    
    public void setNodo1 (int i, DatosTabla datosTabla){
        
        tablaNodo1.set(i, datosTabla);
    
    }
    
    public int retornoIndiceNodo1 (String subred){
        
        int indice= -1;
        
        for(int i=0;i<tablaNodo1.size();i++){

            if(tablaNodo1.get(i).subred.equals(subred)){
                indice = i;
                break;
            }else{indice = -1;} //Si retorna -1 quiere decir que no hay entrada con la misma direccion de red (aun)
                   
        }
       return indice;
       
    } 

    public int getDistanciaNodo1 (int indice){
        
        int distancia = tablaNodo1.get(indice).distancia;
                
        return distancia;
    }
    
    public void setDistanciaNodo1 (String indice){
        
        for(int i=0;i<tablaNodo1.size();i++){
        
            if(tablaNodo1.get(i).siguienteSalto.equals(indice)){
            
                tablaNodo1.get(i).distancia = 16;
            }
        
        }
           
    }    
    
    public int getTamanoNodo1 (){
        
        int tamano = tablaNodo1.size();
                
        return tamano;
    }

    
    public String getFilaNodo1 (int i){
        
        String fila=tablaNodo1.get(i).subred+"-"+tablaNodo1.get(i).mascara+"-"+
                tablaNodo1.get(i).siguienteSalto+"-"+tablaNodo1.get(i).distancia+"-"+
                tablaNodo1.get(i).nodoAnterior+"-"+tablaNodo1.get(i).nodoOriginal;
                
        return fila;
    }
    
    public void imprimirNodo1(){
        
        System.out.println("Tabla de rutas del Nodo 1");
        System.out.println("Subred\t\t"+"Mascara\t\t"+"Siguiente\t"+"Distancia");
              
        for (int i=0;i<tablaNodo1.size();i++){
            System.out.println(tablaNodo1.get(i).subred+"\t"+tablaNodo1.get(i).mascara+"\t"
            +tablaNodo1.get(i).siguienteSalto+"\t"+tablaNodo1.get(i).distancia);
        }
        System.out.println("\n");
        
    } 
    
    // Nodo 2
    public void addNodo2 (DatosTabla datosTabla){
        
        tablaNodo2.add(datosTabla);
    
    }
    
    public void setNodo2 (int i, DatosTabla datosTabla){
        
        tablaNodo2.set(i, datosTabla);
    
    }
    
    public int retornoIndiceNodo2 (String subred){
        
        int indice= -1;
        
        for(int i=0;i<tablaNodo2.size();i++){

            if(tablaNodo2.get(i).subred.equals(subred)){
                indice = i;
                break;
            }else{indice = -1;} //Si retorna -1 quiere decir que no hay entrada con la misma direccion de red (aun)
                   
        }
       return indice;
       
    } 

    public int getDistanciaNodo2 (int indice){
        
        int distancia = tablaNodo2.get(indice).distancia;
                
        return distancia;
    }

    public void setDistanciaNodo2 (String indice){
        
        for(int i=0;i<tablaNodo2.size();i++){
        
            if(tablaNodo2.get(i).siguienteSalto.equals(indice)){
            
                tablaNodo2.get(i).distancia = 16;
            }
        
        }
           
    }
    
    public int getTamanoNodo2 (){
        
        int tamano = tablaNodo2.size();
                
        return tamano;
    }

    
    public String getFilaNodo2 (int i){
        
        String fila=tablaNodo2.get(i).subred+"-"+tablaNodo2.get(i).mascara+"-"+
                tablaNodo2.get(i).siguienteSalto+"-"+tablaNodo2.get(i).distancia+"-"+
                tablaNodo2.get(i).nodoAnterior+"-"+tablaNodo2.get(i).nodoOriginal;
                
        return fila;
    }
    
    public void imprimirNodo2(){
        
        System.out.println("Tabla de rutas del Nodo 2");
        System.out.println("Subred\t\t"+"Mascara\t\t"+"Siguiente\t"+"Distancia");
              
        for (int i=0;i<tablaNodo2.size();i++){
            System.out.println(tablaNodo2.get(i).subred+"\t"+tablaNodo2.get(i).mascara+"\t"
            +tablaNodo2.get(i).siguienteSalto+"\t"+tablaNodo2.get(i).distancia);
        }
        System.out.println("\n");
        
    }     

    // Nodo 3
    public void addNodo3 (DatosTabla datosTabla){
        
        tablaNodo3.add(datosTabla);
    
    }
    
    public void setNodo3 (int i, DatosTabla datosTabla){
        
        tablaNodo3.set(i, datosTabla);
    
    }
    
    public int retornoIndiceNodo3 (String subred){
        
        int indice= -1;
        
        for(int i=0;i<tablaNodo3.size();i++){

            if(tablaNodo3.get(i).subred.equals(subred)){
                indice = i;
                break;
            }else{indice = -1;} //Si retorna -1 quiere decir que no hay entrada con la misma direccion de red (aun)
                   
        }
       return indice;
       
    } 

    public int getDistanciaNodo3 (int indice){
        
        int distancia = tablaNodo3.get(indice).distancia;
                
        return distancia;
    }

    public void setDistanciaNodo3 (String indice){
        
        for(int i=0;i<tablaNodo3.size();i++){
        
            if(tablaNodo3.get(i).siguienteSalto.equals(indice)){
            
                tablaNodo3.get(i).distancia = 16;
            }
        
        }
           
    }
    
    public int getTamanoNodo3 (){
        
        int tamano = tablaNodo3.size();
                
        return tamano;
    }

    
    public String getFilaNodo3 (int i){
        
        String fila=tablaNodo3.get(i).subred+"-"+tablaNodo3.get(i).mascara+"-"+
                tablaNodo3.get(i).siguienteSalto+"-"+tablaNodo3.get(i).distancia+"-"+
                tablaNodo3.get(i).nodoAnterior+"-"+tablaNodo3.get(i).nodoOriginal;
                
        return fila;
    }
    
    public void imprimirNodo3(){
        
        System.out.println("Tabla de rutas del Nodo 3");
        System.out.println("Subred\t\t"+"Mascara\t\t"+"Siguiente\t"+"Distancia");
              
        for (int i=0;i<tablaNodo3.size();i++){
            System.out.println(tablaNodo3.get(i).subred+"\t"+tablaNodo3.get(i).mascara+"\t"
            +tablaNodo3.get(i).siguienteSalto+"\t"+tablaNodo3.get(i).distancia);
        }
        System.out.println("\n");
        
    }
    
    // Nodo 4
    public void addNodo4 (DatosTabla datosTabla){
        
        tablaNodo4.add(datosTabla);
    
    }
    
    public void setNodo4 (int i, DatosTabla datosTabla){
        
        tablaNodo4.set(i, datosTabla);
    
    }
    
    public int retornoIndiceNodo4 (String subred){
        
        int indice= -1;
        
        for(int i=0;i<tablaNodo4.size();i++){

            if(tablaNodo4.get(i).subred.equals(subred)){
                indice = i;
                break;
            }else{indice = -1;} //Si retorna -1 quiere decir que no hay entrada con la misma direccion de red (aun)
                   
        }
       return indice;
       
    } 

    public int getDistanciaNodo4 (int indice){
        
        int distancia = tablaNodo4.get(indice).distancia;
                
        return distancia;
    }

    public void setDistanciaNodo4 (String indice){
        
        for(int i=0;i<tablaNodo4.size();i++){
        
            if(tablaNodo4.get(i).siguienteSalto.equals(indice)){
            
                tablaNodo4.get(i).distancia = 16;
            }
        
        }
           
    }
    
    public int getTamanoNodo4 (){
        
        int tamano = tablaNodo4.size();
                
        return tamano;
    }

    
    public String getFilaNodo4 (int i){
        
        String fila=tablaNodo4.get(i).subred+"-"+tablaNodo4.get(i).mascara+"-"+
                tablaNodo4.get(i).siguienteSalto+"-"+tablaNodo4.get(i).distancia+"-"+
                tablaNodo4.get(i).nodoAnterior+"-"+tablaNodo4.get(i).nodoOriginal;
                
        return fila;
    }
    
    public void imprimirNodo4(){
        
        System.out.println("Tabla de rutas del Nodo 4");
        System.out.println("Subred\t\t"+"Mascara\t\t"+"Siguiente\t"+"Distancia");
              
        for (int i=0;i<tablaNodo4.size();i++){
            System.out.println(tablaNodo4.get(i).subred+"\t"+tablaNodo4.get(i).mascara+"\t"
            +tablaNodo4.get(i).siguienteSalto+"\t"+tablaNodo4.get(i).distancia);
        }
        System.out.println("\n");
        
    }

    // Nodo 5
    public void addNodo5 (DatosTabla datosTabla){
        
        tablaNodo5.add(datosTabla);
    
    }
    
    public void setNodo5 (int i, DatosTabla datosTabla){
        
        tablaNodo5.set(i, datosTabla);
    
    }
    
    public int retornoIndiceNodo5 (String subred){
        
        int indice= -1;
        
        for(int i=0;i<tablaNodo5.size();i++){

            if(tablaNodo5.get(i).subred.equals(subred)){
                indice = i;
                break;
            }else{indice = -1;} //Si retorna -1 quiere decir que no hay entrada con la misma direccion de red (aun)
                   
        }
       return indice;
       
    }

    public int getDistanciaNodo5 (int indice){
        
        int distancia = tablaNodo5.get(indice).distancia;
                
        return distancia;
    }    

    public void setDistanciaNodo5 (String indice){
        
        for(int i=0;i<tablaNodo5.size();i++){
        
            if(tablaNodo5.get(i).siguienteSalto.equals(indice)){
            
                tablaNodo5.get(i).distancia = 16;
            }
        
        }
           
    }
    
    public int getTamanoNodo5 (){
        
        int tamano = tablaNodo5.size();
                
        return tamano;
    }

    
    public String getFilaNodo5 (int i){
        
        String fila=tablaNodo5.get(i).subred+"-"+tablaNodo5.get(i).mascara+"-"+
                tablaNodo5.get(i).siguienteSalto+"-"+tablaNodo5.get(i).distancia+"-"+
                tablaNodo5.get(i).nodoAnterior+"-"+tablaNodo5.get(i).nodoOriginal;
                
        return fila;
    }
    
    public void imprimirNodo5(){
        
        System.out.println("Tabla de rutas del Nodo 5");
        System.out.println("Subred\t\t"+"Mascara\t\t"+"Siguiente\t"+"Distancia");
              
        for (int i=0;i<tablaNodo5.size();i++){
            System.out.println(tablaNodo5.get(i).subred+"\t"+tablaNodo5.get(i).mascara+"\t"
            +tablaNodo5.get(i).siguienteSalto+"\t"+tablaNodo5.get(i).distancia);
        }
        System.out.println("\n");
        
    }


    
}
