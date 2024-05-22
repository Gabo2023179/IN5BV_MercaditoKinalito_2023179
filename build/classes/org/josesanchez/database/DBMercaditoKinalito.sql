drop database if exists DBMercaditoKinalito;
create database DBMercaditoKinalito;
use DBMercaditoKinalito;
 
 set global time_Zone = '-6:00';
-- Creacion de Entidades
create table Clientes(
	codigoCliente int auto_increment,
	NITCliente varchar(10) not null,
	nombreCliente varchar(50) not null,
	apellidoCliente varchar(50) not null,
	direccionCliente varchar(150) not null,
	telefonoCliente varchar(15) not null,
	correoCliente varchar(50) not null,
	primary key PK_codigoCliente(codigoCliente)
);
 
create table Proveedores(
	codigoProveedor int auto_increment,
    NITproveedor varchar(10) not null,
    nombreProveedor varchar (60),
    apellidoProveedor varchar (60),
    direccionProveedor varchar (150),
    razonSocial varchar (60),
    contactoPrincipal varchar (100),
    paginaWeb varchar (50),
    primary key PK_codigoProveedor (codigoProveedor)
);

create table TelefonoProveedor(
	codigoTelefonoProveedor int auto_increment,
    numeroPrincipal varchar(8),
    numeroSecundario varchar(8),
    observaciones varchar(45),
    codigoProveedor int,
    primary key PK_codigoTelefonoProveedor (codigoTelefonoProveedor),
    foreign key FK_telefonoCodigoProveedor (codigoProveedor)
		references Proveedores(codigoProveedor)
);

create table EmailProveedor(
	codigoEmailProveedor int auto_increment,
    emailProveedor varchar(50),
    descripcion varchar(100),
    codigoProveedor int,
    primary key codigoEmailProveedor (codigoEmailProveedor),
    foreign key FK_emailCodigoProveedor (codigoProveedor)
		references Proveedores(codigoProveedor)
);
 
create table TipoDeProductos(
	codigoTipoDeProducto int auto_increment,
    descripcion varchar(45),
    primary key PK_codigoTipoDeProducto(codigoTipoDeProducto)
);
create table Productos (
    productoId int auto_increment,
    descripcionProducto varchar(100),
    precioUnitario decimal(10,2) default 0,
    precioDocena decimal(10,2) default 0,
    precioMayor decimal(10,2) default 0,
    imagenProducto varchar (45),
    existencia int default 0,
    codigoProveedor int,
    codigoTipoDeProducto int,
    primary key PK_productoId (productoId),
    foreign key FK_codigoProveedor (codigoProveedor)
		references Proveedores(codigoProveedor),
	foreign key FK_codigoTipoDeProducto (codigoTipoDeProducto )
		references TipoDeProductos (codigoTipoDeProducto )
);

create table Compras(
	compraId int auto_increment,
    fechaCompra date,
    descripcion varchar(60),
    totalCompra decimal(10,2) default 0,
    primary key PK_compraId (compraId)
);


create table DetalleCompra(
	codigoDetalleCompra int auto_increment,
    costoUnitario decimal(10,2) default 0,
    cantidad int,
    productoId int,
    compraId int,
    primary key PK_codigoDetalleCompra (codigoDetalleCompra),
    foreign key FK_detalleCompraProductoId (productoId)
		references Productos (productoId),
	foreign key FK_compraId (compraId)
		references Compras (compraId)
);

create table Cargos(
    cargoId int auto_increment,
    nombreCargo varchar(30),
    descripcionCargo varchar(100),
    primary key PK_cargoId (cargoId)
);

create table Empleados(
	codigoEmpleado int auto_increment,
    nombreEmpleado varchar(30),
    apellidoEmpleado varchar(30),
    sueldo decimal(10,2),
    direccion varchar(150),
    turno varchar(15),
    cargoId int,
    primary key PK_codigoEmpleado (codigoEmpleado),
    foreign key FK_cargo (cargoId)
		references Cargos (cargoId)
);

create table Facturas(
    numeroFactura int auto_increment,
    estado varchar (50),
    totalFactura decimal (10,2) default 0,
    fechaFactura date,
    codigoCliente int,
    codigoEmpleado int,
    primary key PK_numeroFactura (numeroFactura),
    foreign key FK_facturaClienteId (codigoCliente)
		references Clientes (codigoCliente),
	foreign key FK_facturaEmpleadoId (codigoEmpleado)
		references Empleados (codigoEmpleado)
);

create table Detallefactura (
    codigoDetalleFactura int auto_increment,
    precioUnitario decimal(10, 2),
    cantidad int,
	numeroFactura int,
    productoId int,
    Primary key PK_codigoDetalleFactura (codigoDetalleFactura),
    foreign key FK_detalleFacturaId (numeroFactura)
		references Facturas (numeroFactura),
	foreign key FK_detalleFacturaProductoId(productoId)
		references Productos (productoId)
);

-- -------------------------------------------------- CRUD --------------------------------------------------
-- -------------------------------------------------- Clientes --------------------------------------------------
-- Agregar
delimiter $$
create procedure sp_AgregarClientes (
	in NITCliente varchar(10),
    in nombreCliente varchar(50), 
	in apellidoCliente varchar(50),
    in direccionCliente varchar(150),
    in telefonoCliente varchar(15),
    in correoCliente varchar(50))
begin 
	insert into Clientes (NITCliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente) values 
    (NITCliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente);
    end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarClientes ()
begin 
	select
    C.codigoCliente,
    C.NITCliente, 
    C.nombreCliente, 
    C.apellidoCliente, 
    C.direccionCliente, 
    C.telefonoCliente, 
    C.correoCliente
    from Clientes  C;
    end $$
delimiter ;

 -- Buscar
delimiter $$
create procedure sp_BuscarClientes (in codigoCliente int)
begin 
	select 
		C.codigoCliente,
		C.NITCliente, 
		C.nombreCliente, 
		C.apellidoCliente, 
		C.direccionCliente, 
		C.telefonoCliente, 
		C.correoCliente
        from Clientes C
        where codigoCliente = codigoCliente ;
    end $$
delimiter ;
 
-- Eliminar
Delimiter $$
	create procedure sp_EliminarClientes (in codigoCliente int)
		begin 
			delete from Clientes 
            where codigoCliente = codigoCliente;
	end $$
Delimiter;

-- Editar
delimiter $$
create procedure sp_EditarCliente(in codigoCliente_ int, in NITCliente_ varchar(10), in nombreCliente_ varchar(50), 
in apellidoCliente_ varchar(50), in direccionCliente_ varchar(150), in telefonoCliente_ varchar(15), in correoCliente_ varchar(50))
begin 
	Update Clientes  C
	set 		
		C.NITCliente = NITCliente_,
		C.nombreCliente = nombreCliente_,
		C.apellidoCliente = apellidoCliente_, 
		C.telefonoCliente = telefonoCliente_, 
        C.direccionCliente = direccionCliente_,
		C.correoCliente = correoCliente_
	where codigoCliente = codigoCliente_ ;
end $$
delimiter ;

-- -------------------------------------------------- PROVEEDORES --------------------------------------------------
-- Agregar
delimiter $$
create procedure sp_AgregarProveedores (
	in NITProveedor varchar(10),
	in nombreProveedor varchar(60), 
	in apellidoProveedor varchar(60),
    in direccionProveedor varchar(150),
    in razonSocial varchar(60),
    in contactoPrincipal varchar(100),
    in paginaWeb varchar(50))
begin 
	insert into Proveedores (NITProveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb) values 
    (NITProveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb);
    end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarProveedores ()
begin
    select
        P.codigoProveedor,
        P.NITProveedor,
        P.nombreProveedor,
        P.apellidoProveedor,
        P.direccionProveedor,
        P.razonSocial,
        P.contactoPrincipal,
        P.paginaWeb
    from Proveedores P;
end $$
delimiter ;

 -- Buscar
delimiter $$
create procedure sp_BuscarProveedores(in codigoProveedor int)
begin
	select
		P.codigoProveedor,
        P.nitProveedor,
        P.nombreProveedor,
        P.apellidoProveedor,
        P.direccionProveedor,
        P.razonSocial,
        P.contactoPrincipal,
        P.paginaWeb
    from Proveedores P
    where codigoProveedor = codigoProveedor ;
    end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarProveedor(in codigoProveedor int)
	begin
		delete from Proveedores
        where codigoProveedor = codigoProveedor;
	end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarProveedor(in codigoProveedor_ int, in NITProveedor_ varchar(10), in nombreProveedor_ varchar(60), 
in apellidoProveedor_ varchar(60), in direccionProveedor_ varchar(150), in razonSocial_ varchar(60), in contactoPrincipal_ varchar(100), in paginaWeb_ varchar(50))
begin 
			Update Proveedores  P
            set 		
		P.NITProveedor = NITProveedor_,
		P.nombreProveedor = nombreProveedor_,
		P.apellidoProveedor = apellidoProveedor_, 
		P.direccionProveedor = direccionProveedor_, 
        P.razonSocial = razonSocial_,
		P.contactoPrincipal = contactoPrincipal_ ,
        P.paginaWeb = paginaWeb_ 
        where codigoProveedor = codigoProveedor_ ;
end $$
delimiter ;

-- -------------------------------------------------- Telefono Proveedor --------------------------------------------------
-- Agregar
delimiter $$
create procedure sp_AgregarTelefonoProveedor(
	in numeroPrincipal varchar(8),
    in numeroSecundario varchar(8),
    in observaciones varchar(45),
    in codigoProveedor int)
begin
    insert into TelefonoProveedor (numeroPrincipal, numeroSecundario, observaciones, codigoProveedor)
    values (numeroPrincipal, numeroSecundario, observaciones, codigoProveedor);
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarTelefonoProveedor()
begin
    select
        codigoTelefonoProveedor,
        numeroPrincipal,
        numeroSecundario,
        observaciones,
        codigoProveedor
    from TelefonoProveedor;
end $$
delimiter ;

-- Buscar
delimiter $$
create procedure sp_BuscarTelefonoProveedor (in codigoTelefonoProveedor int)
begin
	select
    T.codigoTelefonoProveedor,
    T.numeroPrincipal,
    T.numeroSecundario,
    T.observaciones,
    T.codigoProveedor
    from TelefonoProveedor T 
    where T.codigoTelefonoProveedor = codigoTelefonoProveedor;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarTelefonoProveedor(in codigoTelefonoProveedor int)
begin
    delete from telefonoProveedor 
    where codigoTelefonoProveedor = codigoTelefonoProveedor;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarTelefonoProveedor(
	in codigoTelefonoProveedor int,
	in numeroPrincipal varchar(8),
    in numeroSecundario varchar(8),
    observaciones varchar(45),
    codigoProveedor int)
begin 
	Update TelefonoProveedor T
		set 
			T.numeroPrincipal = numeroPrincipal,
			T.numeroSecundario = numeroSecundario,
			T.observaciones = observaciones,
			T.codigoProveedor = codigoProveedor
        where T.telefonoProveedorId = telefonoProveedorId ;
end $$
delimiter ;

-- -------------------------------------------------- Email Proveedor --------------------------------------------------
-- Agregar
delimiter $$
create procedure sp_AgregarEmailProveedor(
	in emailProveedor varchar(50),
    in descripcion varchar(100),
    in codigoProveedor int)
begin
    insert into EmailProveedor (emailProveedor, descripcion, codigoProveedor)
    values (emailProveedor, descripcion, codigoProveedor);
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarEmailProveedor()
begin
    select
        codigoEmailProveedor,
        emailProveedor,
        descripcion,
        codigoProveedor
    from EmailProveedor;
end $$
delimiter ;

-- Buscar
delimiter $$
create procedure sp_BuscarEmailProveedor (in codigoEmailProveedor int)
begin
	select
    E.codigoEmailProveedor,
    E.emailProveedor,
    E.descripcion,
    E.codigoProveedor
    from EmailProveedor E 
    where E.codigoEmailProveedor = codigoEmailProveedor;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarEmailProveedor(in codigoEmailProveedor int)
begin
    delete from EmailProveedor 
    where codigoEmailProveedor = codigoEmailProveedor;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarEmailProveedor(
	in codigoEmailProveedor int,
	in emailProveedor varchar(50),
    in descripcion varchar(100),
    in codigoProveedor int)
begin 
	Update EmailProveedor E
		set 
			E.emailProveedor = emailProveedor,
			E.descripcion = descripcion,
			E.codigoProveedor = codigoProveedor 
        where E.emailProveedorId = emailProveedorId ;
end $$
delimiter ;

-- -------------------------------------------------- TIPO PRODUCTO --------------------------------------------------
-- Agregar
delimiter $$
create procedure sp_AgregarTipoDeProducto(in descripcion varchar(45))
begin
    insert into TipoDeProductos (descripcion)
    values (descripcion);
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarTipoDeProducto()
begin
    select
        codigoTipoDeProducto,
        descripcion
    from TipoDeProductos;
end $$
delimiter ;

 -- Buscar
delimiter $$
create procedure sp_BuscarTipoDeProducto (in codigoTipoDeProducto int)
begin
	select
    T.codigoTipoDeProducto,
    T.descripcion
    from TipoDeProductos T
    where T.codigoTipoDeProducto = codigoTipoDeProducto ;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarTipoDeProducto(in codigoTipoDeProducto int)
begin
    delete from TipoDeProductos 
    where codigoTipoDeProducto = codigoTipoDeProducto;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarTipoDeProducto(in codigoTipoDeProducto int, in descripcion varchar(45))
begin 
		Update TipoDeProductos  T
		set 		
			T.descripcion = descripcion
        where T.codigoTipoDeProducto = codigoTipoDeProducto;
end $$
delimiter ;

-- -------------------------------------------------- PRODUCTOS --------------------------------------------------
-- Agregar
delimiter $$
create procedure sp_AgregarProductos(
	in descripcionProducto varchar(100),
    in imagenProducto varchar(45),
    in codigoProveedor int,
    in codigoTipoDeProducto int)
begin
    insert into Productos (
        descripcionProducto,
        imagenProducto,
        codigoProveedor,
        codigoTipoDeProducto)
    values (
        descripcionProducto,
        imagenProducto,
        codigoProveedor,
        codigoTipoDeProducto);
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarProductos()
begin
    select
         productoId,
         descripcionProducto,
         precioUnitario,
         precioDocena,
         precioMayor,
         imagenProducto,
         existencia,
         codigoProveedor,
         codigoTipoDeProducto
    from Productos;
end $$
delimiter ;

 -- Buscar
delimiter $$
create procedure sp_BuscarProductos (in  productoId int)
begin
	select
	P.descripcionProducto,
	P.precioUnitario,
	P.precioDocena,
	P.precioMayor,
	P.imagenProducto,
	P.existencia,
	P.codigoProveedor,
	P.codigoTipoDeProducto
    from Productos P
    where P.productoId = productoId;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarProducto(in productoId int)
begin
    delete from Productos 
    where productoId = productoId;
end $$
delimiter ;

         productoId,
         descripcionProducto,
         precioUnitario,
         precioDocena,
         precioMayor,
         imagenProducto,
         existencia,
         codigoProveedor,
         codigoTipoDeProducto
         
-- Editar
delimiter $$
create procedure sp_EditarProducto(
	in productoId int,
	in descripcionProducto varchar(100),
    in precioUnitario decimal(10,2),
    in precioDocena decimal(10,2),
    in precioMayor decimal(10,2),
    in imagenProducto varchar(45),
    in codigoProveedor int,
    in codigoTipoDeProducto int)
begin 
		Update Productos  P
		set 		
		P.descripcionProducto = descripcionProducto,
        P.precioUnitario = precioUnitario,
        P.precioDocena = precioDocena,
		P.precioMayor = precioMayor,
		P.imagenProducto = imagenProducto,
		P.codigoProveedor = codigoProveedor,
		P.codigoTipoDeProducto = codigoTipoDeProducto
        where p.productoId = productoId;
end $$
delimiter ;

-- -------------------------------------------------- COMPRAS --------------------------------------------------
-- Agregar
delimiter $$
create procedure sp_AgregarCompras(
    in fechaCompra date,
    in descripcion varchar(60))
begin
    insert into Compras (fechaCompra, descripcion)
    values (fechaCompra, descripcion);
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarCompras()
begin
    select
        compraId,
        fechaCompra,
        descripcion,
        totalCompra
    from Compras;
end $$
delimiter ;

-- Buscar
delimiter $$
create procedure sp_BuscarCompras (in compraId int)
begin
	select
    C.compraId,
    C.fechaCompra,
    C.descripcion,
    C.totalCompra
    from Compras C
    where C.compraId = compraId ;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarCompras(in compraId int)
begin
    delete from Compras
    where compraId = compraId;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarCompras(
	in compraId int,
    in fechaCompra date,
    in descripcion varchar(60),
    in totalCompra decimal(10,2))
begin 
	Update Compras  C
	set 		
		C.fechaCompra = fechaCompra,
        C.descripcion = descripcion,
        C.totalCompra = totalCompra
	where C.compraId = compraId;
end $$
delimiter ;

-- -------------------------------------------------- DETALLE COMPRA --------------------------------------------------
-- Agregar 
delimiter $$
create procedure sp_AgregarDetalleCompra(
    in costoUnitario decimal(10,2),
    in cantidad int,
    in productoId int,
    in compraId int)
begin
    insert into DetalleCompra (costoUnitario, cantidad, productoId, compraId)
    values (costoUnitario, cantidad, productoId, compraId);
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarDetalleCompra()
begin
	select
    codigoDetalleCompra,
    costoUnitario,
    cantidad,
    productoId,
    compraId
    from DetalleCompra;
end $$
delimiter ;

-- Buscar
delimiter $$
create procedure sp_BuscarDetalleCompra(in codigoDetalleCompra int)
begin
	select
    D.codigoDetalleCompra,
    D.costoUnitario,
    D.cantidad,
    D.productoId,
    D.compraId
    from DetalleCompra D
    where D.codigoDetalleCompra = codigoDetalleCompra ;
end $$
delimiter ;

-- Eliminar 
delimiter $$
create procedure sp_EliminarDetalleCompra(
    in codigoDetalleCompra int
)
begin
    delete from DetalleCompra where codigoDetalleCompra = codigoDetalleCompra;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarDetalleCompra(
	in codigoDetalleCompra int,
    in costoUnitario int,
    in cantidad int,
    in productoId int,
    in compraId int)
begin
    update DetalleCompra D
    set D.costoUnitario = costoUnitario,
        D.cantidad = cantidad,
        D.productoId = productoId,
        D.compraId = compraId
    where D.codigoDetalleCompra = codigoDetalleCompra;
end $$
delimiter ;

-- -------------------------------------------------- CARGOS --------------------------------------------------
-- Agregar
delimiter $$
create procedure sp_AgregarCargos(in nombreCargo varchar(30), in descripcionCargo varchar(100))
begin
    insert into Cargos (nombreCargo, descripcionCargo)
    values (nombreCargo, descripcionCargo);
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarCargos()
begin
    select
        cargoId,
        nombreCargo,
        descripcionCargo
    from Cargos;
end $$
delimiter ;

-- Buscar
delimiter $$
create procedure sp_BuscarCargos (in cargoId int)
begin
	select
    C.cargoId,
    C.nombreCargo,
    C.descripcionCargo
    from Cargos C 
    where cargoId = cargoId ;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarCargo(in cargoId int)
begin
    delete from Cargos 
    where cargoId = cargoId;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarCargo(
	in cargoId int,
    in nombreCargo varchar(30),
	in descripcionCargo varchar(100))
begin 
	Update Cargos  C
	set 		
		C.nombreCargo = nombreCargo,
		C.descripcionCargo = descripcionCargo
	where C.cargoId = cargoId;
end $$
delimiter ;

-- -------------------------------------------------- EMPLEADOS --------------------------------------------------
-- Agregar
delimiter $$
create procedure sp_AgregarEmpleado(
    in nombreEmpleado varchar(30),
    in apellidoEmpleado varchar(30),
    in sueldo decimal(10,2),
    in direccion varchar(150),
    in turno varchar(15),
    in cargoId int
)
begin
    insert into Empleados (nombreEmpleado, apellidoEmpleado, sueldo, direccion, turno, cargoId)
    values (nombreEmpleado, apellidoEmpleado, sueldo, direccion, turno, cargoId);
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarEmpleados()
begin
    select
        codigoEmpleado,
        nombreEmpleado,
        apellidoEmpleado,
        sueldo,
        direccion,
        turno,
        cargoId
    from Empleados;
end $$
delimiter ;

-- Buscar
delimiter $$
create procedure sp_BuscarEmpleados(in codigoEmpleado int)
begin
    select
        E.codigoEmpleado,
        E.nombreEmpleado,
        E.apellidoEmpleado,
        E.sueldo,
        E.direccion,
        E.turno,
        E.cargoId
    from Empleados E
    where E.codigoEmpleado = codigoEmpleado;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarEmpleado(
    in codigoEmpleado int
)
begin
    delete from Empleados where codigoEmpleado = codigoEmpleado;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarEmpleado(
    in codigoEmpleado int,
    in nombreEmpleado varchar(30),
    in apellidoEmpleado varchar(30),
    in sueldo decimal(10,2),
	in direccion varchar(150),
    in turno varchar(15),
    in cargoId int
)
begin
    update Empleados E
    set E.nombreEmpleado = nombreEmpleado,
        E.apellidoEmpleado = apellidoEmpleado,
        E.sueldo = sueldo,
        E.direccion = direccion,
        E.turno = turno,
        E.cargoId = cargoId
    where E.codigoEmpleado = codigoEmpleado;
end $$
delimiter ;

-- -------------------------------------------------- FACTURAS --------------------------------------------------
-- Agregar
delimiter $$
create procedure sp_AgregarFactura(
    in estado varchar (50),
    in fechaFactura date,
    in codigoCliente int,
    in codigoEmpleado int
)
begin
    insert into Facturas (estado, fechaFactura, codigoCliente, codigoEmpleado)
    values (estado, fechaFactura, codigoCliente, codigoEmpleado);
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarFacturas()
begin
    select
        numeroFactura,
        estado,
        totalFactura,
        fechaFactura,
        codigoCliente,
        codigoEmpleado
    from Facturas;
end $$
delimiter ;

-- Buscar
delimiter $$
create procedure sp_BuscarFacturas(in numeroFactura int)
begin
    select
        F.numeroFactura,
        F.estado,
        F.totalFactura,
        F.fechaFactura,
        F.codigoCliente,
        F.codigoEmpleado
    from Facturas F
    where F.numeroFactura = numeroFactura;
end $$
delimiter ;

-- Elimnar
delimiter $$
create procedure sp_EliminarFactura(
    in numeroFactura int
)
begin
    delete from Facturas where numeroFactura = numeroFactura;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarFactura(
    in numeroFactura int,
    in estado varchar (50),
    in totalFactura decimal (10,2),
    in fechaFactura int,
    in codigoCliente int,
    in codigoEmpleado int)
begin
    update Facturas F
    set F.estado = estado,
        F.totalFactura = totalFactura,
        F.fechaFactura = fechaFactura,
        F.codigoCliente = codigoCliente,
        F.codigoEmpleado = codigoEmpleado
    where F.numeroFactura = numeroFactura;
end $$
-- -------------------------------------------------- DETALLE FACTURA --------------------------------------------------
-- Agregar
delimiter $$
create procedure sp_AgregarDetalleFactura(
    in cantidad int,
    in numeroFactura int,
    in productoId int
)
begin
    insert into DetalleFactura (cantidad, numeroFactura, productoId)
    values (cantidad, numeroFactura, productoId);
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarDetalleFacturas()
begin
    select
        codigoDetalleFactura,
        precioUnitario,
        cantidad,
        numeroFactura,
        productoId
    from Detallefactura;
end $$
delimiter ;

-- Bucar
delimiter $$
create procedure sp_BuscarDetalleFacturas(in codigoDetalleFactura int)
begin
    select
        D.codigoDetalleFactura,
        D.precioUnitario,
        D.cantidad,
        D.numeroFactura,
        D.productoId
    from DetalleFactura D
    where D.codigoDetalleFactura = codigoDetalleFactura;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarDetalleFactura(
    in codigoDetalleFactura int
)
begin
    delete from DetalleFactura where codigoDetalleFactura = codigoDetalleFactura;
end $$
delimiter ;

-- Editar
delimiter $$
create procedure sp_EditarDetalleFactura(
    in codigoDetalleFactura int,
    in precioUnitario decimal(10, 2),
    in cantidad int,
    in numeroFactura int,
    in productoId int
)
begin
    update DetalleFactura D
    set D.precioUnitario = precioUnitario,
        D.cantidad = cantidad,
        D.numeroFactura = numeroFactura,
        D.productoId = productoId
    where D.codigoDetalleFactura = codigoDetalleFactura;
end $$
delimiter ;
-- -------------------------------------------------- Triggers --------------------------------------------------
-- --------------------------------------- despues de insertar detalle compra -----------------------------------
delimiter $$
create trigger tr_DetalleCompra_After_Insert after insert on DetalleCompra
for each row
begin
	declare _totalCompra decimal(10,2);
    
    update Productos
    set precioUnitario = (new.costoUnitario * 1.40),
		precioDocena = (new.costoUnitario * 1.35),
        precioMayor = (new.costoUnitario * 1.25),
        existencia  = (new.cantidad)
	where productoId = new.productoId;
    
	select (costoUnitario * cantidad) into _totalCompra
    from DetalleCompra
    where compraId = new.compraId;
    
    update Compras
    set totalCompra = _totalCompra
    where compraId = new.compraId;
end $$
delimiter ;
    
-- --------------------------------------- despues de actualizar detalle compra -----------------------------------
delimiter $$
create trigger tr_DetalleCompra_After_Update after update on DetalleCompra
for each row
begin
	declare _totalCompra decimal(10,2);
    
    update Productos
    set precioUnitario = (new.costoUnitario * 1.40),
		precioDocena = (new.costoUnitario * 1.35),
        precioMayor = (new.costoUnitario * 1.25)
	where productoId = new.productoId;
    
	select (costoUnitario * cantidad) into _totalCompra
    from DetalleCompra
    where compraId = new.compraId;
    
    update Compras
    set totalCompra = _totalCompra
    where compraId = new.compraId;
end $$
delimiter ;

-- --------------------------------------- antes de insertar detalle factura -----------------------------------
delimiter $$
create trigger tr_DetalleFactura_Before_Insert before insert on DetalleFactura
for each row
begin
	declare _precioUnitario decimal(10,2);
    
    if new.cantidad < 12 then
		select precioUnitario into _precioUnitario
		from Productos
		where productoId = new.productoId;
	elseif new.cantidad = 12 then
		select precioDocena into _precioUnitario
		from Productos
		where productoId = new.productoId;
	else 
		select precioMayor into _precioUnitario
		from Productos
		where productoId = new.productoId;
	end if;
    
    set new.precioUnitario = _precioUnitario;
    
    update Facturas
    set totalFactura = (_precioUnitario * new.cantidad)
    where numeroFactura = new.numeroFactura;
end $$
delimiter ;

-- ------------------------------------------------- Inserciones -------------------------------------------------
 
call sp_AgregarClientes ('1245787856', 'harol', 'luna', 'El basurero zona 3', '21215498', 'harolxluna4ever.com');
call sp_AgregarClientes ('1542387485', 'oliver', 'sisimit', 'El basurero km. 22', '45875221', 'xdxsdadasd.com');

call sp_AgregarProveedores ('54785145', 'richardo', 'posadas', 'zona 1','Fruit company', '45875221', 'fruit.gt');
call sp_AgregarProveedores('54785236', 'jose', 'contreras', 'Mixco','The Cocacola Company', '21215498', 'cocacola.com');

call sp_AgregarTelefonoProveedor('12345678', '87654321', 'telefono de casa y oficina', 1);
call sp_AgregarTelefonoProveedor('90657890', '34656753', 'telefono de oficina 1 y oficina 2', 2);

call sp_AgregarEmailProveedor('asdasd@gmail.com', 'correo personal', 1);
call sp_AgregarEmailProveedor('esotilin@gmail.com', 'correo de empresa', 2);

call sp_AgregarTipoDeProducto ('Cereal dietetico');
call sp_AgregarTipoDeProducto ('PROTEIN POWDER');

call sp_AgregarProductos ('Cereal alto en proteinas', '1000101010101010101', 1, 1);
call sp_AgregarProductos ('Leche chocolatada alto en azucares', '10101010101010', 2, 2);

call sp_AgregarCompras ('10-05-24','5 cereales, 10 atunes');
call sp_AgregarCompras ('04-12-23','bote de proteina en polvo');

call sp_AgregarDetalleCompra(12.5, 2, 1, 1);
call sp_AgregarDetalleCompra(10.5, 3, 2, 2);

call sp_AgregarCargos ('gerente', 'gerente de ventas');
call sp_AgregarCargos ('ingeniero artillero', 'experto en explosivos');

call sp_AgregarEmpleado('Sebastian', 'mendez', 5324, 'zona 18', '12 PM - 6 PM', 1);
call sp_AgregarEmpleado('Jim', 'Del Cid', 7324, 'zona 5', '9 AM - 3 PM', 2);

call sp_AgregarFactura('acitva', '2012-12-12', 1, 1);
call sp_AgregarFactura('acitva', '2023-6-1', 2, 2);

call sp_AgregarDetalleFactura(1, 1, 1);
call sp_AgregarDetalleFactura(2, 2, 2);

call sp_ListarClientes ();

call sp_ListarProveedores ();

call sp_ListarTelefonoProveedor();

call sp_ListarEmailProveedor();

call sp_ListarTipoDeProducto ();

call sp_ListarProductos ();

call sp_ListarCompras ();

call sp_ListarDetalleCompra();

call sp_ListarCargos ();

call sp_ListarEmpleados();

call sp_ListarFacturas();

call sp_ListarDetalleFacturas();