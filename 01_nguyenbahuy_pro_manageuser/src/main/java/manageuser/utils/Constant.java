/**
 * copyright(C) 2022 Luvina JSC
 * Constant.java May 3, 2022 nguyenbahuy
 */
package manageuser.utils;

/**
 * @author nguyenbahuy
 * class chưa các đường dẫn 
 */
public class Constant {
	 
	//khai báo đường dẫn file properties
	public static String PROPERTIES_DATABASE_PATH = "./properties/database.properties";
	//khai báo đường dẫn file properties
	public static String PROPERTIES_ERROR_PATH = "./properties/ErrorMsg.properties";
	//khai báo đường dẫn file properties
	public static String PROPERTIES_CONFIG = "./properties/config.properties";
	//khai báo đường dẫn file properties
	public static String PROPERTIES_MSG = "./properties/Msg.properties";
	public static String DEFAULT_EMPTY = "";
	public static int DEFAULT_INTEGER = 0;
	//khai báo các biến
	public static String lOGINID = "loginId";
	//khai báo các biến fullNamePath
	public static String FULLNAME = "full_name";
	//khai báo các biến groupIdPath
	public static String GROUPID = "groupId";
	//khai báo các biến sortByFullName
	public static String ASC = "ASC"; 
	//khai báo các biến
	public static String DESC = "DESC";	
	//khai báo các biến
	public static String SORTBYCODELEVEL = "sortByCodeLevel";
	public static String SORTBYFULLNAME = "sortByFullName";
	public static String SORTBYENDDATE = "sortByEndDate";
	//khai báo các biến
	public static String CODELEVEL = "code_level";
	//khai báo các biến
	public static String ENDDATE = "end_date";
	//khai báo các biến
	public static String SORTTYPE = "sortType";
	//khai báo các biến
	public static String  CURRENTPAGE = "currentPage";
	//khai báo các biến
	public static String LISTPAGING = "listPaging";
	//khai báo các biến
	public static String BACK = "back_ADM002";
	//khai báo các biến
	public static String PAGING = "paging";
	//khai báo các biến
	public static String SORT = "sort";
	//khai báo các biến
	public static String SEARCH = "search";
	public static String ADD = "add";
	public static String EDIT = "edit";
	public static int RULE_USER = 1;
	public static int RULE_ADMIN = 0;
	public static int RULE_FAIL = -1;
	//khai báo các biến
	public static String ADM001 = "jsp/ADM001.jsp";
	public static String URL_ADM002 = "listUser.do";
	public static String ADD_USER_CONFIRM = "AddUserConfirm.do";
	public static String EDIT_USER_CONFIRM = "EditUserConfirm.do";
	public static String INSERT_SUCCESS = "insert_success";
	public static String UPDATE_SUCCESS = "update_success";
	public static String SUCCESS = "success.do";
	public static String SYSTEM_ERROR = "error";
	public static String ACTION_BACK = "back_ADM003";
	public static String ADM002 = "jsp/ADM002.jsp";
	public static String ADM003 = "jsp/ADM003.jsp";

	public static String ADM004 = "jsp/ADM004.jsp";

	public static String ADM005 = "jsp/ADM005.jsp";
	public static String ADM006 = "jsp/ADM006.jsp";
	public static String ERROR_MSG = "errorMsg";
	public static String PAGE_ERROR = "jsp/System_Error.jsp";
	public static int MAXLENGTHTOTAL = 3;
	public static int MAXLENGTHEMAIL = 100;
	public static int MAXLENGTHTEL = 14;
	public static int MAXLENGTHKANA = 255;
	public static int MAXLENGTHFULLNAME = 255;
	public static final String FORMATLOGINNAME ="^[a-zA-Z][a-zA-Z0-9_]+$";
	public static final String FORMATEMAIL = "^[\\w]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
	public static final String FORMATTEL = "[0-9]{1,4}-[0-9]{1,4}-[0-9]{1,4}";
	public static final int YEARSTART = 1900;
	public static final String DELETE_SUCCESS = "delete_success";
	public static final String CHECKADM003 = "checkADM003";
	
	
}
