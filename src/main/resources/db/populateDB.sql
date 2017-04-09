DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER  SEQUENCE global_seq_meal RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);


INSERT INTO meals (date_time, description, calories)
    VALUES
      ('2015-05-30 10:00:00', 'завтрак', '500'),
      ('2015-05-30 12:00:00', 'обед', '500'),
      ('2015-05-30 20:00:00', 'ужин', '500'),
      ('2015-05-31 10:00:00', 'завтрак', '1000'),
      ('2015-05-31 12:00:00', 'обед', '500'),
      ('2015-05-31 20:00:00', 'ужин', '510');

