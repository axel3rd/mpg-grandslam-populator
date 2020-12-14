INSERT INTO `player` (`id`, `mpg_id`, `name`) VALUES
    (73, 955966, 'Alix'),
    (74, 35635, 'Bertrand'),
    (75, 600737, 'Céline'),
    (76, 963519, 'David'),
    (77, 953561, 'Mansuy'),
    (78, 1567579, 'Mat'),
    (79, 1520001, 'Typhaine'),
    (80, 1662232, 'Youen');

INSERT INTO `grand_slam` (`id`, `year`, `status`) VALUES
    (7, '2019/2', 'Closed'),
    (8, '2020/1', 'Closed'),
    (9, '2020/2', 'Running');    

INSERT INTO `league` (`id`, `mpg_id`, `type`, `name`, `year`, `status`, `grand_slam_id`, `game_played`) VALUES
    (28, 'LJT3FXDF', 'PREMIER', 'PETER OUCH', '2019/2', 'Closed', 7, 18),
    (29, 'LJV92C9Y', 'L1', 'GWADA BOYS', '2019/2', 'Closed', 7, 18),
    (30, 'LH9HKBTD', 'L2', 'D2 MAX', '2019/2', 'Closed', 7, 18),
    (31, 'LJT3FXDF', 'PREMIER', 'BREXIT POWA', '2020/1', 'Closed', 8, 9),
    (32, 'LJV92C9Y', 'L1', 'CHEVRES SHOW', '2020/1', 'Closed', 8, 8),
    (33, 'LH9HKBTD', 'L2', 'LIGUE 11S', '2020/1', 'Closed', 8, 8),
    (34, 'MLMHBPCB', 'PREMIER', 'WHINERS', '2020/2', 'Running', 9, 3),
    (35, 'MLAX7HMK', 'L1', 'CARTON', '2020/2', 'Running', 9, 5),
    (36, 'MLEFEX6G', 'L2', 'PAPIER', '2020/2', 'Running', 9, 5);

INSERT INTO `grand_slam_bonus` (`id`, `player_id`, `grand_slam_id`, `label`, `nb_points`) VALUES
    (1, 73, 7, 'Victoire LDC', 3),
    (2, 77, 7, 'Victoire LDC', 3),
    (3, 74, 7, '2e LDC', 1),
    (4, 76, 7, '2e LDC', 1);    
    
INSERT INTO `team` (`id`, `player_id`, `league_id`, `name`, `short_name`, `victory`, `draw`, `defeat`, `goal_diff`) VALUES
    (214, 79, 33, 'Paillettes FC', 'PFC', 2, 2, 4, -7),
    (215, 77, 33, 'L’ascenseur Nancéen', 'ASN', 4, 4, 0, 9),
    (216, 76, 33, 'L2/D2', 'LDS', 3, 2, 3, 6),
    (217, 80, 33, 'Doudou FC', 'DFC', 4, 2, 2, -2),
    (218, 73, 33, 'Axel Minus Football Club', 'AMC', 4, 1, 3, 3),
    (219, 75, 33, '4 mori', '4MO', 4, 2, 2, 4),
    (220, 74, 33, 'Harry Saint-Germain', 'HSG', 3, 1, 4, -3),
    (221, 78, 33, 'Poney aux Fraises', 'PAF', 2, 1, 5, -5),
    (222, 74, 34, 'North London', 'NLL', 3, 0, 0, 6),
    (223, 73, 34, 'Axel Football Club', 'AFC', 3, 0, 0, 11),
    (224, 80, 34, 'Exeter Football Club', 'EFC', 1, 0, 2, -1),
    (225, 79, 34, 'Queen of Cheat', 'QOC', 1, 1, 1, 1),
    (226, 78, 34, 'Master of Puppets', 'MOP', 1, 1, 1, -1),
    (227, 75, 34, 'Red And Black', 'R&D', 0, 1, 2, -7),
    (228, 77, 34, 'Maybe A Win?', 'MAW', 1, 1, 1, 6),
    (229, 76, 34, 'The UK Subs', 'DCU', 2, 0, 1, 5),
    (230, 74, 35, 'Poissy Saint Bimou', 'PSB', 3, 0, 2, 7),
    (231, 78, 35, 'Cheh & Ze Papa', 'CZP', 3, 1, 1, 3),
    (232, 80, 35, 'Youyou FC', 'YFC', 4, 0, 1, 8),
    (233, 73, 35, 'Axel Football Club', 'AFC', 2, 1, 2, -4),
    (234, 77, 35, 'Le Doublé', 'DOU', 2, 1, 2, 1),
    (235, 76, 35, 'DC1', 'DC1', 2, 0, 3, -1),
    (236, 79, 35, 'Barbie Turik', 'BTK', 0, 2, 3, -6),
    (237, 75, 35, 'O Breizh Ma Bro', 'BZH', 0, 1, 4, -10),
    (238, 79, 36, 'Blabla Pookie', 'BBP', 2, 2, 1, 7),
    (239, 77, 36, 'Le Doublé', 'DOU', 3, 1, 1, 1),
    (240, 76, 36, 'DC2', 'DC2', 3, 1, 1, 5),
    (241, 80, 36, 'Pangolin FC', 'PFC', 2, 1, 2, 0),
    (242, 73, 36, 'Axel Football Club', 'AFC', 2, 3, 0, 2),
    (243, 75, 36, 'Breizh Skiphailh', 'BZH', 1, 2, 2, -1),
    (244, 74, 36, 'Origami FC', 'OFC', 2, 1, 2, -5),
    (245, 78, 36, 'Bergerie FC', 'BFC', 3, 0, 2, 6);    