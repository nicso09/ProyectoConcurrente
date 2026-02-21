package RecursoCompartido;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;

public class CasaPremios {
    private Exchanger<Integer> puntos;
    private Semaphore tiendaDisponible;

    // CONSTRUCTOR
    public CasaPremios() {
        this.puntos = new Exchanger<>();
        this.tiendaDisponible = new Semaphore(1);
    }

    // METODOS UTILIZADOS POR LA CLASE "Persona"
    public void clienteBuscaPremio() {
        try {
            tiendaDisponible.acquire();
        } catch (Exception e) {
        }
    }

    public void clienteSaleDeTienda() {
        tiendaDisponible.release();
    }

    // METODOS UTILIZADOS POR LAS CLASES "Persona" y "EmpleadoPremio"
    public int canjearPremio(int x) {
        int puntosX = 0;
        try {
            puntosX = puntos.exchange(x);
        } catch (Exception e) {
            System.out.println("ERROR EN CANJE DE PUNTOS");
        }
        return puntosX;
    }

}
