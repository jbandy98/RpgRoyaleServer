insert into Job values ('Fighter','Tank, Melee DPS','The fighter is a generic warrior class that can use most weapons and armor. At higher levels, they are capable of taking a lot of punishment as well as providing a lot of enduring damage during long fights.',3);
insert into Job values ('Archer','Ranged DPS, Support','The archer is a long range damage dealer who also has some utilities that allow his team to remain out of harms way. At higher levels, the archer can deal high damage to a single target or good damage to many.',3);
insert into Job values ('Cleric','Healer','The cleric is the primary healer job. They are capable of doing both mass heals to single targets and good heals to the whole team.',3);
insert into Job values ('Wizard','Magic DPS, Support','The wizard is a magical damage powerhouse, capable of doing mass damage to enemies from afar with their spells. They are weak and should remain protected by their bigger brethren.',3);

insert into job_level (id, job_name, level, xp_to_level, base_strength, base_dexterity, base_speed, base_endurance, base_spirit, base_intelligence, base_willpower, base_charisma, base_hp, base_sp, attack_range, attack_type)
values (0, 'Fighter', 1, 0, 5, 3, 3, 4, 1, 1, 2, 1, 20, 6, 1,'physical');
insert into job_level (id, job_name, level, xp_to_level, base_strength, base_dexterity, base_speed, base_endurance, base_spirit, base_intelligence, base_willpower, base_charisma, base_hp, base_sp, attack_range, attack_type)
values (1, 'Archer', 1, 0, 3, 5, 4, 3, 1, 1, 2, 1, 16, 10, 3,'physical');
insert into job_level (id, job_name, level, xp_to_level, base_strength, base_dexterity, base_speed, base_endurance, base_spirit, base_intelligence, base_willpower, base_charisma, base_hp, base_sp, attack_range, attack_type)
values (2, 'Cleric', 1, 0, 2, 3, 3, 2, 4, 4, 3, 1, 16, 10, 1,'physical');
insert into job_level (id, job_name, level, xp_to_level, base_strength, base_dexterity, base_speed, base_endurance, base_spirit, base_intelligence, base_willpower, base_charisma, base_hp, base_sp, attack_range, attack_type)
values (3, 'Wizard', 1, 0, 1, 2, 3, 1, 4, 5, 3, 1, 12, 14, 3,'magic');
