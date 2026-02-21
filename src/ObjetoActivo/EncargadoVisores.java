package ObjetoActivo;
import RecursoCompartido.RealidadVirtual;

public class EncargadoVisores extends Thread{
    private RealidadVirtual realidadVirtualX;

    // CONSTRUCTOR
    public EncargadoVisores(RealidadVirtual realidadVirtualX){
        this.realidadVirtualX = realidadVirtualX;
    }

    public void run(){
        while(true){
            realidadVirtualX.encargadoProcesaJugador();
            System.out.println("El encargado de visores esta procesando a un jugador con el equipo completo...");
            try {
                Thread.sleep(1200);
            } catch (Exception e) {
                // TODO: handle exception
            }
            realidadVirtualX.habilitarIngresoJugador();
        }
    }
}
