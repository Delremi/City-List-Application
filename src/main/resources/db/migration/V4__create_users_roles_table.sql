create table users_roles (
    user_id integer not null,
    role_id integer not null
);

alter table if exists users_roles add constraint users_roles_ref_role_id foreign key (role_id) references role;

alter table if exists users_roles add constraint users_roles_ref_custom_user_id foreign key (user_id) references custom_user;