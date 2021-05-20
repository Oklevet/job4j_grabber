create schema post;
create table post.post (
    id serial primary key,
    "name" varchar,
    "text" varchar,
    link varchar unique,
    created date  
)