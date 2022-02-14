
CREATE TABLE bidlist (
  bid_list_id INT NOT NULL AUTO_INCREMENT,
  account VARCHAR(30),
  type VARCHAR(30),
  bid_quantity DOUBLE,
  ask_quantity DOUBLE,
  bid DOUBLE ,
  ask DOUBLE,
  benchmark VARCHAR(125),
  bid_list_date TIMESTAMP,
  commentary VARCHAR(125),
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  book VARCHAR(125),
  creation_name VARCHAR(125),
  creation_date TIMESTAMP ,
  revision_name VARCHAR(125),
  revision_date TIMESTAMP ,
  deal_name VARCHAR(125),
  deal_type VARCHAR(125),
  source_list_id VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (bid_list_id)
);

CREATE TABLE trade (
  trade_id INT NOT NULL AUTO_INCREMENT,
  account VARCHAR(30),
  type VARCHAR(30),
  buy_quantity DOUBLE,
  sell_quantity DOUBLE,
  buy_price DOUBLE ,
  sell_price DOUBLE,
  trade_date TIMESTAMP,
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  benchmark VARCHAR(125),
  book VARCHAR(125),
  creation_name VARCHAR(125),
  creation_date TIMESTAMP ,
  revision_name VARCHAR(125),
  revision_date TIMESTAMP ,
  deal_name VARCHAR(125),
  deal_type VARCHAR(125),
  source_list_id VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (trade_id)
);

CREATE TABLE curvepoint (
  id INT NOT NULL AUTO_INCREMENT,
  curve_id tinyint,
  as_of_date TIMESTAMP,
  term DOUBLE ,
  value DOUBLE ,
  creation_date TIMESTAMP ,

  PRIMARY KEY (id)
);

CREATE TABLE rating (
  id INT NOT NULL AUTO_INCREMENT,
  moodys_rating VARCHAR(125),
  sand_prating VARCHAR(125),
  fitch_rating VARCHAR(125),
  order_number tinyint,

  PRIMARY KEY (id)
);

CREATE TABLE rulename (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(125),
  description VARCHAR(125),
  json VARCHAR(125),
  template VARCHAR(512),
  sql_str VARCHAR(125),
  sql_part VARCHAR(125),

  PRIMARY KEY (id)
);

CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(125),
  password VARCHAR(125),
  fullname VARCHAR(125),
  enabled BOOLEAN NOT NULL,
  role VARCHAR(125),

  PRIMARY KEY (id)
);

insert into users(fullname, username, password, enabled, role) values("Administrator", "admin", "$2a$10$mgn0wdnv8KSOn6MRkN9cm.ERDGNso7bYS1OCebgl8WEexMMCwJ7Lm", FALSE, "ADMIN");
insert into users(fullname, username, password, enabled, role) values("User", "user", "$2a$10$jdA5eD4qT6/lRD0HkLsv6OJlQBcpndzRokT0A4XzmVHpUR7zYbqsi", FALSE, "USER");

insert into users(fullname, username, password, enabled, role) values("AdministratorFalse", "admin", "$2a$10$tMfS4FQ1Qm1CNzqk6RA5cet8IW75HcOiqLt4eZ2gIPXnKlzkzPC/.", FALSE, "ADMIN");
insert into users(fullname, username, password, enabled, role) values("UserFalse", "user", "$2a$10$eCyZwsSbc6UMbKI5PyGrhO1e75mNPdgpM70CtQeSsKUvJXCUo9Dqe", FALSE, "USER");