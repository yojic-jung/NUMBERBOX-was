/**
  Def. N명의수학 math 관련 테이블 변경사항
 */

RENAME TABLE math_contents_license TO math_con_license;
RENAME TABLE math_contents_ipsi TO math_con_ipsi_src;
RENAME TABLE math_contents_comp TO math_con_similar_src;
RENAME TABLE math_contents_grammer TO math_con_grammar;
RENAME TABLE math_con_like_info TO math_con_like;
RENAME TABLE math_con_repo_info TO math_con_repo;
RENAME TABLE math_type_info TO math_category_type;
RENAME TABLE math_unit_info TO math_category_unit;

-- math_contents 칼럼명 변경
ALTER TABLE math_contents CHANGE contents_no id INT NOT NULL;
ALTER TABLE math_contents MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT;
ALTER TABLE math_contents CHANGE unit_uniq_no unit_id INT NOT NULL;
ALTER TABLE math_contents CHANGE type_no type_id INT NOT NULL;
ALTER TABLE math_contents CHANGE user_uniq_id member_id binary(16) NOT NULL;
ALTER TABLE math_contents CHANGE org_contents_no org_contents_id int DEFAULT '0';
-- math_con_similar_src 칼럼명 및 제약조건 변경
ALTER TABLE math_con_similar_src CHANGE seq_no id INT NOT NULL;
ALTER TABLE math_con_similar_src MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT;
ALTER TABLE math_con_similar_src CHANGE contents_no contents_id INT NOT NULL;
DELETE FROM math_con_similar_src WHERE contents_id IN (2667, 2669, 2670) AND contents_id IS NOT NULL;
ALTER TABLE math_con_similar_src ADD CONSTRAINT math_con_similar_src_contents_id_fk
FOREIGN KEY (contents_id)  REFERENCES math_contents (id) ON UPDATE CASCADE ON DELETE CASCADE;
-- math_con_license 칼럼명 및 제약조건 변경
ALTER TABLE math_con_license CHANGE contents_no contents_id INT NOT NULL;
ALTER TABLE math_con_license ADD CONSTRAINT math_con_license_contents_id_fk
    FOREIGN KEY (contents_id)  REFERENCES math_contents (id) ON UPDATE CASCADE ON DELETE CASCADE;
-- math_con_ipsi_src 칼럼명 및 제약조건 변경
ALTER TABLE math_con_ipsi_src CHANGE seq_no id INT NOT NULL;
ALTER TABLE math_con_ipsi_src MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT;
ALTER TABLE math_con_ipsi_src CHANGE contents_no contents_id INT NOT NULL;
ALTER TABLE math_con_ipsi_src ADD CONSTRAINT math_con_ipsi_src_contents_id_fk
    FOREIGN KEY (contents_id)  REFERENCES math_contents (id) ON UPDATE CASCADE ON DELETE CASCADE;
-- math_con_grammar 칼럼명 및 제약조건 변경
ALTER TABLE math_con_grammar CHANGE contents_no contents_id INT NOT NULL;
ALTER TABLE math_con_grammar CHANGE contents_gram grammar TEXT NOT NULL;
ALTER TABLE math_con_grammar ADD CONSTRAINT math_con_grammar_contents_id_fk
    FOREIGN KEY (contents_id)  REFERENCES math_contents (id) ON UPDATE CASCADE ON DELETE CASCADE;
-- math_con_like 칼럼명 변경
ALTER TABLE math_con_like CHANGE contents_no contents_id INT NOT NULL;
ALTER TABLE math_con_like CHANGE user_uniq_id member_id binary(16) NOT NULL;
-- math_con_repo 칼럼명 변경
ALTER TABLE math_con_repo CHANGE contents_no contents_id INT NOT NULL;
ALTER TABLE math_con_repo CHANGE user_uniq_id member_id binary(16) NOT NULL;
-- math_category_unit 칼럼명 변경
ALTER TABLE math_category_unit CHANGE unit_uniq_no id INT NOT NULL;
-- math_category_type 칼럼명 변경
ALTER TABLE math_category_type CHANGE unit_uniq_no unit_id INT NOT NULL;
ALTER TABLE math_category_type CHANGE type_no type_id int NOT NULL;
-- math_docs_paper 칼럼명 변경
ALTER TABLE math_docs_paper CHANGE docs_no id int AUTO_INCREMENT NOT NULL;
ALTER TABLE math_docs_paper CHANGE contents_no_list contents_id_list varchar(700) NOT NULL;
ALTER TABLE math_docs_paper CHANGE user_uniq_id member_id binary(16) NOT NULL;
ALTER TABLE math_docs_paper CHANGE docs_err_stts docs_stts int NOT NULL;
ALTER TABLE math_docs_paper ADD sys_delete_date datetime;
-- math_docs_usage 칼럼명 변경
ALTER TABLE math_docs_usage CHANGE docs_no id int AUTO_INCREMENT NOT NULL;
ALTER TABLE math_docs_usage CHANGE contents_no_list contents_id_list varchar(700) NOT NULL;
ALTER TABLE math_docs_usage CHANGE user_uniq_id member_id binary(16) NOT NULL;
-- math_resource_menu 칼럼명 변경
ALTER TABLE math_resource_menu CHANGE seq_no id int AUTO_INCREMENT NOT NULL;
ALTER TABLE math_resource_menu CHANGE main_cate_no main_cate_id int NOT NULL;
ALTER TABLE math_resource_menu CHANGE mid_cate_no mid_cate_id int NOT NULL;
-- math_resource 칼럼명 변경
ALTER TABLE math_resource CHANGE resource_no id int AUTO_INCREMENT NOT NULL;
ALTER TABLE math_resource CHANGE user_uniq_id member_id BINARY(16) NOT NULL;
-- math_resource_cate 칼럼명 변경
ALTER TABLE math_resource_cate CHANGE seq_no id int AUTO_INCREMENT NOT NULL;
ALTER TABLE math_resource_cate CHANGE resource_no resource_id int NOT NULL;
ALTER TABLE math_resource_cate CHANGE main_cate_no main_cate_id int NOT NULL;
ALTER TABLE math_resource_cate CHANGE mid_cate_no mid_cate_id int NOT NULL;
-- math_resource_img 칼럼명 변경
ALTER TABLE math_resource_img CHANGE slide_img_no id int AUTO_INCREMENT NOT NULL;
ALTER TABLE math_resource_img CHANGE resource_no resource_id int NOT NULL;
