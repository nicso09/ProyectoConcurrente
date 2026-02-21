package RecursoCompartido;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Teatro {
    private ReentrantLock keyLock;
    private Condition asistentes;
    private Condition personasAfuera;
    private Condition personasDentro;
    private Condition grupo;
    private int espaciosTeatroX;
    private int grupoPersonas;
    private int cantAsistentes;
    private boolean grupoListo;
    private boolean entradaHabilitada;
    private boolean showIniciado;
    private boolean estadoActividad;

    // CONSTRUCTOR
    public Teatro() {
        this.keyLock = new ReentrantLock();
        this.asistentes = keyLock.newCondition();
        this.personasAfuera = keyLock.newCondition();
        this.grupo = keyLock.newCondition();
        this.personasDentro = keyLock.newCondition();
        this.espaciosTeatroX = 20;
        this.cantAsistentes = 0; // CANTIDAD DE ASISTENTES EN EL ESCENARIO
        this.grupoPersonas = 0;
        this.grupoListo = false; // GRUPO LISTO EQUIVALE A QUE HAY UN GRUPO DE 5 PERSONAS PARA INGRESAR
        this.entradaHabilitada = true;
        this.showIniciado = false;
        this.estadoActividad = false; // TRUE SIGNIFICA ABIERTA - FALSE SIGNFICA CERRADA
    }

    // METODOS UTILIZADOS POR LA CLASE "Duenio"
    public void abrirActividad() {
        try {
            keyLock.lock();
            this.estadoActividad = true;
            keyLock.unlock();
        } catch (Exception e) {
        }
    }

    public void cerrarActividad() {
        keyLock.lock();
        this.estadoActividad = false;
        keyLock.unlock();
    }

    // METODOS UTILIZADOS POR LA CLASE "Persona"
    public boolean estaAbierto() {
        boolean actividadAbierta = false;
        keyLock.lock();
        actividadAbierta = this.estadoActividad;
        keyLock.unlock();
        return actividadAbierta;
    }

    public boolean entrarTeatro() {
        boolean pudeIngresar = true;
        keyLock.lock();
        while (espaciosTeatroX <= 0 || grupoListo || !entradaHabilitada) {
            try {
                pudeIngresar = personasAfuera.await(40, TimeUnit.SECONDS);
            } catch (Exception e) {
            }
        }
        if (pudeIngresar) {
            grupoPersonas++;
            while (!grupoListo) {
                if (grupoPersonas < 5) {
                    try {
                        grupo.await();
                    } catch (Exception e) {
                    }
                } else {
                    grupoListo = true;
                    grupo.signalAll();
                }
            }
            espaciosTeatroX--;
            grupoPersonas--;
            if (espaciosTeatroX > 0) {
                if (grupoPersonas <= 0) {
                    this.grupoListo = false;
                    personasAfuera.signalAll();
                }
            } else {
                this.grupoListo = false;
                this.entradaHabilitada = false;
                this.showIniciado = true;
                asistentes.signalAll();
            }
        }
        keyLock.unlock();
        return pudeIngresar;
    }

    public boolean salirTeatro() {
        boolean terminoShow = true;
        keyLock.lock();
        try {
            terminoShow = personasDentro.await(40, TimeUnit.SECONDS);
        } catch (Exception e) {
        }
        espaciosTeatroX++;
        if (espaciosTeatroX >= 20) {
            this.entradaHabilitada = true;
            personasAfuera.signalAll();
        }
        keyLock.unlock();
        return terminoShow;
    }

    // METODOS UTILIZADOS POR LA CLASE "Asistentes"
    public void asistenteEntraShow() {
        keyLock.lock();
        while (!showIniciado) {
            try {
                asistentes.await();
            } catch (Exception e) {
            }
        }
        if (cantAsistentes == 0)
            System.out.println("EL ESPECTACULO ESTÁ LLENO, EL SHOW ESTA LISTO PARA EMPEZAR");
        cantAsistentes++;
        keyLock.unlock();
    }

    public void finalizarShow() {
        keyLock.lock();
        if (showIniciado) {
            showIniciado = false;
            System.out.println("EL SHOW HA FINALIZADO...");
        }
        cantAsistentes--;
        if (cantAsistentes <= 0) {
            this.personasDentro.signalAll();
        }
        keyLock.unlock();
    }
}
