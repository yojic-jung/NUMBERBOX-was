DELETE
FROM `numberbox_tc`.`members_role`;
--
INSERT INTO `numberbox_tc`.`members_role` (`seq_no`, `user_uniq_id`, `enabled`, `role_name`, `sys_create_time`,
                                           `sys_update_time`)
VALUES ('1', UNHEX('10CA3122CDA8EA4D9BC7037CB86FDB20'), '1', 'USER', '2022-12-19 12:25:44', '2022-12-19 12:25:44'),
       ('2', UNHEX('33CA3122CDA8EA4D9BC7037CB86FDB20'), '0', 'USER', '2022-12-19 12:25:44', '2022-12-19 12:25:44');
