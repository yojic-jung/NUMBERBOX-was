DELETE
FROM `numberbox_tc`.`cs_error_report`;
--
INSERT INTO `numberbox_tc`.`cs_error_report` (`id`, `err_type`, `contents_id`, `report_member_id`, `report_contents`,
                                              `client_os`, `client_browser`,
                                              `first_img_path`, `first_img_name`, `second_img_path`, `second_img_name`,
                                              `third_img_path`,
                                              `third_img_name`, `sys_update_date`, `sys_create_date`, `report_stts`,
                                              `reply_member_id`,
                                              `reply_contents`)
VALUES (1, 0, 0, UNHEX('10ED5466CDA8EA4D9BC7037CB86FDB20'), '문의 사항 운영 테스트', 'windows', 'chrome',
        null, null,
        null, null, NULL, NULL, '2022-07-29 17:19:13',
        '2022-07-29 17:18:04', 0, null,
        null),
       (3, 0, 0, UNHEX('10ED5466CDA8EA4D9BC7037CB86FDB20'), '문의 사항 운영 테스트', 'windows', 'chrome',
        'sampleFirImgPath', 'sampleFirImgName',
        'secImgPath', 'secImgName', 'thrImgPath', 'thrImgName', '2022-07-29 17:19:13',
        '2022-07-29 17:18:04', 0, UNHEX('24CA3122CDA8EA4D9BC7037CB86FDB20'),
        'smapleReplyContents');
