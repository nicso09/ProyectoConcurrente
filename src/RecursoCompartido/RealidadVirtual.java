package RecursoCompartido;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class RealidadVirtual {
    private Semaphore visores;
    private Semaphore manoplas;
    private Semaphore bases;
    private Semaphore encargado;
    private Semaphore ingresoJuego;
    private Semaphore mutexActividad;
    private boolean estadoActividad;

    public RealidadVirtual(int cantVisores, int cantManoplas, int cantBases) {
        this.visores = new Semaphore(cantVisores); // PARA UN CORRECTO FUNCIONAMIENTO MINIMO DEBE EXISITR 1 VISOR
        this.manoplas = new Semaphore(cantManoplas); // PARA UN CORRECTO FUNCIONAMIENTO MINIMO DEBEN EXISTIR 2 MANOPLAS
        this.bases = new Semaphore(cantBases); // PARA UN CORRECTO FUNCIONAMIENTO MINIMO DEBE EXISITR 1 BASE
        this.ingresoJuego = new Semaphore(0);
        this.encargado = new Semaphore(0);
        this.mutexActividad = new Semaphore(1);
        this.estadoActividad = false; // TRUE SIGNIFICA ABIERTA - FALSE SIGNFICA CERRADA

    }

      public void cerrarActividad(){
        try {
            this.mutexActividad.acquire();
        } catch (Exception e) {
        }
        this.estadoActividad = false;
        this.mutexActividad.release();
    }

    public void abrirActividad(){
       try {
            this.mutexActividad.acquire();
            this.estadoActividad = true;
            this.mutexActividad.release(); 
        } catch (Exception e) {
        }
    }

    public boolean estaAbierto(){
        boolean actividadAbierta = false;
        try {
            this.mutexActividad.acquire();
            actividadAbierta = this.estadoActividad;
            this.mutexActividad.release();
        } catch (Exception e) {
        }
        return actividadAbierta;
    }


    public boolean intentarPonerCasco() {
        boolean pudoUsarEquipo = true;
        try {
            pudoUsarEquipo = this.visores.tryAcquire(25, TimeUnit.SECONDS); // CAMBIAR POR ACQUIRE
        } catch (Exception e) {
            System.out.println("ERROR CASCO");
        }
        return pudoUsarEquipo;
    }

    public boolean intentarPonerManoplas() {
        boolean pudoUsarEquipo = true;
        try {
            pudoUsarEquipo = this.manoplas.tryAcquire( 2, 25, TimeUnit.SECONDS);
            if(!pudoUsarEquipo)
                this.visores.release();
        } catch (Exception e) {
            System.out.println("ERROR MANOPLAS");
        }
        return pudoUsarEquipo;
    }

    public boolean intentarUsarBase() {
        boolean pudoUsarEquipo = true;
        try {
            pudoUsarEquipo = this.bases.tryAcquire(25, TimeUnit.SECONDS);
            if(!pudoUsarEquipo){
                this.visores.release();
                this.manoplas.release(2);
            }
        } catch (Exception e) {
            System.out.println("ERROR BASE");
        }
        return pudoUsarEquipo;
    }

    public void avisarAEncargado(){
        encargado.release();
    }

    public void encargadoProcesaJugador(){
        try {
            encargado.acquire();
        } catch (Exception e) {
        }
    }

    public void habilitarIngresoJugador(){
        ingresoJuego.release();
    }

    public void ingresarAJugar(){
        try {
            ingresoJuego.acquire();
        } catch (Exception e) {
        }
    }

    public int terminarDeJugar(){
        this.visores.release();
        this.manoplas.release(2);
        this.bases.release();
        return 1;  // RETORNA 1 PORQUE ES LA POS DE LOS PUNTOS DE REALIDAD VIRTUAL
    }
}
