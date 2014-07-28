
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `username` varchar(45) NOT NULL DEFAULT '0',
  `realname` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `headimg` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `is_friend`;

CREATE TABLE `is_friend` (
  `user1` varchar(45) NOT NULL,
  `user2` varchar(45) NOT NULL,
  PRIMARY KEY (`user1`,`user2`),
  KEY `u1_idx` (`user1`),
  KEY `u2_idx` (`user2`),
  CONSTRAINT `u1` FOREIGN KEY (`user1`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `u2` FOREIGN KEY (`user2`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `mission`;

CREATE TABLE `mission` (
  `missionname` varchar(45) NOT NULL DEFAULT '0',
  `missiontime` datetime DEFAULT NULL,
  `missiondeadline` datetime DEFAULT NULL,
  `missionplace` varchar(45) DEFAULT NULL,
  `missionps` varchar(45) DEFAULT NULL,
  `missionstate` int(11) DEFAULT '0',
  PRIMARY KEY (`missionname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `cre_par`;

CREATE TABLE `cre_par` (
  `user` varchar(45) NOT NULL,
  `mission` varchar(45) NOT NULL,
  `cr_or_pr` int(11) DEFAULT '0' COMMENT '0 for create and 1 for participate',
  PRIMARY KEY (`user`,`mission`),
  KEY `userp_idx` (`user`),
  KEY `missionp_idx` (`mission`),
  CONSTRAINT `missionp` FOREIGN KEY (`mission`) REFERENCES `mission` (`missionname`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `userp` FOREIGN KEY (`user`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `info`;

CREATE TABLE `info` (
  `id` int unsigned NOT NULL auto_increment,
  `sender` varchar(45) NOT NULL,
  `receiver` varchar(45) NOT NULL,
  `mes` varchar(140) NOT NULL,
  `state` int unsigned DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


