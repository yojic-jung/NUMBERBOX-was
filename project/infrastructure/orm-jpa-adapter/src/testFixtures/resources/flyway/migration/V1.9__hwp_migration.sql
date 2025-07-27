/**
  Def. N명의수학 hwp 파일 변환 컨텐츠 테이블 변경사항
 */
ALTER TABLE hwp_convert_contents CHANGE convert_no id int NOT NULL AUTO_INCREMENT;
ALTER TABLE hwp_convert_contents CHANGE converted is_converted tinyint NOT NULL DEFAULT '1' comment "파일 변환 완료 여부";
ALTER TABLE hwp_convert_contents CHANGE user_uniq_id member_id binary(16) NOT NULL;
ALTER TABLE hwp_convert_contents CHANGE convert_file_name file_name varchar(70) NOT NULL DEFAULT '' comment "원본 파일명";
ALTER TABLE hwp_convert_contents CHANGE convert_contents contents mediumtext;
ALTER TABLE hwp_convert_contents CHANGE err_stts is_grammar_converted tinyint NOT NULL DEFAULT '1' comment "문법 변환 완료 여부";
update hwp_convert_contents set is_grammar_converted =1;

/*
 변환 요청 파일 정보
 */
CREATE TABLE `hwp_convert_file` (
    `id` int NOT NULL AUTO_INCREMENT,
    `member_id` binary(16) NOT NULL,
    `convert_type` enum('jsonToHwp', 'hwpToHtml') NOT NULL,
    `origin_file_name` varchar(100) NOT NULL comment '원본 파일명',
    `convert_file_name` varchar(100) COMMENT '변환 파일명',
    `request_at` datetime NOT NULL COMMENT '요청 시간',
    `is_request_success` tinyint(1) NOT NULL DEFAULT 0 COMMENT '요청 성공 여부 (0=실패, 1=성공)',
    `convert_at` datetime COMMENT '변환 시간',
    `deleted_at` datetime COMMENT '삭제 시간',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;