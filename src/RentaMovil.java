import java.util.ArrayList;

public class RentaMovil {
    private ArrayList<Vehiculo> vehiculos;
    private double ingresosAcumulados;

    public RentaMovil() {
        vehiculos = new ArrayList<>();
        ingresosAcumulados = 0.0;
    }

    public boolean registrarVehiculo(Vehiculo vehiculo) {
        if (buscarVehiculo(vehiculo.getPlaca()) != null) {
            return false;
        }

        vehiculos.add(vehiculo);
        return true;
    }

    public Vehiculo buscarVehiculo(String placa) {
        if (placa == null) {
            return null;
        }

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getPlaca().equalsIgnoreCase(placa.trim())) {
                return vehiculo;
            }
        }

        return null;
    }

    public void mostrarFlota() {
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
            return;
        }

        for (Vehiculo vehiculo : vehiculos) {
            System.out.println(vehiculo);
        }
    }

    public void cotizar(String placa, int dias) {
        Vehiculo vehiculo = buscarVehiculo(placa);

        if (vehiculo == null) {
            System.out.println("No existe un vehículo con esa placa.");
            return;
        }

        if (dias <= 0) {
            System.out.println("Los días deben ser enteros positivos.");
            return;
        }

        double total = vehiculo.calcularCosto(dias);

        System.out.println("\n--- COTIZACIÓN ---");
        System.out.println(vehiculo);
        System.out.printf("Días: %d%n", dias);
        System.out.printf("Costo total: Q%.2f%n", total);
        System.out.println("Esta cotización no modifica los ingresos ni la disponibilidad.");
    }

    public double obtenerCosto(String placa, int dias) {
        Vehiculo vehiculo = buscarVehiculo(placa);

        if (vehiculo == null || dias <= 0) {
            return -1;
        }

        return vehiculo.calcularCosto(dias);
    }

    public boolean confirmarAlquiler(String placa, int dias) {
        Vehiculo vehiculo = buscarVehiculo(placa);

        if (vehiculo == null || dias <= 0 || !vehiculo.isDisponible()) {
            return false;
        }

        double total = vehiculo.calcularCosto(dias);
        vehiculo.alquilar();
        ingresosAcumulados += total;
        return true;
    }

    public boolean devolverVehiculo(String placa) {
        Vehiculo vehiculo = buscarVehiculo(placa);

        if (vehiculo == null || vehiculo.isDisponible()) {
            return false;
        }

        vehiculo.devolver();
        return true;
    }

    public void mostrarReporte() {
        int total = vehiculos.size();
        int disponibles = 0;

        int autosAlquilados = 0;
        int motosAlquiladas = 0;
        int camionetasAlquiladas = 0;

        int autosDisponibles = 0;
        int motosDisponibles = 0;
        int camionetasDisponibles = 0;

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.isDisponible()) {
                disponibles++;

                if (vehiculo instanceof Carro) {
                    autosDisponibles++;
                } else if (vehiculo instanceof Moto) {
                    motosDisponibles++;
                } else if (vehiculo instanceof CamionetaCarga) {
                    camionetasDisponibles++;
                }
            } else {
                if (vehiculo instanceof Carro) {
                    autosAlquilados++;
                } else if (vehiculo instanceof Moto) {
                    motosAlquiladas++;
                } else if (vehiculo instanceof CamionetaCarga) {
                    camionetasAlquiladas++;
                }
            }
        }

        System.out.println("\n--- REPORTE GENERAL ---");
        System.out.println("Vehículos registrados: " + total);
        System.out.println("Vehículos disponibles: " + disponibles);
        System.out.println("Vehículos alquilados: " + (total - disponibles));
        System.out.println();
        System.out.println("Carroes disponibles: " + autosDisponibles);
        System.out.println("Carroes alquilados: " + autosAlquilados);
        System.out.println("Motos disponibles: " + motosDisponibles);
        System.out.println("Motos alquiladas: " + motosAlquiladas);
        System.out.println("Camionetas disponibles: " + camionetasDisponibles);
        System.out.println("Camionetas alquiladas: " + camionetasAlquiladas);
        System.out.printf("Ingresos acumulados: Q%.2f%n", ingresosAcumulados);
    }

    public double getIngresosAcumulados() {
        return ingresosAcumulados;
    }
}
