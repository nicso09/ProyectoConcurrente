package RecursoCompartido;

import ObjetoActivo.Persona;
import java.util.concurrent.Semaphore;

public class Parque {
    private int horarioActual;
    private int horarioCierre;
    private int horarioApertura;
    private Semaphore espacioParque;
    private Semaphore mutexParque;
    private Semaphore mutexActividades;
    private Semaphore molinetes;
    private boolean estaAbierto;
    private boolean actividadesAbiertas;

    // ---- ACTIVIDADES ----
    private MontaniaRusa montaniaRusaX;
    private Teatro teatroX;
    private RealidadVirtual realidadVirtualX;

    // ---- SHOPPING ----
    private CasaPremios casaPremiosX;
    private Comedor comedorX;

    // CONSTRUCTOR
    public Parque(int horarioApertura, int horarioCierre, int cantMolinetes, int cantEspaciosParque, Teatro teatroX,
            MontaniaRusa montaniaRusaX, CasaPremios casaPremiosX, Comedor comedorX, RealidadVirtual realidadVirtualX) {
        this.molinetes = new Semaphore(cantMolinetes);
        this.mutexParque = new Semaphore(1);
        this.mutexActividades = new Semaphore(1);
        this.espacioParque = new Semaphore(cantEspaciosParque);
        this.estaAbierto = false; // EL PARQUE INICA CERRADO
        this.actividadesAbiertas = false; // LAS ACTIVIDADES INICIAN CERRADAS
        this.horarioActual = 6; // 6 REPRESENTA 6AM
        this.horarioCierre = horarioCierre;
        this.horarioApertura = horarioApertura;
        // ACTIVIDADES O SHOPPING
        this.montaniaRusaX = montaniaRusaX;
        this.teatroX = teatroX;
        this.realidadVirtualX = realidadVirtualX;
        this.casaPremiosX = casaPremiosX;
        this.comedorX = comedorX;
    }

    // METODOS UTILIZADOS POR LA CLASE "Duenio" (MANEJAN EL CONTROL DEL PARQUE)
    public void abrirParque() {
        try {
            mutexParque.acquire();
            estaAbierto = true;
            mutexParque.release();
        } catch (Exception e) {
        }
    }

    public void cerrarParque() {
        try {
            mutexParque.acquire();
            estaAbierto = false;
            mutexParque.release();
        } catch (Exception e) {
        }
    }

    public void abrirActividades() {
        try {
            mutexActividades.acquire();
            this.actividadesAbiertas = true;
            montaniaRusaX.abrirActividad();
            realidadVirtualX.abrirActividad();
            teatroX.abrirActividad();
            mutexActividades.release();
        } catch (Exception e) {
        }
    }

    public void cerrarActividades() {
        try {
            mutexActividades.acquire();
            this.actividadesAbiertas = false;
            montaniaRusaX.cerrarActividad();
            realidadVirtualX.cerrarActividad();
            teatroX.cerrarActividad();
            mutexActividades.release();
        } catch (Exception e) {
        }
    }

    public String obtenerHorario() {
        String horario = "";
        try {
            mutexParque.acquire();
            horario = horario + horarioActual;
            mutexParque.release();
        } catch (Exception e) {
        }
        return horario;
    }

    public void aumentarHorario() {
        try {
            mutexParque.acquire();
            horarioActual++;
            if (horarioActual == 24) {
                horarioActual = 0;
            }
            mutexParque.release();
        } catch (Exception e) {
        }
    }

    public void esHorarioCierre() {
        try {
            mutexParque.acquire();
            if (horarioActual == horarioCierre) {
                estaAbierto = false;
                System.out.println(" --- EL PARQUE HA CERRADO SUS PUERTAS --- ");
            }
            mutexParque.release();
        } catch (Exception e) {
        }
    }

    public void esHorarioCierreActividades() {
        try {
            mutexParque.acquire();
            if (horarioActual == horarioCierre + 1) {
                cerrarActividades();
                System.out.println(" --- LAS ATRACCIONES HAN CERRADO SUS PUERTAS --- ");
            }
            mutexParque.release();
        } catch (Exception e) {
        }
    }

    public boolean esHorarioApertura() {
        boolean esApertura = false;
        try {
            mutexParque.acquire();
            esApertura = (horarioActual == horarioApertura);
            mutexParque.release();
        } catch (Exception e) {
        }
        return esApertura;

    }

    // METODOS UTILIZADOS POR LA CLASE "Persona" -- INGRESO Y SALIDA / CONTROL --
    public boolean parqueEstaAbierto() {
        try {
            mutexParque.acquire();
        } catch (Exception e) {
            System.out.println("ERROR A");
        }
        boolean parqueAbierto = this.estaAbierto;
        // System.out.println(estaAbierto);
        mutexParque.release();
        return parqueAbierto;
    }

    public boolean personaIntentarEntrarParque() {
        return espacioParque.tryAcquire();
    }

    public void salirDeParque() {
        this.espacioParque.release();
    }

    public void usarMolinete() {
        try {
            this.molinetes.acquire();
        } catch (Exception e) {
        }
    }

    public void dejarMolinete() {
        try {
            this.molinetes.release();
        } catch (Exception e) {
        }
    }

    public boolean actividadesDisponibles() {
        boolean actividadesEstanDispo = false;
        try {
            mutexActividades.acquire();
            actividadesEstanDispo = this.actividadesAbiertas;
            mutexActividades.release();
        } catch (Exception e) {
        }

        return actividadesEstanDispo;
    }

    // METODOS UTILIZADOS POR LA CLASE "Persona" -- MONTAÑA RUSA --
    public boolean montaniaRusaAbierto() {
        return montaniaRusaX.estaAbierto();
    }

    public boolean ingresarAMontania(Persona personaX) {
        return montaniaRusaX.entrar(personaX);
    }

    public void subirMontania(Persona personaX) {
        montaniaRusaX.esperarMontania(personaX);

    }

    public boolean esperaArranque(Persona personaX) {
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

    // METODOS UTILIZADOS POR LA CLASE "Persona" -- TEATRO --
    public boolean teatroAbierto() {
        return teatroX.estaAbierto();
    }

    public boolean ingresarATeatro() {
        return teatroX.entrarTeatro();
    }

    public boolean salirTeatro() {
        return teatroX.salirTeatro();
    }

    // METODOS UTILIZADOS POR LA CLASE "Persona" -- REALIDAD VIRTUAL --
    public boolean realidadVirtualAbierto() {
        return realidadVirtualX.estaAbierto();
    }

    public boolean intentarPonerCasco() {
        return realidadVirtualX.intentarPonerCasco();
    }

    public boolean intentarPonerManoplas() {
        return realidadVirtualX.intentarPonerManoplas();
    }

    public boolean intentarUsarBase() {
        return realidadVirtualX.intentarUsarBase();
    }

    public void avisarAEncargado() {
        realidadVirtualX.avisarAEncargado();
    }

    public void ingresarAJugarRealidad() {
        realidadVirtualX.ingresarAJugar();
    }

    public int terminarDeJugarRealidad() {
        return realidadVirtualX.terminarDeJugar();
    }

    // METODOS UTILIZADOS POR LA CLASE "Persona" -- COMEDOR --
    public boolean ingresarComedor() {
        return comedorX.entrarAComedor();
    }

    public void salirDeComedor() {
        comedorX.salirDeComedor();
    }

    public int sentarseEnMesa() {
        return comedorX.sentarseEnMesa();
    }

    public int iniciarAComer(int x) {
        return comedorX.iniciarAComer(x);
    }

    public void liberarMesa(int x) {
        comedorX.liberarMesa(x);
    }

    // METODOS UTILIZADOS POR LA CLASE "Persona" -- CASA PREMIOS --
    public int canjearPremios(int x) {
        return casaPremiosX.canjearPremio(x);
    }

    public void personaIngresaTienda() {
        casaPremiosX.clienteBuscaPremio();
    }

    public void personaSaleTienda() {
        casaPremiosX.clienteSaleDeTienda();
    }

}
