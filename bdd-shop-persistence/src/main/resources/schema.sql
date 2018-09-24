CREATE TABLE category
(
id varchar(255),
name varchar(255),
description varchar(255)
);

CREATE TABLE product
(
id varchar(255),
name varchar(255),
specifications varchar(255),
category_id varchar(255)
);

CREATE TABLE user
(
id varchar(255),
first_name varchar(255),
last_name varchar(255),
admin bit(1)
); 