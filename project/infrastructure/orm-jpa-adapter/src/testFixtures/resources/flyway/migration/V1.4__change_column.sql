/**
  Def. 칼럼명 변경
 */
ALTER TABLE members CHANGE password password varchar(60);
ALTER TABLE members CHANGE tmp_password is_tmp_password tinyint(1) not null;
ALTER TABLE member_refresh_token CHANGE token_create_date sys_create_time datetime not null;
ALTER TABLE member_refresh_token CHANGE user_uniq_id member_id binary(16) not null;

ALTER TABLE members_profile CHANGE nickname nickname varchar(24) not null;

ALTER TABLE math_contents_comp drop column user_uniq_id;

ALTER TABLE math_contents_license DROP PRIMARY KEY;
ALTER TABLE math_contents_license ADD COLUMN id INT AUTO_INCREMENT PRIMARY KEY;
