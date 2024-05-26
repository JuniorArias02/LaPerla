package Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public ObservableList<Productos> ListaProductos(String valor) {
        ObservableList<Productos> productoList = FXCollections.observableArrayList();
        String sql = "SELECT codigo,nombre,categoria,precio,stock FROM productos ORDER BY codigo ASC";
        String buscar = "SELECT codigo,nombre,categoria,precio,stock FROM proveedor WHERE codigo LIKE ? OR nombre LIKE ? OR categoria LIKE ?";
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
                ps.setString(3, valor + "%");
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                Productos pro = new Productos();
                pro.setCodigoProducto(rs.getInt("codigo"));
                pro.setNombreProducto(rs.getString("nombre"));
                pro.setCategoriaProducto(rs.getString("categoria"));
                pro.setPrecioProducto(rs.getInt("precio"));
                pro.setStockProducto(rs.getInt("stock"));
                productoList.add(pro);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Cambia esto a una alerta si es necesario
        }
        return productoList;
    }


    private void mostrarAlerta(String titulo, String mensaje, String detalles) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(detalles);
        alerta.showAndWait();
    }
}
