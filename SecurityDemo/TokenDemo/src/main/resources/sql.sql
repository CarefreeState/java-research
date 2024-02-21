        SELECT
            DISTINCT m.`perms` perms
        FROM
        sys_user u
        LEFT JOIN `sys_user_role` ur ON u.`id` = ur.`user_id`
        LEFT JOIN `sys_role` r ON ur.`role_id` = r.`id`
        LEFT JOIN `sys_role_menu` rm ON ur.`role_id` = rm.`role_id`
        LEFT JOIN `sys_menu` m ON m.`id` = rm.`menu_id`
        WHERE
            u.`id` = 2
        AND r.`status` = 0
        AND m.`status` = 0;