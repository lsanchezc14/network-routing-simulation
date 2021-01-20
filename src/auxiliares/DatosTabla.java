package auxiliares;
/*
 * @author Luis Carlos Sanchez Cespedes - A65285
 */
public class DatosTabla {
    //Argumentos para cada tabla
    public String subred;
    public String mascara;
    public String siguienteSalto;
    public int distancia;
    public int nodoAnterior; //Esta variable sera usada para recordar el nodo anterior que proporciono la informacion (Split Horizon)
    public int nodoOriginal;

    public DatosTabla(String subred, String mascara, String siguienteSalto, int distancia, int nodoAnterior, int nodoOriginal) {
        this.subred = subred;
        this.mascara = mascara;
        this.siguienteSalto = siguienteSalto;
        this.distancia = distancia;
        this.nodoAnterior = nodoAnterior;
        this.nodoOriginal = nodoOriginal;
    }

    //Getters y setters - solo se crearon los necesarios para que la logica funcionara

    public int getNodoAnterior() {
        return nodoAnterior;
    }

    public void setNodoAnterior(int nodoAnterior) {
        this.nodoAnterior = nodoAnterior;
    }


    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public String getSiguienteSalto() {
        return siguienteSalto;
    }

    public void setSiguienteSalto(String siguienteSalto) {
        this.siguienteSalto = siguienteSalto;
    }
   
}
