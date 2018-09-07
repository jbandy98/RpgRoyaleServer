create table world_info (
  game_id int not null,
  width int not null,
  height int not null,
  seed int not null,
  primary key(game_id)
);

create table tile_event (
  game_id int not null,
  x int not null,
  y int not null,
  event_id int not null,
  event_type varchar(50) not null,
  primary key(game_id,event_id)
);
