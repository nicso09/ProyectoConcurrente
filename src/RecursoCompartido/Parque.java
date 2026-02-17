package RecursoCompartido;

import ObjetoActivo.Persona;
import java.util.concurrent.Semaphore;

public class Parque {
    Semaphore espacioParque;
    Semaphore mutexParque;
    Semaphore molinetes;
    boolean estaAbierto;
    // CONTROL MONTANIA RUSA
    MontaniaRusa montaniaRusaX;
    // FIN CONTROL MONTANIA RUSA

    // CONTROL TEATRO
    Teatro teatroX;

    //  CONTROL CASA PREMIOS
    CasaPremios casaPremiosX;

    // CONTROL COMEDOR
    Comedor comedorX;


    public Parque(int cantMolinetes, int cantEspaciosParque, Teatro teatroX, MontaniaRusa montaniaRusaX, CasaPremios casaPremiosX, Comedor comedorX) {
        this.molinetes = new Semaphore(cantMolinetes);
        this.mutexParque = new Semaphore(1);
        this.espacioParque = new Semaphore(cantEspaciosParque);
        this.estaAbierto = false; // EL PARQUE INICA CERRADO
        // this.asientosMontaniaRusa = new ArrayBlockingQueue<>(asientosMontania);
        // this.semaforoSubida = new HashMap<>();
        // this.semaforoBajada = new HashMap<>();
        this.teatroX = teatroX;
        this.montaniaRusaX = montaniaRusaX;
        this.casaPremiosX = casaPremiosX;
        this.comedorX = comedorX;
    }

    public void abrirParque(){
        try {
            mutexParque.acquire();
            estaAbierto = true;
            mutexParque.release();
        } catch (Exception e) {
        }
    }

    public void cerrarParque(){
       try {
            mutexParque.acquire();
            estaAbierto = false;
            mutexParque.release();
        } catch (Exception e) {
        } 
    }

    public void usarMolinete(){
        try {
            this.molinetes.acquire();
        } catch (Exception e) {
        }
    }

    public void dejarMolinete(){
        try {
            this.molinetes.release();
        } catch (Exception e) {
        }
    }

    public void ingresarAParque(){
        try {
            this.espacioParque.acquire();
        } catch (Exception e) {
        }
    }

    public void salirDeParque(){
        this.espacioParque.release();
    }

    public boolean parqueEstaAbierto(){
        try {
            mutexParque.acquire();
        } catch (Exception e) {
        }
        boolean parqueAbierto = this.estaAbierto;
        mutexParque.release();
        return parqueAbierto;
    }


    public boolean ingresarAMontania(Persona personaX) {
        return montaniaRusaX.entrar(personaX);
    }

    public void subirMontania(Persona personaX) {
        montaniaRusaX.esperarMontania(personaX);

    }

    public boolean esperaArranque(Persona personaX){
        return montaniaRusaX.esperarInicio(personaX);
    }

    public int bajarMontania(Persona personaX) {
        try {
            return montaniaRusaX.bajarMontania(personaX);
        } catch (Exception e) {
            System.out.println("ERROR AL BAJAR PASAJERO");
            return 0;
        }
    }

    public boolean ingresarATeatro() {
        return teatroX.entrar();
    }

    public boolean ingresarAShow() {
        return teatroX.personaIngresaAShow();
    }

    public void salirShow() {
         teatroX.personaSaleShow();
    }

    public void salirTeatro(){
        teatroX.salir();
    }

    public int canjearPremios(int x){
        return casaPremiosX.canjearPremio(x);
    }

    public void personaIngresaTienda(){
        casaPremiosX.clienteBuscaPremio();
    }

    public void personaSaleTienda(){
        casaPremiosX.clienteSaleDeTienda();
    }

    public boolean ingresarComedor(){
        return comedorX.entrarAComedor();
    }

    public void salirDeComedor(){
        comedorX.salirDeComedor();
    }

    public int sentarseEnMesa(){
        return comedorX.sentarseEnMesa();
    }

    public int iniciarAComer(int x){
        return comedorX.iniciarAComer(x);
    }

    public void liberarMesa(int x){
        comedorX.liberarMesa(x);
    }
}
