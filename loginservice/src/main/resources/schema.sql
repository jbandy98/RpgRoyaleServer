create table user
(
  username varchar(50) not null,
  password varchar(50) not null,
  primary key(username)
);
create table login
(
  user varchar(50) not null,
  status varchar(50),
  active bit not null,
  sessionId varchar (20),
  primary key(user)
);