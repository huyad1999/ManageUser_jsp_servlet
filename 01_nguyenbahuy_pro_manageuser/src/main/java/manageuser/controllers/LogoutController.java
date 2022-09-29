package manageuser.controllers;

import java.io.IOException;
import javax.servlet.http.HttpSession;

import manageuser.utils.Constant;
import manageuser.utils.MessageErrorProperties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * lớp này để xử lý xóa session và đăng xuất về màn hình ADM001
 * @author nguyenbahuy
 */
@WebServlet("/logout")
public class LogoutController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see phương thức doGet xử lý khi người dùng gọi đến method Get
	 * đăng xuất và xóa tài khoản lưu trên session
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			HttpSession session = req.getSession();
			//xóa tài khoản lưu trên session
	        session.removeAttribute(Constant.lOGINID);
	        //chuyển hướng sang ADM001
			RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.ADM001);
			requestDispatcher.forward(req, resp);
		} catch(Exception e) {
			System.out.println("SuccessController doGet"+e.getMessage());
			req.setAttribute("errorMsg",MessageErrorProperties.getValueByKey("ER015"));
			//chuyển sang trang System_Error.jsp
			RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.PAGE_ERROR);
			requestDispatcher.forward(req, resp);
		}
	}

}
