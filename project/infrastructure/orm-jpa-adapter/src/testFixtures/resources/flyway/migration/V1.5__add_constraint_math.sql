/**
  Def. N명의수학 제약조건 추가(24.10.20)
 */
/* 외래키 제약조건 */
ALTER TABLE math_contents
    ADD CONSTRAINT math_contents_unit_uniq_no_foreign_key FOREIGN KEY (unit_uniq_no)
        REFERENCES math_unit_info (unit_uniq_no) ON UPDATE cascade;