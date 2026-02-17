package RecursoCompartido;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Comedor {
    CyclicBarrier[] mesas;
    Semaphore [] espaciosMesas;
    Semaphore espaciosComedor;

    public Comedor(int cantMesas) {
        this.mesas = new CyclicBarrier[cantMesas];
        this.espaciosMesas = new Semaphore[cantMesas];
        this.espaciosComedor = new Semaphore(cantMesas*4); // cantMesas * 4 = espaciosComedor -> (4 Lugares por mesa)
        for (int i = 0; i < mesas.length; i++) {
            mesas[i] = new CyclicBarrier(4); // 4 lugares por mesa
            espaciosMesas[i] = new Semaphore(4);
        } 
    }

    public boolean entrarAComedor(){
        boolean pudoEntrar = false;
        try {
            pudoEntrar = espaciosComedor.tryAcquire(30, TimeUnit.SECONDS);
        } catch (Exception e) {
        }
        return pudoEntrar;
    }

    public int sentarseEnMesa(){
        Random randomX = new Random();
        int x = randomX.nextInt(mesas.length);
        while(!(espaciosMesas[x].tryAcquire())){
            x = (x+1)%4;
        }
        return x;
    }

    public int iniciarAComer(int x){
        int mesa = x;
        try {
            mesas[x].await(30, TimeUnit.SECONDS);
        } catch (TimeoutException | BrokenBarrierException e) {
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
