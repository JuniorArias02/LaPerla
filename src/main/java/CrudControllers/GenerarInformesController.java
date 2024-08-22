package CrudControllers;

import com.itextpdf.text.*;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;


import java.awt.*;
import java.io.File;
import java.io.IOException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;;
import com.itextpdf.text.pdf.PdfPTable;


import java.io.FileOutputStream;


import Dao.ClienteFrecuente;
import Dao.ProductoMasVendido;
import Dao.VentasDao;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Image;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class GenerarInformesController {

    VentasDao ventasDao = new VentasDao();
    @FXML
    private DatePicker FechaFin;
    @FXML
    private DatePicker FechaInicio;


    @javafx.fxml.FXML
    public void generarIInforme(ActionEvent actionEvent) {
        LocalDate fechaInicio = FechaInicio.getValue();
        LocalDate fechaFin = FechaFin.getValue();

        if (fechaInicio != null && fechaFin != null && !fechaInicio.isAfter(fechaFin)) {
            try {
                // Obtener los datos filtrados por rango de fechas
                List<ProductoMasVendido> productosMasVendidos = ventasDao.obtenerProductosPorRangoFechas(fechaInicio, fechaFin);
                List<ClienteFrecuente> clientesFrecuentes = ventasDao.obtenerClientesPorRangoFechas(fechaInicio, fechaFin);
                double gananciasTotales = ventasDao.obtenerGananciasPorRangoFechas(fechaInicio, fechaFin);

                // Crear el gráfico
                DefaultPieDataset dataset = new DefaultPieDataset();
                for (ProductoMasVendido producto : productosMasVendidos) {
                    dataset.setValue(producto.getNombre(), producto.getTotalCantidad());
                }
                JFreeChart chart = ChartFactory.createPieChart(
                        "Productos Más Vendidos - " + fechaInicio + " a " + fechaFin, // Título
                        dataset,                                                       // Datos
                        true,                                                          // Leyenda
                        true,                                                          // Tooltips
                        false                                                          // URLs
                );

                // Guardar el gráfico como una imagen PNG con tamaño ajustado
                File chartFile = new File("chart.png");
                ChartUtils.saveChartAsPNG(chartFile, chart, 400, 300); // Cambia el tamaño aquí (ancho x alto)

                // Crear el informe en PDF
                String filePath = "reportes/informe_global_" + fechaInicio + "_a_" + fechaFin + ".pdf";
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();

                // Encabezado con imagen en la esquina superior derecha
                PdfPTable headerTable = new PdfPTable(2);
                headerTable.setWidthPercentage(100);

                // Celda para el texto "LA PERLA"
                PdfPCell textCell = new PdfPCell();
                textCell.setBorder(PdfPCell.NO_BORDER);
                PdfPCell imageCell = new PdfPCell();
                imageCell.setBorder(PdfPCell.NO_BORDER);

                BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED);
                Font font = new Font(baseFont, 36, Font.BOLD, BaseColor.BLACK);

                Paragraph paragraph = new Paragraph("LA PERLA", font);
                paragraph.setAlignment(Paragraph.ALIGN_LEFT);
                textCell.addElement(paragraph);

                // Añadir la imagen
                Image image = Image.getInstance("src/main/resources/img/perla.png"); // Ruta de la imagen
                image.scaleToFit(100, 50); // Escala la imagen al tamaño deseado
                image.setAlignment(Image.ALIGN_RIGHT);
                imageCell.addElement(image);

                headerTable.addCell(textCell);
                headerTable.addCell(imageCell);
                document.add(headerTable);

                // Informe de productos más vendidos
                Paragraph productosTitle = new Paragraph("Productos Más Vendidos - " + fechaInicio + " a " + fechaFin, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
                productosTitle.setSpacingBefore(20);  // Espacio antes del título
                productosTitle.setSpacingAfter(10);   // Espacio después del título
                document.add(productosTitle);

                PdfPTable productosTable = new PdfPTable(3);
                productosTable.setWidthPercentage(100);

                // Encabezado de la tabla de productos
                PdfPCell cell = new PdfPCell(new Phrase("Código", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                productosTable.addCell(cell);
                cell.setPhrase(new Phrase("Nombre", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                productosTable.addCell(cell);
                cell.setPhrase(new Phrase("Cantidad Vendida", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                productosTable.addCell(cell);

                // Datos de la tabla de productos
                for (ProductoMasVendido producto : productosMasVendidos) {
                    productosTable.addCell(String.valueOf(producto.getProductoId()));
                    productosTable.addCell(producto.getNombre());
                    productosTable.addCell(String.valueOf(producto.getTotalCantidad()));
                }
                document.add(productosTable);

                // Añadir el gráfico al PDF con tamaño ajustado
                Image chartImage = Image.getInstance(chartFile.getAbsolutePath());
                chartImage.scaleToFit(400, 300); // Ajusta el tamaño aquí (ancho x alto)
                chartImage.setAlignment(Image.ALIGN_CENTER);
                document.add(chartImage);

                // Espacio antes de la tabla de clientes
                document.add(new Paragraph("\n"));

                // Informe de clientes más frecuentes
                Paragraph clientesTitle = new Paragraph("Clientes Más Frecuentes - " + fechaInicio + " a " + fechaFin, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
                clientesTitle.setSpacingBefore(30);
                clientesTitle.setSpacingAfter(10);
                document.add(clientesTitle);

                PdfPTable clientesTable = new PdfPTable(3);
                clientesTable.setWidthPercentage(100);

                // Encabezado de la tabla de clientes
                cell.setPhrase(new Phrase("Código", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                clientesTable.addCell(cell);
                cell.setPhrase(new Phrase("Nombre", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                clientesTable.addCell(cell);
                cell.setPhrase(new Phrase("Número de Compras", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                clientesTable.addCell(cell);

                // Datos de la tabla de clientes
                for (ClienteFrecuente cliente : clientesFrecuentes) {
                    clientesTable.addCell(String.valueOf(cliente.getClienteId()));
                    clientesTable.addCell(cliente.getNombre());
                    clientesTable.addCell(String.valueOf(cliente.getTotalCompras()));
                }
                document.add(clientesTable);

                // Informe de ganancias totales
                Paragraph gananciasTitle = new Paragraph("Ganancias Totales - " + fechaInicio + " a " + fechaFin, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
                gananciasTitle.setSpacingBefore(30);
                gananciasTitle.setSpacingAfter(10);
                document.add(gananciasTitle);

                Paragraph gananciasTotalesParagraph = new Paragraph("Ganancias Totales: $" + gananciasTotales, FontFactory.getFont(FontFactory.HELVETICA, 14));
                document.add(gananciasTotalesParagraph);

                // Cerrar el documento
                document.close();

                // Abrir el PDF automáticamente
                File pdfFile = new File(filePath);
                if (pdfFile.exists()) {
                    Desktop.getDesktop().open(pdfFile);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Manejar caso donde las fechas son nulas o inválidas
            System.out.println("Por favor, seleccione un rango de fechas válido.");
        }

    }

    @javafx.fxml.FXML
    public void salirVentanaInforme(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = stage.getOwner().getScene().getRoot();
        root.setEffect(null);
        stage.close();
    }

    @javafx.fxml.FXML
    public void generarIInformeGlobal(ActionEvent actionEvent) throws SQLException {
        VentasDao ventasDao = new VentasDao();

        // Obtener los datos para el informe
        List<ProductoMasVendido> productosMasVendidos = ventasDao.obtenerProductosMasVendidos();
        List<ClienteFrecuente> clientesFrecuentes = ventasDao.obtenerClientesFrecuentes();
        double gananciasTotales = ventasDao.obtenerGananciasTotales();

        // Crear gráfico de productos más vendidos
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (ProductoMasVendido producto : productosMasVendidos) {
            dataset.setValue(producto.getNombre(), producto.getTotalCantidad());
        }
        JFreeChart chart = ChartFactory.createPieChart(
                "Productos Más Vendidos", // Título
                dataset,                  // Datos
                true,                     // Leyenda
                true,                     // Tooltips
                false                     // URLs
        );
        File chartFile = new File("chart_global.png");
        try {
            ChartUtils.saveChartAsPNG(chartFile, chart, 600, 400); // Cambia el tamaño aquí (ancho x alto)
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ruta del archivo PDF
        String filePath = "reportes/informe_global.pdf";

        try {
            // Crear el documento PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Añadir encabezado
            PdfPTable headerTable = new PdfPTable(1);
            headerTable.setWidthPercentage(100);
            PdfPCell headerCell = new PdfPCell(new Phrase("LA PERLA", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 36, Font.BOLD, BaseColor.BLACK)));
            headerCell.setBorder(PdfPCell.NO_BORDER);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerTable.addCell(headerCell);
            document.add(headerTable);

            // Informe de productos más vendidos
            Paragraph productosTitle = new Paragraph("Productos Más Vendidos", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            productosTitle.setSpacingBefore(20);  // Espacio antes del título
            productosTitle.setSpacingAfter(10);   // Espacio después del título
            document.add(productosTitle);

            PdfPTable productosTable = new PdfPTable(3);
            productosTable.setWidthPercentage(100);

            // Encabezado de la tabla de productos más vendidos
            PdfPCell cell = new PdfPCell(new Phrase("Código", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            productosTable.addCell(cell);
            cell.setPhrase(new Phrase("Nombre", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            productosTable.addCell(cell);
            cell.setPhrase(new Phrase("Cantidad Vendida", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            productosTable.addCell(cell);

            // Datos de la tabla de productos más vendidos
            for (ProductoMasVendido producto : productosMasVendidos) {
                productosTable.addCell(String.valueOf(producto.getProductoId()));
                productosTable.addCell(producto.getNombre());
                productosTable.addCell(String.valueOf(producto.getTotalCantidad()));
            }
            document.add(productosTable);

            // Añadir el gráfico al PDF con tamaño ajustado
            Image chartImage = Image.getInstance(chartFile.getAbsolutePath());
            chartImage.scaleToFit(600, 400); // Ajusta el tamaño aquí (ancho x alto)
            chartImage.setAlignment(Image.ALIGN_CENTER);
            document.add(chartImage);

            // Informe de clientes más frecuentes
            Paragraph clientesTitle = new Paragraph("Clientes Más Frecuentes", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            clientesTitle.setSpacingBefore(30); // Espacio antes del título
            clientesTitle.setSpacingAfter(10);  // Espacio después del título
            document.add(clientesTitle);

            PdfPTable clientesTable = new PdfPTable(3);
            clientesTable.setWidthPercentage(100);

            // Encabezado de la tabla de clientes más frecuentes
            cell.setPhrase(new Phrase("Código", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            clientesTable.addCell(cell);
            cell.setPhrase(new Phrase("Nombre", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            clientesTable.addCell(cell);
            cell.setPhrase(new Phrase("Número de Compras", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
            clientesTable.addCell(cell);

            // Datos de la tabla de clientes más frecuentes
            for (ClienteFrecuente cliente : clientesFrecuentes) {
                clientesTable.addCell(String.valueOf(cliente.getClienteId()));
                clientesTable.addCell(cliente.getNombre());
                clientesTable.addCell(String.valueOf(cliente.getTotalCompras()));
            }
            document.add(clientesTable);

            // Informe de ganancias totales
            Paragraph gananciasTitle = new Paragraph("Ganancias Totales", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            gananciasTitle.setSpacingBefore(30); // Espacio antes del título
            gananciasTitle.setSpacingAfter(10);  // Espacio después del título
            document.add(gananciasTitle);

            document.add(new Paragraph("Total Ganancias: $" + gananciasTotales));

            document.close();

            // Abrir el archivo PDF
            File pdfFile = new File(filePath);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile);
            }

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private void generarReportePDF(List<ProductoMasVendido> productos, List<ClienteFrecuente> clientes, double gananciasTotales) throws DocumentException, IOException {
        String directory = "reportes";
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs(); // Crear el directorio si no existe
        }

        String dest = directory + "/reporte_global.pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();

        // Añadir encabezado con el texto "LA PERLA"
        PdfPTable headerTable = new PdfPTable(1);
        headerTable.setWidthPercentage(100);

        PdfPCell cell = new PdfPCell();
        cell.setBorder(PdfPCell.NO_BORDER);

        BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED);
        Font font = new Font(baseFont, 36, Font.BOLD, BaseColor.BLACK);

        Paragraph paragraph = new Paragraph("LA PERLA", font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        cell.addElement(paragraph);
        headerTable.addCell(cell);
        document.add(headerTable);

        // Título del reporte
        document.add(new Paragraph("Reporte Global", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));

        // Espacio adicional antes de la tabla de productos
        document.add(new Paragraph(" "));

        // Sección de Productos Más Vendidos
        document.add(new Paragraph("Productos Más Vendidos", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        PdfPTable productosTable = new PdfPTable(3); // 3 columnas: Producto ID, Nombre y Cantidad
        productosTable.setWidthPercentage(100);

        PdfPCell headerCell = new PdfPCell(new Phrase("Producto ID", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        headerCell.setBackgroundColor(new BaseColor(173, 216, 230)); // Color azul claro
        productosTable.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Nombre", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        productosTable.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Cantidad Vendida", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        productosTable.addCell(headerCell);

        for (ProductoMasVendido producto : productos) {
            productosTable.addCell(String.valueOf(producto.getProductoId()));
            productosTable.addCell(producto.getNombre());
            productosTable.addCell(String.valueOf(producto.getTotalCantidad()));
        }
        document.add(productosTable);

        // Espacio adicional
        document.add(new Paragraph(" "));

        // Sección de Clientes Más Frecuentes
        document.add(new Paragraph("Clientes Más Frecuentes", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        PdfPTable clientesTable = new PdfPTable(3); // 3 columnas: Cliente ID, Nombre y Compras
        clientesTable.setWidthPercentage(100);

        PdfPCell clienteHeaderCell = new PdfPCell(new Phrase("Cliente ID", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        clienteHeaderCell.setBackgroundColor(new BaseColor(173, 216, 230)); // Color azul claro
        clientesTable.addCell(clienteHeaderCell);

        clienteHeaderCell.setPhrase(new Phrase("Nombre", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        clientesTable.addCell(clienteHeaderCell);

        clienteHeaderCell.setPhrase(new Phrase("Total Compras", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        clienteHeaderCell.setPhrase(new Phrase("Total Compras", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        clientesTable.addCell(clienteHeaderCell);

        for (ClienteFrecuente cliente : clientes) {
            clientesTable.addCell(String.valueOf(cliente.getClienteId()));
            clientesTable.addCell(cliente.getNombre());
            clientesTable.addCell(String.valueOf(cliente.getTotalCompras()));
        }
        document.add(clientesTable);

        // Espacio adicional
        document.add(new Paragraph(" "));

        // Sección de Ganancias Totales
        document.add(new Paragraph("Ganancias Totales", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        document.add(new Paragraph("Total Ganancias: $" + gananciasTotales, FontFactory.getFont(FontFactory.HELVETICA, 12)));

        // Cerrar el documento
        document.close();

        System.out.println("Reporte generado exitosamente: " + dest);
        // Abrir el archivo PDF


    }
}
