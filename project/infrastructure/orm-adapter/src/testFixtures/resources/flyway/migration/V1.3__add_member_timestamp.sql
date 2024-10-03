/**
  Def. members_*** 테이블 타임스탬프 추가
 */
alter table members_veify_code add try_cnt int not null default 0;
alter table members_veify_code add code_type tinyint not null default 0;
alter table members_veify_code add sys_update_time datetime not null default CURRENT_TIMESTAMP;
alter table members_profile modify profile_type int not null default 0 comment "0: 미등록, 1: 원장, 2: 강사, 3: 교사, 4: 학부모, 5: 학생, 6: 미등록 ";
alter table member_refresh_token add sys_update_time datetime not null default CURRENT_TIMESTAMP;

alter table members add sys_update_time datetime;
alter table members_role add sys_create_time datetime;
alter table members_role add sys_update_time datetime;
alter table members_private add sys_create_time datetime;
alter table members_private add sys_update_time datetime;
alter table members_profile add sys_create_time datetime;
alter table members_profile add sys_update_time datetime;

SET SQL_SAFE_UPDATES = 0;

/* members 타임스탬프 update */
UPDATE members
    SET sys_update_time = signup_date;

/* members_role 타임스탬프 update */
UPDATE members_role mr
    JOIN members m ON mr.user_uniq_id = m.user_uniq_id
    SET mr.sys_create_time = m.signup_date;
UPDATE members_role mr
    JOIN members m ON mr.user_uniq_id = m.user_uniq_id
    SET mr.sys_update_time = m.signup_date;

/* members_private 타임스탬프 update */
UPDATE members_private mp
    JOIN members m ON mp.user_uniq_id = m.user_uniq_id
    SET mp.sys_create_time = m.signup_date;
UPDATE members_private mp
    JOIN members m ON mp.user_uniq_id = m.user_uniq_id
    SET mp.sys_update_time = m.signup_date;

/* members_profile 타임스탬프 update */
UPDATE members_profile mp
    JOIN members m ON mp.user_uniq_id = m.user_uniq_id
    SET mp.sys_create_time = m.signup_date;
UPDATE members_profile mp
    JOIN members m ON mp.user_uniq_id = m.user_uniq_id
    SET mp.sys_update_time = m.signup_date;

SET SQL_SAFE_UPDATES = 1;

/* members, members_role, members_private, members_profile 타임스탬프 not null로 변경 */
alter table members modify sys_update_time datetime not null default CURRENT_TIMESTAMP;
alter table members_role modify sys_create_time datetime not null default CURRENT_TIMESTAMP;
alter table members_role modify sys_update_time datetime not null default CURRENT_TIMESTAMP;
alter table members_private modify sys_create_time datetime not null default CURRENT_TIMESTAMP;
alter table members_private modify sys_update_time datetime not null default CURRENT_TIMESTAMP;
alter table members_profile modify sys_create_time datetime not null default CURRENT_TIMESTAMP;
alter table members_profile modify sys_update_time datetime not null default CURRENT_TIMESTAMP;
