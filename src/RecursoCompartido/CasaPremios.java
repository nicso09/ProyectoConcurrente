package RecursoCompartido;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;

public class CasaPremios{
        Exchanger<Integer> puntos;
        Semaphore tiendaDisponible;
    public CasaPremios() {
        this.puntos = new Exchanger<>();
        this.tiendaDisponible = new Semaphore(1);
    }

   
    public void clienteBuscaPremio(){
        try {
            tiendaDisponible.acquire();
        } catch (Exception e) {
        }
    }

    public int canjearPremio(int x){
        int puntosX = 0;
        try {
            puntosX = puntos.exchange(x);
        } catch (Exception e) {
            System.out.println("ERROR EN CANJE DE PUNTOS");
        }
        return puntosX;
    }
    
    public void clienteSaleDeTienda(){
        tiendaDisponible.release();
    }
}
