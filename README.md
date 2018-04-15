# houseManager

房屋维修基金管理

## 编码规范

1.类、方法、成员变量使用驼峰命名法 MyClass、myMethod、myField
2.数据库、表、字段使用匈牙利命名法 my_table
3.方法包含复杂业务逻辑(查询/影响超过两个表数据)，必须写注释
4.可用于多种场景的公共类，请写明类和方法注释说明用途
5.restful风格API的URL统一以rest作为访问前缀，如rest/region/insert
6.页面跳转请求统一以page作为访问前缀

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