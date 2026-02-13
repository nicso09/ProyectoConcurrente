public class Main {
    public static void main(String[] args) {
        int capacidadMontania = 5;

        Parque parqueX = new Parque(0, capacidadMontania);
        MontaniaRusa montaniaRusaX = new MontaniaRusa(capacidadMontania, parqueX);
        montaniaRusaX.start();

        for (int i = 0; i < 20; i++) {
            Persona p = new Persona(parqueX, "Persona " + i);
            p.start();
            try {
                Thread.sleep(1200);
            } catch (Exception e) {
            }
        }
    }
}
