# houseManager

房屋维修基金管理

## 编码规范

1.类、方法、成员变量使用驼峰命名法 MyClass、myMethod、myField

2.数据库、表、字段使用匈牙利命名法 my_table

3.数据库表都必须有一个int类型主键命名为ID，所有可能会应用到下拉列表(select),单选(radio),多选(checkbox)等选择组件必须有一个name字段作为呈现字段

4.方法包含复杂业务逻辑(查询/影响超过两个表数据)，必须写注释

5.可用于多种场景的公共类，请写明类和方法注释说明用途

6.restful风格API的URL统一以rest作为访问前缀，如rest/region/insert,返回Json类型统一由CommonRsp对象包装

7.页面跳转请求统一以page作为访问前缀

## 工程架构

后端使用springMVC4 + mybatis3开发。

前端使用bootstrap3.x + jquery 1.x + datatable等插件 

利用**maven**构建，**git**做版本管理

### 包结构

action：springMVC controller类，负责请求参数处理，和页面跳转，不负责业务流程封装，bean委托spring管理,统一配置扫描，无需单独声明。
service：业务逻辑处理类，一个service类的方法描述一种业务场景,事务请在此处理，bean委托spring管理，统一配置扫描，无需单独声明。
dao：数据访问处理类，一个Dao类的方法描述一个数据库表的原子操作(一句sql)，已配置spring实现代理，此包仅声明接口即可，一个dao对应一个mapper.xml
model：数据模型类，ORM实体对象
inspector：Spring拦截器类
util：公用工具类

### 配置文件

#### spring

classpath:spring/spring-*.xml已配置部分通用配置，如需要加入全局配置，请协商添加，避免相互影响。

#### mybatis

一个dao接口需要匹配一个mapper.xml(名字不限定，spring统一到classpath:mybatis/mapper下扫描)

xml中**Mapper**节点的**namespace**属性对应dao接口名，每个**sqlStatement**的**id**对应dao中的方法名，参数与返回值均需要匹配

#### 开发流程

1.建立相应的数据库表及字段

2.数据库表对应实体类,存放在model包

3.dao接口,编写数据库原子操作，增删改查，关联查询等（不需要些实现类）

4.编写mybatis mapper.xml 

5.service层的接口及实现，根据dao对象来操作数据库，管理事务，类上加@Service注解，类上的@Transation为全局事务，如需要更细粒度事务在相应方法上再加@Transation注解，@AutoWire注解来引入相应的dao对象

6.action层实现类,页面跳转由IndexController统一路由，约定/page/jsp名称.action会forward到webRoot/web-inf/view/下的jsp文件，restApi应答统一用CommonRsp类型包装，约定使用通用页面模版的controller需要具备 **/rest/实体名称(小写)/one**、**/rest/实体名称(小写)/delete**、**/rest/实体名称(小写)/update**、、**/rest/实体名称(小写)/insert**、**/rest/实体名称(小写)/table**。记住这个实体名称就是要配置给渲染页面的domain的name,

7.页面：基础页面（带查询表单、数据表格、添加/修改窗口）参考 区域和街道模块，其中表单id和表格id，查询、添加按钮的id都是约定的，不用修改，也可以根据配置文件指定id。


#### 数据导入注意事项

1、房屋信息导入时，需要计息完毕，账户变更记录表记录，并填入最后计息时间，最后计息余额，**即一个房屋余额不为0，则必然要有余额变更记录和最后计息时间和计息余额**