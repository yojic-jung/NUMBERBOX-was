/**
  Def. N명의수학 CS(고객센터) 관련 테이블 변경사항
 */

RENAME TABLE error_report TO cs_error_report;

ALTER TABLE cs_error_report CHANGE report_id id int not null auto_increment;
ALTER TABLE cs_error_report CHANGE contents_no contents_id int;
ALTER TABLE cs_error_report CHANGE report_user report_member_id binary(16) not null;
ALTER TABLE cs_error_report CHANGE reply_user reply_member_id binary(16);
ALTER TABLE cs_error_report CHANGE os_info client_os varchar(7) not null;
ALTER TABLE cs_error_report CHANGE browser client_browser varchar(7) not null;
