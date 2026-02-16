package ObjetoActivo;

import RecursoCompartido.CasaPremios;
import RecursoCompartido.MontaniaRusa;
import RecursoCompartido.Parque;
import RecursoCompartido.Teatro;

public class Main {
    public static void main(String[] args) {
        Teatro teatroX = new Teatro();
        MontaniaRusa montaniaX = new MontaniaRusa();
        CasaPremios casaPremiosX = new CasaPremios();
        Parque parqueX = new Parque(0, 0, teatroX, montaniaX, casaPremiosX);
        EmpleadoPremios empleadoX = new EmpleadoPremios(casaPremiosX);
        empleadoX.start();
        // OperadorMontania operadorX = new OperadorMontania(montaniaX);
        // operadorX.start();
        // for (int i = 0; i < 5; i++) {
        //     Asistente asistenteX = new Asistente(teatroX);
        //     asistenteX.start();
        //     System.out.println("Llego un asistente al teatro");
        //     try {
        //         Thread.sleep(900);
        //     } catch (Exception e) {
        //     }
        // }

        for (int i = 0; i < 8; i++) {
            Persona personaI = new Persona(parqueX,"" + i);
            personaI.start();
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("LOL");
            }
        }

        // int capacidadMontania = 5;

        // Parque parqueX = new Parque(0, capacidadMontania);
        // MontaniaRusa montaniaRusaX = new MontaniaRusa(capacidadMontania, parqueX);
        // montaniaRusaX.start();

        // for (int i = 0; i < 20; i++) {
        //     Persona p = new Persona(parqueX, "Persona " + i);
        //     p.start();
        //     try {
        //         Thread.sleep(1200);
        //     } catch (Exception e) {
        //     }
        // }
    }
}
