COMO SIMULAR DE MANERA CORRECTA EL PARQUE DE ATRACCIONES 2026 - NICOLÁS AGUIRRE:

SEGUIR LAS SIGUIENTES CONDICIONES EN ORDEN DE IMPLEMENTACION PARA UN PROGRAMAM MAIN DONDE PODRA EJECUTAR EL PROGRAMA (DE TODAS MANERAS HAY
UN PROGRAMA MAIN DE EJEMPLO PARA EL USUARIO)

CONDICIONES:
1: Crear Instancias de las clases correspondientes a las atracciones // (VER CONDICIONES DE CADA ATRACCION PARA SU CORRECTO FUNCIONAMIENTO!!!)
2: Crear Instancias de las clases correspondientes a los lugares de shopping (Comedor - Casa premios)
3: Crear instancia del parque // IMPORTANTE!!! EL PARQUE ARRANCA EN SU CONFIGURACION COMO CERRADO, EN CASO DE QUERER CAMBIAR ESTO REVISAR ATRIBUTOS DE CLASES CORRESPONDIENTES.
4: Crear instancia de clase Dueño y arrancar dicho hilo (Dueño es un hilo que controla el transcurso del tiempo y la apertura y cierre del parque - Equivale a un clock) 
5: Crear instancias de clases de los operarios de cada atracción y arrancar dichos hilos (OperadorMontania, EmpleadoPremios, encargadoVisiores, asistentes)
	EN CASO DE NO CREAR DICHOS OPERARIOS, ES MUY POSIBLE TENER ERRORES DURANTE LA EJECUCIÓN DEL PROGRAMA - YA SEA POR DEADLOCK, INANICION O LIVELOCKM, U OTROS.
6: Crear instancias de la clase persona y arrancar dichos hilos. 
	Aclaración: Estos hilos a diferencia de los demas (Operadores de atracciones), tendran un ciclo de vida que consistirá en llegar al parque, intentar entrar, disfrutar el parque y salir.
	En caso de no poder entrar, finalizarán omitiendo todo lo que suceda dentro del parque. Es importante que para simularlo de forma correcta, implementar un sistema de generación infinito
	(O muy largo) de personas. Sino llegará un punto donde nadie más ingrese al parque porque no habrá persona que ingresar.