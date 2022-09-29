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
import manageuser.entities.UserInfor;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Constant;
import manageuser.utils.MessageErrorProperties;

/**
 * @author nguyenbahuy Controller thực hiện hiển thị ra chi tiết User ra màn
 *  hình ADM005 khi click vào id của ADM002
 */
@WebServlet({ "/DetailUser.do" })
public class DetailUserController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * hiển thị ra chi tiết User ra màn hình ADM005 khi click vào id của ADM002
	 * 
	 * @param req,resp
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			int userId = Integer.parseInt(req.getParameter("userId"));

			if (userId > 0) {
				// check userId
				boolean checkId = tblUserLogic.checkExistUserId(userId);
				if (!checkId) {
					req.setAttribute("errorMsg", MessageErrorProperties.getValueByKey("ER015"));
					RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.PAGE_ERROR);
					requestDispatcher.forward(req, resp);
				} else {
					UserInfor userInfor = tblUserLogic.getUserInforByUserId(userId);
					req.setAttribute("userInfor", userInfor);
					RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.ADM005);
					requestDispatcher.forward(req, resp);
				}
			} else {
				req.setAttribute("errorMsg", MessageErrorProperties.getValueByKey("ER013"));
				// chuyển sang trang System_Error.jsp
				RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.PAGE_ERROR);
				requestDispatcher.forward(req, resp);
			}

		} catch (Exception e) {
			System.out.println("DetailUserController doGet " + e.getMessage());
			req.setAttribute("errorMsg", MessageErrorProperties.getValueByKey("ER015"));
			// chuyển sang trang System_Error.jsp
			RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.PAGE_ERROR);
			requestDispatcher.forward(req, resp);
		}
	}

}
