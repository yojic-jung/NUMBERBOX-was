DELETE
FROM `numberbox_tc`.`log_client_api`;
--
INSERT INTO `numberbox_tc`.`log_client_api` (`id`, `member_id`, `browser`, `os`, `ip`, `http_method`, `uri`,
                                             `response_code`, `sys_create_time`)
VALUES ('1', UNHEX('10ED5466CDA8EA4D9BC7037CB86FDB20'), 'chrome', 'mac', '127.0.0.1', 'GET',
        '/myWasApi/public/math/menu/unit', '200', '2025-01-19 10:52:27');
