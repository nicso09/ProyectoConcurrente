package ObjetoActivo;

import RecursoCompartido.MontaniaRusa;

public class OperadorMontania extends Thread{
    private MontaniaRusa montaniaRusaX;

    public OperadorMontania(MontaniaRusa montaniaRusaX){
        this.montaniaRusaX = montaniaRusaX;
    }

    public void run(){
        while (true) { 
            montaniaRusaX.subirPersonaAMontania();
            if(montaniaRusaX.estaLlena()){
                try {
                    Thread.sleep(800);
                } catch (Exception e) {
                }
                System.out.println("LA MONTAÑA RUSA ESTA LLENA, INICIANDO RECORRIDO...");
                try {
                    Thread.sleep(5000);
                    System.out.println("EL VIAJE HA FINALIZADO... BAJANDO PERSONAS");
                    while(!montaniaRusaX.estaVacia()){
                        Thread.sleep(800);
                        montaniaRusaX.bajarPersonaMontania();
                    }
                    Thread.sleep(800);
                    System.out.println("La montaña rusa esta vacia...");
                    Thread.sleep(800);
                } catch (Exception e) {
                }
                
            }
        }
    }

}
