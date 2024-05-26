package Dao;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductosDao {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public ProductosDao( ) {

    }

    public Productos agregarProductos(int codigo, String nombre, int precio, String categoria, int proveedorId, int stock) throws SQLException {
        String insertSQL = "INSERT INTO productos(codigo, nombre, precio, categoria, proveedor, stock) VALUES (?,?,?,?,?,?)";
        Productos pro = new Productos();
        con = cn.getConexion();
        try {
            ps = con.prepareStatement(insertSQL);
            ps.setInt(1, codigo);
            ps.setString(2, nombre);
            ps.setInt(3, precio);
            ps.setString(4, categoria);
            ps.setInt(5, proveedorId); // Usamos el ID del proveedor
            ps.setInt(6, stock);

            pro.setCodigoProducto(codigo);
            pro.setNombreProducto(nombre);
            pro.setPrecioProducto(precio);
            pro.setCategoriaProducto(categoria);
            pro.setCodigoProveedorPro(proveedorId);
            pro.setStockProducto(stock);

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
                // Manejo de errores de cierre de recursos
            }
        }
        return pro;
    }


    private void mostrarAlerta(String titulo, String mensaje, String detalles) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(detalles);
        alerta.showAndWait();
    }
}
