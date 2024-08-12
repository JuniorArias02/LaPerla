package Dao;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductosDao {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public ProductosDao() {

    }

    public void actualizarStock(int idProducto, int nuevoStock) throws SQLException {
        String sql = "UPDATE productos SET stock = ? WHERE codigo = ?";
        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nuevoStock);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al actualizar el stock del producto", e);
        }
    }

    public Productos buscarProducto(int id) throws SQLException {
        Productos producto = null;
        String query = "SELECT codigo,nombre,precio,stock FROM productos WHERE codigo = ?";
        Connection con = cn.getConexion();

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    producto = new Productos();
                    producto.setCodigoProducto(rs.getInt("codigo"));
                    producto.setNombreProducto(rs.getString("nombre"));
                    producto.setPrecioProducto(rs.getInt("precio"));
                    producto.setStockProducto(rs.getInt("stock"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al buscar el producto", e);
        }

        return producto;
    }

    public Productos agregarProductos(int codigo, String nombre, int precio, String categoria, int proveedorId,
                                      int stock) throws SQLException {
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

    public void modificarProducto(int codigo, String nombre, int precio, String categoria, Integer proveedor,
                                  int Stock) {
        String modifySql = "UPDATE productos SET codigo = ?, nombre = ?, precio = ?, categoria = ?,proveedor = ?, stock = ? WHERE codigo = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(modifySql);
            ps.setInt(1, codigo);
            ps.setString(2, nombre);
            ps.setInt(3, precio);
            ps.setString(4, categoria);
            ps.setInt(5, proveedor);
            ps.setInt(6, Stock);
            ps.setInt(7, codigo);
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("producto modificado exitosamente.");
            } else {
                System.out.println("No se encontró el producto con el código especificado.");
            }

        } catch (SQLException e) {
            mostrarAlerta("error", e.toString(), "");
        }
    }

    public void eliminarProducto(int codigo) {
        String deleteSql = "DELETE FROM productos WHERE codigo = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(deleteSql);
            ps.setInt(1, codigo);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                mostrarOperacionExitosa();
            } else {
                mostrarAlerta("error", "error", "");
            }
        } catch (SQLException | IOException e) {
            mostrarAlerta("error", e.toString(), "");
        }
    }


    public ObservableList<Productos> ListaProductos(String valor) {
        ObservableList<Productos> productoList = FXCollections.observableArrayList();
        String sql = "SELECT productos.codigo, productos.nombre, productos.categoria, productos.precio, productos.stock, proveedor.codigo AS proveedorCodigo, proveedor.nombre AS proveedorNombre " +
                "FROM productos " +
                "INNER JOIN proveedor ON productos.proveedor = proveedor.codigo " +
                "ORDER BY productos.codigo ASC";
        String buscar = "SELECT productos.codigo, productos.nombre, productos.categoria, productos.precio, productos.stock, proveedor.codigo AS proveedorCodigo, proveedor.nombre AS proveedorNombre " +
                "FROM productos " +
                "INNER JOIN proveedor ON productos.proveedor = proveedor.codigo " +
                "WHERE productos.codigo LIKE ? OR productos.nombre LIKE ? OR productos.categoria LIKE ?";
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
                pro.setIdProveedor(rs.getInt("proveedorCodigo")); // Guarda el código del proveedor
                pro.setNombreProveedor(rs.getString("proveedorNombre")); // Guarda el nombre del proveedor
                productoList.add(pro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productoList;
    }



    public int obtenerStock(int idProducto) throws SQLException {
        int stock = 0;
        String query = "SELECT stock FROM productos WHERE codigo = ?";

        try (Connection con = cn.getConexion();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    stock = rs.getInt("stock");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al obtener el stock del producto", e);
        }

        return stock;
    }
    private void mostrarAlerta(String titulo, String mensaje, String detalles) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(detalles);
        alerta.showAndWait();
    }

    private void mostrarOperacionExitosa() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/alertas/OperacionExitosa.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> stage.close()));
        timeline.play();
    }

}
