package ObjetoActivo;

import RecursoCompartido.CasaPremios;

public class EmpleadoPremios extends Thread{
    CasaPremios casaPremiosX;
    int puntosTotales;

    public EmpleadoPremios(CasaPremios casaPremiosX){
        this.casaPremiosX = casaPremiosX;
        this.puntosTotales = 0;
    }
    
    public void run(){
        int puntosCliente = 0; 
        while (true) {
            System.out.println("--- VENDEDOR ESPERA CLIENTE ---");
            puntosCliente = casaPremiosX.canjearPremio(0);
            entregarTipoPremio(puntosCliente);
            // puntosTotales += puntosCliente;
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            // System.out.println("PUNTOS ACTUALES VENDEDOR: "  + puntosTotales);
        }
    }

    private void entregarTipoPremio(int valor){
        if(valor < 2){
            System.out.println("--- PREMIO PEQUEÑO ENTREGADO ---");
        } else if(valor < 4){
            System.out.println(" --- PREMIO MEDIANO ENTREGADO ---");
        } else{
            System.out.println("--- PREMIO GRANDE ENTREGADO ---");
        }
    }
}
