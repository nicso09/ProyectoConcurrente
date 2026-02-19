package ObjetoActivo;

import RecursoCompartido.*;

public class Main {
    public static void main(String[] args) {

        // ATRACCIONES
        Teatro teatroX = new Teatro();
        MontaniaRusa montaniaX = new MontaniaRusa();
        RealidadVirtual realidadVirtualX = new RealidadVirtual(2, 3, 2);

        // ACTIVIDADES DE SHOPPING
        CasaPremios casaPremiosX = new CasaPremios();
        Comedor comedorX = new Comedor(6);

        // PARQUE Y DUENIO
        Parque parqueX = new Parque(9, 18, 2, 50, teatroX, montaniaX, casaPremiosX, comedorX, realidadVirtualX);
        Duenio duenioX = new Duenio(parqueX);
        duenioX.start();

        // EMPLEADOS
        OperadorMontania operadorX = new OperadorMontania(montaniaX); // EMPLEADO MONTANIA RUSA
        EmpleadoPremios empleadoX = new EmpleadoPremios(casaPremiosX); // EMPLEADO DE PREMIOS
        EncargadoVisores encargadoX = new EncargadoVisores(realidadVirtualX); // EMPLEADO DE REALIDAD VIRTUAL

        operadorX.start();
        empleadoX.start();
        encargadoX.start();

        for (int i = 0; i < 6; i++) {
            Asistente asistenteX = new Asistente(teatroX);
            asistenteX.start();
            try {
                Thread.sleep(900);
            } catch (Exception e) {
            }
        }

        int j = 0;
        while (true) {
            Persona personaI = new Persona(parqueX, "" + j);
            j++;
            personaI.start();
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
        }
    }
}
