/**
 * 
 */
package manageuser.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.entities.MstGroup;
import manageuser.entities.MstJapan;
import manageuser.entities.UserInfor;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Constant;
import manageuser.utils.MessageErrorProperties;
import manageuser.validates.ValidateUser;

/**
 * @author nguyenbahuy
 * controller hiện thị giá trị của màn hình ADM004 EDIT và xử lý edit khi click button[OK]
 */
@WebServlet({"/EditUserConfirm.do","/EditUserOk.do"})
public class EditUserConfirmController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	/**
	 * hiển thị màn hình ADM004 từ màn hình ADM003 sang
	 * @param HttpServletRequest req, HttpServletResponse resp
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try { 
			String key = req.getParameter("key");
			HttpSession session = req.getSession();
			String checkADM003 = (String) session.getAttribute(Constant.CHECKADM003);
			session.removeAttribute(Constant.CHECKADM003);
			if(checkADM003 != null) {
				//lấy giá trị userInfor từ session
				UserInfor userInfor = (UserInfor) session.getAttribute(key);
				req.setAttribute("key", key); 
				//set groupname
				MstGroupLogic mstGroupLogic = new MstGroupLogicImpl();
				MstGroup mstGroup = mstGroupLogic.getMstGroupByGroupId(userInfor.getGroupId());
				userInfor.setGroupName(mstGroup.getGroupName());
				//set NameLevel
				MstJapanLogic mstJapanLogic = new MstJapanLogicImpl();
				MstJapan mstJapan = mstJapanLogic.getMstJapanByCodeLevel(userInfor.getCodeLevel());
				userInfor.setNameLevel(mstJapan.getNameLevel());
				
				//set userInfor lên view
				req.setAttribute("userInfor", userInfor);
				//hiển thị màn hình adm004
				RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.ADM004);
				requestDispatcher.forward(req, resp);
			} else {
				req.setAttribute("errorMsg",MessageErrorProperties.getValueByKey("ER015"));
				RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.PAGE_ERROR);
				requestDispatcher.forward(req, resp);
			}
		} catch (Exception e) {
			System.out.println("EditUserConfirmController doGet "+e.getMessage());
			req.setAttribute("errorMsg",MessageErrorProperties.getValueByKey("ER015"));
			//chuyển sang trang System_Error.jsp
			RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.PAGE_ERROR);
			requestDispatcher.forward(req, resp);
		}
	}
	/**
	 * xử lý khi click vào button[ok]
	 * @param HttpServletRequest req, HttpServletResponse resp
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String key = req.getParameter("key");
			HttpSession session = req.getSession();
			//lấy giá trị userInfor từ session
			UserInfor userInfor = (UserInfor) session.getAttribute(key);
			//xóa key
			session.removeAttribute(key);
			//set userInfor lên view
			
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			String error = ValidateUser.validateADM004(userInfor);
		
			if(!error.isEmpty()) {
				req.setAttribute(Constant.ERROR_MSG, error);
				//chuyển sang trang System_Error.jsp
				RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.PAGE_ERROR);
				requestDispatcher.forward(req, resp);
			} else {
				boolean checkUserInfor = tblUserLogic.updateUser(userInfor);
				if(checkUserInfor) {
					resp.sendRedirect(Constant.SUCCESS+"?type="+Constant.UPDATE_SUCCESS);
				} else {
					resp.sendRedirect(Constant.SUCCESS+"?type="+Constant.SYSTEM_ERROR);
				}
			}

		} catch(Exception e) {
			System.out.println("EditUserConfirmController doPost"+e.getMessage());
			req.setAttribute("errorMsg",MessageErrorProperties.getValueByKey("ER015"));
			//chuyển sang trang System_Error.jsp
			RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.PAGE_ERROR);
			requestDispatcher.forward(req, resp);
		}
	}
}
