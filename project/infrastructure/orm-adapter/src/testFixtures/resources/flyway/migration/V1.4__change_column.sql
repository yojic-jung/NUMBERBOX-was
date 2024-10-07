/**
  Def. 칼럼명 변경
 */
ALTER TABLE member_refresh_token CHANGE token_create_date sys_create_time datetime not null;
ALTER TABLE member_refresh_token CHANGE user_uniq_id member_id binary(16) not null;

ALTER TABLE members_profile CHANGE nickname nickname varchar(24) not null;
