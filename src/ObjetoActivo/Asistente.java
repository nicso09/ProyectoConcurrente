package ObjetoActivo;
import RecursoCompartido.Teatro;

public class Asistente extends Thread{
    private Teatro teatroX;

    // CONSTRUCTOR
    public Asistente(Teatro teatroX){
        this.teatroX = teatroX;
    }
    public void run(){
        try {
            while (true) {
                teatroX.asistenteEntraShow();
                System.out.println("Un asistente ingreso al escenario");
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                }
                teatroX.finalizarShow();
            }
        } catch (Exception e) {
        }
    }
}
