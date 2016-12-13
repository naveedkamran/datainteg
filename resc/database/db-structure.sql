drop table if exists `src_table`;
create table `src_table` (
  `id` bigint(20) not null auto_increment,
  `recid` varchar(100) default null,
  `rcity` varchar(1000) default null,
  `rstate` varchar(1000) default null,
  `rzip` varchar(1000) default null,
  `raddress` varchar(100) default null,
  primary key (`id`)
) engine=myisam auto_increment=188613 default charset=latin1;

drop table if exists `tar_table`;
create table `tar_table` (
  `id` bigint(20) not null auto_increment,
  `recid` varchar(100) default null,
  `rcity` varchar(1000) default null,
  `rcity_temp` varchar(1000) default null,
  `rstate` varchar(1000) default null,
  `rstate_temp` varchar(1000) default null,
  `rzip` varchar(1000) default null,
  `rzip_temp` varchar(1000) default null,
  `raddress` varchar(100) default null,
  primary key (`id`)
) engine=myisam auto_increment=188613 default charset=latin1;

drop table if exists `zip_state_city`;

create table `zip_state_city` (
  `id` bigint(20) not null auto_increment,
  `rstate` varchar(100) default null,
  `rcity` varbinary(100) default null,
  `rzip` int(11) default null,
  primary key (`id`)
) engine=myisam default charset=latin1;
