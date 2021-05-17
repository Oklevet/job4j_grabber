create schema if not exists rabbit_s;
--drop table rabbit_s.rabbit;
create table if not exists rabbit_s.rabbit (created_date bigserial);
select * from rabbit_s.rabbit;
