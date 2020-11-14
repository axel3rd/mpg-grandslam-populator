
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
