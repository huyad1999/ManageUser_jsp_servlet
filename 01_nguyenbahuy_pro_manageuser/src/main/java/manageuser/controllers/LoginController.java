/**
 * copyright(C) 2022 Luvina JSC
 * LoginController.java May 5, 2022 nguyenbahuy
 */
package manageuser.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Constant;
import manageuser.utils.MessageErrorProperties;
import manageuser.validates.ValidateUser;

/**
 * @author nguyenbahuy
 *	class lấy dữ liệu từ view và hiển thị màn hình ADM001
 */
@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet{

	private static final long serialVersionUID = 1L;

	/** 
	 * phương thức doPost xử lý khi người dùng gọi đăng nhập sẽ gọi đến đến method post
	 * lấy dữ liệu từ người dùng, truyền dữ liệu lên và điều hướng sang ADM002 nếu đăng nhập thành công ,còn không thì quay lại ADM001
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {

			//lấy dữ liệu logic từ người dùng
			String loginId = req.getParameter(Constant.lOGINID);
			//lấy dữ liệu password từ người dùng
			String password = req.getParameter("password");
			List<String> listErrorMsg = ValidateUser.validateLogin(loginId, password);
			//kiểm tra dữ liệu từ người dùng
			if(listErrorMsg.size() < 1) {
				 HttpSession session = req.getSession();
				 //đưa tài khoản lên session
		            session.setAttribute(Constant.lOGINID, loginId);
				//đúng thì chuyển sang màn hình ADM002
		            resp.sendRedirect(Constant.URL_ADM002); 
				
			} else {
				//sai thì thông báo lỗi
				req.setAttribute("listErrorMsg", listErrorMsg);
				//truyền lại dữ liệu nhập loginId sai lên textbox
				req.setAttribute(Constant.lOGINID,loginId);

				//vẫn ở màn hình ADM001
				RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.ADM001);
				requestDispatcher.forward(req, resp);
			}
			
		} catch(Exception e) {
			//ghi log
			System.out.println("LoginController doPost"+e.getMessage());
			req.setAttribute("errorMsg",MessageErrorProperties.getValueByKey("ER015"));
			//chuyển sang trang System_Error.jsp
			RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.PAGE_ERROR);
			requestDispatcher.forward(req, resp);
		}
	}
	/**
	 * hiển thị màn hình ADM001
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.ADM001);
		requestDispatcher.forward(req, resp);
	}
}
