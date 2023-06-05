insert into custom_user (username, password) values ('editor', '$2a$10$nEHoqBgXZvoBEL4Hf9/7wuW9fzVM6dr6PicbXT0xOcthG4.1aa0Me');
insert into users_roles (user_id, role_id) values (
    select u.id from custom_user u where u.username='editor',
    select r.id from role r where r.role='ROLE_EDITOR'
);
insert into users_roles (user_id, role_id) values (
    select u.id from custom_user u where u.username='editor',
    select r.id from role r where r.role='ROLE_VIEWER'
);

insert into custom_user (username, password) values ('viewer', '$2a$10$zfHlUUFTRBdbrDSIj943VustmrvgW1mK2MPHOIPbAGCEN1mYAXKeq');
insert into users_roles (user_id, role_id) values (
    select u.id from custom_user u where u.username='viewer',
    select r.id from role r where r.role='ROLE_VIEWER'
);