-- password: admin1admin1
-- password: admin2admin2
-- password: admin4admin4
-- password: admin3admin3
insert into i_user(first_name, middle_name, last_name, date_birth, login, passwords, role_id)
values ('Ivan', 'Petrovich', 'Petrov', '2000-01-23', 'admin1',
        '$2a$10$IfzJDncYwZvjXNkgGqzKEOM06LzF7yBfO/f5jVTgEkFkE3XzaTsje', 2),
       ('Ivan', 'Petrovich', 'Petrov', '2000-01-23', 'admin2',
        '$2a$10$i69Wh7r2TIOCkyagrRzv2emppyJ4RUiWRakWQMj22mGsyqSFgCsRW', 2),
       ('Ivan', 'Petrovich', 'Petrov', '2000-01-23', 'admin4',
        '$2a$10$j6wgnnXj0UdlSSqt4g3VL.Q6H.oYVPWzrrE2UupjaRujQiOf3GTAK', 1),
       ('Ivan', 'Petrovich', 'Petrov', '2000-01-23', 'admin3',
        '$2a$10$SV2Um7LCfLuKs0thCAot4OHU8fbUVmXHPFvDIh/xiisqJGEOxohZy', 1),
        ('Ivan', 'Petrovich', 'Petrov', '1999-01-23', 'test_user1',
            '$2a$10$SV2Um7LCfLuKs0thCAot4OHU8fbUVmXHPFvDIh/xiisqJGEOxohZy', 1),
        ('Ivan', 'Petrovich', 'Petrov', '1998-01-23', 'test_user2',
            '$2a$10$SV2Um7LCfLuKs0thCAot4OHU8fbUVmXHPFvDIh/xiisqJGEOxohZy', 1),
        ('Ivan', 'Petrovich', 'Petrov', '1997-01-23', 'test_user3',
            '$2a$10$SV2Um7LCfLuKs0thCAot4OHU8fbUVmXHPFvDIh/xiisqJGEOxohZy', 1),
        ('Ivan', 'Petrovich', 'Petrov', '1996-01-23', 'test_user4',
            '$2a$10$SV2Um7LCfLuKs0thCAot4OHU8fbUVmXHPFvDIh/xiisqJGEOxohZy', 1),
        ('Ivan', 'Petrovich', 'Petrov', '1995-01-23', 'test_user5',
            '$2a$10$SV2Um7LCfLuKs0thCAot4OHU8fbUVmXHPFvDIh/xiisqJGEOxohZy', 1),
        ('Ivan', 'Petrovich', 'Petrov', '1994-01-23', 'test_user6',
            '$2a$10$SV2Um7LCfLuKs0thCAot4OHU8fbUVmXHPFvDIh/xiisqJGEOxohZy', 1),
        ('Ivan', 'Petrovich', 'Petrov', '1993-01-23', 'test_user7',
            '$2a$10$SV2Um7LCfLuKs0thCAot4OHU8fbUVmXHPFvDIh/xiisqJGEOxohZy', 1),
        ('Ivan', 'Petrovich', 'Petrov', '1992-01-23', 'test_user8',
            '$2a$10$SV2Um7LCfLuKs0thCAot4OHU8fbUVmXHPFvDIh/xiisqJGEOxohZy', 1);

insert into stat (completed_total, uncompleted_total, user_id)
values (0, 0, 1),
       (0, 0, 2);

insert into priority (title, color)
values ('low', '#caffdd'),
       ('middle', '#883bdc'),
       ('high', '#f05f5f');

insert into category (title, user_id)
values ('products', 1),
       ('sport', 1),
       ('sport', 2),
       ('jobs', 2);

INSERT INTO task (title, completed, date, priority_id, category_id, user_id)
VALUES ('Позвонить родителям', 1, '2021-01-26', 1, 1, 1),
       ('Посмотреть мультики', 0, '2021-02-15', 2, 2, 1),
       ('Пройти курсы по Java', 1, '2021-03-05', 3, 1, 1),

       ('Сделать зеленый коктейль', 0, '2021-04-08', 3, 3, 2),
       ('Купить буханку хлеба', 0, '2021-05-10', 2, 4, 2),
       ('Позвонить начальнику', 1, '2021-06-15', 3, 4, 2);