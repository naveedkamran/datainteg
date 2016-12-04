drop table if exists `src_table`;
create table `src_table` (
  `id` bigint(20) not null auto_increment,
  `rcity` varchar(1000) default null,
  `rstate` varchar(1000) default null,
  `rzip` varchar(1000) default null,
  `recid` varchar(100) default null,
  primary key (`id`)
) engine=myisam auto_increment=188613 default charset=latin1;

drop table if exists `tar_table`;
create table `tar_table` (
  `id` bigint(20) not null auto_increment,
  `rcity` varchar(1000) default null,
  `rcity_temp` varchar(1000) default null,
  `rstate` varchar(1000) default null,
  `rstate_temp` varchar(1000) default null,
  `rzip` varchar(1000) default null,
  `rzip_temp` varchar(1000) default null,
  `recid` varchar(100) default null,
  primary key (`id`)
) engine=myisam auto_increment=188613 default charset=latin1;

DROP TABLE IF EXISTS `zipstatecity`;

CREATE TABLE `zipstatecity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rstate` varchar(100) DEFAULT NULL,
  `rcity` varbinary(100) DEFAULT NULL,
  `rzip` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

update tar_table set rstate = ( select name_short from tstate where name = rstate_temp ) where rstate is null;
