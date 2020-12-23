DROP TABLE IF EXISTS `grand_slam_day`;
DROP TABLE IF EXISTS `team`;
DROP TABLE IF EXISTS `league`;
DROP TABLE IF EXISTS `grand_slam_bonus`;
DROP TABLE IF EXISTS `player`;
DROP TABLE IF EXISTS `grand_slam`;

CREATE TABLE IF NOT EXISTS `player` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mpg_id` int(11) NOT NULL, 
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `grand_slam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` varchar(6) NOT NULL,
  `status` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `league` ( 
  `id` int(11) NOT NULL AUTO_INCREMENT, 
  `mpg_id` varchar(8) NOT NULL, 
  `type` varchar(20) NOT NULL, 
  `name` varchar(100) NOT NULL, 
  `year` varchar(6) NOT NULL, 
  `status` varchar(30) NOT NULL, 
  `grand_slam_id` int(11) DEFAULT NULL, 
  `game_played` int(11) NOT NULL, 
  PRIMARY KEY (`id`), 
  KEY `IDX_3EB4C31831D0A180` (`grand_slam_id`), 
  CONSTRAINT `FK_3EB4C31831D0A180` FOREIGN KEY (`grand_slam_id`) REFERENCES `grand_slam` (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `grand_slam_bonus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `grand_slam_id` int(11) NOT NULL,
  `label` varchar(50) NOT NULL,
  `nb_points` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_566760D099E6F5DF` (`player_id`),
  KEY `IDX_566760D031D0A180` (`grand_slam_id`),
  CONSTRAINT `FK_566760D031D0A180` FOREIGN KEY (`grand_slam_id`) REFERENCES `grand_slam` (`id`),
  CONSTRAINT `FK_566760D099E6F5DF` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `team` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `league_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `short_name` varchar(3) NOT NULL,
  `victory` int(11) NOT NULL,
  `draw` int(11) NOT NULL,
  `defeat` int(11) NOT NULL,
  `goal_diff` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_C4E0A61F99E6F5DF` (`player_id`),
  KEY `IDX_C4E0A61F58AFC4DE` (`league_id`),
  CONSTRAINT `FK_C4E0A61F58AFC4DE` FOREIGN KEY (`league_id`) REFERENCES `league` (`id`),
  CONSTRAINT `FK_C4E0A61F99E6F5DF` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=246 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `grand_slam_day` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grand_slam_id` int(11) NOT NULL,
  `day` int(11) NOT NULL,
  `label` varchar(50) NOT NULL,
  `players` json NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_63FD128231D0A180` (`grand_slam_id`),
  CONSTRAINT `FK_63FD128231D0A180` FOREIGN KEY (`grand_slam_id`) REFERENCES `grand_slam` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4;
