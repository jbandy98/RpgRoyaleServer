create table Job (
    name varchar(25) not null,
    roles varchar(50) not null,
    description varchar(255) not null,
    max_level int not null,
    primary key (name)
);

create table JobLevel (
    id int not null AUTO_INCREMENT,
    name varchar (25) not null,
    level int not null,
    xpToLevel int not null,
    baseStrength int not null,
    baseDexterity int not null,
    baseSpeed int not null,
    baseEndurance int not null,
    baseSpirit int not null,
    baseIntelligence int not null,
    baseWillpower int not null,
    baseCharisma int not null,
    baseHp int not null,
    baseSp int not null,
    primary key (id)
);