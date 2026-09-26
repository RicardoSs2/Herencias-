public class Carro extends Vehiculo {
    private int pasajeros;
    private boolean automatico;

    public Carro(String placa, String marca, String modelo, double tarifaDiaria,
                     int pasajeros, boolean automatico) {
        super(placa, marca, modelo, tarifaDiaria);

        if (pasajeros <= 0) {
            throw new IllegalArgumentException("La cantidad de pasajeros debe ser mayor que cero.");
        }

        this.pasajeros = pasajeros;
        this.automatico = automatico;
    }

    public int getPasajeros() {
        return pasajeros;
    }

    public boolean isAutomatico() {
        return automatico;
    }

    @Override
    public double calcularCosto(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Los días deben ser enteros positivos.");
        }

        double total = getTarifaDiaria() * dias;

        if (automatico) {
            total += 50.0 * dias;
        }

        return total;
    }

    @Override
    public String getCategoria() {
        return "Carro";
    }

    @Override
    public String getDetalles() {
        return "Pasajeros: " + pasajeros +
               " | Transmisión: " + (automatico ? "Automática" : "Manual");
    }
}
