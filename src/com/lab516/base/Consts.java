package com.lab516.base;

/**
 * 常量类
 */
public abstract class Consts {

	/** 全角-逗号 */
	public static final String SBC_SPLIT = "___";

	/** 半角-逗号 */
	public static final String DBC_SPLIT = ",";

	/** 是 */
	public static final String TRUE = "1";

	/** 否 */
	public static final String FALSE = "0";

	/** 无 */
	public static final String NONE = "-1";

	/** 左括号 */
	public static final String L_BRACE = "(";

	/** 右括号 */
	public static final String R_BRACE = ")";

	/** 日期格式:年-月-日 */
	public static final String YMD = "yyyy-MM-dd";

	/** 日期格式:年-月-日 时:分:秒 */
	public static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";

	/** 日期格式:时:分:秒 */
	public static final String HMS = "HH:mm:ss";

	/** 用户初始密码 */
	public static final String INIT_PWD = "123456";

	/** 图片格式 */
	public static final String IMG_FILE_TYPES = "bmp|jpg|jpeg|gif|png";

	/** 最大文件大小 */
	public static final long MAX_FILE_SIZE = 1024 * 1024 * 5;

	/** 登录地址 */
	public static final String LOGIN_URL = "login_url";

	/** 被挤下线错误信息 */
	public static final String KICKOUT_MSG = "kickOutMsg";

	public static final String KAPTCHA_SESSION_KEY = "kaptchaSessionKey";

	public static final int MAX_UPLOAD_SIZE = 11428000;

	public static final String MAX_UPLOAD_SIZE_EXCEEDED_ERROR = "一次上传的所有附件大小总共请不要超过10MB";

	/** 字典-图书类型 */
	public static final String DICT_BOOK_TYPE = "book_type";

	/** 角色-学生 **/
	public static final String ROLE_STUDENT = "student";

	/** 角色-招生负责人 **/
	public static final String ROLE_COLLEGE = "college";

	/** 参加方式-网络开放日 */
	public static final String COL_PART_WAY_NET = "net";

	/** 配置名称-手机APK下载地址 */
	public static final String CFG_NAME_APK_URL = "apk_url";

	/** 配置名称-手机APK最新版本 */
	public static final String CFG_NAME_APK_VER = "apk_ver";

	/** 配置名称-手机登陆后提示信息 */
	public static final String CFG_TIP_MESSAGE = "tip_msg";

	/** 配置名称-首页版权HTML */
	public static final String CFG_COPYRIGHT = "copyright";

}
