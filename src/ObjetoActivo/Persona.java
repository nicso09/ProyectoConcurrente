package ObjetoActivo;

import RecursoCompartido.Parque;
import java.util.Random;

public class Persona extends Thread {
    private Parque parqueX;
    private int[] cantTickets; // MR POS [0] ; RV POS [1]
    private String nombre;
    private boolean [] actividadRealizada;
    private int cantActRealizadas;

    public Persona(Parque parqueX, String nombre) {
        this.parqueX = parqueX;
        this.cantTickets = new int[2];
        this.cantTickets[0] = 0;
        this.cantTickets[1] = 0;
        this.nombre = nombre;
        this.actividadRealizada = new boolean[3]; // DEFINIMOS 3 YA QUE SON LA CANTIDAD DE ATRACCIONES DEL PARQUE
        this.cantActRealizadas = 0;
        for (int i = 0; i < actividadRealizada.length; i++) {
            actividadRealizada[i] = false;
        }
    }

    public void run() {
        if(parqueX.parqueEstaAbierto()){
            // PROCESO INGRESAR A PARQUE UNA VEZ "ENTRADA" COMPRADA
            parqueX.ingresarAParque();
            System.out.println("Una persona ingreso al parque");
            this.utilizarMolinete();
            System.out.println("Una persona utilizo el molinete -- INGRESO -- ");
            // PERSONA YA DENTRO DEL PARQUE
            Random randX = new Random();
            int numActividad;
            while((cantActRealizadas < actividadRealizada.length) && parqueX.actividadesDisponibles()){
                numActividad = randX.nextInt(3);
                if(!actividadRealizada[numActividad])
                    this.intentarRealizarActividad(numActividad);
            }
            // LA PERSONA LUEGO DE TERMINAR LAS ACTIVIDADES VA A COMER
            this.comedor();
            
            // CAMBIA LOS TICKETS ANTES DE IRSE
            this.canjearPremio(); 

            // PROCESO SALIDA DEL PARQUE
            this.utilizarMolinete();
            System.out.println("Una persona utilizo el molinete -- SALIDA -- "); 
            parqueX.salirDeParque(); // LA SALIDA SE IMAGINA QUE ES POR 
        } else{
            System.out.println("El parque estaba cerrado, la persona se fue...");
        }


        // canjearPremio();
        
        // espectaculo();
        // montaniaRusa();
    }
    
    public void intentarRealizarActividad(int x){
        boolean fueRealizada = false;
        switch (x){
            case 0:
                fueRealizada = montaniaRusa();
                break;
            case 1:
                fueRealizada = realidadVirtual();
                break;
            case 2:
                fueRealizada = espectaculo();
                break;
        }
        actividadRealizada[x] = fueRealizada;
        if(fueRealizada)
            cantActRealizadas++;
    }

    public void utilizarMolinete(){
        parqueX.usarMolinete();
            try {
                Thread.sleep(1200);
                parqueX.dejarMolinete();
            } catch (Exception e) {
            }
    }

    public boolean montaniaRusa() {
        boolean realizarActividad = false;
        try {
            if(parqueX.montaniaRusaAbierto()){
            if (parqueX.ingresarAMontania(this)) {
                System.out.println("Llegó una persona " + nombre + " a la fila de espera");
                // Espera en su semáforo único hasta que la montaña rusa la llame
                parqueX.subirMontania(this);
                System.out.println("Una persona " + nombre + " se subió a la montania rusa");
                if (parqueX.esperaArranque(this)) {
                    // Espera en su semáforo único hasta que MontaniaRusa termine y la baje
                    this.aumentarPuntosAtraccion(parqueX.bajarMontania(this));
                    realizarActividad = true;
                    System.out.println("Una persona " + nombre + " persona bajó de la montania rusa");
                } else {
                    System.out.println("Una persona espero mucho tiempo y se cansó, se ha bajado de la montaña rusa");
                }
            } else {
                System.out.println("FILA DE MONTAÑA RUSA LLENA, Se va a otra atraccion...");
            }
        }
        } catch (Exception e) {
        }
        return realizarActividad;
    }

    public boolean realidadVirtual(){
        boolean fueRealizada = false;
        if (parqueX.realidadVirtualAbierto()) {
            System.out.println("Una persona llego a la atraccion de realidad Virtual");
            try {
                Thread.sleep(1200);
                if(parqueX.intentarPonerCasco()){
                    System.out.println("Una persona " + nombre + " se ha colocado un casco");
                    Thread.sleep(600);
                    if(parqueX.intentarPonerManoplas()){
                        Thread.sleep(600);
                        System.out.println("Una persona " + nombre + " se ha colocado las manoplas");
                        if(parqueX.intentarUsarBase()){
                            Thread.sleep(600);
                            System.out.println("Una persona " + nombre + " tomo una base");
                            parqueX.avisarAEncargado();
                            parqueX.ingresarAJugarRealidad();
                            System.out.println("Una persona con equipo completo ingreso a jugar...");
                            Thread.sleep(4000);
                            System.out.println("Una persona termino de jugar y dejo el equipo");
                            this.aumentarPuntosAtraccion(parqueX.terminarDeJugarRealidad());
                            fueRealizada = true;
                        }else{
                            System.out.println("Una persona se canso de esperar una base y se fue...");
                        }
                    } else{
                       System.out.println("Una persona se canso de esperar manoplas y se fue...");  
                    }
                } else{
                    System.out.println("Una persona se canso de esperar cascos y se fue...");
                }
            } catch (Exception e) {
            }
        }
        return fueRealizada;
    }

    public boolean espectaculo() {
        boolean fueRealizada = false;
        if (parqueX.teatroAbierto()) {
            try {
                if (parqueX.ingresarATeatro()) {
                    System.out.println("Una persona ingreso al teatro");
                    Thread.sleep(1000);
                    if (parqueX.ingresarAShow()) {
                        System.out.println("Una persona entro al show");
                        parqueX.salirShow();
                        fueRealizada = true;
                        System.out.println("Una persona salio del show");
                    }
                    parqueX.salirTeatro();
                }
            } catch (Exception e) {
            }
        }
        return fueRealizada;
    }

    public void canjearPremio(){
        parqueX.personaIngresaTienda();
        for (int i = 0; i < cantTickets.length; i++) {
            if (cantTickets[i] != 0) {
                cantTickets[i] = parqueX.canjearPremios(cantTickets[i]);
                System.out.println("Persona " + nombre + " canjeo sus puntos " + conversion(i));
            }
            try {
                Thread.sleep(500);
            } catch (Exception e) {
            }
        }
        parqueX.personaSaleTienda();
    }

    public void comedor(){
        try {
            if(parqueX.ingresarComedor()){
                System.out.println("Una persona entro al comedor");
                Thread.sleep(500);
                int mesa = parqueX.sentarseEnMesa();
                System.out.println("UNA PERSONA SE SENTO EN LA MESA " + mesa);
                if(parqueX.iniciarAComer(mesa) == -1){
                    System.out.println("Una persona se canso de esperar en la mesa " + mesa + " y se fue");
                }else{
                    Thread.sleep(5000);
                    System.out.println("Una persona termino de comer en la mesa " + mesa);
                }
                parqueX.liberarMesa(mesa);
                Thread.sleep(500);
                parqueX.salirDeComedor();
            } else{
                System.out.println(" me fui lol");
            }
        } catch (Exception e) {
             System.out.println("ERROR C");
             System.out.println(e);
        }
    }

    public String conversion(int x){
        String atraccion = "";
        switch (x) {
            case 0:
                atraccion = "MR";
                break;
            case 1:
                atraccion = "RV";
                break;
            default:
                System.out.println("VALOR ATRACCION INVALIDO");
                break;
        }
        return atraccion;
    }

    public void aumentarPuntosAtraccion(int atraccion) {
        this.cantTickets[atraccion]++;
    }
}
