/**
  Def. N명의수학 신규 등록 테이블
 */
CREATE TABLE `sys_garbage_file` (
                                    `id` int NOT NULL AUTO_INCREMENT,
                                    `type` enum ('S3') NOT NULL comment 's3 : aws s3 저장 파일',
                                    `path` varchar(30) NOT NULL,
                                    `name` varchar(100) NOT NULL,
                                    `fail_cnt` int default 0 NOT NULL comment '파일 삭제 실패 횟수',
                                    `sys_update_date` datetime NOT NULL,
                                    `sys_create_date` datetime NOT NULL,
                                    PRIMARY KEY (`id`)
) comment '삭제대상 유휴 파일 목록'
;


create table log_client_api (
                                id int not null auto_increment,
                                member_id binary(16) NOT NULL,
                                browser varchar(7) NOT NULL,
                                os varchar(7) NOT NULL,
                                ip varchar(15) NOT NULL,
                                http_method varchar(5) not null,
                                uri varchar(500) NOT NULL,
                                response_code tinyint UNSIGNED NOT NULL,
                                request_body TEXT,
                                sys_create_time datetime NOT NULL,
                                PRIMARY KEY (`id`)
) comment '사용자 api 요청 로깅 정보';
