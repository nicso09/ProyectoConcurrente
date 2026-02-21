package RecursoCompartido;

import ObjetoActivo.Persona;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class MontaniaRusa {
    private BlockingQueue<Persona> colaDeEsperaMontania;
    private BlockingQueue<Persona> asientosMontaniaRusa;
    private Semaphore mutexHash;
    private Map<Persona, Semaphore> semaforoSubida; // Semáforo único por persona para subir
    private Map<Persona, Semaphore> semaforoBajada; // Semáforo único por persona para bajar
    private Semaphore mutexActividad;
    private boolean estadoActividad;
    private Semaphore arranqueMontania;
    private final int puntosActividad = 10;

    // CONSTRUCTOR
    public MontaniaRusa() {
        this.colaDeEsperaMontania = new ArrayBlockingQueue<>(10);
        this.asientosMontaniaRusa = new ArrayBlockingQueue<>(5);
        this.semaforoSubida = new HashMap<>();
        this.semaforoBajada = new HashMap<>();
        this.arranqueMontania = new Semaphore(0);
        this.mutexHash = new Semaphore(1);
        this.mutexActividad = new Semaphore(1);
        this.estadoActividad = false; // TRUE SIGNIFICA ABIERTA - FALSE SIGNFICA CERRADA
    }

    // METODOS UTILIZADOS POR LA CLASE "Duenio" (CONTROLA LA APERTURA Y CIERRE DE
    // LAS ACTIVIDADES)
    public void abrirActividad() {
        try {
            this.mutexActividad.acquire();
            this.estadoActividad = true;
            this.mutexActividad.release();
        } catch (Exception e) {
        }
    }

    public void cerrarActividad() {
        try {
            this.mutexActividad.acquire();
        } catch (Exception e) {
        }
        this.estadoActividad = false;
        this.mutexActividad.release();
    }

    // METODOS UTILIZADOS POR LA CLASE "Persona"
    public boolean estaAbierto() {
        boolean actividadAbierta = false;
        try {
            this.mutexActividad.acquire();
            actividadAbierta = this.estadoActividad;
            this.mutexActividad.release();
        } catch (Exception e) {
        }
        return actividadAbierta;
    }

    public boolean entrar(Persona personaX) {
        // Asigna semáforos únicos a esta persona cuando entra
        boolean puedeEntrar = false;
        try {
            mutexHash.acquire();
            puedeEntrar = colaDeEsperaMontania.offer(personaX);
            if (puedeEntrar) {
                semaforoSubida.put(personaX, new Semaphore(0));
                semaforoBajada.put(personaX, new Semaphore(0));
                mutexHash.release();
            }
        } catch (Exception e) {
        }
        return puedeEntrar;
    }

    public void esperarMontania(Persona personaX) {
        Semaphore semaforo = semaforoSubida.get(personaX);
        if (semaforo != null) {
            try {
                semaforo.acquire();
            } catch (Exception e) {
                System.out.println("ERROR C");
            } // Espera a su semáforo único
            try {
                mutexHash.acquire();
            } catch (Exception e) {
                System.out.println("ERROR C");
            }
            semaforoSubida.remove(personaX);
            mutexHash.release();
        }
    }

    public boolean esperarInicio(Persona personaX) {
        boolean inicioViaje = true;
        try {
            inicioViaje = arranqueMontania.tryAcquire(30, TimeUnit.SECONDS);
        } catch (Exception e) {
        }
        if (!inicioViaje) {
            asientosMontaniaRusa.poll(); // SACA UNA PERSONA DEL ASIENTO DE LA MONTAÑA RUSA
            semaforoBajada.remove(personaX);
        }
        return inicioViaje;
    }

    public int bajarMontania(Persona personaX) throws InterruptedException {
        Semaphore semaforo = semaforoBajada.get(personaX);
        if (semaforo != null) {
            semaforo.acquire(); // Espera a su semáforo único
            try {
                mutexHash.acquire();
            } catch (Exception e) {
            }
            semaforoBajada.remove(personaX);
            mutexHash.release();
        }
        return puntosActividad;
    }

    // METODOS UTILIZADOS POR LA CLASE "OperadorMontania"
    public void subirPersonaAMontania() {
        try {
            Persona p = colaDeEsperaMontania.take();
            mutexHash.acquire();
            Semaphore sem = semaforoSubida.get(p);
            mutexHash.release();
            asientosMontaniaRusa.offer(p);
            sem.release();

        } catch (Exception e) {
            System.out.println("ERROR DE HASH");
        }
    }

    public void arrancarMontania() {
        this.arranqueMontania.release(5); // LOS 5 PERMISOS QUE LIBERA SON DE LAS 5 PERSONAS EN LA MONTAÑA RUSA
    }

    public boolean estaLlena() {
        return asientosMontaniaRusa.remainingCapacity() == 0;
    }

    public boolean estaVacia() {
        return asientosMontaniaRusa.remainingCapacity() == 5;
    }

    public void bajarPersonaMontania() {
        try {
            Persona p = asientosMontaniaRusa.take();
            mutexHash.acquire();
            semaforoBajada.get(p).release();
            mutexHash.release();
        } catch (Exception e) {
        }
    }

}
