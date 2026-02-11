import java.util.concurrent.Exchanger;

public class Test {
    public static void main(String[] args) {

        // El punto donde los hilos van a intercambiar datos
        Exchanger<String> exchanger = new Exchanger<>();

        // Hilo 1
        Thread hilo1 = new Thread(() -> {
            try {
                String datoEnviado = "Mensaje desde Hilo 1";
                System.out.println("Hilo 1: voy a enviar -> " + datoEnviado);

                // Envía su dato y recibe el del otro hilo
                String recibido = exchanger.exchange(datoEnviado);

                System.out.println("Hilo 1: recibí -> " + recibido);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Hilo 2
        Thread hilo2 = new Thread(() -> {
            try {
                String datoEnviado = "Mensaje desde Hilo 2";
                System.out.println("Hilo 2: voy a enviar -> " + datoEnviado);

                String recibido = exchanger.exchange(datoEnviado);

                System.out.println("Hilo 2: recibí -> " + recibido);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread hilo3 = new Thread(() -> {
            try {
                String datoEnviado = "Mensaje desde Hilo 3";
                System.out.println("Hilo 3: voy a enviar -> " + datoEnviado);

                String recibido = exchanger.exchange(datoEnviado);

                System.out.println("Hilo 3: recibí -> " + recibido);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread hilo4 = new Thread(() -> {
            try {
                String datoEnviado = "Mensaje desde Hilo 4";
                System.out.println("Hilo 4: voy a enviar -> " + datoEnviado);

                String recibido = exchanger.exchange(datoEnviado);

                System.out.println("Hilo 4: recibí -> " + recibido);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Iniciar los hilos
        hilo1.start();
        hilo3.start();
        hilo4.start();
        hilo2.start();
        
    }
}
