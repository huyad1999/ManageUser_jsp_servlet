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
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;

import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.utils.MessageErrorProperties;
import manageuser.validates.ValidateUser;

/**
 * @author nguyenbahuy xử lý hiển thị ADM003 add và xử lý khi click vào Button
 *         [確認]
 */
@WebServlet({ "/addUserInput.do", "/AddUserValidate.do" })
public class AddUserInputController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * day là hàm xử lý khi click buttton add từ màn hình ADM002
	 * 
	 * @param req
	 * @param resp
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// tạo 1 userInfor gán giá trị mặc định
			UserInfor userInfor = getDefaultValue(req, resp); 
			req.setAttribute("userInfor", userInfor);
			setDataLogic(req);
			// hiển thị màn hình ADM003
			RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.ADM003);
			requestDispatcher.forward(req, resp);
		} catch (Exception e) {
			// ghi log
			System.out.println("AddUserInputController doGet " + e.getMessage());
			req.setAttribute("errorMsg", MessageErrorProperties.getValueByKey("ER015"));
			// chuyển sang trang System_Error.jsp
			RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.PAGE_ERROR);
			requestDispatcher.forward(req, resp);
		}
	}

	/**
	 * day là hàm xử lý khi click buttton xác nhận từ màn hình ADM003
	 * 
	 * @param req
	 * @param resp
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {

			// tạo userInfor từ getDefaultValue
			UserInfor userInfor = getDefaultValue(req, resp);
			// lấy danh sách lỗi
			List<String> listError = ValidateUser.validateADM003(userInfor);

			// kiểm tra danh sách
			if (listError.isEmpty()) {
				HttpSession session = req.getSession();
				// tạo key session
				String checkADM003 = req.getParameter("checkADM003");
				session.setAttribute(Constant.CHECKADM003, checkADM003);
				String key = Common.creatSalt();
				// set key lên session
				session.setAttribute(key, userInfor);
				// sang servlet màn hình ADM004
				resp.sendRedirect(Constant.ADD_USER_CONFIRM + "?key=" + key);

			} else {
				setDataLogic(req);
				req.setAttribute("listError", listError);
				req.setAttribute("userInfor", userInfor);
				// nếu có lỗi thì ở lại màn hình adm003 và hiển thị là thông báo lỗi
				RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.ADM003);
				requestDispatcher.forward(req, resp);
			}

		} catch (Exception e) {
			// ghi log
			System.out.println("AddUserInputController doPost " + e.getMessage());
			req.setAttribute("errorMsg", MessageErrorProperties.getValueByKey("ER015"));
			// chuyển sang trang System_Error.jsp
			RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.PAGE_ERROR);
			requestDispatcher.forward(req, resp);
		}
	}

	/**
	 * hàm gán giá trị mặc định và lấy giá trị cho userInfor từ view
	 * 
	 * @param req
	 * @param resp
	 * @return userInfor
	 * @throws Exception
	 */
	private UserInfor getDefaultValue(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		try {
			UserInfor userInfor = new UserInfor();
			// lấy kiểu hiển thị
			String type = req.getParameter("type");

			int currentYear = Common.getYearNow();
			int currentMonth = Common.getCurrentMonth();
			int currentDay = Common.getCurrentDay();

			// Khởi tạo giá trị mặc định cho năm
			if (Constant.ADD.equals(type)) {

				userInfor.setCodeLevel(Constant.DEFAULT_EMPTY);
				userInfor.setPassword(Constant.DEFAULT_EMPTY);
				userInfor.setPasswordComfirm(Constant.DEFAULT_EMPTY);
				userInfor.setEmail(Constant.DEFAULT_EMPTY);
				userInfor.setFullName(Constant.DEFAULT_EMPTY);
				userInfor.setFullNameKana(Constant.DEFAULT_EMPTY);
				userInfor.setGroupId(Constant.DEFAULT_INTEGER);
				userInfor.setGroupName(Constant.DEFAULT_EMPTY);
				userInfor.setLoginName(Constant.DEFAULT_EMPTY);
				userInfor.setNameLevel(Constant.DEFAULT_EMPTY);
				userInfor.setTel(Constant.DEFAULT_EMPTY);
				userInfor.setTotal(Constant.DEFAULT_EMPTY);
				userInfor.setYearBirthday(currentYear);
				userInfor.setYearStart(currentYear);
				userInfor.setYearEnd(currentYear + 1);
				// Khởi tạo giá trị mặc định cho tháng
				userInfor.setMonthBirthday(currentMonth);
				userInfor.setMonthEnd(currentMonth);
				userInfor.setMonthStart(currentMonth);

				// Khởi tạo giá trị mặc định cho ngày
				userInfor.setDayBirthday(currentDay);
				userInfor.setDayEnd(currentDay);
				userInfor.setDayStart(currentDay);

				userInfor.setStartDate(Common.convertString(userInfor.getYearStart(), userInfor.getMonthStart(),
						userInfor.getDayStart()));
				userInfor.setEndDate(
						Common.convertString(userInfor.getYearEnd(), userInfor.getMonthEnd(), userInfor.getDayEnd()));
				userInfor.setBirthday(Common.convertString(userInfor.getYearBirthday(), userInfor.getMonthBirthday(),
						userInfor.getDayBirthday()));

			} else if ("validate".equals(type)) {

				userInfor.setLoginName(req.getParameter("loginName"));
				userInfor.setGroupId(Integer.parseInt(req.getParameter("groupId")));

				userInfor.setPassword(req.getParameter("password"));
				userInfor.setPasswordComfirm(req.getParameter("passwordComfirm"));

				// set ngày,thang,nam birthday

				userInfor.setDayBirthday(Integer.parseInt(req.getParameter("dayBirthday")));
				userInfor.setMonthBirthday(Integer.parseInt(req.getParameter("monthBirthday")));
				userInfor.setYearBirthday(Integer.parseInt(req.getParameter("yearBirthday")));
				userInfor.setEmail(req.getParameter("email"));
				userInfor.setFullName(req.getParameter("fullName"));
				userInfor.setFullNameKana(req.getParameter("fullNameKana"));
				userInfor.setTel(req.getParameter("tel"));
				userInfor.setCodeLevel(req.getParameter("codeLevel"));
				userInfor.setBirthday(Common.convertString(userInfor.getYearBirthday(), userInfor.getMonthBirthday(),
						userInfor.getDayBirthday()));
				// chọn trình độ tiếng nhật
				if (!"0".equals(req.getParameter("codeLevel"))) {
					if ("".equals(req.getParameter("total"))) {
						userInfor.setTotal("");
					} else {
						userInfor.setTotal(req.getParameter("total"));
					}
					// set ngày,thang,nam enddate và startdate
					userInfor.setYearStart(Integer.parseInt(req.getParameter("yearStart")));
					userInfor.setMonthStart(Integer.parseInt(req.getParameter("monthStart")));
					userInfor.setDayStart(Integer.parseInt(req.getParameter("dayStart")));

					userInfor.setDayEnd(Integer.parseInt(req.getParameter("dayEnd")));
					userInfor.setMonthEnd(Integer.parseInt(req.getParameter("monthEnd")));
					userInfor.setYearEnd(Integer.parseInt(req.getParameter("yearEnd")));

					userInfor.setStartDate(Common.convertString(userInfor.getYearStart(), userInfor.getMonthStart(),
							userInfor.getDayStart()));
					userInfor.setEndDate(Common.convertString(userInfor.getYearEnd(), userInfor.getMonthEnd(),
							userInfor.getDayEnd()));
				} else {// không chọn trình độ tiếng nhật
					userInfor.setTotal("");
					userInfor.setYearStart(currentYear);
					userInfor.setYearEnd(currentYear + 1);

					// Khởi tạo giá trị mặc định cho tháng

					userInfor.setMonthEnd(currentMonth);
					userInfor.setMonthStart(currentMonth);

					// Khởi tạo giá trị mặc định cho ngày
					userInfor.setDayEnd(currentDay);
					userInfor.setDayStart(currentDay);

					userInfor.setStartDate(Constant.DEFAULT_EMPTY);
					userInfor.setEndDate(Constant.DEFAULT_EMPTY);

				}

			} else if (Constant.ACTION_BACK.equals(type)) {
				// lấy keySession
				String keySession = req.getParameter("key");

				HttpSession session = req.getSession();
				userInfor = (UserInfor) session.getAttribute(keySession);
			}

			return userInfor;

		} catch (Exception e) {
			System.out.print(" AddUserInputController getDefaultValue();" + e.getMessage());
			throw e;
		}
	}

	/**
	 * hàm set giá trị cho selectbox
	 * 
	 * @param request
	 * @throws SQLException
	 */

	private void setDataLogic(HttpServletRequest request) throws SQLException {
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
			System.out.print(" AddUserInputController setDataLogic();" + e.getMessage());
			throw e;
		}
	}
}
