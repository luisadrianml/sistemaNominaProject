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
insert into ingresos_emp(id_empleado,tipo_ingreso,monto) values(4,1,500);
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

insert into empresa(nombre,rnc) values ('CROOM', '101027797');




SELECT NOMBRE,MONTO FROM EMPLEADO_ADMIN EA JOIN CARGO C ON EA.ID_CARGO = C.ID WHERE EA.ID = 1;


insert into tipo_usuarios(nombre)  values ('administrador');
insert into tipo_usuarios(nombre) values ('contable');

insert into usuario_pers values ('luis', 'luisa','martinez','lm@outlook.com');

select * from tipo_usuarios;
select * from empresa;

select LAST_INSERT_ID()  from empleado_admin;

select max(id) from empleado_admin

select * from empleado_personal

