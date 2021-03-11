INSERT INTO USER_DTL (id,username, password,IS_ENABLED) VALUES
  (1001,'shubham', '$2y$12$5Qb3wGeQkiHCceDTukkhUuZJK0rYke.cr.IGfHeGx87HPltL7tyEm',true),
  (1002,'dan', '$2y$12$5Qb3wGeQkiHCceDTukkhUuZJK0rYke.cr.IGfHeGx87HPltL7tyEm',true),
  (1003,'tim', '$2y$12$5Qb3wGeQkiHCceDTukkhUuZJK0rYke.cr.IGfHeGx87HPltL7tyEm',true);
  
  insert into ROLES (ROLE_ID,ROLE_NAME) values
  (2001,'ADMIN'),
  (2002,'EDITOR'),
  (2003,'USER');
  
  insert into USER_ROLE (USER_ID,ROLE_ID) VALUES
  (1001,2001),
  (1001,2002),
  (1001,2003),
  (1002,2002),
  (1002,2003),
  (1003,2003);