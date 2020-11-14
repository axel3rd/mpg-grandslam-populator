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