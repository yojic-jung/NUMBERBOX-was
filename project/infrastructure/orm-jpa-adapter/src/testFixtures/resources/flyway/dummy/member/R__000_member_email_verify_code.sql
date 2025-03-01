DELETE
FROM `numberbox_tc`.`members_verify_code`;
--
INSERT INTO `numberbox_tc`.`members_verify_code` (`email`, `id_code`, `sys_create_time`, `code_type`, `try_cnt`,
                                                  `sys_update_time`)
VALUES ('wlrtl@test.com', '10ed5466-cda8-ea4d-9bc7-037cb86fdb20', '2023-07-19 10:32:36', '1', '0',
        '2024-09-20 22:18:26'),
       ('wlrtl22@test.com', '32CA3122-CDA8-EA4D-9BC7-037CB86FDB20', '2023-07-19 10:32:36', '1', '0',
        '2024-09-20 22:18:26');

