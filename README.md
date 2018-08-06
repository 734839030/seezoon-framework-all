## Seezoon项目介绍 ##
基于spring,mybatis,shiro面向接口开发的的一套后台管理系统，方便快速开发；采用常用的技术栈，降低学习成本，项目完全前后端分离，后端定义统一的接口格式，统一参数校验，统一权限控制，异常拦截，全局错误码等，让后续开发只需关注业务代码。

项目定位于快速开发，所以不需要复杂的分布式，分模块的的开发方式，方便快速部署升级，项目支持按钮级别权限控制，自动控制按钮隐藏显示，按钮支持父子权限，支持本地和云存储。

前端采用最简单jquery + wayjs(双向数据绑定)，wayjs 只做数据绑定这一件事，所以入门非常快，几分钟就可以熟练使用，这也是没有采用Vue的原因，这里引入nodejs工具gulp 打包工具，方便编译出前端文件。

官方QQ群：514685454、574933593
## 体验地址 ##
[https://dev.seezoon.com](https://dev.seezoon.com)   大家如果觉得做的还可以，麻烦给个star,万分感谢；

### 1M 带宽请大家忍耐下，为了给大家看到最全的功能这里提供超级管理员账号admin 123456 希望大家不要改密码，删默认菜单等，其余可以随意操作。 ###

> 本项目会持续完善更新，麻烦Watch、Star下项目，同时也是对项目最好的支持，谢谢。 

## 功能介绍 ##
* 前后端完全分离，采用node插件gulp 管理
* 前端数据双向绑定wayjs
* 统一异常处理，全局错误码
* RBAC权限管理，精细到菜单，按钮级别权限，支持父子权限
* 数据权限控制，不需要额外代码，框架自动支持，支持手动插拔
* 前后端数据双向校验
* 强大的代码生成，支持富文本，图片，文件,数据字典等复杂控件的生成，生成代码包含，完整的前端，后端，权限控制，校验等
* 文件上传支持本地和阿里云(本人只对阿里云有感觉，所以不考虑其他厂商的)两种模式，通过配置文件转换
* 集成elastic job 分片作业，流式作业，任务调度轨迹等
* 丰富的工具类，excel导入导出，图片裁剪，验证码，二维码，基于线程池的httpclient
* 微信公众号授权登录，js支付，扫码支付(两种模式)，微信小程序授权登录，小程序支付，小程序通过统一请求上送header实现上送cookie
* 易上手，在多个团队中使用，团队成员接受度较高，上手较快

## 常用框架 ##

#### 后端 ####

技术 | 名称 | 文档地址
----|----- |-----
Spring Framework |依赖管理 |[https://docs.spring.io/spring/docs/5.0.6.BUILD-SNAPSHOT/spring-framework-reference/](https://docs.spring.io/spring/docs/5.0.6.BUILD-SNAPSHOT/spring-framework-reference/)
Spring MVC | MVC|[https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html)
Mybatis | ORM |[http://www.mybatis.org/mybatis-3/zh/index.html](http://www.mybatis.org/mybatis-3/zh/index.html)
Shiro | 权限、认证|[http://shiro.apache.org/documentation.html#apache-shiro-reference-and-api](http://shiro.apache.org/documentation.html#apache-shiro-reference-and-api)
Shiro Redis|shiro 缓存|[https://github.com/alexxiyang/shiro-redis](https://github.com/alexxiyang/shiro-redis)
Spring session（C端）|分布式会话|[https://docs.spring.io/spring-session/docs/2.0.3.BUILD-SNAPSHOT/reference/html5/](https://docs.spring.io/spring-session/docs/2.0.3.BUILD-SNAPSHOT/reference/html5/)
PageHelper|分页|[https://github.com/pagehelper/Mybatis-PageHelper](https://github.com/pagehelper/Mybatis-PageHelper)
Druid |连接池|[https://github.com/alibaba/druid](https://github.com/alibaba/druid)
AliOSS|云存储|[https://help.aliyun.com/document_detail/32008.html?spm=a2c4g.11186623.6.670.9vnD4m](https://help.aliyun.com/document_detail/32008.html?spm=a2c4g.11186623.6.670.9vnD4m)
Zxing|二维码|[https://github.com/zxing/zxing](https://github.com/zxing/zxing)
POI|excel|[https://poi.apache.org/spreadsheet/quick-guide.html](https://poi.apache.org/spreadsheet/quick-guide.html)

#### 前端 ####
技术 | 名称 | 文档地址
----|----- |-----
Bootstrap |CSS/HTML框架|[https://v3.bootcss.com/](https://v3.bootcss.com/)
Jquery|JavaScript 库|[http://api.jquery.com/](http://api.jquery.com/)
Bootstrap table|数据表格|[http://bootstrap-table.wenzhixin.net.cn/documentation/#table-options](http://bootstrap-table.wenzhixin.net.cn/documentation/#table-options)
Bootstrap Table Editable|可编辑表格|http://vitalets.github.io/x-editable/docs.html#editable
Bootstrap Select|下拉组件|http://silviomoreto.github.io/bootstrap-select/
AdminLte |后台模板|[https://adminlte.io/themes/AdminLTE/index2.html](https://adminlte.io/themes/AdminLTE/index2.html)
Layer|弹出层|[http://www.layui.com/doc/modules/layer.html#type](http://www.layui.com/doc/modules/layer.html#type)
Bootstrap DateTimePicker |时间选择器|[http://www.bootcss.com/p/bootstrap-datetimepicker/](http://www.bootcss.com/p/bootstrap-datetimepicker/)
Wayjs |双向数据绑定|[https://github.com/gwendall/way.js/blob/master/docs/zh-CN.md](https://github.com/gwendall/way.js/blob/master/docs/zh-CN.md)
Ztree|树组件|[http://www.treejs.cn/v3/api.php](http://www.treejs.cn/v3/api.php)
Bootstrapvalidator|表单验证|[https://github.com/nghuuphuoc/bootstrapvalidator](https://github.com/nghuuphuoc/bootstrapvalidator)
JSON serialize|JSON serialize|[https://github.com/marioizquierdo/jquery.serializeJSON](https://github.com/marioizquierdo/jquery.serializeJSON)
Jquery-treegrid|树形表格|[jquery-treegrid](jquery-treegrid)
JQuery-File-Upload|异步上传插件|[https://github.com/blueimp/jQuery-File-Upload/wiki/Basic-plugin](https://github.com/blueimp/jQuery-File-Upload/wiki/Basic-plugin)
Node js|工具|[https://nodejs.org/zh-cn/](https://nodejs.org/zh-cn/)
Gulp |构建工具|[https://www.gulpjs.com.cn/](https://www.gulpjs.com.cn/)

## 本地运行 ##
1. 本工程为maven项目，导入eclipse
2. 建立数据库seezoon-framework，运行/src/main/resources/db/seezoon-framework.sql
3. 配置/src/main/resources/env/application.properties
4. 运行到tomcat 或者mvn jetty:run(代码改动时命令行任意键重新加载)
5. 运行前端需要先配置/src/main/webapp/static/gulpfile.js
中dev=后端接口地址上下文，gulp 是node插件，所以先安装node环境，
安装完成后命令行执行npm install 安装相关插件，然后再命令行到gulpfile.js 所在目录运行gulp 如图：
![gulp运行图](https://github.com/734839030/seezoon-framework-all/blob/master/screenshots/gulp-run.png?raw=true)
static/src 为前端源文件，static/dist 为编译后的静态资源，这边简单用了gulp的include 功能，后续会使用压缩合并等功能。

打开浏览器输入http://127.0.0.1:8888/admin/pages/index.html 自动拦截回到登录页。
![登录页](https://github.com/734839030/seezoon-framework-all/blob/master/screenshots/login-page.png?raw=true)


## 后续逐步开源如下功能（star 是开源的动力） ##

* spring boot maven多模块版本；
* spring cloud 版本脚手架；

## 部分项目截图 ##
#### 代码生成 ####
![代码生成](https://github.com/734839030/seezoon-framework-all/blob/master/screenshots/codegen.png?raw=true)
#### 首页 ####
![首页](https://github.com/734839030/seezoon-framework-all/blob/master/screenshots/index-page.png?raw=true)
#### 用户管理 ####
![用户管理](https://github.com/734839030/seezoon-framework-all/blob/master/screenshots/user-manager.png?raw=true)
#### 字典管理 ####
![地点管理](https://github.com/734839030/seezoon-framework-all/blob/master/screenshots/dict.png?raw=true)
#### 文件管理 ####
![文件管理](https://github.com/734839030/seezoon-framework-all/blob/master/screenshots/file.png?raw=true)
#### 角色管理 ####
![角色管理](https://github.com/734839030/seezoon-framework-all/blob/master/screenshots/role-manage.png?raw=true)
#### 菜单管理 ####
![菜单管理](https://github.com/734839030/seezoon-framework-all/blob/master/screenshots/menu1.png?raw=true)
![菜单管理](https://github.com/734839030/seezoon-framework-all/blob/master/screenshots/menu2.png?raw=true)
![图标选择](https://github.com/734839030/seezoon-framework-all/blob/master/screenshots/icon.png?raw=true)
#### 多主题 ####
![主题](https://github.com/734839030/seezoon-framework-all/blob/master/screenshots/muti-themes.png?raw=true)

> 更多功能参见演示网站。

下面为分布式版本线上案列，小型项目还是一体比较方便。
![产品编辑](https://github.com/734839030/seezoon-framework-all/blob/master/screenshots/eg1.png?raw=true)
![运费](https://github.com/734839030/seezoon-framework-all/blob/master/screenshots/eg2.png?raw=true)
![规格](https://github.com/734839030/seezoon-framework-all/blob/master/screenshots/eg3.png?raw=true)
![规格配置](https://github.com/734839030/seezoon-framework-all/blob/master/screenshots/eg4.png?raw=true)


