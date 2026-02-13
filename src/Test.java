import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Test {
    public static void main(String[] args) {

        // Cola de capacidad 3 (si se llena, el productor se bloquea)
        BlockingQueue<Integer> cola = new ArrayBlockingQueue<>(3);

        // Productor: genera números y los pone en la cola
        Thread productor = new Thread(() -> {
            int numero = 1;
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Productor quiere poner: " + numero);

                    cola.put(numero);  // Se bloquea si la cola está llena

                    System.out.println("Productor puso: " + numero);
                    numero++;

                    Thread.sleep(700); // Simula producción lenta
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Consumidor: saca números de la cola
        Thread consumidor = new Thread(() -> {
            try {
                while (true) {
                    System.out.println("Consumidor intenta tomar...");

                    int valor = cola.take();  // Se bloquea si la cola está vacía

                    System.out.println("Consumidor tomó: " + valor);

                    Thread.sleep(1200); // Simula procesamiento más lento
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        productor.start();
        consumidor.start();
    }
}
