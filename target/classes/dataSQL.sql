-- insert into i_user(first_name, middle_name, last_name, date_birth, login, passwords, role_id)
-- password: admin1admin1
-- password: admin2admin2
-- values ('Ivan', 'Petrovich', 'Petrov', '2000-01-23', 'admin1', '$2a$10$IfzJDncYwZvjXNkgGqzKEOM06LzF7yBfO/f5jVTgEkFkE3XzaTsje', 2),
--        ('Ivan', 'Petrovich', 'Petrov', '2000-01-23', 'admin2', '$2a$10$i69Wh7r2TIOCkyagrRzv2emppyJ4RUiWRakWQMj22mGsyqSFgCsRW', 2);

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