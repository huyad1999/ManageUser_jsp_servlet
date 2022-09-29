/**
 * 
 */
package manageuser.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.utils.MessageErrorProperties;

/**
 * @author nguyenbahuy
 * class lọc user chưa login
 */
public class LoginFilter implements Filter {
	/**
	 * kiểm tra các user đăng nhập hay chưa
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {
		
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession session = req.getSession();
			String urlLogin = req.getContextPath() + "/login";
			boolean checkLogin = Common.checkLogin(session);
			//kiểm tra xem có phải url Login k
			boolean checkUrlLogin = req.getRequestURI().equals(urlLogin);
			String urlADM002 = req.getContextPath() + "/listUser.do";
			//nếu user đã login
			if (checkLogin) {
				// nếu user gửi request login
				if (checkUrlLogin) {
					//di chuyển đến trang ADM002
					resp.sendRedirect(urlADM002);
					//nếu không phải login request thì đi tiếp
				}  else {
					chain.doFilter(request, response);
				}
				//nếu user chưa login thì di chuyển đến trang login
			} else {
				
				if(checkUrlLogin) {
					chain.doFilter(request, response);
			} else {
					resp.sendRedirect("login");
				}
			}
			
		} catch (Exception e) {
//			Ghi log
			System.out.println("LoginFilter doFilter" +e.getMessage());
//			trang thông báo lỗi
			//chuyển sang trang System_Error.jsp
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.ADM001);
			requestDispatcher.forward(request, response);
		}
	}

}
