package ObjetoActivo;
import RecursoCompartido.Teatro;

public class Asistente extends Thread{
    private Teatro teatroX;

    Asistente(Teatro teatroX){
        this.teatroX = teatroX;
    }
    public void run(){
        try {
            while (true) {
                teatroX.asistenteListoParaShow();
                teatroX.asistenteIngresaAShow();
                Thread.sleep(4000);
                teatroX.asistenteSaleDelShow();
            }
        } catch (Exception e) {
        }
    }
}
