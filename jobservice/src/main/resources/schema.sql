create table Job (
    name varchar(25) not null,
    roles varchar(50) not null,
    description varchar(255) not null,
    max_level int not null,
    primary key (name)
);

create table job_level (
    id int not null AUTO_INCREMENT,
    job_name varchar (25) not null,
    level int not null,
    xp_to_level int not null,
    base_strength int not null,
    base_dexterity int not null,
    base_speed int not null,
    base_endurance int not null,
    base_spirit int not null,
    base_intelligence int not null,
    base_willpower int not null,
    base_charisma int not null,
    base_hp int not null,
    base_sp int not null,
    attack_range int not null,
    attack_type varchar(15) not null,
    primary key (id)
);