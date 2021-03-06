--========== Create table tb_user ============--

CREATE TABLE tb_user(
  id INTEGER not null,
  user_name varchar(30) not null,
  parent integer not null,
  CONSTRAINT user_pkey PRIMARY KEY (id)
);

--==============================================--


--======== Insert value to table tb_user =======--

INSERT INTO tb_user (id,user_name ,parent) VALUES
  (1, 'Ali',2),
  (2, 'Budi',0),
  (3, 'Cecep',1);

--===============================================--  


--======= SQL Query to get parents name ==========--

WITH RECURSIVE user_parents AS
(
  SELECT
     id                     AS id
    ,user_name              AS user_name
    ,null :: text           AS parent
  FROM tb_user
  WHERE
    parent = 0
  UNION
  SELECT
     child.id               AS id
    ,child.user_name        AS user_name
    ,p.user_name            AS parent
  FROM tb_user child
    INNER JOIN user_parents p ON p.id = child.parent
)
SELECT
  id
  ,coalesce(user_name,'-') user_name
  ,coalesce(parent,'-')    parent
FROM user_parents
order by id;

--================================================--