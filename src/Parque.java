import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class Parque {
    int cantMolinetes;
    // CONTROL MONTANIA RUSA
    MontaniaRusa montaniaRusaX;
    BlockingQueue<Persona> colaDeEsperaMontania= new ArrayBlockingQueue<>(10);
    BlockingQueue<Persona> asientosMontaniaRusa;
    Map<Persona, Semaphore> semaforoSubida;    // Semáforo único por persona para subir
    Map<Persona, Semaphore> semaforoBajada;    // Semáforo único por persona para bajar
    // FIN CONTROL MONTANIA RUSA

    public Parque(int cantMolinetes, int asientosMontania) {
    this.cantMolinetes = cantMolinetes;
    asientosMontaniaRusa= new ArrayBlockingQueue<>(asientosMontania);
    this.semaforoSubida = new HashMap<>();
    this.semaforoBajada = new HashMap<>();
    }
    
    public boolean ingresarAFilaMontania(Persona personaX){
        // Asigna semáforos únicos a esta persona cuando entra
        semaforoSubida.put(personaX, new Semaphore(0));
        semaforoBajada.put(personaX, new Semaphore(0));
        return colaDeEsperaMontania.offer(personaX);
    }

    public Persona subirAmontaniaRusa(){
        try {
            Persona p = colaDeEsperaMontania.take();
            asientosMontaniaRusa.offer(p);
            return p;
        } catch (Exception e) {
            return null;
        }
    }

    public Persona bajarPersonaMontania(){
        try {
            Persona p = asientosMontaniaRusa.take();
            return p;
        } catch (Exception e) {
            return null;
        }
    }
    
    public void esperarYSubirMontania(Persona personaX) throws InterruptedException {
        Semaphore semaforo = semaforoSubida.get(personaX);
        if(semaforo != null) {
            semaforo.acquire();   // Espera a su semáforo único
            semaforoSubida.remove(personaX);
        }
    }
    
    public void permitirSubirPersona(Persona personaX) {
        Semaphore semaforo = semaforoSubida.get(personaX);
        if(semaforo != null) {
            semaforo.release();   // Libera especificamente a esta persona del hashmap
        }
    }
    
    public void esperarYBajarMontania(Persona personaX) throws InterruptedException {
        Semaphore semaforo = semaforoBajada.get(personaX);
        if(semaforo != null) {
            semaforo.acquire();   // Espera a su semáforo único
            semaforoBajada.remove(personaX);
        }
    }
    
    public void permitirBajarPersona(Persona personaX) {
        Semaphore semaforo = semaforoBajada.get(personaX);
        if(semaforo != null) {
            semaforo.release();   // Libera especificamente a esta persona del hashmap
        }
    }

    // public void test(){
    //     System.out.println("TAMAÑO HASH SUBIDA" + semaforoSubida.size());
    //     System.out.println("TAMAÑO HASH BAJADA" + semaforoBajada.size());
    // }

}
