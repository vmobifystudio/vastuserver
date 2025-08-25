insert  into `role`(`id`,`description`,`role`,`version`) values (1,'Super Admin Role','ROLE_SUPER_ADMIN',0),
(2,'Device User Role','ROLE_DEVICE_USER',0);

insert into SystemProperty(propname, propvalue) values ('MASTER_DATA_VERSION', '0'), ('DEFAULT_TIMEZONE', 'IST');

/*Data for the table `user` */
insert  into `User`(`id`, `firstname`, `lastname`, `username`, `gender`, `password`, `version`, `email`, `mobileNumber` ) values
(1,'Vishal', 'Pawale', 'admin', 'M', '21232f297a57a5a743894a0e4a801fc3', 0, 'vishalpawale123@gmail.com', '9923427766' ); 

/*Data for the table `userrole` */
insert  into `userrole`(`userId`,`roleId`) values (1,1);

/* MENU Privileges */
insert into privilege (id, name) value (1, 'PRIV_SUPER_ADMIN');

insert into Privileges_Roles (roleId, privilegeId) values (1,1);