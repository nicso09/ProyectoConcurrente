package RecursoCompartido;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Teatro{
    CyclicBarrier barreraAsistentes;
    CyclicBarrier barreraPublico;
    Semaphore showListo;
    Semaphore espaciosTeatro;
    Semaphore espaciosEspectaculo;
    int cantPersonasShow;
    int cantAsistentesEnEscenario;
    Semaphore mutexAsistentes;
    Semaphore mutexPersonas;
    Semaphore salidaPersonas;

    public Teatro(){
        this.barreraAsistentes = new CyclicBarrier(4); // SE NECESITAN 4 ASISTENTES PARA UN SHOW
        this.barreraPublico = new CyclicBarrier(5, () -> {
    System.out.println("NIGGAT TEST");
});
        this.espaciosTeatro = new Semaphore(20);
        this.espaciosEspectaculo = new Semaphore(5);
        this.showListo = new Semaphore(0);
        this.mutexPersonas = new Semaphore(1);
        this.mutexAsistentes = new Semaphore(1);
        this.salidaPersonas = new Semaphore(0);
        this.cantAsistentesEnEscenario = 0;
        this.cantPersonasShow = 0;
    }

    public boolean entrar(){
        return espaciosTeatro.tryAcquire();
    }

    public void salir(){
        espaciosTeatro.release();
    }

    public boolean personaIngresaAShow(){  // MEJORAR ESTO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        boolean ingresoShow = true;
        try {
            espaciosEspectaculo.acquire();
            // IMPLEMENTAR BARRERA CYCLICA ACÁ
            barreraPublico.await(30, TimeUnit.SECONDS);
            if(this.estaLleno()){
                System.out.println("EL ESPECTACULO ESTÁ LLENO, EL SHOW ESTA LISTO PARA EMPEZAR");
                showListo.release();
            }
        } catch (TimeoutException | BrokenBarrierException e) {
            System.out.println("Una persona se cansó de esperar el espectaculo y se ha ido");
            espaciosEspectaculo.release();
            ingresoShow = false;
        } catch(Exception e){
        }
        return ingresoShow;
    }


    public boolean estaLleno() throws InterruptedException{
        boolean estaLleno;
        mutexPersonas.acquire();
        cantPersonasShow++;
        estaLleno = (cantPersonasShow >= 5);
        mutexPersonas.release();
        return estaLleno;
    }

    public void asistenteListoParaShow(){
       try {
            barreraAsistentes.await();
       } catch (Exception e) {
       } 
    }
    
    public void habilitarShow(){
        showListo.release();
    }

    public void asistenteIngresaAShow(){
       try {
           showListo.acquire();
           mutexAsistentes.acquire();
           cantAsistentesEnEscenario++; 
           if(cantAsistentesEnEscenario < 4){
            showListo.release();
           } else{
            System.out.println("Los asistentes han ingresado al show");
           }
           mutexAsistentes.release();
       } catch (Exception e) {
       }
    }

    public void asistenteSaleDelShow(){
        try {
            mutexAsistentes.acquire();
            cantAsistentesEnEscenario--;
            if(cantAsistentesEnEscenario <= 0){
                System.out.println("EL SHOW HA FINALIZADO, PUEDEN SALIR LAS PERSONAS DEL SHOW");
                salidaPersonas.release();
            }
            mutexAsistentes.release();
        } catch (Exception e) {
        }
    }

    public void personaSaleShow(){
        try {
            salidaPersonas.acquire();
            mutexPersonas.acquire();
            cantPersonasShow--;
            if(cantPersonasShow > 0){
                salidaPersonas.release();
            } else{
                espaciosEspectaculo.release(5);
            }
            mutexPersonas.release();
        } catch (Exception e) {
        }
    }


}
