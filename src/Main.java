import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final RentaMovil sistema = new RentaMovil();

    public static void main(String[] args) {
        cargarDatosIniciales();

        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    registrarVehiculo();
                    break;
                case 2:
                    sistema.mostrarFlota();
                    break;
                case 3:
                    cotizar();
                    break;
                case 4:
                    alquilar();
                    break;
                case 5:
                    devolver();
                    break;
                case 6:
                    sistema.mostrarReporte();
                    break;
                case 0:
                    System.out.println("Programa finalizado.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n========== RentaMovil ==========");
        System.out.println("1. Registrar vehículo");
        System.out.println("2. Consultar flota");
        System.out.println("3. Cotizar alquiler");
        System.out.println("4. Confirmar alquiler");
        System.out.println("5. Registrar devolución");
        System.out.println("6. Reporte general");
        System.out.println("0. Salir");
    }

    private static void cargarDatosIniciales() {
        sistema.registrarVehiculo(new Carro(
            "A001", "Toyota", "Corolla", 250, 5, true
        ));

        sistema.registrarVehiculo(new Carro(
            "A002", "Honda", "Civic", 220, 5, false
        ));

        sistema.registrarVehiculo(new Moto(
            "M001", "Yamaha", "FZ25", 120, 249
        ));

        sistema.registrarVehiculo(new Moto(
            "M002", "Kawasaki", "Ninja", 180, 400
        ));

        sistema.registrarVehiculo(new CamionetaCarga(
            "C001", "Toyota", "Hilux", 200, 1.5
        ));

        sistema.registrarVehiculo(new CamionetaCarga(
            "C002", "Ford", "Ranger", 250, 2.0
        ));
    }

    private static void registrarVehiculo() {
        System.out.println("\nTipo de vehículo:");
        System.out.println("1. Carro");
        System.out.println("2. Moto");
        System.out.println("3. Camioneta de carga");

        int tipo = leerEntero("Seleccione: ");

        String placa = leerTextoNoVacio("Placa: ");

        if (sistema.buscarVehiculo(placa) != null) {
            System.out.println("Ya existe un vehículo con esa placa.");
            return;
        }

        String marca = leerTextoNoVacio("Marca: ");
        String modelo = leerTextoNoVacio("Modelo: ");
        double tarifa = leerDoublePositivo("Tarifa diaria: ");

        try {
            Vehiculo nuevo;

            switch (tipo) {
                case 1:
                    int pasajeros = leerEnteroPositivo("Cantidad de pasajeros: ");
                    boolean automatico = leerSiNo("¿Es automático? (s/n): ");
                    nuevo = new Carro(
                        placa, marca, modelo, tarifa, pasajeros, automatico
                    );
                    break;

                case 2:
                    int cilindraje = leerEnteroPositivo("Cilindraje en cc: ");
                    nuevo = new Moto(
                        placa, marca, modelo, tarifa, cilindraje
                    );
                    break;

                case 3:
                    double capacidad = leerDoublePositivo(
                        "Capacidad máxima en toneladas: "
                    );
                    nuevo = new CamionetaCarga(
                        placa, marca, modelo, tarifa, capacidad
                    );
                    break;

                default:
                    System.out.println("Tipo inválido.");
                    return;
            }

            if (sistema.registrarVehiculo(nuevo)) {
                System.out.println("Vehículo registrado correctamente.");
            } else {
                System.out.println("No se pudo registrar el vehículo.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void cotizar() {
        String placa = leerTextoNoVacio("Placa: ");
        int dias = leerEnteroPositivo("Cantidad de días: ");
        sistema.cotizar(placa, dias);
    }

    private static void alquilar() {
        String placa = leerTextoNoVacio("Placa: ");
        Vehiculo vehiculo = sistema.buscarVehiculo(placa);

        if (vehiculo == null) {
            System.out.println("No existe un vehículo con esa placa.");
            return;
        }

        if (!vehiculo.isDisponible()) {
            System.out.println("El vehículo ya está alquilado.");
            return;
        }

        int dias = leerEnteroPositivo("Cantidad de días: ");
        double total = sistema.obtenerCosto(placa, dias);

        System.out.println("\n--- RESUMEN DEL ALQUILER ---");
        System.out.println(vehiculo);
        System.out.printf("Total a pagar: Q%.2f%n", total);

        boolean confirmar = leerSiNo("¿Desea confirmar el alquiler? (s/n): ");

        if (!confirmar) {
            System.out.println("Operación cancelada. No se realizaron cambios.");
            return;
        }

        if (sistema.confirmarAlquiler(placa, dias)) {
            System.out.println("Alquiler confirmado correctamente.");
        } else {
            System.out.println("No fue posible confirmar el alquiler.");
        }
    }

    private static void devolver() {
        String placa = leerTextoNoVacio("Placa: ");

        Vehiculo vehiculo = sistema.buscarVehiculo(placa);

        if (vehiculo == null) {
            System.out.println("No existe un vehículo con esa placa.");
            return;
        }

        if (vehiculo.isDisponible()) {
            System.out.println("El vehículo ya se encuentra disponible.");
            return;
        }

        if (sistema.devolverVehiculo(placa)) {
            System.out.println("Devolución registrada correctamente.");
        } else {
            System.out.println("No se pudo registrar la devolución.");
        }
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número entero.");
            }
        }
    }

    private static int leerEnteroPositivo(String mensaje) {
        while (true) {
            int valor = leerEntero(mensaje);

            if (valor > 0) {
                return valor;
            }

            System.out.println("El valor debe ser mayor que cero.");
        }
    }

    private static double leerDoublePositivo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();

            try {
                double valor = Double.parseDouble(entrada);

                if (valor > 0) {
                    return valor;
                }

                System.out.println("El valor debe ser mayor que cero.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
            }
        }
    }

    private static String leerTextoNoVacio(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("Este campo no puede quedar vacío.");
        }
    }

    private static boolean leerSiNo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String respuesta = scanner.nextLine().trim().toLowerCase();

            if (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) {
                return true;
            }

            if (respuesta.equals("n") || respuesta.equals("no")) {
                return false;
            }

            System.out.println("Responda s o n.");
        }
    }
}
