# ˵��

����һ���Ҵ��ȫջʽ��ܵ�**����**Դ�룬Ϊ���ҹ�����ʱ�����Ƹ��Ա���£�����ʱ���**ɾȥ**
��Ϊ��ʾ��������һ���򵥵�ͼ��ݹ���ϵͳ��������**��Ҫ�޸�ϵͳģ������**
��ʾ��ַ��μ��ҵļ��������˵���뵥��ϵͳ�Ϸ�**���ʾ��**

# ��ܽ���
1. ������Tapestry��Spring MVC���ŵ㣬��װ�˴������
2. ��д������У�飬ʹǰ�����֤ͳһ
3. ��д������ת�������ݰ�
4. ʵ���˰�ȫ���ƣ������¼��ȫ������
5. ʵ����ϵͳģ�飺�û�����ɫ���˵������á��ֵ�...

# ˵��

## ǰ��
`Tapestry`��һ��ǰ��ǿ�����ʽ��ܣ�����˼����������¼�
`Spring MVC`����ǰ����ȵ�ǰ��������Ӧʽ���

<br/>

��ǰ�˵����˼����ǽ�϶����ŵ㣬����`Freemarker`��װ�˺ܶ������
������д��������֤�ͷ�װ��Ŀ���ǣ�**�þ����ٵĴ���ɸ������**

<br/>

�������ʹ��Դ���Ч�����£�

![ɨ������](https://raw.githubusercontent.com/huyu516/Library/master/screenshots/component.png)


## ������֤�ͷ�װ
��֤��Ϊǰ��`JS`��֤�ͺ��`Java`��֤
һ��ǰ����֤��`jquery validate`�������֤��`Hiberntae`��ע����ʵ��

<br/>

��Ȼ������֤������ͬ��������ȻҪд���飬�ҵ�Ŀ���ǣ�**ʹǰ�˺ͺ����֤����ͳһ��ֻ����ʵ��������ע�⣬�����Զ�ȥִ��ǰ�˺ͺ��У��**

<br/>

��������:
```
@Entity
@Table(name = "a_student")
public class Student extends BaseEntity {

	@Id
	@Require
	@StrLen(maxLen = 40)
	@FieldTipName("ѧ�����")
	private String stu_id;

	@Require
	@StrLen(maxLen = 40)
	@FieldTipName("ѧ������")
	private String stu_name;
	
	...
	
```
```
@RequestMapping(value = ADD, method = RequestMethod.POST)
public String onSubmit(EhWebRequest req, Student student) { // �Զ���װ��ʵ��
	if (req.hasBindErrors()) { // ִ�е��˴���̨У���Ѿ���ϣ��鿴�Ƿ��д���
		return FM_FTL;
	}

	stuService.insert(student);

	return GOTO_LIST;
}
```

Ч��ͼ��


![ɨ������](https://raw.githubusercontent.com/huyu516/Library/master/screenshots/student.png)


## �־ò�

### ��ɾ�Ĳ�ļ� 
��`Dao`��`Service`�ϲ���һ�𣬲��Ҳ�д�ӿ�

<br/>

ÿ��`Service`������ɾ�Ĳ飬����`findAll()`֮��ķ�������ôֻҪ��һ��`BaseService`��ʵ����Щ������Ȼ��������Service�̳в��ͺ��ˡ�

<br/>
 
 ```
 public class BookService extends BaseService<Book>
 ```

### ��ҳ�ļ�

����ҳ����Ҫ��ȫ�����ݷ�װ�ɶ���
```
// ��ҳ���� 
public class Page {

	private List recordList;    // ҳ������          : ��Ҫ��ѯ���ݿ�

	private long recordCount;   // һ������������	  : ��Ҫ��ѯ���ݿ�
	
	private int pageSize;       // һ������������    ��ָ��
	
	private int pageNo;         // ҳ��				 : ָ�� 

	private int pageCount;      // һ��ҳ����		 ��= ceil(recordCount/pageSize)

	...
}
```
 
#### 1. sql/hql
����ԭʼ��`sql`��`select uid, uname from user where uname like 'xxx' order by uname`
</br>
��ҳ��ѯ`list`�Ŀ���ֱ����`jpa`��`startFirstResult()`��`setMaxResults()`�õ�
<br/>
�鿴`count`��`select`��`from`֮��������滻Ϊ`count(*)`ȥ��`order by`��ɣ���Ϊ`select count(*) from user where uname like 'xxx'`
Ч�����£�
```
// ���ҳ����ֻ��д��list��sql������дselect count()��sql��
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
#### 2.Criteria�ӿ�
�Ȳ�ѯ`count`�ٲ�ѯ`list`�ѷ�װ��
```
public Page findPage(int page_no, int page_size, String stu_id, String stu_name) {
	JPAQuery query = createJPAQuery();
	query.whereNullableContains("stu_id", stu_id);
	query.whereNullableContains("stu_name", stu_name);
	query.orderByAsc("stu_id");
	return query.getPage(page_no, page_size);
}
```

## ϵͳģ��
���û��ֽ�ɫ - ��ɫ�ֲ˵� - ʵ�ֶ�̬�˵�

## ��ȫ
����shiroʵ���˻���Ȩ�޵Ķ���Դ�����غ͵����¼���о�shiroҪ��spring securityǿ��
���Ұ�ȫ��������̬������

# ����ѡ��
Boostrap��jQuery��Freemarker��Spring MVC��JPA��Shiro��Ehcache

# �����˳ɹ��Ľ����ʹ��
��ȫģ����嶼�����ſ��������ġ�����ѧshiro������

ҳ�沼�ַ�����JEESITE

# ��ͼ

![ɨ������](https://raw.githubusercontent.com/huyu516/Library/master/screenshots/home.png)

 
 
 

 


