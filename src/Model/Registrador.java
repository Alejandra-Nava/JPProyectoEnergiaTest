package Model;

import java.util.ArrayList;
import java.util.List;

public class Registrador {
    private String id;
    private String direccion;
    private String ciudad;
    private double[][] consumo; // 31 días, 24 horas

    public Registrador(String id, String direccion, String ciudad) {
        this.id = id;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.consumo = new double[31][24];
    }

    public void registrarConsumo(int dia, int hora, double valor) {
        if (dia >= 0 && dia < 31 && hora >= 0 && hora < 24) {
            consumo[dia][hora] = valor;
        }
    }

    public double[][] getConsumo() {
        return consumo;
    }

    // ✅ NUEVO: obtener consumo específico
    public double getConsumo(int dia, int hora) {
        if (dia >= 0 && dia < 31 && hora >= 0 && hora < 24) {
            return consumo[dia][hora];
        }
        return -1; // valor inválido
    }

    // ✅ NUEVO: obtener consumo total del registrador
    public double getConsumoTotal() {
        double total = 0;
        for (int d = 0; d < 31; d++) {
            for (int h = 0; h < 24; h++) {
                total += consumo[d][h];
            }
        }
        return total;
    }

    public String getId() {
        return id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    private List<Consumo> consumos = new ArrayList<>();

public void agregarConsumo(Consumo consumo) {
    consumos.add(consumo);
}

public List<Consumo> getConsumos() {
    return consumos;
}
}
