insert into departamento values (01, 1, 'Recursos humanos');

insert into tipo_salario values (1, 'Mensual', 23.83);
insert into tipo_salario values (2, 'Quincenal', 11.91);
insert into tipo_salario values (3, 'Semanal', 5.5);
insert into tipo_salario values (4, 'Diario por hora', 8);

insert into cargo values (1, 'CEO');

insert into cargo_salario values (1, 120000);

select cargo.id, cargo.nombre, cargo_salario.monto from cargo,cargo_salario where cargo.id = cargo_salario.id_cargo;
-- VIEW CREADA para ver
select * from cargo_monto;

insert into estados values ('a', 'Activo');

select * from empleado_admin
