/*
 Def. 23.11 N명의수학 운영 버전
 */

DROP TABLE IF EXISTS `numberbox_tc`.`email_id_code`;
CREATE TABLE `numberbox_tc`.`email_id_code` (
                                                `email` varchar(60) NOT NULL,
                                                `id_code` varchar(60) NOT NULL,
                                                `sys_create_time` datetime NOT NULL,
                                                PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS `numberbox_tc`.`error_report`;
CREATE TABLE `numberbox_tc`.`error_report` (
                                               `report_id` int NOT NULL AUTO_INCREMENT,
                                               `err_type` int NOT NULL,
                                               `contents_no` int DEFAULT NULL,
                                               `report_user` binary(16) NOT NULL,
                                               `report_contents` varchar(500) DEFAULT NULL,
                                               `os_info` varchar(7) NOT NULL,
                                               `browser` varchar(7) NOT NULL,
                                               `first_img_path` varchar(30) DEFAULT NULL,
                                               `first_img_name` varchar(70) DEFAULT NULL,
                                               `second_img_path` varchar(30) DEFAULT NULL,
                                               `second_img_name` varchar(70) DEFAULT NULL,
                                               `third_img_path` varchar(30) DEFAULT NULL,
                                               `third_img_name` varchar(70) DEFAULT NULL,
                                               `sys_update_date` datetime NOT NULL,
                                               `sys_create_date` datetime NOT NULL,
                                               `report_stts` int NOT NULL,
                                               `reply_user` binary(16) DEFAULT NULL,
                                               `reply_contents` varchar(500) DEFAULT NULL,
                                               PRIMARY KEY (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS `numberbox_tc`.`formul_key`;
CREATE TABLE `formul_key` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `formul_order` int NOT NULL,
                              `formul_name` varchar(30) NOT NULL,
                              `formul_ui` varchar(300) NOT NULL,
                              `shortcut_key` varchar(2) DEFAULT NULL,
                              `latex_grammer` varchar(40) DEFAULT NULL,
                              `guide` varchar(80) DEFAULT NULL,
                              `shortcut_keycode` varchar(5) DEFAULT NULL,
                              `tex_grammer` varchar(40) DEFAULT NULL,
                              `line_change` int NOT NULL DEFAULT '0',
                              `nb_grammer` varchar(450) DEFAULT NULL,
                              `classification` varchar(5) NOT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`hwp_convert_contents`;
CREATE TABLE `hwp_convert_contents` (
                                        `convert_no` int NOT NULL AUTO_INCREMENT,
                                        `user_uniq_id` binary(16) NOT NULL,
                                        `converted` tinyint NOT NULL DEFAULT '0',
                                        `convert_file_name` varchar(70) NOT NULL DEFAULT '',
                                        `convert_contents` mediumtext,
                                        `img_path` varchar(120) NOT NULL,
                                        `err_stts` tinyint NOT NULL DEFAULT '0',
                                        `sys_create_date` datetime NOT NULL,
                                        `sys_update_date` datetime NOT NULL,
                                        PRIMARY KEY (`convert_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS  `numberbox_tc`.`hwp_convert_contents_statistic`;
CREATE TABLE `hwp_convert_contents_statistic` (
                                                  `seq_no` int NOT NULL AUTO_INCREMENT,
                                                  `convert_no` int NOT NULL,
                                                  `user_uniq_id` binary(16) NOT NULL,
                                                  `convert_file_name` varchar(70) NOT NULL DEFAULT '',
                                                  `sys_create_date` datetime NOT NULL,
                                                  PRIMARY KEY (`seq_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`img_file_info`;
CREATE TABLE `img_file_info` (
                                 `seq_uuid` binary(16) NOT NULL,
                                 `action_id` int NOT NULL,
                                 `contents_no` int DEFAULT NULL,
                                 `img_path_code` int NOT NULL,
                                 `img_path` varchar(30) NOT NULL,
                                 `img_file_name` varchar(100) NOT NULL,
                                 `sys_create_date` datetime NOT NULL,
                                 PRIMARY KEY (`seq_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`math_con_like_info`;
CREATE TABLE `math_con_like_info` (
                                      `contents_no` int NOT NULL,
                                      `user_uniq_id` binary(16) NOT NULL,
                                      PRIMARY KEY (`contents_no`,`user_uniq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`math_con_repo_info`;
CREATE TABLE `math_con_repo_info` (
                                      `contents_no` int NOT NULL,
                                      `user_uniq_id` binary(16) NOT NULL,
                                      `sys_create_date` datetime DEFAULT NULL,
                                      PRIMARY KEY (`contents_no`,`user_uniq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`math_contents`;
CREATE TABLE `math_contents` (
                                 `contents_no` int NOT NULL AUTO_INCREMENT,
                                 `unit_uniq_no` int NOT NULL,
                                 `type_no` int NOT NULL,
                                 `contents` text NOT NULL,
                                 `contents_img` varchar(70) DEFAULT NULL,
                                 `img_path` varchar(30) DEFAULT NULL,
                                 `solution` text,
                                 `solution_img` varchar(70) DEFAULT NULL,
                                 `solution_img_path` varchar(30) DEFAULT NULL,
                                 `fir_no` text,
                                 `sec_no` text,
                                 `thr_no` text,
                                 `four_no` text,
                                 `fif_no` text,
                                 `multi_choice_type` varchar(1) NOT NULL,
                                 `answer` text,
                                 `choice_answer` varchar(9) DEFAULT NULL,
                                 `user_uniq_id` binary(16) NOT NULL,
                                 `org_src_ref` varchar(20) DEFAULT NULL,
                                 `org_src_no` int DEFAULT NULL,
                                 `ques_level` int NOT NULL,
                                 `ans_exist_stts` int NOT NULL,
                                 `contents_classify` int NOT NULL,
                                 `org_contents_no` int DEFAULT '0',
                                 `svc_posb_stts` int NOT NULL DEFAULT '0',
                                 `sys_create_date` datetime NOT NULL,
                                 `sys_update_date` datetime NOT NULL,
                                 `trans_con_cnt` int DEFAULT '0',
                                 PRIMARY KEY (`contents_no`),
                                 KEY `unit_uniq_no` (`unit_uniq_no`,`type_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`math_contents_comp`;
CREATE TABLE `math_contents_comp` (
                                      `seq_no` int NOT NULL AUTO_INCREMENT,
                                      `contents_no` int NOT NULL,
                                      `org_src_ref` varchar(20) NOT NULL,
                                      `org_src_no` int DEFAULT NULL,
                                      `org_src_page` int DEFAULT NULL,
                                      `copyright_year` varchar(20) DEFAULT NULL,
                                      `math_type_classify` varchar(20) DEFAULT NULL,
                                      `user_uniq_id` binary(16) NOT NULL,
                                      `sys_create_date` datetime NOT NULL,
                                      `sys_update_date` datetime NOT NULL,
                                      PRIMARY KEY (`seq_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`math_contents_grammer`;
CREATE TABLE `math_contents_grammer` (
                                         `contents_no` int NOT NULL,
                                         `contents_gram` text NOT NULL,
                                         PRIMARY KEY (`contents_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`math_contents_ipsi`;
CREATE TABLE `math_contents_ipsi` (
                                      `seq_no` int NOT NULL AUTO_INCREMENT,
                                      `contents_no` int NOT NULL,
                                      `manage_ins` int NOT NULL,
                                      `imp_year` int NOT NULL,
                                      `imp_month` int NOT NULL,
                                      `wrong_ratio` int NOT NULL,
                                      `paper_type` int DEFAULT NULL,
                                      `odd_ques_num` int NOT NULL,
                                      `even_ques_num` int DEFAULT NULL,
                                      `sys_create_date` datetime NOT NULL,
                                      `sys_update_date` datetime NOT NULL,
                                      PRIMARY KEY (`seq_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`math_contents_license`;
CREATE TABLE `math_contents_license` (
                                         `contents_no` int NOT NULL,
                                         `online_lic_stts` int NOT NULL,
                                         `per_lic_stts` int NOT NULL,
                                         `per_lic_price` int DEFAULT NULL,
                                         `ent_lic_stts` int NOT NULL,
                                         `ent_lic_price` int DEFAULT NULL,
                                         `share_stts` int NOT NULL,
                                         `sys_create_date` datetime NOT NULL,
                                         `sys_update_date` datetime NOT NULL,
                                         PRIMARY KEY (`contents_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`math_docs_paper`;
CREATE TABLE `math_docs_paper` (
                                   `docs_no` int NOT NULL AUTO_INCREMENT,
                                   `contents_no_list` varchar(700) NOT NULL,
                                   `user_uniq_id` binary(16) NOT NULL,
                                   `docs_grade` varchar(7) DEFAULT NULL,
                                   `docs_title` varchar(20) DEFAULT NULL,
                                   `docs_sub_title` varchar(100) DEFAULT NULL,
                                   `docs_owner` varchar(20) DEFAULT NULL,
                                   `docs_err_stts` int NOT NULL,
                                   `sys_update_date` datetime NOT NULL,
                                   `sys_create_date` datetime NOT NULL,
                                   PRIMARY KEY (`docs_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`math_docs_usage`;
CREATE TABLE `math_docs_usage` (
                                   `docs_no` int NOT NULL AUTO_INCREMENT,
                                   `contents_no_list` varchar(700) DEFAULT NULL,
                                   `user_uniq_id` binary(16) DEFAULT NULL,
                                   `docs_grade` varchar(7) DEFAULT NULL,
                                   `docs_title` varchar(20) DEFAULT NULL,
                                   `docs_sub_title` varchar(100) DEFAULT NULL,
                                   `docs_owner` varchar(20) DEFAULT NULL,
                                   `sys_update_date` datetime DEFAULT NULL,
                                   `sys_create_date` datetime DEFAULT NULL,
                                   PRIMARY KEY (`docs_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`math_resource`;
CREATE TABLE `math_resource` (
                                 `resource_no` int NOT NULL AUTO_INCREMENT,
                                 `user_uniq_id` binary(16) NOT NULL,
                                 `title` varchar(30) NOT NULL,
                                 `img_path` varchar(30) NOT NULL,
                                 `img_name` varchar(70) NOT NULL,
                                 `ppt_path` varchar(30) NOT NULL,
                                 `ppt_name` varchar(70) NOT NULL,
                                 `ppt_page_cnt` int NOT NULL,
                                 `down_cnt` int NOT NULL,
                                 `sys_create_date` datetime NOT NULL,
                                 `sys_update_date` datetime NOT NULL,
                                 PRIMARY KEY (`resource_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`math_resource_cate`;
CREATE TABLE `math_resource_cate` (
                                      `seq_no` int NOT NULL AUTO_INCREMENT,
                                      `resource_no` int NOT NULL,
                                      `main_cate_no` int NOT NULL,
                                      `mid_cate_no` int NOT NULL,
                                      PRIMARY KEY (`seq_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`math_resource_img`;
CREATE TABLE `math_resource_img` (
                                     `slide_img_no` int NOT NULL AUTO_INCREMENT,
                                     `resource_no` int NOT NULL,
                                     `img_path` varchar(30) NOT NULL,
                                     `img_name` varchar(70) NOT NULL,
                                     PRIMARY KEY (`slide_img_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`math_resource_menu`;
CREATE TABLE `math_resource_menu` (
                                      `seq_no` int NOT NULL AUTO_INCREMENT,
                                      `main_cate_no` int NOT NULL,
                                      `main_cate_name` varchar(20) NOT NULL,
                                      `mid_cate_no` int NOT NULL,
                                      `mid_cate_name` varchar(20) NOT NULL,
                                      `align_order` int NOT NULL,
                                      PRIMARY KEY (`seq_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`math_type_info`;
CREATE TABLE `math_type_info` (
                                  `unit_uniq_no` int NOT NULL,
                                  `type_no` int NOT NULL DEFAULT '0',
                                  `ques_type` varchar(1500) NOT NULL,
                                  `type_order` int NOT NULL,
                                  PRIMARY KEY (`unit_uniq_no`,`type_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`math_unit_info`;
CREATE TABLE `math_unit_info` (
                                  `unit_uniq_no` int NOT NULL,
                                  `subject` varchar(20) NOT NULL,
                                  `fir_unit` varchar(30) NOT NULL,
                                  `sec_unit` varchar(30) NOT NULL,
                                  `thr_unit` varchar(1000) DEFAULT NULL,
                                  PRIMARY KEY (`unit_uniq_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`math_unit_keyword`;
CREATE TABLE `math_unit_keyword` (
                                     `id` int NOT NULL AUTO_INCREMENT,
                                     `unit_uniq_no` int NOT NULL,
                                     `keyword` varchar(30) DEFAULT NULL,
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`members`;
CREATE TABLE `members` (
                           `user_uniq_id` binary(16) NOT NULL,
                           `email` varchar(60) NOT NULL,
                           `password` varchar(60) NOT NULL,
                           `human_status` int NOT NULL DEFAULT '0',
                           `fail_count` int NOT NULL,
                           `last_fail_time` datetime DEFAULT NULL,
                           `tmp_password` tinyint(1) NOT NULL DEFAULT '0',
                           `signup_date` datetime NOT NULL,
                           `last_login_date` datetime NOT NULL,
                           PRIMARY KEY (`user_uniq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`members_follow_info`;
CREATE TABLE `members_follow_info` (
                                       `following_user_no` int NOT NULL,
                                       `follower_user_no` int NOT NULL,
                                       `sys_create_date` datetime DEFAULT NULL,
                                       PRIMARY KEY (`following_user_no`,`follower_user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`members_private`;
CREATE TABLE `members_private` (
                                   `user_uniq_id` binary(16) NOT NULL,
                                   `user_name` varchar(34) DEFAULT NULL,
                                   `phone_number` varchar(11) DEFAULT NULL,
                                   `birth` varchar(6) DEFAULT NULL,
                                   PRIMARY KEY (`user_uniq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`members_profile`;
CREATE TABLE `members_profile` (
                                   `user_no` int NOT NULL AUTO_INCREMENT,
                                   `user_uniq_id` binary(16) NOT NULL,
                                   `nickname` varchar(24) DEFAULT NULL,
                                   `profile_img_name` varchar(70) DEFAULT NULL,
                                   `profile_img_path` varchar(30) DEFAULT NULL,
                                   `profile_type` int DEFAULT '0',
                                   `hwp_down_cnt` int NOT NULL DEFAULT '0',
                                   `unit_mapping_cnt` int NOT NULL DEFAULT '0',
                                   `ai_contents_cnt` int NOT NULL DEFAULT '0',
                                   PRIMARY KEY (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`members_role`;
CREATE TABLE `members_role` (
                                `seq_no` int NOT NULL AUTO_INCREMENT,
                                `user_uniq_id` binary(16) NOT NULL,
                                `enabled` tinyint(1) NOT NULL,
                                `role_name` varchar(10) NOT NULL,
                                PRIMARY KEY (`seq_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`refresh_token_info`;
CREATE TABLE `refresh_token_info` (
                                      `id` int NOT NULL AUTO_INCREMENT,
                                      `token` varchar(300) NOT NULL,
                                      `user_uniq_id` binary(16) NOT NULL,
                                      `token_create_date` datetime NOT NULL,
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

DROP TABLE IF EXISTS  `numberbox_tc`.`tmp_img_file_info`;
CREATE TABLE `tmp_img_file_info` (
                                     `seq_no` int NOT NULL AUTO_INCREMENT,
                                     `action_id` int NOT NULL,
                                     `user_uniq_id` binary(16) NOT NULL,
                                     `img_path_code` int NOT NULL,
                                     `img_path` varchar(30) NOT NULL,
                                     `img_file_name` varchar(100) NOT NULL,
                                     `sys_create_date` datetime NOT NULL,
                                     PRIMARY KEY (`seq_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;