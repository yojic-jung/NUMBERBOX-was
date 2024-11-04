/**
  Def. N명의수학 CS(고객센터) 관련 테이블 변경사항
 */

RENAME TABLE error_report TO cs_err_report;

ALTER TABLE cs_err_report CHANGE contents_no contents_id int;

