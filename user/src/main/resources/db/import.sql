create table user_table (id bigint not null,
 createdDate timestamp(6), email varchar(255),
 jsonFile varchar(255), role varchar(255)
 check (role in ('USER','ADMIN')),
 primary key (id));

create sequence user_table_SEQ start with 1 increment by 50;






