package ObjetoActivo;
import RecursoCompartido.Parque;

public class Duenio extends Thread{
    Parque parqueX;

    public Duenio(Parque parqueX){
        this.parqueX = parqueX;
    }

    public void run(){
        while(true){
            System.out.println("SON LAS: "  + parqueX.obtenerHorario() + ":00" );
            try {
                Thread.sleep(15000);
            } catch (Exception e) {
            }
            parqueX.aumentarHorario();
            parqueX.esHorarioCierre();
            parqueX.esHorarioCierreActividades();
            if (parqueX.esHorarioApertura()) {
                System.out.println("EL PARQUE ABRIO SUS PUERTAS!!!");
                parqueX.abrirActividades();
                parqueX.abrirParque();
            }
        }
    }
}
