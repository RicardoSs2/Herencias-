public class CamionetaCarga extends Vehiculo {
    private double capacidadToneladas;

    public CamionetaCarga(String placa, String marca, String modelo, double tarifaDiaria,
                          double capacidadToneladas) {
        super(placa, marca, modelo, tarifaDiaria);

        if (capacidadToneladas <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero.");
        }

        this.capacidadToneladas = capacidadToneladas;
    }

    public double getCapacidadToneladas() {
        return capacidadToneladas;
    }

    @Override
    public double calcularCosto(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Los días deben ser enteros positivos.");
        }

        double tarifaBase = getTarifaDiaria() * dias;
        double recargo = 100.0 * capacidadToneladas * dias;

        return tarifaBase + recargo;
    }

    @Override
    public String getCategoria() {
        return "Camioneta de carga";
    }

    @Override
    public String getDetalles() {
        return String.format("Capacidad máxima: %.2f toneladas", capacidadToneladas);
    }
}
