public class Persona extends Thread{
    Parque parqueX;
    int cantTickets;
    String nombre;

    public Persona(Parque parqueX,String nombre){
        this.parqueX = parqueX;
        this.cantTickets = 0;
        this.nombre = nombre;
    }


    public void run(){
        try {
            if(parqueX.ingresarAFilaMontania(this)){
                System.out.println("Llegó una persona a la fila de espera");
                // Espera en su semáforo único hasta que la montaña rusa la llame
                parqueX.esperarYSubirMontania(this);
                System.out.println("Una " + nombre + " se subió a la montania rusa");
                
                // Espera en su semáforo único hasta que MontaniaRusa termine y la baje
                parqueX.esperarYBajarMontania(this);
                System.out.println("Una" + nombre + " persona bajó de la montania rusa");
                
            } else{
                System.out.println("FILA LLENA, Se va a otra atraccion");
            }
        } catch (Exception e) {
        }
    }
}
