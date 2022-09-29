/**
 * Copyright(C) 2022 Luvina Software Company
 *
 * Common.java, Apr 20, 2022, nguyenbahuy
 */
package manageuser.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import manageuser.dao.BaseDao;
import manageuser.dao.TblDetailUserJapanDao;
import manageuser.dao.TblUserDao;
import manageuser.dao.impl.BaseDaoImpl;
import manageuser.dao.impl.TblDetailUserJapanDaoImpl;
import manageuser.dao.impl.TblUserDaoImpl;
import manageuser.entities.TblDetailUserJapan;
import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;

/**
 * Contain common method
 *
 * @author nguyenbahuy
 * Chứa các hàm common của dự án
 */
public class Common {


	/**
	 * hàm mã hoá mật khẩu và salt
	 * @Param nhập mật khẩu và salt\
	 * trả về chuỗi kí tự mã hoá
	 */
	public static String encriptPass(String pass, String salt) throws NoSuchAlgorithmException {
		try {
			StringBuilder sb = new StringBuilder(salt);
			sb.append(pass);
			// getInstance() method is called with algorithm SHA-1
			MessageDigest md = MessageDigest.getInstance("SHA-1");

			// digest() method is called
			// to calculate message digest of the input string
			// returned as array of byte 
			return DatatypeConverter.printHexBinary(md.digest(sb.toString().getBytes())).toLowerCase();
		} catch(NoSuchAlgorithmException e) {
			System.out.println("Common encriptPass "+e.getMessage());
			throw e;
		}
	}
 
	/**
	 * tạo ra salt
	 * @return chuỗi salt
	 */
	public static String creatSalt() {
		String salt = LocalDateTime.now(ZoneId.of("GMT")).toString();
		salt = salt.replace(":", "").replace("-", "").replace(".", "");
		return salt;
	}

	/**
	 * hàm checklogin và kiểm tra xem rule có là admin 
	 * @throws SQLException 
	 * @Param truyền vào 1 đối tượng sesion
	 * trả về đúng nếu có loginId trên session sai nếu không có
	 */
	public static boolean checkLogin(HttpSession session) throws SQLException {
		try {
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			String loginId = (String) session.getAttribute(Constant.lOGINID);
			int rule = tblUserLogic.getRuleByLoginName(loginId);
			if (session != null && rule == Constant.RULE_ADMIN && loginId != null) {
				return  true;
			}
		} catch (SQLException e) {
			System.out.println(" Common, checkLogin" + e.getMessage());
			throw e;
		}
		return false;
	}
	/**
	 * hàm tạo ra danh sách các paging theo tham số
	 * @Param totalUser tổng số User
	 * @Param limit số lượng bản ghi trong 1 page
	 * @Param currentPage trang hiện tại
	 * trả về danh sách các trang hiển thị
	 */
	public static List<Integer> getListPaging(int totalUser,int limit,int currentPage){
		//tạo listPage
		List<Integer> listPage = new ArrayList<Integer>();
		//tạo limitPage (số page hiển thị trong màn hình) tr lấy từ Config và ép về kiểu Integer
		int limitPage = Integer.parseInt(Config.getValueByKey("limitPage"));;	
		//khởi tạo totalPage từ hàm getTotalPage theo tham số totalUser và limit
		int totalPage = Common.getTotalPage(totalUser, limit);
		//khởi tạo currentGroup từ hàm getCurrentGroup theo tham số currentPage và limitPage
		int currentGroup = Common.getCurrentGroup(currentPage, limitPage);
		//khởi tạo startPage từ hàm getStartPage theo tham số currentPage và limitPage
		int startPage = Common.getStartPage(currentGroup, limitPage);
		//khởi tạo endPage từ hàm getEndPage theo tham số currentGroup , limitPage và totalPage
		int endPage = Common.getEndPage(currentGroup, limitPage,totalPage);
		//duyệt các phần tử từ startPage -> endPage
		if(endPage > totalPage) {
			endPage = totalPage;
		}
		for (int i = startPage; i <= endPage; i++) {
			//thêm vào mảng listPage
			listPage.add(i);
		}
		//trả về mảng
		return listPage;
	}
	/**
	 * hàm tính toán trả về giá trị của offset
	 * @Param currentPage trang hiện tại
	 * @Param limit số lượng bản ghi trong 1 page
	 */
	public static int getOffset(int currentPage , int limit) {
		int offSet = 0;
		
		offSet = limit * (currentPage - 1);
		
		return offSet;
	}
	/**
	 * tính tổng số trang
	 * @Param totalUser tổng số user
	 * @Param số lượng bản ghi trong 1 page
	 */
	public static int getTotalPage(int totalUser,int limit){
		
		int totalrPage = 0;
		//nếu totalUser chia hết cho limit thì không tăng lên 1 page,còn không thì tăng lên 1 page
		totalrPage =  (int) Math.ceil((double)totalUser / limit);
		return totalrPage;
	}
	/**
	 * hàm tính nhóm hiện tại của chuỗi paging
	 * @Param currentPage trang hiện tại
	 * @Param limitPage số lượng page trên màn hình
	 * @ trả về nhóm hiện tại
	 */
	public static int getCurrentGroup(int currentPage,int limitPage){

		//nếu totalUser chia hết cho limit thì không tăng lên 1 page,còn không thì tăng lên 1 page
		int CurrentGroup =  (int) Math.ceil((double) currentPage / limitPage);
		
		return CurrentGroup;
	}
	/**
	 * phương thức lấy trang đầu
	 * @param currentGroup nhóm hiện tại
	 * @param limitPage số lượng page trên màn hình
	 * @return số trang
	 */
	public static int getStartPage(int currentGroup, int limitPage) {
		int startPage = (currentGroup -1) * limitPage + 1;
		return startPage;

	}
	/**
	 * phương thức lấy trang cuối
	 * @param currentGroup nhóm hiện tại
	 * @param limitPage số lượng page trên màn hình
	 * @param totalPage tổng số trang
	 * @return số trang
	 */
	public static int getEndPage(int currentGroup, int limitPage,int totalPage) {
		int end = limitPage * currentGroup;
		
		return end;
	}
	
	/**
	 * xử lý lỗi wildcard
	 * @param value
	 * @return chuỗi đã replace
	 */
	public static String replaceWilrcard(String value) {
		if (value != null) {
			value = value.replace("%", "\\%").replace("_", "\\_");
		}
		return value;
	}

	/**
	 * Chuyển năm. Tháng ngày thành dạng có format yyyy/mm/dd
	 * @param year  số năm
	 * @param month số tháng
	 * @param day   số ngày
	 * @return chuỗi năm tháng ngày dạng yyyy/mm/dd
	 */
	public static String convertString(int year, int month, int day) {
		// tạo ra đối tượng stringbuilder
		StringBuilder date = new StringBuilder("");
		// set giá trị cho đối tượng theo định dạng yyyy/MM/dd
		date.append(String.valueOf(year)).append("/").append(String.valueOf(month)).append("/")
				.append(String.valueOf(day));
		return date.toString();
	}
	/**
	 * chuyển từ yyyy-mm-dd sang dạng yyyy/mm/dd
	 * @param dateInput
	 * @return chuỗi đã xử lý
	 */
	public static String convertDateFormat(String dateInput) {
		dateInput = dateInput.replace("-", "/");
			return dateInput;
	}

	/**
	 * phương thức lấy năm hiện tại
	 * @return năm hiện tại
	 */
	public static int getYearNow() {
		Calendar now = Calendar.getInstance(); 
		int year = now.get(Calendar.YEAR);
		return year;
	}
	/**
	 * Hàm lấy tháng hiện tại
	 * @return tháng hiện tại
	 */
	public static int getCurrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * Hàm lấy ngày hiện tại
	 * trả về ngày hiện tại
	 */
	public static int getCurrentDay() {
		return Calendar.getInstance().get(Calendar.DATE);
	}

/**
 * hàm xử lý lấy ra danh sách các năm
 * @param startYear số năm bắt đầu
 * @param endYear số năm kết thúc
 * @return danh sách các năm
 */
	public static List<Integer> getListYear(int startYear,int endYear){
		List<Integer> listYear = new ArrayList<Integer>();
		for (int i = startYear; i <= endYear; i++) {
			listYear.add(i);
		}
		return listYear;
	}
	/*
	 * hàm xử lý lấy ra danh sách các tháng
	 * trả về danh sách các tháng
	 * 
	 */
	public static List<Integer> getListMonth(){
		List<Integer> listMonth = new ArrayList<Integer>();
		for (int i = 1; i <= 12; i++) {
			listMonth.add(i);
		}
		return listMonth;
	}
	/*
	 * hàm xử lý lấy ra danh sách các ngày
	 * trả về danh sách các ngày
	 * 
	 */
	public static List<Integer> getListDay(){
		List<Integer> listDay = new ArrayList<Integer>();
		for (int i = 1; i <= 31; i++) {
			listDay.add(i);
		}
		return listDay;
	}
	/**
	 * check không nhập
	 * @param value
	 * @return đúng nếu là null và rỗng
	 */
	public static boolean isEmpty(String value) {
		if("".equals(value) || value == null) {
			return true;
		}
		return false;
	}
	
	/*
	 * check Maxlength
	 * @tham số value là giá trị cần kiếm tra,max là giá trị lớn nhất
	 * @return đúng nếu độ dài nhỏ hơn max
	 */
	public static boolean Maxlength(String value,int max) {
		if(value.length() <= max) {
			return true;
		}
		return false;
	}
	/*
	 * check checkFormat
	 * @tham số value là giá trị cần check,regex là format để check
	 * @return false nếu chuỗi đúng format
	 */
	public static boolean checkFormat(String value,String regex) {
		 
		if(Pattern.matches(regex,value)) {
			return false;
		}
		return true;
	}

	/*
	 * check trong khoảng
	 * @tham số value là giá trị cần kiểm tra,min và max là nhỏ nhất và lớn nhất
	 * @return false nếu độ dài chuỗi lớn hơn min và nhỏ hơn max
	 */
	public static boolean checkRange(String value,int min,int max) {
		if(value.length() >= min && value.length() <= max) {
			return false;
		}
		
		return true;
	}
/**
 * check kana
 * @param c
 * @return đúng nếu là kí tự kana
 */
	 public static boolean isKata(char c) {
	        // katakana:
	        if (c >= '\u30a0' && c <= '\u30ff') {
	        	 return true;
	        }
	        if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.KATAKANA) {
	        	 return true;
	        }
	        // other character..  return false;
	        return false;
	    }

	 /**
	  * check Kana
	  * @param value
	  * @return đúng nếu các kí tự trong String đều là kana
	  */
	    public static boolean checkKata(String value) {

	        if ("".equals(value) || value == null) {
	            return false;
	        }
	        for(int i =0; i < value.length(); i ++) {
	            if (!isKata(value.charAt(i))) {
	                return false;
	            }
	        }
	        return true;
	    }
	    /**
	     * checkDate định dạng date yyyy/mm/dd
	     * @param dateInput
	     * @return đúng nếu ngày tháng năm hợp lệ
	     */
	public static boolean checkDate(String dateInput) {
		String[] date = dateInput.split("/");
		
		int year = Integer.parseInt(date[0]);
		int month = Integer.parseInt(date[1]);
		int day = Integer.parseInt(date[2]);
		
	
		boolean checkYear = checkYear(year);
		
			//tạo biến lưu các ngày trong tháng
			int dayInMonth = 0;
			if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
				dayInMonth = 31;
			} else if(month == 2) {
				//check nếu là năm nhuận 
				if(checkYear) {
					dayInMonth = 29;
				} else {//không phải năm nhuận 
					dayInMonth = 28;
				}
			} else {
				dayInMonth = 30;
			}
			for (int i = 1; i <= dayInMonth; i++) {
				if(i == day) {
					return true;
				}
			}
		
		return false;
	}

/**
 * hàm check năm nhuận
 * @param year
 * @return đúng nếu là năm nhuận
 */
	public static boolean checkYear(int year) { 
	    // Nếu số năm chia hết cho 400,
	    // đó là 1 năm nhuận
	    if (year % 400 == 0) 
	        return true; 
	  
	    // Nếu số năm chia hết cho 4 và không chia hết cho 100,
	    // đó không là 1 năm nhuận
	    if (year % 4 == 0 && year % 100 != 0) 
	        return true;
	 
	    // trường hợp còn lại 
	    // không phải năm nhuận
	    return false; 
	} 
	/**
	 * chuyển từ dạng yyyy/mm/dd sang mảng chứa ngày tháng năm
	 * @param dateInput
	 * @return ngày tháng năm theo format
	 */
	public static String[] convertToDayMonthYear(String dateInput) {
		String[] date = dateInput.split("-");
		return date;
		
	}

	/**
	 * phương thức check xem có phải số halfsize không
	 * @param tel
	 * @return nếu đúng trả về true, sai trả về false 
	 */
	 public static boolean checkIsHalfSizeNum(String number) {
	    	return Pattern.matches("[0-9]+", number);
	    }

	/**
	 * phương thức check xem chuỗi có phải toàn kí tự 1 byte không
	 * 
	 * @param value
	 * @return nếu đúng trả về true. ngược lại;
	 * @throws UnsupportedEncodingException 
	 */
	public static boolean checkIs1Byte(String value) throws UnsupportedEncodingException {
		try {
			if (value.getBytes("UTF8").length == value.length()) {
				return true;
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println(" Common checkIs1Byte()" + e.getMessage());
			throw e;
		}
		return false;
	}
	
/**
 * check ngày hết hạn >= ngày cấp chứng chỉ
 * @param dateStart
 * @param dateEnd
 * @return đúng nếu hợp lệ
 */
	public static boolean checkStartWithEnd(String dateStart,String dateEnd) {
		String[] startDate = dateStart.split("/");
		
		int yearStart = Integer.parseInt(startDate[0]);
		int monthStart = Integer.parseInt(startDate[1]);
		int dayStart = Integer.parseInt(startDate[2]);
		
		String[] endDate = dateEnd.split("/");
		
		int yearEnd = Integer.parseInt(endDate[0]);
		int monthEnd = Integer.parseInt(endDate[1]);
		int dayEnd = Integer.parseInt(endDate[2]);
		
		if(yearEnd > yearStart) {
			return true;
		} else if(yearEnd == yearStart) {
			if(monthEnd > monthStart) {
				return true;
			} else if(monthEnd == monthStart) {
				if(dayEnd >= dayStart) {
					return true;
				} else {
					return false;
				}
			}
		}
		
		return false;
	}


	/**
	 * Phương thức lấy về ngày/ tháng/ năm hiện tại
	 * @return mảng lưu ngày/ tháng/ năm hiện tại
	 */
	public static List<Integer> getCurrentDate() {
		Calendar now = Calendar.getInstance();
		int year = getYearNow();
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DATE);
		List<Integer> listCurrentDate = new ArrayList<Integer>();
		listCurrentDate.add(year);
		listCurrentDate.add(month);
		listCurrentDate.add(day);
		return listCurrentDate;
	}

	/**
	 * hàm chuyển từ userInfor sang tblUser dùng cho add
	 * trả về tblUser
	 */
	public static TblUser getTblUser(UserInfor userInfor) throws NoSuchAlgorithmException {
		TblUser tblUser = new TblUser();
		try {

		tblUser.setUserId(userInfor.getUserId());

		tblUser.setBirthday(userInfor.getBirthday());

		tblUser.setEmail(userInfor.getEmail());

		tblUser.setLoginName(userInfor.getLoginName());
	
		tblUser.setFullNameKana(userInfor.getFullNameKana());

		tblUser.setFullName(userInfor.getFullName());

		tblUser.setGroupId(userInfor.getGroupId());
	
		tblUser.setTel(userInfor.getTel());
		tblUser.setRule(Constant.RULE_USER);
		if(userInfor.getUserId() == 0) {
			//tạo salt
			tblUser.setSalt(Common.creatSalt());
			//mã hoá password
			String password = Common.encriptPass(tblUser.getSalt(), userInfor.getPassword());
			tblUser.setPassword(password);
		}
		
			
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Common getTblUser"+e.getMessage());
			throw e;
		}
		return tblUser;

	}
	
	/**
	 * hàm chuyển từ userInfor sang TblDetailUserJapan
	 * trả về tblDetailUserJapan
	 */
	public static TblDetailUserJapan getTblDetailUserJapan(UserInfor userInfor)  {
		TblDetailUserJapan tblDetailUserJapan = new TblDetailUserJapan();
		
		tblDetailUserJapan.setCodeLevel(userInfor.getCodeLevel());
		tblDetailUserJapan.setTotal(Integer.parseInt(userInfor.getTotal()));
		tblDetailUserJapan.setStartDate(userInfor.getStartDate());
		tblDetailUserJapan.setEndDate(userInfor.getEndDate());
		System.out.println(tblDetailUserJapan.toString());
		return tblDetailUserJapan;

	}


}
