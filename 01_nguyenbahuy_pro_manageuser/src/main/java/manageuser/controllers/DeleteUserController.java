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

import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Constant;
import manageuser.utils.MessageErrorProperties;

/**
 * @author nguyenbahuy
 * Controller thực hiện xoá 
 */
@WebServlet({"/deleteUser.do"})
public class DeleteUserController extends HttpServlet{

	private static final long serialVersionUID = 1L;

	/**
	 * thực hiện xóa và chuyển sang màn hình ADM006
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			int userId = Integer.parseInt(req.getParameter("userId"));
			if(userId > 0) {
				int rule = tblUserLogic.getRuleByUserId(userId);
				//rule = -1
				if(rule == Constant.RULE_FAIL) {
					req.setAttribute("errorMsg",MessageErrorProperties.getValueByKey("ER013"));
					RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.PAGE_ERROR);
					requestDispatcher.forward(req, resp);
				} else if(rule == Constant.RULE_ADMIN) { //rule = 0
					req.setAttribute("errorMsg",MessageErrorProperties.getValueByKey("ER020"));
					RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.PAGE_ERROR);
					requestDispatcher.forward(req, resp);
				} else if (rule == Constant.RULE_USER) { //rule = 1 
					boolean checkDelete = tblUserLogic.deleteUser(userId);
					if(checkDelete) {
						resp.sendRedirect(Constant.SUCCESS+"?type="+Constant.DELETE_SUCCESS);
					} else {
						 resp.sendRedirect(Constant.SUCCESS+"?type="+Constant.SYSTEM_ERROR);
					}
				}
			} else {
				req.setAttribute("errorMsg",MessageErrorProperties.getValueByKey("ER013"));
				RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.PAGE_ERROR);
				requestDispatcher.forward(req, resp);
			}
		}  catch (Exception e) {
			System.out.println("DeleteUserController doPost "+e.getMessage());
			req.setAttribute("errorMsg",MessageErrorProperties.getValueByKey("ER015"));
			//chuyển sang trang System_Error.jsp
			RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.PAGE_ERROR);
			requestDispatcher.forward(req, resp);
		}
	}
}
