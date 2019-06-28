INSERT INTO enemy_ref (enemy_id, enemy_name, level, xp_value, gp_value, ap_value, strength, dexterity, speed, endurance, spirit, intelligence, willpower, charisma, hp, sp, attack_range, attack_type)
              VALUES (0, 'small rat', 1, 1, 1, 0, 1, 1, 2, 1, 2, 0, 0, 0, 20, 0, 1, 'physical');
INSERT INTO enemy_ref (enemy_id, enemy_name, level, xp_value, gp_value, ap_value, strength, dexterity, speed, endurance, spirit, intelligence, willpower, charisma, hp, sp, attack_range, attack_type)
              VALUES (1, 'wolf cub', 2, 2, 4, 1, 2, 2, 3, 4, 4, 2, 2, 0, 40, 10, 1 , 'physical');
    INSERT INTO enemy_ref (enemy_id, enemy_name, level, xp_value, gp_value, ap_value, strength, dexterity, speed, endurance, spirit, intelligence, willpower, charisma, hp, sp, attack_range, attack_type)
              VALUES (2, 'small snake', 1, 2, 2, 0, 1, 2, 3, 2, 2, 1, 1, 0, 30, 5, 1, 'physical');
INSERT INTO zone_enemies (zone_enemy_id, world_id, zone, start_percent, end_percent, enemy_ids) VALUES (0, 1, 1, 0, 25, '0,1,2');
INSERT INTO zone_enemies (zone_enemy_id, world_id, zone, start_percent, end_percent, enemy_ids) VALUES (1, 1, 2, 26, 50, '0,1,2');
INSERT INTO zone_enemies (zone_enemy_id, world_id, zone, start_percent, end_percent, enemy_ids) VALUES (2, 1, 3, 51, 75, '0,1,2');
INSERT INTO zone_enemies (zone_enemy_id, world_id, zone, start_percent, end_percent, enemy_ids) VALUES (3, 1, 4, 76, 100, '0,1,2');
