package RecursoCompartido;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Comedor {
    private CyclicBarrier[] mesas;
    private Semaphore [] espaciosMesas;
    private Semaphore espaciosComedor;

    // CONSTRUCTOR
    public Comedor(int cantMesas) {
        this.mesas = new CyclicBarrier[cantMesas];
        this.espaciosMesas = new Semaphore[cantMesas];
        this.espaciosComedor = new Semaphore(cantMesas*4); // cantMesas * 4 = espaciosComedor -> (4 Lugares por mesa)
        for (int i = 0; i < mesas.length; i++) {
            final int mesaNum = i;
            mesas[i] = new CyclicBarrier(4, () -> {    System.out.println("LA MESA " + mesaNum + " INICIO A COMER");}); // 4 lugares por mesa
            espaciosMesas[i] = new Semaphore(4);
        } 
    }

    // METODOS UTILIZADOS POR LA CLASE "Persona"
    public boolean entrarAComedor(){
        boolean pudoEntrar = false;
        try {
            pudoEntrar = espaciosComedor.tryAcquire(40, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("ERROR A");
        }
        return pudoEntrar;
    }

    public int sentarseEnMesa(){
        Random randomX = new Random();
        int x = randomX.nextInt(mesas.length); // 0 o 1 si cantMesas = 2
        while(!(espaciosMesas[x].tryAcquire())){
            x = (x+1)% mesas.length;
        }
        return x;
    }

    public int iniciarAComer(int x){
        int mesa = x;
        try {
            mesas[x].await(30, TimeUnit.SECONDS);
        } catch (TimeoutException | BrokenBarrierException e) {
            synchronized (mesas[x]) { // CAMBIAR POR MUTEX
                if(mesas[x].isBroken()) // REPARAMOS LA BARRERA PARA PROXIMAS PERSONAS (HILOS)
                    mesas[x].reset();
            }
            mesa = -1;
        } catch (Exception e) {
            mesa = -1;
        } 
        return mesa;
    }

    public void liberarMesa(int x){
        espaciosMesas[x].release();
    }

    public void salirDeComedor(){
        espaciosComedor.release();
    }
    
}
