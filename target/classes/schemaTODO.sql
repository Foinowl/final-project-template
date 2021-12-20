DROP TABLE IF EXISTS role CASCADE;
CREATE TABLE role
(
    role_id SERIAL,
    title   VARCHAR(25) NOT NULL,

    CONSTRAINT PK_role_role_id PRIMARY KEY (role_id)
);

DROP TABLE IF EXISTS i_user CASCADE;
CREATE TABLE i_user
(
    user_id     SERIAL,
    first_name  VARCHAR(40) NOT NULL,
    middle_name VARCHAR(40) NOT NULL,
    last_name   VARCHAR(40) NOT NULL,
    date_birth  DATE        NOT NULL,
    login       VARCHAR(40) NOT NULL UNIQUE,
    passwords   VARCHAR(80) NOT NULL,
    role_id     INT         NOT NULL,

    CONSTRAINT PK_i_user_user_id PRIMARY KEY (user_id),
    CONSTRAINT FK_i_user_role_id FOREIGN KEY (role_id) REFERENCES role (role_id)
);

DROP TABLE IF EXISTS priority CASCADE;
CREATE TABLE priority
(
    priority_id SERIAL,
    title       VARCHAR(45) NOT NULL,
    color       VARCHAR(45) NOT NULL,

    CONSTRAINT PK_priority_priority_id PRIMARY KEY (priority_id)
);

DROP TABLE IF EXISTS category CASCADE;
CREATE TABLE category
(
    category_id       SERIAL,
    title             VARCHAR(45) NOT NULL,
    completed_count   bigint      NOT NULL DEFAULT 0,
    uncompleted_count bigint      NOT NULL DEFAULT 0,
    user_id           int,

    CONSTRAINT PK_category_category_id PRIMARY KEY (category_id),
    CONSTRAINT FK_category_user_id foreign key (user_id) references i_user (user_id) ON DELETE CASCADE

);

DROP TABLE IF EXISTS stat;
CREATE TABLE stat
(
    stat_id           SERIAL,
    completed_total   bigint DEFAULT 0,
    uncompleted_total bigint DEFAULT 0,
    user_id           int UNIQUE NOT NULL,

    CONSTRAINT PK_stat_stat_id PRIMARY KEY (stat_id),
    CONSTRAINT FK_stat_user_id FOREIGN KEY (user_id) references i_user (user_id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS task CASCADE;
CREATE TABLE task
(
    task_id     SERIAL,
    title       VARCHAR(60) NOT NULL,
    completed   int         not null CHECK (completed = 0 or completed = 1) default 0,
    date        TIMESTAMP,
    priority_id INT         NOT NULL,
    category_id INT         NOT NULL,
    user_id     INT         NOT NULL,

    CONSTRAINT PK_task_task_id PRIMARY KEY (task_id),
    CONSTRAINT FK_task_priority_id FOREIGN KEY (priority_id) REFERENCES priority (priority_id) ON DELETE RESTRICT,
    CONSTRAINT FK_task_category_id FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE RESTRICT,
    CONSTRAINT FK_task_user_id FOREIGN KEY (user_id) REFERENCES i_user (user_id) ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION task_insert_trigger_fn()
    RETURNS trigger AS
$$
BEGIN

    -- 	Обновление категории, при условии, что она не пустая и статус задачи завершён.
    if (coalesce(NEW.category_id, 0) > 0 and coalesce(NEW.completed, 0) = 1) then
        update category set completed_count = completed_count + 1 where category_id = NEW.category_id;

        -- 	Обновление категории, при условии, что она не пустая и статус задачи незавершён.
    elsif (coalesce(NEW.category_id, 0) > 0 and coalesce(NEW.completed, 0) = 0) then
        update category
        set uncompleted_count = uncompleted_count + 1
        where category_id = NEW.category_id;
    end if;

-- 	updating the table stat when depending on column "completed"
    if (coalesce(NEW.completed, 0) = 1) then
        update stat set completed_total = completed_total + 1 where stat.user_id = new.user_id;
    else
        update stat set uncompleted_total = uncompleted_total + 1 where stat.user_id = new.user_id;
    end if;

    RETURN NEW;
END;
$$
    LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION task_update_trigger_fn()
    RETURNS trigger AS
$$
BEGIN

    -- completed изменился на 1 - "выполнен", но не изменилась категория
    IF (
                coalesce(old.completed, 0) <> coalesce(new.completed, 0) and
                new.completed = 1 and
                coalesce(old.category_id, 0) = coalesce(new.category_id, 0)
        ) THEN

        update category
        set uncompleted_count = uncompleted_count - 1,
            completed_count   = completed_count + 1
        where category_id = old.category_id;

        update stat
        set uncompleted_total = uncompleted_total - 1,
            completed_total   = completed_total + 1
        where user_id = new.user_id;

-- 	изменился completed - статус не завершён, категория прежняя
    elsIF (
                coalesce(old.completed, 0) <> coalesce(new.completed, 0) and
                new.completed = 0 and
                coalesce(old.category_id, 0) = coalesce(new.category_id, 0)
        ) THEN

        update category
        set completed_count   = completed_count - 1,
            uncompleted_count = uncompleted_count + 1
        where category_id = old.category_id;

        update stat
        set completed_total   = completed_total - 1,
            uncompleted_total = uncompleted_total + 1
        where user_id = new.user_id;

--  changed the table of category for the unchanged column 'completed=1'
    ELSIF (
                coalesce(old.completed, 0) = coalesce(new.completed, 0) and
                new.completed = 1 and
                coalesce(old.category_id, 0) <> coalesce(new.category_id, 0)
        ) THEN

-- 		updating the old category - reducing the column completed
        update category set completed_count = completed_count - 1 where category_id = old.category_id;

-- 		updating the new category - increase the column completed
        update category set completed_count = completed_count + 1 where category_id = new.category_id;

        --  changed the table of category for the unchanged column 'completed=0'
    ELSIF (
                coalesce(old.completed, 0) = coalesce(new.completed, 0) and
                old.completed = 0 and
                coalesce(old.category_id, 0) <> coalesce(new.category_id, 0)
        ) THEN

-- 		updating the old category - reducing the column completed
        update category
        set uncompleted_count = uncompleted_count - 1
        where category_id = old.category_id;

-- 		updating the new category - increase the column completed
        update category
        set uncompleted_count = uncompleted_count + 1
        where category_id = new.category_id;

--      changed the category, changed completed from 1 to 0
    ELSIF (
                coalesce(old.completed, 0) <> coalesce(new.completed, 0) and
                new.completed = 0 and
                coalesce(old.category_id, 0) <> coalesce(new.category_id, 0)
        ) THEN

        update category set completed_count = completed_count where category_id = old.category_id;

        update category
        set uncompleted_count = uncompleted_count + 1
        where category_id = new.category_id;

        update stat
        set uncompleted_total = uncompleted_total + 1,
            completed_total   = completed_total - 1
        where user_id = new.user_id;

        --      changed the category, changed completed from 0 to 1
    ELSIF (
                coalesce(old.completed, 0) <> coalesce(new.completed, 0) and
                new.completed = 1 and
                coalesce(old.category_id, 0) <> coalesce(new.category_id, 0)
        ) THEN

        update category
        set uncompleted_count = uncompleted_count - 1
        where category_id = old.category_id;

        update category set completed_count = completed_count + 1 where category_id = new.category_id;

        update stat
        set uncompleted_total = uncompleted_total - 1,
            completed_total   = completed_total + 1
        where user_id = new.user_id;
    END IF;

    RETURN NEW;
END;
$$
    LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION task_delete_trigger_fn()
    RETURNS trigger AS
$$
BEGIN

    if (coalesce(old.category_id, 0) > 0 and coalesce(old.completed, 0) = 1) then
        update category set completed_count = completed_count - 1 where category_id = old.category_id;
    end if;

    if (coalesce(old.category_id, 0) > 0 and coalesce(old.completed, 0) = 0) then
        update category
        set uncompleted_count = uncompleted_count - 1
        where category_id = old.category_id;
    end if;

    if coalesce(old.completed, 0) = 1 then
        update stat set completed_total = completed_total - 1 where user_id = old.user_id;
    else
        update stat set uncompleted_total = uncompleted_total - 1 where user_id = old.user_id;
    end if;

    RETURN NEW;
END;
$$
    LANGUAGE 'plpgsql';

CREATE TRIGGER task_insert_trigger
    AFTER INSERT
    ON task
    FOR EACH ROW
EXECUTE PROCEDURE task_insert_trigger_fn();

CREATE TRIGGER task_update_trigger
    AFTER UPDATE
    ON task
    FOR EACH ROW
EXECUTE PROCEDURE task_update_trigger_fn();

CREATE TRIGGER task_delete_trigger
    AFTER DELETE
    ON task
    FOR EACH ROW
EXECUTE PROCEDURE task_delete_trigger_fn();


insert into role (title)
values ('ADMIN'),
       ('USER');



