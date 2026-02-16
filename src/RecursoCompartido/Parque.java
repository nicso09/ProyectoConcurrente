package RecursoCompartido;

import ObjetoActivo.Persona;

public class Parque {
    int cantMolinetes;
    // CONTROL MONTANIA RUSA
    MontaniaRusa montaniaRusaX;
    // FIN CONTROL MONTANIA RUSA

    // CONTROL TEATRO
    Teatro teatroX;

    //  CONTROL CASA PREMIOS
    CasaPremios casaPremiosX;


    public Parque(int cantMolinetes, int asientosMontania, Teatro teatroX, MontaniaRusa montaniaRusaX, CasaPremios casaPremiosX) {
        this.cantMolinetes = cantMolinetes;
        // this.asientosMontaniaRusa = new ArrayBlockingQueue<>(asientosMontania);
        // this.semaforoSubida = new HashMap<>();
        // this.semaforoBajada = new HashMap<>();
        this.teatroX = teatroX;
        this.montaniaRusaX = montaniaRusaX;
        this.casaPremiosX = casaPremiosX;
    }

    public boolean ingresarAMontania(Persona personaX) {
        return montaniaRusaX.entrar(personaX);
    }

    public void subirMontania(Persona personaX) {
        montaniaRusaX.esperarMontania(personaX);

    }

    public boolean esperaArranque(Persona personaX){
        return montaniaRusaX.esperarInicio(personaX);
    }

    public int bajarMontania(Persona personaX) {
        try {
            return montaniaRusaX.bajarMontania(personaX);
        } catch (Exception e) {
            System.out.println("ERROR AL BAJAR PASAJERO");
            return 0;
        }
    }

    public boolean ingresarATeatro() {
        return teatroX.entrar();
    }

    public boolean ingresarAShow() {
        return teatroX.personaIngresaAShow();
    }

    public void salirShow() {
         teatroX.personaSaleShow();
    }

    public void salirTeatro(){
        teatroX.salir();
    }

    public int canjearPremios(int x){
        return casaPremiosX.canjearPremio(x);
    }

    public void personaIngresaTienda(){
        casaPremiosX.clienteBuscaPremio();
    }

    public void personaSaleTienda(){
        casaPremiosX.clienteSaleDeTienda();
    }

}
