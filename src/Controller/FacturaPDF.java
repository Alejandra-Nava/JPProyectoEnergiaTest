package Controller;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import Model.Cliente;
import Model.Registrador;

import java.io.FileOutputStream;
import java.util.Date;

public class FacturaPDF {

    public static void generarFactura(Cliente cliente, Registrador registrador, double total) {
        if (cliente == null || registrador == null) {
            System.out.println("Error: cliente o registrador es null.");
            return;
        }

        String archivoPDF = "factura_cliente_" + cliente.getId() + "_" + registrador.getId() + ".pdf";

        Document doc = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(archivoPDF));
            doc.open();

            // Título
            Font tituloFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("FACTURA DE SERVICIO DE ENERGÍA", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);

            // Espaciado
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Fecha de emisión: " + new Date()));
            doc.add(new Paragraph("Cliente: " + cliente.getId()));
            doc.add(new Paragraph("Dirección: " + cliente.getDireccion()));
            doc.add(new Paragraph("Número del medidor: " + registrador.getId()));
            doc.add(new Paragraph("Ciudad: " + registrador.getCiudad()));
            doc.add(new Paragraph(" "));

            // Tabla de consumo
            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidthPercentage(100);
            tabla.addCell("Concepto");
            tabla.addCell("Consumo");
            tabla.addCell("Valor");

            double[][] matriz = registrador.getConsumo();
            double kWh = 0;
            for (double[] dia : matriz) {
                for (double valor : dia) {
                    kWh += valor;
                }
            }

            tabla.addCell("Energía consumida (kWh)");
            tabla.addCell(String.format("%.2f", kWh));
            tabla.addCell("$" + String.format("%.2f", total));

            // Cargo fijo opcional (puedes ajustar el valor fijo)
            double cargoFijo = 5000.0;
            tabla.addCell("Cargo fijo");
            tabla.addCell("-");
            tabla.addCell("$" + String.format("%.2f", cargoFijo));

            // Total final
            tabla.addCell("TOTAL A PAGAR");
            tabla.addCell("-");
            tabla.addCell("$" + String.format("%.2f", total + cargoFijo));

            doc.add(tabla);

            doc.close();
            System.out.println("Factura generada correctamente: " + archivoPDF);

        } catch (Exception e) {
            System.out.println("Error al generar factura PDF: " + e.getMessage());
        }
    }
}
