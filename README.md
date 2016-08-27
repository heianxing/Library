# 说明

这是一套我搭建的全栈式框架的**部分**源码，为了找工作的时候使用，过段时间会**删去**

作为演示我制作了一个简单的图书馆管理系统，但是请**不要修改系统模块配置**

演示地址请参见我的简历，组件说明请单击主页上方**框架示例**

# 框架特色
1. 吸收了`Tapestry`和`Spring MVC`的优点，封装了大量组件
2. 重写了数据校验，使前后端数据校验统一
3. 重写了数据类型转换和数据绑定
4. 实现了安全机制：单点登录(默认未开启)和权限拦截
5. 实现了系统模块：用户、角色、菜单、配置、字典...

# 说明

## 前端
`Tapestry`是一个前端强大组件式框架，核心思想是组件和事件

`Spring MVC`是最前最火热的前端请求响应式框架

我前端的设计思想就是结合二者优点，基于`Freemarker`封装了很多组件，**用很少的代码干了更多的事**

部分组件使用源码和效果如下：

![扫码下载](https://raw.githubusercontent.com/huyu516/Library/master/screenshots/component.png)

## 数据验证和封装
验证分为前端`JS`验证和后端`Java`验证，一般前端验证用`jquery validate`，后端验证用`Hiberntae`的注解来实现，虽然表单的验证规则都相同，但是仍然要写两遍


所以，我采用了**将前端和后端数据校验统一，在实体上配置注解，则框架自动去执行前端和后端数据校验**


验证规则在实体属性上配置，代码如下:
```
@Entity
@Table(name = "a_student")
public class Student extends BaseEntity {

	@Id
	@Require
	@StrLen(maxLen = 40)
	@FieldTipName("学生编号")
	private String stu_id;

	@Require
	@StrLen(maxLen = 40)
	@FieldTipName("学生姓名")
	private String stu_name;
	
	...
	
```
后端无形之中已经调用了数据校验：
```
@RequestMapping(value = ADD, method = RequestMethod.POST)
public String onSubmit(EhWebRequest req, Student student) { // 请求自动封装成实体student
	if (req.hasBindErrors()) { // 执行到此处后台校验已经完毕，查看是否有错误
		return FM_FTL;
	}

	stuService.insert(student);

	return GOTO_LIST;
}
```

实体属性上的验证规则会发到前端，执行数据校验：

![扫码下载](https://raw.githubusercontent.com/huyu516/Library/master/screenshots/student.png)


## 持久层

### 增删改查的简化 
把`Dao`和`Service`合并在一起，并且不写接口

每个`Service`都有增删改查，还有`findAll()`之类的方法，那么只要建一个`BaseService`，实现这些方法，然后让其余Service继承不就好了。

代码如下：
 ```
 // 继承一下就有了增删改查等方法
 public class BookService extends BaseService<Book>
 ```

### 分页的简化

将分页所需要的全部数据封装成对象
```
// 分页数据 
public class Page {

	private List recordList;    // 页中数据          : 需要查询数据库

	private long recordCount;   // 一共的数据条数	 : 需要查询数据库
	
	private int pageSize;       // 一共的数据条数     : 指定
	
	private int pageNo;         // 页号		       : 指定 

	private int pageCount;      // 一共页面数        : = ceil(recordCount/pageSize)

	...
}
```
 
#### 1. sql/hql
假如原始的`sql`是`select uid, uname from user where uname like 'xxx' order by uname`

分页查询`list`的可以直接用`jpa`的`startFirstResult()`和`setMaxResults()`得到

查询`count`的`sql`是将`select`和`from`之间的内容替换为`count(*)`去掉`order by`便可，即为`select count(*) from user where uname like 'xxx'`

代码如下：
```
// 查分页数据只用写查list的sql，不用写select count(*)的sql了
public Page findPage(int page_no, int page_size, String book_id, String book_name, String stu_id, String stu_name) {
		PageQuery query = createPageQuery();
		query.append("select t01.book_id, t02.book_name, t01.stu_id, t03.stu_name, t01.is_returned,"
				+ "          t01.borrow_time, t01.return_time   								   "
				+ "   from   a_borrowinfo t01 left join a_book t02    on t01.book_id = t02.book_id "
				+ "                           left join a_student t03 on t01.stu_id  = t03.stu_id  "
				+ "   where  1 = 1  ");
		query.whereNullableContains("and t01.book_id   like :book_id", book_id);
		query.whereNullableContains("and t02.book_name like :book_name", book_name);
		query.whereNullableContains("and t01.stu_id    like :stu_id", stu_id);
		query.whereNullableContains("and t03.stu_name  like :stu_name", stu_name);
		query.append("order by t01.borrow_time desc");
		return query.getPageBySql(page_no, page_size);
	}
```

#### 2.Criteria
使用`Criteria`来构造查询，先查询`count`再查询`list`已封装好，但是不能多表联查，只能查询单表
```
public Page findPage(int page_no, int page_size, String stu_id, String stu_name) {
	JPAQuery query = createJPAQuery();
	query.whereNullableContains("stu_id", stu_id);
	query.whereNullableContains("stu_name", stu_name);
	query.orderByAsc("stu_id");
	return query.getPage(page_no, page_size);
}
```

## 系统模块
给用户分角色，角色分菜单，实现了从用户到菜单的动态配置
增加了配置管理模块，并使用了`Ehcache`缓存

## 安全
基于shiro实现了基于权限的对资源的拦截和单点登录，而且安全拦截器动态可配置

# 技术选型
Boostrap、jQuery、Freemarker、Spring MVC、JPA、Shiro、Ehcache

# 对他人成果的借鉴和使用
安全模块大体都来自张开涛先生的《跟我学shiro》代码

页面布局风格参照JEESITE

# 截图

![扫码下载](https://raw.githubusercontent.com/huyu516/Library/master/screenshots/home.png)

 
 
 

 


