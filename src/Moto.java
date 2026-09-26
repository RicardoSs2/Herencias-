public class Moto extends Vehiculo {
    private int cilindraje;

    public Moto(String placa, String marca, String modelo, double tarifaDiaria,
                       int cilindraje) {
        super(placa, marca, modelo, tarifaDiaria);

        if (cilindraje <= 0) {
            throw new IllegalArgumentException("El cilindraje debe ser mayor que cero.");
        }

        this.cilindraje = cilindraje;
    }

    public int getCilindraje() {
        return cilindraje;
    }

    @Override
    public double calcularCosto(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Los días deben ser enteros positivos.");
        }

        double total = getTarifaDiaria() * dias;

        if (cilindraje > 250) {
            total += 75.0;
        }

        return total;
    }

    @Override
    public String getCategoria() {
        return "Moto";
    }

    @Override
    public String getDetalles() {
        return "Cilindraje: " + cilindraje + " cc";
    }
}
