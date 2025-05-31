package Model;

public class Consumo {

    public static double calcularValorTotal(double[][] matriz) {
        double total = 0;
        for (int dia = 0; dia < matriz.length; dia++) {
            for (int hora = 0; hora < matriz[dia].length; hora++) {
                double valor = matriz[dia][hora];
                if (hora >= 0 && hora <= 6 && valor >= 100 && valor <= 300) {
                    total += valor * 200;
                } else if (hora >= 7 && hora <= 17 && valor > 300 && valor <= 600) {
                    total += valor * 300;
                } else if (hora >= 18 && hora <= 23 && valor > 600 && valor < 1000) {
                    total += valor * 500;
                }
            }
        }
        return total;
    }

    public static double consumoMinimo(double[][] matriz) {
        double min = Double.MAX_VALUE;
        for (double[] dia : matriz) {
            for (double valor : dia) {
                if (valor < min) min = valor;
            }
        }
        return min;
    }

    public static double consumoMaximo(double[][] matriz) {
        double max = 0;
        for (double[] dia : matriz) {
            for (double valor : dia) {
                if (valor > max) max = valor;
            }
        }
        return max;
    }

    public static double[] consumoPorFranjas(double[][] matriz) {
        double[] franjas = new double[3];
        for (int dia = 0; dia < matriz.length; dia++) {
            for (int hora = 0; hora < matriz[dia].length; hora++) {
                double valor = matriz[dia][hora];
                if (hora >= 0 && hora <= 6) {
                    franjas[0] += valor; // Franja 1: 00h–06h
                } else if (hora >= 7 && hora <= 17) {
                    franjas[1] += valor; // Franja 2: 07h–17h
                } else {
                    franjas[2] += valor; // Franja 3: 18h–23h
                }
            }
        }
        return franjas;
    }

    public static double[] consumoPorDia(double[][] matriz) {
        double[] dias = new double[31];
        for (int d = 0; d < matriz.length && d < 31; d++) {
            double suma = 0;
            for (int h = 0; h < matriz[d].length; h++) {
                suma += matriz[d][h];
            }
            dias[d] = suma;
        }
        return dias;
    }

    // ✅ NUEVO: útil para calcular el valor de una hora específica
    public static double calcularValorHora(int hora, double consumo) {
        if (hora >= 0 && hora <= 6 && consumo >= 100 && consumo <= 300) {
            return consumo * 200;
        } else if (hora >= 7 && hora <= 17 && consumo > 300 && consumo <= 600) {
            return consumo * 300;
        } else if (hora >= 18 && hora <= 23 && consumo > 600 && consumo < 1000) {
            return consumo * 500;
        }
        return 0;
    }
}
