select * from cargo;

insert into cargo(nombre,monto) values('CTO.1',400000);
insert into cargo(nombre,monto) values('CTO.2',500000);
insert into cargo(nombre,monto) values('CTO.3',600000);
insert into cargo(nombre,monto) values('CTO.4',700000);
insert into cargo(nombre,monto) values('CTO.5',800000);
insert into cargo(nombre,monto) values('CTO.6',900000);
insert into cargo(nombre,monto) values('CTO.7',1000000);

select * from impuesto;



insert into impuesto(nombre,factor) values('SFS',0.0304);
insert into impuesto(nombre,factor) values('AFP',0.0287);
insert into impuesto(nombre,factor,desde_salario,hasta_salario) values('ISR-Primer Rango',0,0,399923.00 );
insert into impuesto(nombre,factor,desde_salario,hasta_salario) values('ISR-Segundo Rango',0.15,399923.01,599884.00 );
insert into impuesto(nombre,factor,desde_salario,hasta_salario,exceso) values('ISR-Tercer Rango',0.20,599884.01,833171.00,29994.00 );
insert into impuesto(nombre,factor,desde_salario,hasta_salario,exceso) values('ISR-Cuarto Rango',0.25,833171.01,0,76652.00 );


select * from tipo_salario;
insert into tipo_salario(nombre,factor) values('mensual',0.1);


select * from empresa;
insert into empresa(nombre,rnc) values ('empresa1','21654165');

select * from departamento;

insert into departamento(id_empresa,nombre) values(2,'IT');
-- El 2 es por el id de una de las empresas insertadas.action


select * from estados;
insert into estados(nombre) values('activo');
insert into estados(nombre) values('inactivo');




select * from empleado_admin;
insert into empleado_admin(id_departamento,id_cargo,tipo_salario,id_estado) values(1,1,1,1);
insert into empleado_admin(id_departamento,id_cargo,tipo_salario,id_estado) values(1,2,1,1);
insert into empleado_admin(id_departamento,id_cargo,tipo_salario,id_estado) values(1,3,1,1);
insert into empleado_admin(id_departamento,id_cargo,tipo_salario,id_estado) values(1,4,1,1);
insert into empleado_admin(id_departamento,id_cargo,tipo_salario,id_estado) values(1,5,1,1);
insert into empleado_admin(id_departamento,id_cargo,tipo_salario,id_estado) values(1,6,1,1);
insert into empleado_admin(id_departamento,id_cargo,tipo_salario,id_estado) values(1,7,1,1);

select * from tipos_ingresos;
insert into tipos_ingresos(nombre) values('Ingreso 1');


insert into empleado_admin(fecha_ingreso,id_departamento,id_cargo,tipo_salario,id_estado) values ('2008-05-01',1,1,1,1);

select * from tipos_descuentos;
insert into tipos_descuentos(nombre) values('Deduccion 1');


select * from ingresos_emp;
insert into ingresos_emp(id_empleado,tipo_ingreso,monto) values(1,1,500);
insert into ingresos_emp(id_empleado,tipo_ingreso,monto) values(2,1,500);
insert into ingresos_emp(id_empleado,tipo_ingreso,monto) values(3,1,500);
insert into ingresos_emp(id_empleado,tipo_ingreso,monto) values(1,1,1500);
insert into ingresos_emp(id_empleado,tipo_ingreso,monto) values(2,1,500);
insert into ingresos_emp(id_empleado,tipo_ingreso,monto) values(3,1,500);
insert into ingresos_emp(id_empleado,tipo_ingreso,monto) values(4,1,700);
insert into ingresos_emp(id_empleado,tipo_ingreso,monto) values(5,1,500);
insert into ingresos_emp(id_empleado,tipo_ingreso,monto) values(6,1,500);
insert into ingresos_emp(id_empleado,tipo_ingreso,monto) values(7,1,500);
insert into ingresos_emp(id_empleado,tipo_ingreso,monto) values(1,1,300);
insert into ingresos_emp(id_empleado,tipo_ingreso,monto) values(1,1,800);



select * from ingresos_emp where id_empleado = 1;
select ti.nombre ingreso, ie.monto from ingresos_emp ie join tipos_ingresos ti on ie.tipo_ingreso = ti.id where ie.id_empleado = 1;



select * from deduccioness_emp;
insert into deduccioness_emp(id_empleado,tipo_deduccion,monto,estado) values(1,1,200,1);
insert into deduccioness_emp(id_empleado,tipo_deduccion,monto,estado) values(1,1,500,1);
insert into deduccioness_emp(id_empleado,tipo_deduccion,monto,estado) values(2,1,400,1);
insert into deduccioness_emp(id_empleado,tipo_deduccion,monto,estado) values(3,1,200,1);
insert into deduccioness_emp(id_empleado,tipo_deduccion,monto,estado) values(4,1,200,1);

select*from empresa;
insert into empresa(nombre,rnc) values ('CROOM', '101027797');




SELECT NOMBRE,MONTO FROM EMPLEADO_ADMIN EA JOIN CARGO C ON EA.ID_CARGO = C.ID WHERE EA.ID = 1;


insert into tipo_usuarios(nombre)  values ('administrador');
insert into tipo_usuarios(nombre) values ('contable');

insert into usuario_pers values ('luis', 'luisa','martinez','lm@outlook.com');

select * from tipo_usuarios;
select * from empresa;

select max(id) from empleado_admin;

select tipos_ingresos.nombre, ingresos_emp.tipo_ingreso, ingresos_emp.monto, ingresos_emp.id_empleado,ingresos_emp.id from tipos_ingresos,ingresos_emp where tipos_ingresos.id = ingresos_emp.tipo_ingreso and ingresos_emp.id_empleado = 4;

select tipos_descuentos.nombre, deduccioness_emp.tipo_deduccion, deduccioness_emp.monto, deduccioness_emp.id_empleado, deduccioness_emp.id, deduccioness_emp.estado 
from tipos_descuentos, deduccioness_emp 
where tipos_descuentos.id = deduccioness_emp.tipo_deduccion and deduccioness_emp.id_empleado = 4;


insert into tipos_ingresos values ('Ingreso 1');
insert into tipos_ingresos values ('HORAS_EXTRAS');

select * from tipos_descuentos;

select empleado_admin.id, empleado_personal.nombre, empleado_personal.apellido,empleado_admin.id_estado from empleado_admin, empleado_personal where empleado_admin.id = empleado_personal.id_empleado and empleado_admin.id_estado = 1 and empleado_admin.tipo_salario=1;

SELECT NOMBRE,MONTO FROM EMPLEADO_ADMIN EA JOIN CARGO C ON EA.ID_CARGO = C.ID WHERE EA.ID = 9;

select ti.nombre ingreso, ie.monto from ingresos_emp ie join tipos_ingresos ti on ie.tipo_ingreso = ti.id where ie.id_empleado = 9;

select * from tipo_salario;

insert into tipo_salario(nombre, factor) values ('quincenal', 11.91);

update tipo_salario set factor = 23.83 where id = 1;

insert into tipo_salario(nombre,factor) values ('semana', 5.5);
insert into tipo_salario(nombre,factor) values ('hora', 8);

select * from usuarios;

insert into usuarios(usuario,clave,tipo_usuario) values ('admin', 'admin', 1);
insert into usuarios(usuario,clave,tipo_usuario) values ('contable','contable',2);
select * from usuario_pers;
