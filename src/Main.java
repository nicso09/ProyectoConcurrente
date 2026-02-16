import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Exchanger;

class Cliente extends Thread {
    private int fichas;
    private BlockingQueue<Cliente> fila;
    private Exchanger<Integer> exchanger;

    public Cliente(String nombre, int fichas, BlockingQueue<Cliente> fila, Exchanger<Integer> ex) {
        super(nombre);
        this.fichas = fichas;
        this.fila = fila;
        this.exchanger = ex;
    }

    @Override
    public void run() {
        try {
            System.out.println(getName() + " llegó con " + fichas + " fichas y se pone en la fila");
            fila.put(this); // se forma en la fila

            // Cuando es su turno, el vendedor lo atiende y hace el intercambio
            int premio = exchanger.exchange(fichas); // intercambio bloqueante

            System.out.println(getName() + " recibió un premio de valor: " + premio);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Vendedor extends Thread {
    private BlockingQueue<Cliente> fila;
    private Exchanger<Integer> exchanger;

    public Vendedor(BlockingQueue<Cliente> fila, Exchanger<Integer> ex) {
        this.fila = fila;
        this.exchanger = ex;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Cliente c = fila.take(); // toma al siguiente cliente
                System.out.println("Vendedor atiende a: " + c.getName());

                // El vendedor ofrece un premio según las fichas recibidas
                int fichasDelCliente = exchanger.exchange(0); // recibe fichas
                int premio = fichasDelCliente * 10;           // regla simple

                exchanger.exchange(premio); // entrega el premio al cliente
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Cliente> fila = new ArrayBlockingQueue<>(10);
        Exchanger<Integer> exchanger = new Exchanger<>();

        Vendedor vendedor = new Vendedor(fila, exchanger);
        vendedor.start();

        new Cliente("Cliente-1", 5, fila, exchanger).start();
        new Cliente("Cliente-2", 3, fila, exchanger).start();
        new Cliente("Cliente-3", 10, fila, exchanger).start();
    }
}
