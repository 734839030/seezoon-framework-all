#按钮
insert into `sys_menu` ( `id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `type`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`) values ( REPLACE(UUID(),"-",""), '${id}', '0,${id},', '修改', '2', null, null, '2', null, '1', '${moduleName}:${functionName}:update', '1', now(), '1', now(), null);
insert into `sys_menu` ( `id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `type`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`) values ( REPLACE(UUID(),"-",""), '${id}', '0,${id},', '删除', '10', null, null, '2', null, '1', '${moduleName}:${functionName}:delete', '1', now(), '1', now(), null);
insert into `sys_menu` ( `id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `type`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`) values ( REPLACE(UUID(),"-",""), '${id}', '0,${id},', '添加', '1', null, null, '2', null, '1', '${moduleName}:${functionName}:save', '1', now(), '1', now(), null);
#菜单如果已经存在就不需要跑了。
insert into `sys_menu` ( `id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `type`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`) values ( '${id}', '0', '0,', '${menuName}', '1', '/pages/${moduleName}/${functionName}.html', 'main', '1', 'fa fa-fw fa-user', '1', '${moduleName}:${functionName}:qry', '1', now(), '1', now(), null);
commit;

${id1}