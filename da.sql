CREATE DATABASE la_perla;
USE la_perla;
CREATE TABLE usuario (
  id_usuario int(11) NOT NULL,
  usuario varchar(30) NOT NULL,
  nombre varchar(30) NOT NULL,
  gmail varchar(250) NOT NULL,
  clave varchar(15) NOT NULL
);

INSERT INTO usuario (id_usuario, usuario, nombre, gmail, clave) VALUES(1, 'admi', 'administrador', 'administrador@gmail.com', '12345');

CREATE TABLE ventas (
  codigo int(11) NOT NULL,
  fecha timestamp NOT NULL DEFAULT current_timestamp(),
  cliente int(11) DEFAULT NULL,
monto double NOT NULL,
  estado enum('Fiado','Pago') NOT NULL DEFAULT 'Pago'
);

CREATE TABLE productos (
  codigo int(11) NOT NULL,
  nombre varchar(250) NOT NULL,
  precio int(30) NOT NULL,
  categoria varchar(250) NOT NULL,
  proveedor int(11) NOT NULL,
  stock int(11) NOT NULL
);

CREATE TABLE detalle_venta (
  codigo int(11) NOT NULL,
  producto int(11) NOT NULL
);

CREATE TABLE cliente (
  codigo int(11) NOT NULL,
  nombre varchar(250) NOT NULL,
  telefono varchar(20) NOT NULL
);

 CREATE TABLE proveedor (
  codigo int(11) NOT NULL,
  nombre varchar(250) NOT NULL,
  telefono varchar(20) NOT NULL
);

INSERT INTO cliente (codigo,nombre,telefono) VALUES
(1000322243, 'Cliente C', '3203333333'),
(1000511111, 'Cliente E', '3205555555'),
(1002311104, 'Cliente D', '3204444444'),
(1034422453, 'Cliente B', '3202222222'),
(1093900012, 'Cliente A', '3201111111');

INSERT INTO ventas (codigo, fecha, cliente, monto, estado) VALUES
(2, '2024-05-15 17:17:01', NULL, 150000, 'Pago'),
(3, '2024-05-15 17:21:08', 1093900012, 85000, 'Fiado'),
(4, '2024-05-15 17:22:50', 1034422453, 215000, 'Fiado');

INSERT INTO detalle_venta (codigo,producto) VALUES
(2, 9),
(2, 11),
(2, 11),
(2, 14),
(3, 16),
(4, 9),
(4, 9),
(4, 10),
(4, 10),
(4, 15),
(4, 16);

INSERT INTO productos (codigo, nombre, precio, categoria, proveedor, stock) VALUES
(9, 'Producto 1', 15000, 'Electrónicos', 1, 50),
(10, 'Producto 2', 25000, 'Electrodomésticos', 2, 100),
(11, 'Producto 3', 35000, 'Ropa', 3, 75),
(12, 'Producto 4', 45000, 'Hogar', 4, 30),
(13, 'Producto 5', 55000, 'Electrónicos', 5, 60),
(14, 'Producto 6', 65000, 'Electrodomésticos', 1, 25),
(15, 'Producto 7', 75000, 'Ropa', 2, 80),
(16, 'Producto 8', 85000, 'Hogar', 3, 40);

INSERT INTO proveedor (codigo, nombre, telefono) VALUES
(1, 'Proveedor A', '3201234567'),
(2, 'Proveedor B', '3202345678'),
(3, 'Proveedor C', '3203456789'),
(4, 'Proveedor D', '3204567890'),
(5, 'Proveedor E', '3205678901');

ALTER TABLE cliente
  ADD PRIMARY KEY (codigo);
  
  
ALTER TABLE detalle_venta
  ADD KEY producto (producto),
  ADD KEY codigo (codigo);

ALTER TABLE productos
  ADD PRIMARY KEY (codigo),
  ADD KEY proveedor (proveedor);


ALTER TABLE proveedor
  ADD PRIMARY KEY (codigo);
  
ALTER TABLE usuario
  ADD PRIMARY KEY (id_usuario);

ALTER TABLE ventas
  ADD PRIMARY KEY (codigo),
  ADD KEY cliente (cliente);

ALTER TABLE productos
  MODIFY codigo int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

ALTER TABLE proveedor
  MODIFY codigo int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;


ALTER TABLE usuario
  MODIFY id_usuario int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

ALTER TABLE ventas
  MODIFY codigo int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE detalle_venta
  ADD CONSTRAINT detalle_venta_ibfk_1 FOREIGN KEY (codigo) REFERENCES ventas (codigo),
  ADD CONSTRAINT detalle_venta_ibfk_2 FOREIGN KEY (producto) REFERENCES productos (codigo);

ALTER TABLE productos
  ADD CONSTRAINT productos_ibfk_1 FOREIGN KEY (proveedor) REFERENCES proveedor (codigo);

ALTER TABLE ventas
  ADD CONSTRAINT ventas_ibfk_1 FOREIGN KEY (cliente) REFERENCES cliente (codigo);
COMMIT;
