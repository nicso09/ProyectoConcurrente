package ObjetoActivo;
import RecursoCompartido.RealidadVirtual;

public class EncargadoVisores extends Thread{
    RealidadVirtual realidadVirtualX;

    public EncargadoVisores(RealidadVirtual realidadVirtualX){
        this.realidadVirtualX = realidadVirtualX;
    }

    public void run(){
        while(true){
            realidadVirtualX.encargadoProcesaJugador();
            System.out.println("El encargado esta procesando a un jugador con el equipo completo...");
            try {
                Thread.sleep(1200);
            } catch (Exception e) {
                // TODO: handle exception
            }
            realidadVirtualX.habilitarIngresoJugador();
        }
    }
}
