INSERT INTO `player` (`id`, `name`, `mpg_id`,`active`) VALUES (73,'Alix (axel3rd)',955966,false),(74,'Bertrand (Bimou)',35635,false),(75,'Celine (Celbzh)',600737,false),(76,'David (David#XDXZ)',963519,false),(77,'Mansuy (Mansuy)',953561,false),(78,'Mat (Master)',1567579,false),(79,'Typhaine (Licornasse)',1520001,false),(80,'Youenn (Youennn)',1662232,false);
INSERT INTO `grand_slam` (`id`, `year`, `status`) VALUES (7,'2019/2','Closed'),(8,'2020/1','Closed'),(9,'2020/2','Closed'),(10,'2021/1','Closed'),(11,'2021/2','Running');
INSERT INTO `league` (`id`, `type`, `name`, `year`, `status`, `grand_slam_id`, `game_played`, `mpg_id`) VALUES (28,'PREMIER','PETER OUCH','2019/2','Closed',7,18,'LJT3FXDF'),(29,'L1','GWADA BOYS','2019/2','Closed',7,18,'LJV92C9Y'),(30,'L2','D2 MAX','2019/2','Closed',7,18,'LH9HKBTD'),(31,'PREMIER','BREXIT POWA','2020/1','Closed',8,9,'LJT3FXDF'),(32,'L1','CHEVRES SHOW','2020/1','Closed',8,8,'LJV92C9Y'),(33,'L2','LIGUE 11S','2020/1','Closed',8,8,'LH9HKBTD'),(34,'PL','The Whiner Takes It All','2020/2','Closed',9,18,'MLMHBPCB'),(35,'L1','Des Cartons','2020/2','Closed',9,18,'MLAX7HMK'),(36,'L2','Des Papiers','2020/2','Closed',9,18,'MLEFEX6G'),(37,'PL','Invariant Brits','2021/1','Closed',10,18,'MLMHBPCB'),(38,'L1','Du Tyran ?','2021/1','Closed',10,18,'MLAX7HMK'),(39,'L2','Fantôme de l Operi','2021/1','Closed',10,18,'MLEFEX6G'),(40,'PL','Brit Knee B*tch','2021/2','Running',11,3,'MLMHBPCB'),(41,'L2','Ligue 2 Fous','2021/2','Running',11,6,'MLEFEX6G'),(42,'L1','Ici c est Billiers !','2021/2','Running',11,4,'MLAX7HMK');
INSERT INTO `grand_slam_bonus` (`id`, `player_id`, `grand_slam_id`, `label`, `nb_points`) VALUES (1,73,7,'Victoire LDC',3),(2,77,7,'Victoire LDC',3),(3,74,7,'2e LDC',1),(4,76,7,'2e LDC',1),(5,78,9,'Victoire LDC',3),(6,79,9,'Victoire LDC',3),(7,73,9,'2e LDC',1),(8,77,9,'2e LDC',1);
INSERT INTO `team` (`id`, `player_id`, `league_id`, `name`, `short_name`, `victory`, `draw`, `defeat`, `goal_diff`) VALUES (174,73,28,'Axel Football Club','AFC',13,0,5,22),(175,73,29,'Axel Football Club','AFC',8,6,4,8),(176,73,30,'Axel Minus Football Club','AMC',7,6,5,1),(177,74,28,'North London','ARS',8,6,4,9),(178,74,29,'AS Poissirène','ASS',12,3,3,11),(179,74,30,'Lacazette du Sorcier','NRF',6,6,6,0),(180,75,28,'Hogwarts FC','HFC',7,2,9,-1),(181,75,29,'Ruz Ha Du','RHD',6,2,10,-5),(182,75,30,'4 mori','4MO',7,5,6,-6),(183,76,28,'Finders Goalkeepers','FGK',6,2,10,-14),(184,76,29,'Foot Lose','GFL',7,5,6,6),(185,76,30,'Les 21eme de L1','XXI',8,1,9,1),(186,77,28,'Football United Kings','FUK',7,2,9,-2),(187,77,29,'Chasse Au Titre','CAT',8,4,6,1),(188,77,30,'L’ascenseur Nancéen','ASN',8,3,7,4),(189,78,28,'Super Magic Pony','SMP',7,4,7,13),(190,78,29,'Cheh & Ze Papa','CZP',9,3,6,7),(191,78,30,'Poney aux Fraises','PAF',6,4,8,-5),(192,79,28,'Typh Universal Band','TUB',8,2,8,-9),(193,79,29,'Barbie Olympique Football','BOF',6,8,4,2),(194,79,30,'Paillettes FC','PAC',8,5,5,7),(195,80,28,'AS Weasley','ASW',11,2,5,12),(196,80,29,'Youyou FC','YFC',8,6,4,10),(197,80,30,'Doudou FC','DFC',7,7,4,3),(198,74,31,'North London','NOR',7,0,2,10),(199,73,31,'Axel Football Club','AFC',3,0,6,-6),(200,80,31,'Brittany Team','BRT',4,3,2,4),(201,79,31,'Devil Typh Club','DTC',2,1,6,-10),(202,78,31,'Super Magic Pony','SMP',5,1,3,4),(203,75,31,'Hogwarts FC','HFC',2,1,6,-11),(204,77,31,'Football United Kings','FUK',3,2,4,0),(205,76,31,'Smells Like Team Spirit','STS',5,2,2,12),(206,74,32,'AS Poissirène','ASS',3,1,4,-5),(207,78,32,'Cheh & Ze Papa','CZP',5,1,2,4),(208,80,32,'Youyou FC','YFC',5,2,1,10),(209,73,32,'Axel Football Club','AFC',3,1,4,-6),(210,77,32,'Chasse Au Titre','CAT',6,1,1,14),(211,76,32,'Foot ⚽ Lose','GFL',4,0,4,4),(212,79,32,'Barbie Olympique Football','BOF',2,2,4,-2),(213,75,32,'Ruz Ha Du','RHD',3,1,4,-2),(214,79,33,'Paillettes FC','PAC',2,2,4,-7),(215,77,33,'L’ascenseur Nancéen','ASN',4,4,0,9),(216,76,33,'L2/D2','LDS',3,2,3,6),(217,80,33,'Doudou FC','DFC',4,2,2,-2),(218,73,33,'Axel Minus Football Club','AMC',4,1,3,3),(219,75,33,'4 mori','4MO',4,2,2,4),(220,74,33,'Harry Saint-Germain','HSG',3,1,4,-3),(221,78,33,'Poney aux Fraises','PAF',2,1,5,-5),(222,74,34,'North London','NOR',7,4,7,-10),(223,73,34,'Axel Football Club','AFC',13,2,3,29),(224,80,34,'Exeter Football Club','EFC',6,2,10,-5),(225,79,34,'Queen of Cheat','QOC',8,3,7,1),(226,78,34,'Master of Puppets ','MOP',12,3,3,16),(227,75,34,'Red and black','RDD',5,4,9,-10),(228,77,34,'Maybe A Win','MAW',6,6,6,11),(229,76,34,'The UK Subs','DCU',10,0,8,19),(230,74,35,'Poissy Saint Bimou','PSB',6,4,8,1),(231,78,35,'Cheh and Ze Papa','CZP',7,5,6,1),(232,80,35,'Youyou FC','YFC',13,3,2,23),(233,73,35,'Axel Football Club','AFC',8,4,6,-2),(234,77,35,'Le Doublé','DOU',10,2,6,14),(235,76,35,'DC1','DC1',6,2,10,-8),(236,79,35,'Barbie TuriK','BTK',4,4,10,-17),(237,75,35,'O Breizh Ma Bro','BZH',4,4,10,-13),(238,79,36,'Blabla pookie','BBP',6,5,7,13),(239,77,36,'Le Doublé','DOU',11,3,4,12),(240,76,36,'DC2','DC2',7,4,7,0),(241,80,36,'Pangolin FC','PFC',11,2,5,24),(242,73,36,'Axel Football Club','AFC',10,4,4,6),(243,75,36,'Breizh skiphailh','BZH',4,8,6,2),(244,74,36,'Origami FC','OFC',11,4,3,14),(245,78,36,'Bergerie FC','BFC',7,1,10,4),(246,76,37,'Dans le UK','DCU',4,5,9,-8),(247,77,37,'Bollockshire','BOS',9,2,7,11),(248,75,37,'Red and black','RDD',6,3,9,-15),(249,78,37,'Master of Puppets ','MOP',12,0,6,20),(250,79,37,'Win Or Whine','WOW',4,3,11,-7),(251,80,37,'Exeter Football Club','EFC',9,1,8,-10),(252,73,37,'Axel Football Club','AFC',9,3,6,8),(253,74,37,'North London','NOR',13,1,4,19),(254,75,38,'O Breizh Ma Bro','BZH',8,4,6,5),(255,79,38,'Barbie TuriK','BTK',6,4,8,-3),(256,76,38,'DC1','DC1',8,4,6,0),(257,77,38,'Balek1','BA1',8,5,5,12),(258,73,38,'Axel Football Club','AFC',8,2,8,3),(259,80,38,'Youyou FC','YFC',5,6,7,2),(260,78,38,'Cheh and Ze Papa','CZP',8,4,6,-1),(261,74,38,'Poissy Saint Bimou','PSB',5,5,8,-5),(262,78,39,'Bergerie FC','BFC',6,8,4,10),(263,74,39,'Origami FC','OFC',9,5,4,18),(264,75,39,'Breizh skiphailh','BZH',5,3,10,-23),(265,73,39,'Axel Football Club','AFC',8,4,6,9),(266,80,39,'Pangolin FC','PFC',5,7,6,-8),(267,76,39,'DC2','DC2',8,3,7,5),(268,77,39,'Balek2','BA2',11,4,3,18),(269,79,39,'Blabla pookie','BBP',6,4,8,-3),(270,74,40,'North London','NFC',0,0,3,-6),(271,78,40,'Master of Puppets','MOP',2,1,0,6),(272,77,41,'Mad is back','MAD',3,1,2,2),(273,73,41,'Axel Football Club','AFC',0,1,5,-9),(274,80,42,'Youyou FC','YFC',3,1,0,6),(275,76,40,'DUK','DUK',2,0,1,5),(276,73,42,'Axel Football club','AFC',1,0,3,-3),(277,76,41,'DC2','DC2',4,2,0,7),(278,77,42,'Mad L1','MAD',1,0,3,-6),(279,76,42,'DC1','DC1',1,0,3,-2),(280,79,40,'Bernie Outdated Team','BOT',1,0,2,-3),(281,79,41,'AS Canard ','ASC',1,1,4,-1),(282,75,42,'Beler','BZH',2,0,2,-2),(283,78,41,'Ze Papa FC 2','ZPF',5,0,1,10),(284,74,41,'Paris Saint Valère','PSV',4,1,1,4),(285,74,42,'Passe Dé Sanitaire ','PDS',2,1,1,2),(286,75,41,'Breizhil','BZH',2,1,3,0),(287,79,42,'Fille De Pau','FDP',3,1,0,9),(288,80,40,'Roazhon Celtic','ROC',1,0,2,-4),(289,73,40,'Axel Football Club','AFC',1,1,1,-1),(290,80,41,'District FC','DFC',0,3,3,-9),(291,75,40,'Hogwarts','HFC',2,0,1,3),(292,78,42,'Ze Papa FC1','ZPF',3,0,1,6),(293,77,40,'MAD Brit','MAD',1,0,2,-4);

INSERT INTO `grand_slam_day` (`id`, `grand_slam_id`, `day`, `label`, `players`) VALUES
    (77, 9, 1, 'J0 : L2j0 L1j0 PLj0', '[{"pos": 9, "pts": 0, "diff": 0, "player": 73}, {"pos": 9, "pts": 0, "diff": 0, "player": 74}, {"pos": 9, "pts": 0, "diff": 0, "player": 75}, {"pos": 9, "pts": 0, "diff": 0, "player": 76}, {"pos": 9, "pts": 0, "diff": 0, "player": 77}, {"pos": 9, "pts": 0, "diff": 0, "player": 78}, {"pos": 9, "pts": 0, "diff": 0, "player": 79}, {"pos": 9, "pts": 0, "diff": 0, "player": 80}]'),
    (78, 9, 2, 'J1 : L2j3 L1j3 PLj1', '[{"pos": 1, "pts": 16, "diff": 8, "player": 80}, {"pos": 2, "pts": 15, "diff": 8, "player": 76}, {"pos": 3, "pts": 14, "diff": 4, "player": 73}, {"pos": 4, "pts": 12, "diff": 10, "player": 74}, {"pos": 5, "pts": 11, "diff": 2, "player": 77}, {"pos": 6, "pts": 10, "diff": -1, "player": 78}, {"pos": 7, "pts": 7, "diff": -10, "player": 75}, {"pos": 8, "pts": 6, "diff": -5, "player": 79}]'),
    (79, 9, 3, 'J2 : L2j4 L1j4 PLj2 + corrections', '[{"pos": 1, "pts": 22, "diff": 16, "player": 74}, {"pos": 2, "pts": 21, "diff": 9, "player": 73}, {"pos": 3, "pts": 16, "diff": 3, "player": 80}, {"pos": 4, "pts": 15, "diff": 2, "player": 77}, {"pos": 5, "pts": 14, "diff": 0, "player": 78}, {"pos": 6, "pts": 13, "diff": 0, "player": 76}, {"pos": 7, "pts": 11, "diff": -2, "player": 79}, {"pos": 8, "pts": 6, "diff": -13, "player": 75}]'),
    (80, 9, 4, 'J3 : L2j5 L1j5 PLj3', '[{"pos": 1, "pts": 25, "diff": 9, "player": 73}, {"pos": 2, "pts": 25, "diff": 8, "player": 74}, {"pos": 3, "pts": 23, "diff": 8, "player": 78}, {"pos": 4, "pts": 22, "diff": 9, "player": 76}, {"pos": 5, "pts": 22, "diff": 7, "player": 80}, {"pos": 6, "pts": 21, "diff": 8, "player": 77}, {"pos": 7, "pts": 14, "diff": 2, "player": 79}, {"pos": 8, "pts": 7, "diff": -18, "player": 75}]');

