drop database if exists DBMercaditoKinalito;
create database DBMercaditoKinalito;
use DBMercaditoKinalito;
 
 set global time_Zone = '-6:00';
-- Creacion de Entidades
create table Clientes(
	codigoCliente int not null,
	NITCliente varchar(10) not null,
	nombreCliente varchar(50) not null,
	apellidoCliente varchar(50) not null,
	direccionCliente varchar(150) not null,
	telefonoCliente varchar(15) not null,
	correoCliente varchar(50) not null,
	primary key PK_codigoCliente(codigoCliente)
);
 
create table Proveedores(
	codigoProveedor int not null,
    NITproveedor varchar(10) not null,
    nombreProveedor varchar (60),
    apellidoProveedor varchar (60),
    direccionProveedor varchar (150),
    razonSocial varchar (60),
    contactoPrincipal varchar (100),
    paginaWeb varchar (50),
    primary key PK_codigoProveedor (codigoProveedor)
);


create table CategoriaProductos(
	categoriaProductosId int,
    nombreCategoria varchar(30),
    descripcionCategoria varchar(30),
    primary key PK_categoriaProductosId (categoriaProductosId)
);

create table TipoDeProductos(
	codigoTipoDeProducto int,
    descripcion varchar(45),
    primary key PK_codigoTipoDeProducto(codigoTipoDeProducto)
);
create table Productos (
    productoId int,
    descripcionProducto varchar(100),
    precioUnitario decimal(10,2),
    precioDocena decimal(10,2),
    precioMayor decimal(10,2),
    imagenProducto varchar (45),
    existencia int,
    codigoProveedor int,
    codigoTipoDeProducto int,
    primary key PK_productoId (productoId),
    foreign key FK_codigoProveedor (codigoProveedor)
		references Proveedores(codigoProveedor),
	foreign key FK_codigoTipoDeProducto (codigoTipoDeProducto )
		references TipoDeProductos (codigoTipoDeProducto )
);


create table Promociones (
    promocionId int,
    precioPromocion decimal(10,2),
    descripcionPromocion varchar(100),
    fechaInicio date,
    fechaFinalizacion date,
    productoId int,
    primary key PK_promocionId (promocionId),
    foreign key FK_productoId (productoId)
		references Productos (productoId)
);

create table Compras(
	compraId int,
    fechaCompra date,
    descripcion varchar(60),
    totalCompra decimal(10,2),
    primary key PK_compraId (compraId)
);


create table DetalleCompra(
	detalleCompraId int,
    cantidadCompra int,
    productoId int,
    compraId int,
    primary key PK_detalleCompraId (detalleCompraId),
    foreign key FK_detalleCompraProductoId (productoId)
		references Productos (productoId),
	foreign key FK_compraId (compraId)
		references Compras (compraId)
);

create table Cargos(
    cargoId int,
    nombreCargo varchar(30),
    descripcionCargo varchar(100),
    primary key PK_cargoId (cargoId)
);

create table Empleados(
	empleadoId int,
    nombreEmpleado varchar(30),
    apellidoEmpleado varchar(30),
    sueldo decimal(10,2),
    horaEntrada time,
    horaSalida time,
    cargoId int,
    encargadoId int,
    primary key PK_empleadoId (empleadoId),
    foreign key FK_cargo (cargoId)
		references Cargos (cargoId),
	foreign key FK_encargadoId (encargadoId)
		references Empleados (empleadoId)
);

create table Facturas(
    facturaId int,
    fecha date,
    hora time,
    clienteId int,
    empleadoId int,
    total decimal(10,2),
    primary key PK_facturaId (facturaId),
    foreign key FK_facturaClienteId (clienteId)
		references Clientes (codigoCliente),
	foreign key FK_empleadoId (empleadoId)
		references Empleados (empleadoId)
);


create table Ticketsoprte (
    ticketSoporteId int,
    descripcionTicket varchar(250),
    estatus varchar(30),
    clienteId int,
    facturaId int,
    primary key PK_ticketSoporteId (ticketSoporteId),
    foreign key FK_clienteId (clienteId)
		references Clientes (codigoCliente),
	foreign key FK_facturaId (facturaId)
		references Facturas (facturaId)
);

create table detallefactura (
    detalleFacturaId int(11),
    facturaId int(11),
    productoId int(11),
    Primary key PK_detalleFacturaId (detalleFacturaId),
    foreign key FK_detalleFacturaFacturaId (facturaId)
		references Facturas (facturaId),
	foreign key FK_detalleFacturaProductoId(productoId)
		references Productos (productoId)
);

-- CRUD
-- Clientes
-- Agregar
delimiter $$
 
create procedure sp_AgregarClientes (in codigoCliente int, in NITCliente varchar(10), in nombreCliente varchar(50), 
in apellidoCliente varchar(50), in direccionCliente varchar(150), in telefonoCliente varchar(15), in correoCliente varchar(50))
begin 
	insert into Clientes (codigoCliente, NITCliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente) values 
    (codigoCliente, NITCliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente);
    end $$
 
delimiter ;
 
call sp_AgregarClientes (01, '1245787856', 'harol', 'luna', 'El basurero zona 3', '21215498', 'harolxluna4ever.com');
call sp_AgregarClientes (02, '1542387485', 'oliver', 'sisimit', 'El basurero km. 22', '45875221', 'xdxsdadasd.com');
 
delimiter $$
 
-- Listar
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
 
call sp_ListarClientes ();
 
delimiter $$
 -- Buscar
create procedure sp_BuscarClientes (in cLID int)
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
        where codigoCliente = cLID ;
    end $$
delimiter ;
 
call sp_BuscarClientes (1);
call sp_BuscarClientes (2);
 
-- Eliminar
Delimiter $$
	create procedure sp_EliminarClientes (in codCLI int)
		begin 
			delete from Clientes 
            where codigoCliente = codCLI;
	end $$
Delimiter;
 
call sp_EliminarClientes (1);
 
 

 
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


delimiter $$
create procedure sp_AgregarProveedores (in codigoProveedor int, in NITProveedor varchar(10), in nombreProveedor varchar(60), 
in apellidoProveedor varchar(60), in direccionProveedor varchar(150), in razonSocial varchar(60), in contactoPrincipal varchar(100),  in paginaWeb varchar(50))
begin 
	insert into Proveedores (codigoProveedor, NITProveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb) values 
    (codigoProveedor, NITProveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb);
    end $$
 
delimiter ;


call sp_AgregarProveedores (02, '54785145', 'richardo', 'posadas', 'zona 1','Fruit company', '45875221', 'fruit.gt');
call sp_AgregarProveedores(03, '54785236', 'jose', 'contreras', 'Mixco','The Cocacola Company', '21215498', 'cocacola.com');
 

-- Actualizar
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

call sp_ListarProveedores ();


delimiter $$

create procedure sp_BuscarProveedores(in cPID int)
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
    where codigoProveedor = cPID ;
    end $$
delimiter ;

call sp_BuscarProveedores (2);
call sp_BuscarProveedores (3);

-- Eliminar
delimiter $$
create procedure sp_EliminarProveedor(in codPro int)
	begin
		delete from Proveedores
        where codigoProveedor = codPro;
	end $$
delimiter ;

call sp_EliminarProveedor (1);

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





delimiter $$
create procedure sp_AgregarCargos(in cargoId int, in nombreCargo varchar(30), in descripcionCargo varchar(100))
begin
    insert into Cargos (cargoId, nombreCargo, descripcionCargo)
    values (cargoId, nombreCargo, descripcionCargo);
end $$
delimiter ;

call sp_AgregarCargos (02, 'factura', 'pagar las facturas');
call sp_AgregarCargos (03, 'mandado', 'regoger los productos faltantes');


-- Listar
delimiter $$
create procedure sp_ListarCargos()
begin
    select
        cargoId,
        nombreCargo,
        descripcionCargo
    from Cargos S;
end $$
delimiter ;

call sp_ListarCargos ();

-- Actualizar
delimiter $$
create procedure sp_BuscarCargos (in cCID int)
begin
	select
    S.cargoId,
    S.nombreCargo,
    S.descripcionCargo
    from Cargos S 
    where cargoId = cCID ;
end $$
delimiter ;

call sp_BuscarCargos (2);
call sp_BuscarCargos (3);
    

-- Eliminar
delimiter $$
create procedure sp_EliminarCargo(in codCAR int)
begin
    delete from Cargos 
    where cargoId = codCAR;
end $$
delimiter ;

call sp_EliminarCargo (1);

delimiter $$
create procedure sp_EditarCargo(in cargoId_ int, in nombreCargo_ varchar(30), in descripcionCargo_ varchar(100))
begin 
			Update Cargos  S
            set 		
		S.nombreCargo = nombreCargo_,
		S.descripcionCargo = descripcionCargo_
        where cargoId = cargoId_ ;
end $$
delimiter ;


delimiter $$
create procedure sp_AgregarTipoDeProducto(in codigoTipoDeProducto int, in descripcion varchar(35))
begin
    insert into TipoDeProductos (codigoTipoDeProducto, descripcion)
    values (codigoTipoDeProducto, descripcion);
end $$
delimiter ;

call sp_AgregarTipoDeProducto (02, 'Cereal dietetico');
call sp_AgregarTipoDeProducto (03, 'PROTEIN POWDER');


-- Listar
delimiter $$
create procedure sp_ListarTipoDeProducto()
begin
    select
        codigoTipoDeProducto,
        descripcion
    from TipoDeProductos T;
end $$
delimiter ;

call sp_ListarTipoDeProducto ();

-- Actualizar
delimiter $$
create procedure sp_BuscarTipoDeProducto (in cTPO int)
begin
	select
    T.codigoTipoDeProducto,
    T.descripcion
    from TipoDeProductos T
    where codigoTipoDeProducto = cTPO ;
end $$
delimiter ;

call sp_BuscarTipoDeProducto (2);
call sp_BuscarTipoDeProducto (3);
    

-- Eliminar
delimiter $$
create procedure sp_EliminarTipoDeProducto(in codTPO int)
begin
    delete from TipoDeProductos 
    where codigoTipoDeProducto = codTPO;
end $$
delimiter ;

call sp_EliminarTipoDeProducto (1);

delimiter $$
create procedure sp_EditarTipoDeProducto(in codigoTipoDeProducto_ int, in descripcion_ varchar(30))
begin 
			Update TipoDeProductos  T
            set 		
		T.descripcion = descripcion_
        where codigoTipoDeProducto = codigoTipoDeProducto_ ;
end $$
delimiter ;


delimiter $$
create procedure sp_AgregarCompras(in compraId int, in fechaCompra date, in descripcion varchar (60), totalCompra decimal(10,2))
begin
    insert into Compras (compraId, fechaCompra, descripcion, totalCompra)
    values (compraId, fechaCompra, descripcion, totalCompra);
end $$
delimiter ;

call sp_AgregarCompras (02, '10-05-24','5 cereales, 10 atunes', 59.99);
call sp_AgregarCompras (03, '04-12-23','bote de proteina en polvo', 39.99);


-- Listar
delimiter $$
create procedure sp_ListarCompras()
begin
    select
        compraId,
        fechaCompra,
        descripcion,
        totalCompra
    from Compras O;
end $$
delimiter ;

call sp_ListarCompras ();

-- Actualizar
delimiter $$
create procedure sp_BuscarCompras (in cCOM int)
begin
	select
    O.compraId,
    O.fechaCompra,
    O.descripcion,
    O.totalCompra
    from Compras O
    where compraId = cCOM ;
end $$
delimiter ;

call sp_BuscarCompras (2);
call sp_BuscarCompras (3);
    

-- Eliminar
delimiter $$
create procedure sp_EliminarCompras(in codCOM int)
begin
    delete from Compras
    where compraId = codCOM;
end $$
delimiter ;

call sp_EliminarCompras (1);

delimiter $$
create procedure sp_EditarCompras(in compraId_ int, in fechaCompra_ date, in descripcion_ varchar (60), in totalCompra_ decimal (10,2))
begin 
			Update Compras  O
            set 		
		O.fechaCompra = fechaCompra_,
        O.descripcion = descripcion_,
        O.totalCompra = totalCompra_
        where compraId = compraId_ ;
end $$
delimiter ;


delimiter $$
create procedure sp_AgregarProductos(in productoId int, in descripcionProducto varchar(100), in precioUnitario decimal(10,2), in precioDocena decimal(10,2), in precioMayor decimal(10,2), in imagenProducto varchar(45), in existencia int, in codigoProveedor int, in codigoTipoDeProducto int)
begin
    insert into Productos (productoId, descripcionProducto, precioUnitario, precioDocena, precioMayor, imagenProducto, existencia, codigoProveedor, codigoTipoDeProducto)
    values (productoId, descripcionProducto, precioUnitario, precioDocena, precioMayor, imagenProducto, existencia, codigoProveedor, codigoTipoDeProducto);
end $$
delimiter ;

call sp_AgregarProductos (02, 'Cereal alto en proteinas', 25.00, 300.00, 50.00, 'nose que poner aqiui xd', 12, 02, 03);
call sp_AgregarProductos (03, 'Leche chocolatada alto en azucares', 12.00, 144.00, 20.00, 'nose q poner aqiui xd', 07, 03, 02);


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
    from Productos R;
end $$
delimiter ;

call sp_ListarProductos ();

-- Actualizar
delimiter $$
create procedure sp_BuscarProductos (in  cPrs int)
begin
	select
	R.descripcionProducto,
	R.precioUnitario,
	R.precioDocena,
	R.precioMayor,
	R.imagenProducto,
	R.existencia,
	R.codigoProveedor,
	R.codigoTipoDeProducto
    from Productos R
    where productoId = cPrs ;
end $$
delimiter ;

call sp_BuscarProductos (2);
call sp_BuscarProductos (3);
    

-- Eliminar
delimiter $$
create procedure sp_EliminarProducto(in codPrs int)
begin
    delete from Productos 
    where productoId = codPrs;
end $$
delimiter ;

call sp_EliminarProducto (1);

delimiter $$
create procedure sp_EditarProducto(in productoId_ int, in descripcionProducto_ varchar(100), in precioUnitario_ decimal(10,2), in precioDocena_ decimal(10,2), in precioMayor_ decimal(10,2), in imagenProducto_ varchar(45), in existencia_ int, in codigoProveedor_ int, in codigoTipoDeProducto_ int)
begin 
			Update Productos  R
            set 		
		R.descripcionProducto = descripcionProducto_,
		R.precioUnitario = precioUnitario_,
		R.precioDocena = precioDocena_,
		R.precioMayor = precioMayor_,
		R.imagenProducto = imagenProducto_,
		R.existencia = existencia_,
		R.codigoProveedor = codigoProveedor_,
		R.codigoTipoDeProducto = codigoTipoDeProducto_
        where productoId = productoId_ ;
end $$
delimiter ;














-- CategoriaProductos
-- Crear
delimiter $$
create procedure sp_CrearCategoriaProducto(
    in categoriaProductosId int,
    in nombreCategoria varchar(30),
    in descripcionCategoria varchar(30)
)
begin
    insert into CategoriaProductos (categoriaProductosId, nombreCategoria, descripcionCategoria)
    values (categoriaProductosId, nombreCategoria, descripcionCategoria);
end $$
delimiter ;

-- Actualizar
delimiter $$
create procedure sp_ActualizarCategoriaProducto(
    in categoriaProductosId int,
    in nombreCategoria varchar(30),
    in descripcionCategoria varchar(30)
)
begin
    update CategoriProductos C
    set C.nombreCategoria = nombreCategoria,
        C.descripcionCategoria = descripcionCategoria
    where C.categoriaProductosId = categoriaProductosId;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarCategoriaProducto(
    in categoriaProductosId int
)
begin
    delete from CategoriaProductos where categoriaProductosId = categoriaProductosId;
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarCategoriaProductos()
begin
    select
        categoriaProductosId,
        nombreCategoria,
        descripcionCategoria
    from CategoriaProductos;
end $$
delimiter ;

-- productos
-- Crear

-- promociones
-- Crear
delimiter $$
create procedure sp_CrearPromocion(
    in promocionId int,
    in precioPromocion decimal(10,2),
    in descripcionPromocion varchar(100),
    in fechaInicio date,
    in fechaFinalizacion date,
    in productoId int
)
begin
    insert into Promociones (promocionId, precioPromocion, descripcionPromocion, fechaInicio, fechaFinalizacion, productoId)
    values (promocionId, precioPromocion, descripcionPromocion, fechaInicio, fechaFinalizacion, productoId);
end $$
delimiter ;

-- Actualizar 
delimiter $$
create procedure sp_ActualizarPromocion(
    in promocionId int,
    in precioPromocion decimal(10,2),
    in descripcionPromocion varchar(100),
    in fechaInicio date,
    in fechaFinalizacion date,
    in productoId int
)
begin
    update Promociones P
    set P.precioPromocion = precioPromocion,
        P.descripcionPromocion = descripcionPromocion,
        P.fechaInicio = fechaInicio,
        P.fechaFinalizacion = fechaFinalizacion,
        P.productoId = productoId
    where P.promocionId = promocionId;
end $$
delimiter ;

-- Eliminar 
delimiter $$
create procedure sp_EliminarPromocion(
    in promocionId int
)
begin
    delete from Promociones where promocionId = promocionId;
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarPromociones()
begin
    select
        promocionId,
        precioPromocion,
        descripcionPromocion,
        fechaInicio,
        fechaFinalizacion,
        productoId
    from Promociones;
end $$
delimiter ;

delimiter $$
create procedure sp_CrearDetalleCompra(
    in detalleCompraId int,
    in cantidadCompra int,
    in productoId int,
    in compaId int
)
begin
    insert into DetalleCompra (detalleCompraId, cantidadCompra, productoId, compaId)
    values (detalleCompraId, cantidadCompra, productoId, compaId);
end $$
delimiter ;

-- Actualizar
delimiter $$
create procedure sp_ActualizarDetalleCompra(
    in detalleCompraId int,
    in cantidadCompra int,
    in productoId int,
    in compaId int
)
begin
    update DetalleCompra D
    set D.cantidadCompra = cantidadCompra,
        D.productoId = productoId,
        D.compaId = compaId
    where D.detalleCompraId = detalleCompraId;
end $$
delimiter ;
-- Eliminar 
delimiter $$
create procedure sp_EliminarDetalleCompra(
    in detalleCompraId int
)
begin
    delete from DetalleCompra where detalleCompraId = detalleCompraId;
end $$
delimiter ;



-- Empleados
-- Crear
delimiter $$
create procedure sp_CrearEmpleado(
    in empleadoId int,
    in nombreEmpleado varchar(30),
    in apellidoEmpleado varchar(30),
    in sueldo decimal(10,2),
    in horaEntrada time,
    in horaSalida time,
    in cargoId int,
    in encargadoId int
)
begin
    insert into Empleados (empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId, encargadoId)
    values (empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId, encargadoId);
end $$
delimiter ;

-- Actualizar
delimiter $$
create procedure sp_ActualizarEmpleado(
    in empleadoId int,
    in nombreEmpleado varchar(30),
    in apellidoEmpleado varchar(30),
    in sueldo decimal(10,2),
    in horaEntrada time,
    in horaSalida time,
    in cargoId int,
    in encargadoId int
)
begin
    update Empleados E
    set E.nombreEmpleado = nombreEmpleado,
        E.apellidoEmpleado = apellidoEmpleado,
        E.sueldo = sueldo,
        E.horaEntrada = horaEntrada,
        E.horaSalida = horaSalida,
        E.cargoId = cargoId,
        E.encargadoId = encargadoId
    where E.empleadoId = empleadoId;
end $$
delimiter ;

-- Eliminar
delimiter $$
create procedure sp_EliminarEmpleado(
    in empleadoId int
)
begin
    delete from Empleados where empleadoId = empleadoId;
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarEmpleados()
begin
    select
        empleadoId,
        nombreEmpleado,
        apellidoEmpleado,
        sueldo,
        horaEntrada,
        horaSalida,
        cargoId,
        encargadoId
    from Empleados;
end $$
delimiter ;

-- Facturas
-- Crear
delimiter $$
create procedure sp_CrearFactura(
    in facturaId int,
    in fecha date,
    in hora time,
    in clienteId int,
    in empleadoId int,
    in total decimal(10,2)
)
begin
    insert into Facturas (facturaId, fecha, hora, clienteId, empleadoId, total)
    values (facturaId, fecha, hora, clienteId, empleadoId, total);
end $$
delimiter ;

-- Actualizar
delimiter $$
create procedure sp_ActualizarFactura(
    in facturaId int,
    in fecha date,
    in hora time,
    in clienteId int,
    in empleadoId int,
    in total decimal(10,2)
)
begin
    update Facturas F
    set F.fecha = fecha,
        F.hora = hora,
        F.clienteId = clienteId,
        F.empleadoId = empleadoId,
        F.total = total
    where facturaId = facturaId;
end $$
delimiter ;

-- Elimnar
delimiter $$
create procedure sp_EliminarFactura(
    in facturaId int
)
begin
    delete from Facturas where facturaId = facturaId;
end $$
delimiter ;

-- Listar
delimiter $$
create procedure sp_ListarFacturas()
begin
    select
        facturaId,
        fecha,
        hora,
        clienteId,
        empleadoId,
        total
    from Facturas;
end $$
delimiter ;

-- TicketsSoporte

delimiter $$
create procedure sp_CrearTicketSoporte(
    in ticketSoporteId int,
    in descripcionTicket varchar(250),
    in estatus varchar(30),
    in clienteId int,
    in facturaId int
)
begin
    insert into TicketsSoporte (ticketSoporteId, descripcionTicket, estatus, clienteId, facturaId)
    values (ticketSoporteId, descripcionTicket, estatus, clienteId, facturaId);
end $$
delimiter ;

delimiter $$
create procedure sp_ActualizarTicketSoporte(
    in ticketSoporteId int,
    in descripcionTicket varchar(250),
    in estatus varchar(30),
    in clienteId int,
    in facturaId int
)
begin
    update TicketsSoporte T
    set T.descripcionTicket = descripcionTicket,
        T.estatus = estatus,
        T.clienteId = clienteId,
        T.facturaId = facturaId
    where T.ticketSoporteId = ticketSoporteId;
end $$
delimiter ;

delimiter $$
create procedure sp_EliminarTicketSoporte(
    in ticketSoporteId int
)
begin
    delete from TicketsSoporte where ticketSoporteId = ticketSoporteId;
end $$
delimiter ;

delimiter $$
create procedure sp_ListarTicketsSoporte()
begin
    select
        ticketSoporteId,
        descripcionTicket,
        estatus,
        clienteId,
        facturaId
    from TicketsSoporte;
end $$
delimiter ;

-- DetalleFactura
delimiter $$
create procedure sp_CrearDetalleFactura(
    in detalleFacturaId int,
    in facturaId int,
    in productoId int
)
begin
    insert into DetalleFactura (detalleFacturaId, facturaId, productoId)
    values (detalleFacturaId, facturaId, productoId);
end $$
delimiter ;

delimiter $$
create procedure sp_ActualizarDetalleFactura(
    in detalleFacturaId int,
    in facturaId int,
    in productoId int
)
begin
    update DetalleFactura D
    set D.facturaId = facturaId,
        D.productoId = productoId
    where D.detalleFacturaId = detalleFacturaId;
end $$
delimiter ;


delimiter $$
create procedure sp_EliminarDetalleFactura(
    in detalleFacturaId int
)
begin
    delete from DetalleFactura where detalleFacturaId = detalleFacturaId;
end $$
delimiter ;


delimiter $$
create procedure sp_ListarDetalleFacturas()
begin
    select
        detalleFacturaId,
        facturaId,
        productoId
    from DetalleFactura;
end $$
delimiter ;


