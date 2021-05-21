create schema post;
create table post.post (
    id serial primary key,
    "name" varchar,
    "text" varchar,
    link varchar unique,
    created timestamp
)
select * from post.post;
delete from post.post;