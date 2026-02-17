package ObjetoActivo;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            Random randomX = new Random();
            int x = randomX.nextInt(4);
            while (true) {
                System.out.println(x);
                x = (x + 1) % 4;
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                }
            }
        }
        // Teatro teatroX = new Teatro();
        // MontaniaRusa montaniaX = new MontaniaRusa();
        // CasaPremios casaPremiosX = new CasaPremios();
        // Parque parqueX = new Parque(2, 50, teatroX, montaniaX, casaPremiosX);
        // parqueX.abrirParque();
        // EmpleadoPremios empleadoX = new EmpleadoPremios(casaPremiosX);
        // empleadoX.start();

        // OperadorMontania operadorX = new OperadorMontania(montaniaX);
        // operadorX.start();
        // for (int i = 0; i < 5; i++) {
        // Asistente asistenteX = new Asistente(teatroX);
        // asistenteX.start();
        // System.out.println("Llego un asistente al teatro");
        // try {
        // Thread.sleep(900);
        // } catch (Exception e) {
        // }
        // }

        // for (int i = 0; i < 8; i++) {
        // Persona personaI = new Persona(parqueX,"" + i);
        // personaI.start();
        // try {
        // Thread.sleep(700);
        // } catch (Exception e) {
        // System.out.println("LOL");
        // }
        // }

        // int capacidadMontania = 5;

        // Parque parqueX = new Parque(0, capacidadMontania);
        // MontaniaRusa montaniaRusaX = new MontaniaRusa(capacidadMontania, parqueX);
        // montaniaRusaX.start();

        // for (int i = 0; i < 20; i++) {
        // Persona p = new Persona(parqueX, "Persona " + i);
        // p.start();
        // try {
        // Thread.sleep(1200);
        // } catch (Exception e) {
        // }
        // }
    }
}
