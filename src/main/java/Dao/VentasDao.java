package Dao;

import com.itextpdf.text.*;

import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;;
import com.itextpdf.text.pdf.PdfPTable;

import javax.imageio.ImageIO;
import java.io.FileOutputStream;
import java.sql.SQLException;






public class VentasDao {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;



    public Ventas nuevaVenta(long cliente, double monto) throws SQLException {
        String insertSQL = "INSERT INTO ventas (cliente,monto) VALUES(?,?)";
        con = cn.getConexion();
        Ventas ventas = new Ventas();
        try {
            ps = con.prepareStatement(insertSQL);
            ps.setLong(1, cliente);
            ps.setLong(2, (long) monto);

            ventas.setCliente(cliente);
            ventas.setMonto((long)monto);

            ps.executeUpdate();

        } catch (SQLException e) {
            mostrarAlerta("error", e.toString(), "");
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                mostrarAlerta("error", e.toString(), "");
            }
        }
        return ventas;
    }

    public void detalle_venta(long producto,int cantidad) throws SQLException {
        String selectSQL = "SELECT MAX(codigo) AS ultimo_id FROM ventas";
        String insertSQL = "INSERT INTO detalle_venta(codigo, producto,cantidad) VALUES (?,?,?)";

        con = cn.getConexion();

        try {
            // Obtener el último ID
            ps = con.prepareStatement(selectSQL);
            rs = ps.executeQuery();

            long ultimoId = 0;
            if (rs.next()) {
                ultimoId = rs.getLong("ultimo_id");
            }

            // Insertar en detalle_venta usando el último ID
            ps = con.prepareStatement(insertSQL);
            ps.setLong(1, ultimoId);
            ps.setLong(2, producto);
            ps.setInt(3,cantidad);

            ps.executeUpdate();

        } catch (SQLException e) {
            mostrarAlerta("error", e.toString(), "");
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                mostrarAlerta("error", e.toString(), "");
            }
        }
    }

    public ObservableList<Ventas> ListaVentas(String valor) {
        ObservableList<Ventas> ventasList = FXCollections.observableArrayList();
        String sql = "select * from ventas";
        String buscar = "SELECT * FROM ventas WHERE codigo LIKE ? OR  cliente LIKE ?";
        PreparedStatement ps;
        ResultSet rs;
        Connection con;
        try {
            con = cn.getConexion();
            if (valor == null || valor.isEmpty()) {
                ps = con.prepareStatement(sql);
            } else {
                ps = con.prepareStatement(buscar);
                ps.setString(1, valor + "%");
                ps.setString(2, valor + "%");
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                Ventas vent = new Ventas();
                vent.setCodigoVenta(rs.getLong("codigo"));
                vent.setFecha(rs.getDate("fecha"));
                vent.setCliente(rs.getLong("cliente"));
                vent.setMonto(rs.getLong("monto"));
                ventasList.add(vent);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Cambia esto a una alerta si es necesario
        }
        return ventasList;
    }


    private void mostrarAlerta(String titulo, String mensaje, String detalles) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(detalles);
        alerta.showAndWait();
    }

    public Ventas obtenerVentaPorCodigo(long codigoVenta) throws SQLException {
        String query = "SELECT codigo, fecha, cliente, monto FROM ventas WHERE codigo = ?";
        Ventas venta = null;

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setLong(1, codigoVenta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    venta = new Ventas();
                    venta.setCodigoVenta(rs.getLong("codigo"));
                    venta.setFecha(rs.getTimestamp("fecha"));
                    venta.setCliente(rs.getLong("cliente"));
                    venta.setMonto(rs.getLong("monto"));
                }
            }
        }

        return venta;
    }

    public List<DetalleVenta> obtenerDetallesPorVenta(long codigoVenta) throws SQLException {
        String query = "SELECT dv.cantidad, productos.nombre AS nombre_producto, productos.precio " +
                "FROM detalle_venta dv " +
                "INNER JOIN productos ON productos.codigo = dv.producto " +
                "WHERE dv.codigo = ?";
        List<DetalleVenta> detalles = new ArrayList<>();

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setLong(1, codigoVenta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetalleVenta detalle = new DetalleVenta();
                    detalle.setCantidad(rs.getInt("cantidad"));
                    detalle.setNombre(rs.getString("nombre_producto"));
                    detalle.setPrecio(rs.getLong("precio"));
                    detalles.add(detalle);
                }
            }
        }

        return detalles;
    }


    public void generarFacturaPDF(long codigoVenta) throws SQLException, IOException, DocumentException {
        Ventas venta = obtenerVentaPorCodigo(codigoVenta);
        List<DetalleVenta> detalles = obtenerDetallesPorVenta(codigoVenta);

        if (venta != null) {
            String directory = "facturas";
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs(); // Crear el directorio si no existe
            }

            String dest = directory + "/factura_" + codigoVenta + ".pdf";
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            // Añadir encabezado con texto e imagen
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);

            // Celda para el texto "LA PERLA"
            PdfPCell textCell = new PdfPCell();
            textCell.setBorder(PdfPCell.NO_BORDER);

            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, 36, Font.BOLD, BaseColor.BLACK);

            Paragraph paragraph = new Paragraph("LA PERLA", font);
            paragraph.setAlignment(Paragraph.ALIGN_LEFT);
            textCell.addElement(paragraph);

            headerTable.addCell(textCell);

            // Celda para la imagen
            PdfPCell imageCell = new PdfPCell();
            imageCell.setBorder(PdfPCell.NO_BORDER);

            Image image = Image.getInstance("src/main/resources/img/perla.png"); // Ruta de la imagen
            image.scaleToFit(200, 150); // Escala la imagen al tamaño deseado
            image.setAlignment(Image.ALIGN_RIGHT);

            imageCell.addElement(image);
            headerTable.addCell(imageCell);

            document.add(headerTable);

            // Información de la venta
            document.add(new Paragraph("Factura", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("Código de venta: " + venta.getCodigoVenta()));
            document.add(new Paragraph("Fecha: " + venta.getFecha().toString()));
            document.add(new Paragraph("Cliente: " + venta.getCliente()));
            document.add(new Paragraph("Monto: $" + venta.getMonto()));

            // Espacio adicional antes de la tabla de productos
            Paragraph space = new Paragraph(" ");
            space.setSpacingBefore(20);
            document.add(space);

            // Crear la tabla con encabezado
            PdfPTable table = new PdfPTable(4); // 4 columnas
            table.setWidthPercentage(100); // Establecer el ancho de la tabla al 100% de la página

            // Encabezado de la tabla
            PdfPCell headerCell = new PdfPCell(new Phrase("Producto", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            headerCell.setBackgroundColor(new BaseColor(173, 216, 230)); // Color azul claro
            table.addCell(headerCell);
            headerCell.setPhrase(new Phrase("Cantidad", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            table.addCell(headerCell);
            headerCell.setPhrase(new Phrase("Precio Unitario", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            table.addCell(headerCell);
            headerCell.setPhrase(new Phrase("Precio Total", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            table.addCell(headerCell);

            // Datos de la tabla
            for (DetalleVenta detalle : detalles) {
                table.addCell(detalle.getNombre());
                table.addCell(String.valueOf(detalle.getCantidad()));
                table.addCell(String.valueOf(detalle.getPrecio()));
                table.addCell(String.valueOf(detalle.getCantidad() * detalle.getPrecio()));
            }

            document.add(table);
            document.close();

            // Abre el archivo PDF después de generarlo
            if (Desktop.isDesktopSupported()) {
                try {
                    File pdfFile = new File(dest);
                    if (pdfFile.exists()) {
                        Desktop.getDesktop().open(pdfFile);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//
//    public Ventas obtenerVentaPorCodigo(long codigo) throws SQLException {
//        String selectSQL = "SELECT * FROM ventas WHERE codigo = ?";
//        Ventas venta = null;
//
//        con = cn.getConexion();
//        try {
//            ps = con.prepareStatement(selectSQL);
//            ps.setLong(1, codigo);
//            rs = ps.executeQuery();
//
//            if (rs.next()) {
//                venta = new Ventas();
//                venta.setCodigoVenta(rs.getLong("codigo"));
//                venta.setFecha(rs.getTimestamp("fecha"));
//                venta.setCliente(rs.getLong("cliente"));
//                venta.setMonto(rs.getLong("monto"));
//            }
//        } finally {
//            if (rs != null) rs.close();
//            if (ps != null) ps.close();
//            if (con != null) con.close();
//        }
//
//        return venta;
//    }
//
//    public List<DetalleVenta> obtenerDetallesPorVenta(long codigoVenta) throws SQLException {
//        String selectSQL = "SELECT * FROM detalle_venta WHERE codigo = ?";
//        List<DetalleVenta> detalles = new ArrayList<>();
//
//        con = cn.getConexion();
//        try {
//            ps = con.prepareStatement(selectSQL);
//            ps.setLong(1, codigoVenta);
//            rs = ps.executeQuery();
//
//            while (rs.next()) {
//                DetalleVenta detalle = new DetalleVenta();
//                detalle.setCodigo(rs.getLong("codigo"));
//                detalle.setProducto(rs.getString("producto"));
//                detalle.setCantidad(rs.getInt("cantidad"));
//                detalles.add(detalle);
//            }
//        } finally {
//            if (rs != null) rs.close();
//            if (ps != null) ps.close();
//            if (con != null) con.close();
//        }
//
//        return detalles;
//    }
//    public void generarFacturaPDF(long codigoVenta) throws SQLException, IOException, DocumentException {
//        Ventas venta = obtenerVentaPorCodigo(codigoVenta);
//        List<DetalleVenta> detalles = obtenerDetallesPorVenta(codigoVenta);
//
//        if (venta != null) {
//            String directory = "facturas";
//            File dir = new File(directory);
//            if (!dir.exists()) {
//                dir.mkdirs(); // Crear el directorio si no existe
//            }
//
//            String dest = directory + "/factura_" + codigoVenta + ".pdf";
//            Document document = new Document();
//            PdfWriter.getInstance(document, new FileOutputStream(dest));
//            document.open();
//
//            document.add(new Paragraph("Factura"));
//            document.add(new Paragraph("Código de venta: " + venta.getCodigoVenta()));
//            document.add(new Paragraph("Fecha: " + venta.getFecha().toString()));
//            document.add(new Paragraph("Cliente: " + venta.getCliente()));
//            document.add(new Paragraph("Monto: $" + venta.getMonto()));
//
//            PdfPTable table = new PdfPTable(3); // 3 columnas
//            table.addCell("Código");
//            table.addCell("Producto");
//            table.addCell("Cantidad");
//
//            for (DetalleVenta detalle : detalles) {
//                table.addCell(String.valueOf(detalle.getCodigo()));
//                table.addCell(String.valueOf(detalle.getProducto()));
//                table.addCell(String.valueOf(detalle.getCantidad()));
//            }
//
//            document.add(table);
//            document.close();
//        }
//    }

//    generar informes

    // Obtener productos más vendidos
    public List<ProductoMasVendido> obtenerProductosMasVendidos() throws SQLException {
        List<ProductoMasVendido> productosMasVendidos = new ArrayList<>();
        String sql = "SELECT p.codigo, p.nombre, SUM(dv.cantidad) as totalCantidad " +
                "FROM detalle_venta dv " +
                "JOIN productos p ON dv.producto = p.codigo " +
                "GROUP BY p.codigo, p.nombre " +
                "ORDER BY totalCantidad DESC";

        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ProductoMasVendido producto = new ProductoMasVendido();
                producto.setProductoId(rs.getInt("codigo"));
                producto.setNombre(rs.getString("nombre"));
                producto.setTotalCantidad(rs.getInt("totalCantidad"));
                productosMasVendidos.add(producto);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }

        return productosMasVendidos;
    }

    // Obtener clientes más frecuentes
    public List<ClienteFrecuente> obtenerClientesFrecuentes() throws SQLException {
        List<ClienteFrecuente> clientesFrecuentes = new ArrayList<>();
        String sql = "SELECT c.codigo, c.nombre, COUNT(v.codigo) as totalCompras " +
                "FROM ventas v " +
                "JOIN cliente c ON v.cliente = c.codigo " +
                "GROUP BY c.codigo, c.nombre " +
                "ORDER BY totalCompras DESC";

        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ClienteFrecuente cliente = new ClienteFrecuente();
                cliente.setClienteId(rs.getInt("codigo"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setTotalCompras(rs.getInt("totalCompras"));
                clientesFrecuentes.add(cliente);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }

        return clientesFrecuentes;
    }

    // Obtener ganancias totales
    public double obtenerGananciasTotales() throws SQLException {
        double gananciasTotales = 0;
        String sql = "SELECT SUM(monto) as totalGanancias FROM ventas";

        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                gananciasTotales = rs.getDouble("totalGanancias");
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }

        return gananciasTotales;
    }


//    generar informe por fecha
// Obtener productos más vendidos por fecha
public List<ProductoMasVendido> obtenerProductosPorFecha(LocalDate fecha) {
    List<ProductoMasVendido> productosMasVendidos = new ArrayList<>();
    String query = "SELECT p.codigo, p.nombre, SUM(dv.cantidad) AS cantidad_vendida " +
            "FROM productos p " +
            "JOIN detalle_venta dv ON p.codigo = dv.producto " +
            "JOIN ventas v ON dv.codigo = v.codigo " +
            "WHERE DATE(v.fecha) = ? " +  // Filtrar por fecha
            "GROUP BY p.codigo, p.nombre " +
            "ORDER BY cantidad_vendida DESC";

    try (Connection con = cn.getConexion();
         PreparedStatement ps = con.prepareStatement(query)) {

        ps.setDate(1, java.sql.Date.valueOf(fecha));  // Convertir LocalDate a SQL Date

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ProductoMasVendido producto = new ProductoMasVendido();
                producto.setProductoId(rs.getInt("codigo"));
                producto.setNombre(rs.getString("nombre"));
                producto.setTotalCantidad(rs.getInt("cantidad_vendida"));
                productosMasVendidos.add(producto);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return productosMasVendidos;
}
    // Obtener clientes más frecuentes por fecha
    public List<ClienteFrecuente> obtenerClientesPorFecha(LocalDate fecha) {
        List<ClienteFrecuente> clientesFrecuentes = new ArrayList<>();
        String query = "SELECT c.codigo, c.nombre, COUNT(v.codigo) AS numero_compras " +
                "FROM cliente c " +
                "JOIN ventas v ON c.codigo = v.cliente " +
                "WHERE DATE(v.fecha) = ? " +  // Filtrar por fecha
                "GROUP BY c.codigo, c.nombre " +
                "ORDER BY numero_compras DESC";

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setDate(1, java.sql.Date.valueOf(fecha));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ClienteFrecuente cliente = new ClienteFrecuente();
                    cliente.setClienteId(rs.getInt("codigo"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setTotalCompras(rs.getInt("numero_compras"));
                    clientesFrecuentes.add(cliente);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientesFrecuentes;
    }

    public double obtenerGananciasPorFecha(LocalDate fecha) {
        double gananciasTotales = 0;
        String query = "SELECT SUM(v.monto) AS total_ganancias " +
                "FROM ventas v " +
                "WHERE DATE(v.fecha) = ?";  // Filtrar por fecha

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setDate(1, java.sql.Date.valueOf(fecha));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    gananciasTotales = rs.getDouble("total_ganancias");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gananciasTotales;
    }

    public int obtenerTotalVentasHoy() {
        int totalVentas = 0;
        String query = "SELECT COUNT(*) AS total_ventas FROM ventas WHERE DATE(fecha) = CURDATE()";

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                totalVentas = rs.getInt("total_ventas");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalVentas;
    }

}
