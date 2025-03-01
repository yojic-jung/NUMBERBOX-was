DELETE
FROM `numberbox_tc`.`math_resource_menu`;
--
INSERT INTO `numberbox_tc`.`math_resource_menu` (`id`, `main_cate_id`, `main_cate_name`, `mid_cate_id`, `mid_cate_name`,
                                                 `align_order`)
VALUES (1, 1, '평면도형', 1, '삼각형', 1),
       (2, 1, '평면도형', 2, '사각형', 2),
       (3, 1, '평면도형', 3, '원', 3),
       (4, 1, '평면도형', 4, '기타', 4),
       (5, 2, '입체도형', 1, '육면체', 5),
       (6, 2, '입체도형', 2, '각뿔', 6),
       (7, 2, '입체도형', 3, '구', 7),
       (8, 3, '수직선', 1, '수직선', 8),
       (9, 4, '함수', 1, '함수', 9),
       (10, 5, '도형의 방정식', 1, '도형의 방정식', 10),
       (11, 6, '전개도', 1, '전개도', 11);
