DELETE
FROM `numberbox_tc`.`members_private`;
--
INSERT INTO `numberbox_tc`.`members_private` (`user_uniq_id`, `user_name`, `phone_number`, `birth`, `sys_create_time`,
                                              `sys_update_time`)
VALUES (UNHEX('10ED5466CDA8EA4D9BC7037CB86FDB20'), '홍길동', '01009870987', '810202', '2022-12-21 18:55:42',
        '2022-12-21 18:55:42'),
       (UNHEX('24CA3122CDA8EA4D9BC7037CB86FDB20'), '고길동', '01009870987', '810202', '2022-12-21 18:55:42',
        '2022-12-21 18:55:42'),
       (UNHEX('33CA3122CDA8EA4D9BC7037CB86FDB20'), '정길동', '01012870987', '810203', '2022-12-21 18:55:42',
        '2022-12-21 18:55:42'),
       -- for delete
       (UNHEX('32CA3122CDA8EA4D9BC7037CB86FDB20'), '김길동', '01072870987', '810203', '2022-12-21 18:55:42',
        '2022-12-21 18:55:42');
