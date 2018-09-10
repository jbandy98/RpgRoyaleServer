create table game_data (
  player_id varchar(50) not null,
  game_id int not null,
  game_state varchar(50),
  lava_timer float,
  gold int,
  loc_x int,
  loc_y int,
  primary key(player_id, game_id)
);