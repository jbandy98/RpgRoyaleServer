create table player (
  name varchar(50) not null,
  session varchar(20),
  xp int not null,
  level int not null,
  diamonds int not null,
  wins int not null,
  losses int not null,
  avgplace double not null,
  elo int not null,
  primary key(name)
);