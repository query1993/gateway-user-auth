**gateway-user-auth 包括gateway的用户系统**

**技术组件：** spring cloud gateway , nacos(注册中心和配置中心) ,openfeign , hystrix

**nacos:** nacos集群镜像（nacos官方文档)

**mysql：**
  `` create schema `user-service` collate utf8mb4_general_ci;
   
   create table user_access
   (
   	id int auto_increment comment '主键ID'
   		primary key,
   	user_id int(10) not null comment '用户ID',
   	login_ip varchar(255) not null comment '登陆IP',
   	token varchar(255) null comment '令牌',
   	token_expired_time datetime null comment '令牌过期时间',
   	create_time datetime not null comment '创建时间',
   	update_time datetime not null comment '更新时间',
   	constraint unique_user_id_login_ip
   		unique (user_id, login_ip)
   )
   charset=utf8;
   
   create table user_access_log
   (
   	id int auto_increment comment '主键ID'
   		primary key,
   	user_id int(10) not null comment '用户ID',
   	user_option tinyint default 0 null comment '登录/登出',
   	login_ip varchar(255) not null comment '登陆IP',
   	create_time datetime not null comment '创建时间',
   	update_time datetime not null comment '更新时间'
   )
   charset=utf8;
   
   create table user_info
   (
   	id int auto_increment comment '主键ID'
   		primary key,
   	user_name varchar(128) not null comment '用户名',
   	password varchar(128) null comment '密码',
   	email varchar(128) null comment '邮箱',
   	cellphone varchar(128) null comment '手机',
   	status tinyint default 0 null comment '用户状态',
   	role tinyint default 1 null comment '角色[1, "超级管理员"|2, "操作管理员"|3, "普通用户"]',
   	create_time timestamp default '0000-00-00 00:00:00' not null comment '创建时间',
   	update_time timestamp default CURRENT_TIMESTAMP not null comment '更新时间',
   	constraint user_name
   		unique (user_name)
   )
   charset=utf8;``
   
