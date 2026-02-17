package ObjetoActivo;

import RecursoCompartido.Parque;


public class Persona extends Thread {
    Parque parqueX;
    int[] cantTickets; // MR POS [0] ; RV POS [1]
    String nombre;

    public Persona(Parque parqueX, String nombre) {
        this.parqueX = parqueX;
        this.cantTickets = new int[2];
        this.cantTickets[0] = (int)(Math.random() * 9) + 1;
        this.cantTickets[1] = (int)(Math.random() * 9) + 1;
        this.nombre = nombre;
    }

    public void run() {
        if(parqueX.parqueEstaAbierto()){
            parqueX.ingresarAParque();
            System.out.println("Una persona ingreso al parque");
            this.utilizarMolinete();
            System.out.println("Una persona utilizo el molinete -- INGRESO -- ");
            // PERSONA YA DENTRO DEL PARQUE
            try {
                Thread.sleep((int)(Math.random() * 9000) + 1000);
            } catch (Exception e) {
            }
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

    public void utilizarMolinete(){
        parqueX.usarMolinete();
            try {
                Thread.sleep(2000);
                parqueX.dejarMolinete();
            } catch (Exception e) {
            }
    }

    public void montaniaRusa() {
        try {
            if (parqueX.ingresarAMontania(this)) {
                System.out.println("Llegó una persona " + nombre + " a la fila de espera");
                // Espera en su semáforo único hasta que la montaña rusa la llame
                parqueX.subirMontania(this);
                System.out.println("Una persona " + nombre + " se subió a la montania rusa");
                if (parqueX.esperaArranque(this)) {
                    // Espera en su semáforo único hasta que MontaniaRusa termine y la baje
                    int puntos = parqueX.bajarMontania(this);
                    System.out.println("Una persona " + nombre + " persona bajó de la montania rusa");
                    this.aumentarPuntosAtraccion(puntos);
                } else {
                    System.out.println("Una persona espero mucho tiempo y se cansó, se ha bajado de la montaña rusa");
                }
            } else {
                System.out.println("FILA LLENA, Se va a otra atraccion");
            }
        } catch (Exception e) {
        }
    }

    public void espectaculo() {
        try {
            if (parqueX.ingresarATeatro()) {
                System.out.println("Una persona ingreso al teatro");
                Thread.sleep(1000);
                if (parqueX.ingresarAShow()) {
                    System.out.println("Una persona entro al show");
                    parqueX.salirShow();
                    System.out.println("Una persona salio del show");
                }
                parqueX.salirTeatro();
            }
        } catch (Exception e) {
        }
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
