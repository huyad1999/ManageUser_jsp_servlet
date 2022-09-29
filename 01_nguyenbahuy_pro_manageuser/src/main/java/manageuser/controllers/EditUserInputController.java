/**
 * 
 */
package manageuser.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import manageuser.entities.UserInfor;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.utils.MessageErrorProperties;
import manageuser.validates.ValidateUser;

/**
 * @author nguyenbahuy
 * xử lý hiển thị ADM003 Edit và xử lý khi click vào Button [確認]
 */
@WebServlet({"/editUserInput.do", "/editUserValidate.do"})
public class EditUserInputController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	/**
	 * hiển thị ban đầu khi từ ADM005 sang ADM003 để update
	 * back từ màn hình ADM004 về ADM003 trường hợp update
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
				
				int userId = Integer.parseInt(req.getParameter("userId"));
				 
				if(userId > 0) {
					TblUserLogic tblUserLogic = new TblUserLogicImpl();
					//check UserId có tồn tại không
					boolean checkId = tblUserLogic.checkExistUserId(userId);
					if(checkId) {
						String key = req.getParameter("key");
						System.out.println("key="+key);
						UserInfor userInfor = getDefaultValue(req,resp);
						req.setAttribute("userInfor", userInfor);
						setDataLogic(req);
						//hiển thị màn hình ADM003
						RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.ADM003);
						requestDispatcher.forward(req, resp);
					} else {
						 resp.sendRedirect(Constant.SUCCESS+"?type="+Constant.SYSTEM_ERROR);
					}
				}

		} catch(Exception e) {
			System.out.println("EditUserController doGet"+e.getMessage());
			req.setAttribute("errorMsg",MessageErrorProperties.getValueByKey("ER015"));
			//chuyển sang trang System_Error.jsp
			RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.PAGE_ERROR);
			requestDispatcher.forward(req, resp);
		}
	}
	/**
	 * hiển thị ban đầu khi từ ADM004 sang ADM004 để update
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		try {
		//tạo userInfor từ getDefaultValue
		UserInfor userInfor = getDefaultValue(req, resp);
		//lấy danh sách lỗi
		List<String> listError = ValidateUser.validateADM003(userInfor);
		//kiểm tra danh sách
		if(listError.isEmpty()) {
			 HttpSession session = req.getSession();
			 String checkADM003 = req.getParameter("checkADM003");
			session.setAttribute(Constant.CHECKADM003, checkADM003);
			 //tạo key session
			 String key = Common.creatSalt();
			 //set key lên session
			 session.setAttribute(key, userInfor);
			//sang servlet màn hình ADM004
			 resp.sendRedirect(Constant.EDIT_USER_CONFIRM+"?key="+key);
			
		} else {
			setDataLogic(req);
			req.setAttribute("listError", listError);
			req.setAttribute("userInfor", userInfor);
			//nếu có lỗi thì ở lại màn hình adm003 và hiển thị là thông báo lỗi
			RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.ADM003);
			requestDispatcher.forward(req, resp);
		}
	 
	} catch(Exception e) {
		//ghi log
		System.out.println("EditUserInputController doPost "+e.getMessage());
		req.setAttribute("errorMsg",MessageErrorProperties.getValueByKey("ER015"));
		//chuyển sang trang System_Error.jsp
		RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.PAGE_ERROR);
		requestDispatcher.forward(req, resp);
		}
	}
	/**
	 * lấy giá trị cho màn hình ADM003
	 * @param req
	 * @param resp
	 * @return userInfor
	 * @throws Exception
	 */
	private UserInfor getDefaultValue(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		try {
		TblUserLogic tblUserLogic = new TblUserLogicImpl();
		UserInfor userInforEdit = new UserInfor();
		
		//lấy kiểu hiển thị
		String type = req.getParameter("type");
		
		
		int currentYear = Common.getYearNow();
		int currentMonth = Common.getCurrentMonth();
		int currentDay = Common.getCurrentDay();
		// Khởi tạo giá trị mặc định cho năm
		
		if(Constant.EDIT.equals(type)) {
			int userid = Integer.parseInt(req.getParameter("userId"));
			userInforEdit = tblUserLogic.getUserInforByUserId(userid);
		
			String [] arrBirthday = Common.convertToDayMonthYear(userInforEdit.getBirthday());
			
			userInforEdit.setYearBirthday(Integer.parseInt(arrBirthday[0]));
			userInforEdit.setMonthBirthday(Integer.parseInt(arrBirthday[1]));
			userInforEdit.setDayBirthday(Integer.parseInt(arrBirthday[2]));
			
			if(userInforEdit.getCodeLevel() == null) {
				userInforEdit.setYearEnd(currentYear+1);
				userInforEdit.setYearStart(currentYear);
				userInforEdit.setMonthStart(currentMonth);
				userInforEdit.setMonthEnd(currentMonth);
				userInforEdit.setDayStart(currentDay);
				userInforEdit.setDayEnd(currentDay);

			} else {
				String [] arrEndDate = Common.convertToDayMonthYear(userInforEdit.getEndDate());
				String [] arrStartDate = Common.convertToDayMonthYear(userInforEdit.getStartDate());
				

				userInforEdit.setYearEnd(Integer.parseInt(arrEndDate[0]));
				userInforEdit.setYearStart(Integer.parseInt(arrStartDate[0]));
				
				userInforEdit.setMonthEnd(Integer.parseInt(arrEndDate[1]));
				userInforEdit.setMonthStart(Integer.parseInt(arrStartDate[1]));
				
				userInforEdit.setDayEnd(Integer.parseInt(arrEndDate[2]));
				userInforEdit.setDayStart(Integer.parseInt(arrStartDate[2]));
			}
			userInforEdit.setStartDate(Common.convertString(userInforEdit.getYearStart(),userInforEdit.getMonthStart(),userInforEdit.getDayStart()));
			userInforEdit.setEndDate(Common.convertString(userInforEdit.getYearEnd(),userInforEdit.getMonthEnd(),userInforEdit.getDayEnd()));
			userInforEdit.setBirthday(Common.convertString(userInforEdit.getYearBirthday(),userInforEdit.getMonthBirthday(),userInforEdit.getDayBirthday()));
			
		} else if("validate".equals(type)) {//trường hợp validate

			userInforEdit.setLoginName(req.getParameter("loginName"));;
			userInforEdit.setUserId(Integer.parseInt(req.getParameter("userId")));
			userInforEdit.setGroupId(Integer.parseInt(req.getParameter("groupId")));
			
			//set ngày,thang,nam birthday
			userInforEdit.setPassword(req.getParameter("password"));
	
			userInforEdit.setDayBirthday(Integer.parseInt(req.getParameter("dayBirthday")));
			userInforEdit.setMonthBirthday(Integer.parseInt(req.getParameter("monthBirthday")));
			userInforEdit.setYearBirthday(Integer.parseInt(req.getParameter("yearBirthday")));
			
			userInforEdit.setEmail(req.getParameter("email"));
	
			userInforEdit.setFullName(req.getParameter("fullName")); 
			userInforEdit.setFullNameKana(req.getParameter("fullNameKana")); 
			userInforEdit.setTel(req.getParameter("tel"));
			userInforEdit.setCodeLevel(req.getParameter("codeLevel"));
			
			userInforEdit.setBirthday(Common.convertString(userInforEdit.getYearBirthday(), userInforEdit.getMonthBirthday(),userInforEdit.getDayBirthday())); 
			//chọn trình độ tiếng nhật
			if(!"0".equals(req.getParameter("codeLevel"))) {
				if("".equals(req.getParameter("total"))) {
					userInforEdit.setTotal("");
				} else {
					
					 userInforEdit.setTotal(req.getParameter("total"));
				}
				//set ngày,thang,nam enddate và startdate
				userInforEdit.setYearStart(Integer.parseInt(req.getParameter("yearStart")));
				userInforEdit.setMonthStart(Integer.parseInt(req.getParameter("monthStart")));
				userInforEdit.setDayStart(Integer.parseInt(req.getParameter("dayStart")));
				
				userInforEdit.setYearEnd(Integer.parseInt(req.getParameter("yearEnd")));
				userInforEdit.setMonthEnd(Integer.parseInt(req.getParameter("monthEnd")));
				userInforEdit.setDayEnd(Integer.parseInt(req.getParameter("dayEnd")));

				
				userInforEdit.setStartDate(Common.convertString(userInforEdit.getYearStart(),userInforEdit.getMonthStart(),userInforEdit.getDayStart())); 
				userInforEdit.setEndDate(Common.convertString(userInforEdit.getYearEnd(),userInforEdit.getMonthEnd(),userInforEdit.getDayEnd())); 

			} else {//không chọn trình độ tiếng nhật
				userInforEdit.setTotal("");
				userInforEdit.setYearStart(currentYear);
				userInforEdit.setYearEnd(currentYear+1);
				// Khởi tạo giá trị mặc định cho tháng
				userInforEdit.setMonthStart(currentMonth);
				userInforEdit.setMonthEnd(currentMonth);
				// Khởi tạo giá trị mặc định cho ngày
				userInforEdit.setDayEnd(currentDay);
				userInforEdit.setDayStart(currentDay);
				
				userInforEdit.setStartDate(Constant.DEFAULT_EMPTY); 
				userInforEdit.setEndDate(Constant.DEFAULT_EMPTY); 
			}

		} else if(Constant.ACTION_BACK.equals(type)) {//trường hợp back 
			//lấy keySession 
			
			String keySession = req.getParameter("key");
			 HttpSession session = req.getSession();
			 userInforEdit = (UserInfor) session.getAttribute(keySession);
			 System.out.println(userInforEdit.toString());
		}
		return userInforEdit;
		
		} catch (Exception e) {
			System.out.print(" EditUserInputController getDefaultValue();" + e.getMessage());
			throw e;
		}
	}
	/**
	 * set giá trị cho các selectBox
	 * @param request
	 * @throws SQLException
	 */
	private void setDataLogic(HttpServletRequest request) throws  SQLException {
		try {
			MstGroupLogic listMstGroup = new MstGroupLogicImpl();
			MstJapanLogic listMstJapan = new MstJapanLogicImpl();
			request.setAttribute("listMstGroup", listMstGroup.getAllMstGroup());
			request.setAttribute("listMstJapan", listMstJapan.getAllMstJapan());
			request.setAttribute("listDay", Common.getListDay());
			request.setAttribute("listMonth", Common.getListMonth());
			request.setAttribute("listYearEndDate", Common.getListYear(Constant.YEARSTART, Common.getYearNow() + 1));
			request.setAttribute("listYear", Common.getListYear(Constant.YEARSTART, Common.getYearNow()));
		} catch (SQLException e) {	
			// Hiển thị log catch
			System.out.print(" EditUserInputController setDataLogic();" + e.getMessage());
			throw e;
		}
	}
	
	
	
}
