package Controllers;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public class ChartToPDF {


    public static void main(String[] args) {
        // Crear el conjunto de datos del gráfico
        DefaultPieDataset dataset = createDataset();

        // Crear el gráfico
        JFreeChart chart = ChartFactory.createPieChart(
                "Distribución de Ventas", // Título del gráfico
                dataset,                 // Datos del gráfico
                true,                    // Incluir leyenda
                true,                    // Incluir tooltips
                false                    // Incluir URL
        );

        // Guardar el gráfico como una imagen PNG
        File chartFile = new File("chart.png");
        try {
            ChartUtils.saveChartAsPNG(chartFile, chart, 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Crear el documento PDF
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("report.pdf"));
            document.open();

            // Añadir título al PDF
            document.add(new Paragraph("Informe de Ventas"));

            // Añadir el gráfico al PDF
            Image chartImage = Image.getInstance(chartFile.getAbsolutePath());
            chartImage.setAlignment(Image.ALIGN_CENTER);
            document.add(chartImage);

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private static DefaultPieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Producto A", 40);
        dataset.setValue("Producto B", 30);
        dataset.setValue("Producto C", 20);
        dataset.setValue("Producto D", 10);
        return dataset;
    }
}
