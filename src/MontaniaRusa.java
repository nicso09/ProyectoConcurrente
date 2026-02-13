
public class MontaniaRusa extends Thread{
    int lugaresOcupados;
    int capacidadMaxima;
    Parque parqueX;

    public MontaniaRusa(int capacidadMaxima, Parque parqueX){
        this.lugaresOcupados = 0;
        this.capacidadMaxima = capacidadMaxima;
        this.parqueX = parqueX;
    }

    public void run(){
        try {
            while(true){
                // Intenta sacar una persona de la fila
                Persona p = parqueX.subirAmontaniaRusa();
                if(p != null) {
                    System.out.println("Una persona se ha subido a la montania rusa");
                    parqueX.permitirSubirPersona(p);  // Libera específicamente a esta persona
                    lugaresOcupados++;
                }
                
                if(lugaresOcupados == capacidadMaxima){
                    Thread.sleep(200);
                    System.out.println("LA MONTAÑA RUSA INICIO");
                    Thread.sleep(5000);
                    System.out.println("LA MONTAÑA RUSA FINALIZO EL RECORRIDO");
                    while(lugaresOcupados > 0){
                        Persona p2 = parqueX.bajarPersonaMontania();
                        if(p2 != null) {
                            parqueX.permitirBajarPersona(p2);  // Libera específicamente a esta persona
                        }
                        lugaresOcupados--;
                        Thread.sleep(800);
                    }
                    parqueX.test();
                }
            Thread.sleep(1000);
            }
        } catch (Exception e) {
        }
    }
}
