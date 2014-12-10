use nomina1;

create table estado_civil (id integer primary key, tipo varchar(25));

create table tipo_usuarios (id integer primary key, nombre varchar(20));

create table usuarios(id integer auto_increment, usuario varchar(20) not null, clave varchar(50) not null, tipo_usuario integer, foreign key (tipo_usuario) references tipo_usuarios(id), unique (id), primary key (usuario));

create table usuario_pers (id_usuario varchar(20) not null, nombre varchar(50) not null, apellido varchar(50) not null, correo varchar(100), foreign key (id_usuario) references usuarios(usuario) ON DELETE CASCADE);

-- Llenandoooo!

insert into tipo_usuarios (id, nombre) values (1, 'Administrador');

insert into tipo_usuarios (id, nombre) values (2, 'Contable');

insert into usuarios (usuario, clave, tipo_usuario) values ('admin', '1234', 1);
insert into usuario_pers values ('admin', 'Administrador', 'Doe', 'admin@administrador.com');


-- modulo de empresa

create table empresa (id integer auto_increment, nombre varchar (25), rnc varchar(25), primary key (id));

select * from empresa;
insert into empresa values (1, 'CROOM', '101132671');

create table tipo_salario (id integer, nombre varchar(25), factor decimal(5,2), primary key (id));

create table departamento (id integer primary key, id_empresa integer, nombre varchar(25), 
foreign key (id_empresa) references empresa(id));

create table cargo (id integer, nombre varchar(25), primary key (id));

create table cargo_salario (id_cargo integer, monto numeric, foreign key (id_cargo) references
cargo(id));

create table estados (id varchar(1) primary key, nombre varchar(25));

create table empleado_admin (id integer, fecha_ingreso date, id_departamento integer, id_cargo integer, tipo_salario integer,
id_estado varchar(1), foreign key (id_departamento) references departamento(id), foreign key (id_cargo) references cargo(id), foreign key (tipo_salario) references 
tipo_salario(id), primary key (id), foreign key (id_estado) references estados(id));

create table empleado_personal (id_empleado integer, cedula varchar(11) not null, nombre varchar(50),
apellido varchar(50), direccion varchar(100), sexo enum ('m', 'f'), estado_civil enum ('soltero', 'divorciado','casado','viudo'), nacimiento date,
email varchar(100), movil varchar(10), telefono varchar(10), foreign key (id_empleado) references empleado_admin(id));


create table dependientes (id integer auto_increment, id_empleado integer, nombre varchar(50), apellido varchar(50), cedula varchar(11) not null, tipo_dependiente enum ('hijo','conyuge','padre','madre','abuelo','abuela'), 
foreign key (id_empleado) references empleado_admin(id), primary key (id));

create table tipos_ingresos (id integer primary key, nombre varchar(20));

create table tipos_descuentos(id integer primary key, nombre varchar(20));

create table ingresos_emp( id integer auto_increment primary key, id_empleado integer, tipo_ingreso integer, monto float(2), foreign key (id_empleado) references empleado_admin(id),
foreign key (tipo_ingreso) references tipos_ingresos(id));

create table deduccioness_emp( id integer auto_increment primary key, id_empleado integer, tipo_deduccion integer, monto float(2), estado varchar(1), foreign key (id_empleado) references empleado_admin(id),
foreign key (tipo_deduccion) references tipos_descuentos(id), foreign key (estado) references estados(id));

create table mes (id integer primary key, nombre varchar(20));

create table nomina (id integer auto_increment primary key, id_empleado integer, fecha datetime, s_ingresos float(2), s_deducciones float (2), s_neto float(2));

-- el historico aun esta dudoso
create table historico (id_empleado integer, id_trans_ingreso integer, id_trans_deduccion integer, fecha datetime, foreign key (id_empleado) references empleado_admin(id), foreign key (id_trans_ingreso) references ingresos_emp(id), foreign key (id_trans_deduccion) references deducciones_emp(id));

create view cargo_monto as select cargo.id, cargo.nombre, cargo_salario.monto from cargo,cargo_salario where cargo.id = cargo_salario.id_cargo;