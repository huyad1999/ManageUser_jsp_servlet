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

import manageuser.utils.Constant;
import manageuser.utils.MessageErrorProperties;
import manageuser.utils.MessageProperties;

/**
 * @author nguyenbahuy
 * hiển thị màn hình ADM006 và câu thông báo lỗi
 */
@WebServlet({"/success.do"})
public class SuccessController extends HttpServlet{

	private static final long serialVersionUID = 1L;

	/**
	 * hiển thị màn hình ADM006 với thông báo lỗi tương ứng
	 * @param HttpServletRequest req,HttpServletResponse resp
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			String type = req.getParameter("type");
			String error = "";
			if(Constant.INSERT_SUCCESS.equals(type) ) {
				error = MessageProperties.getValueByKey("MSG001");
			} else if( Constant.UPDATE_SUCCESS.equals(type)) {
				error = MessageProperties.getValueByKey("MSG002");
			} else if(Constant.DELETE_SUCCESS.equals(type)) {
				error = MessageProperties.getValueByKey("MSG003");
			} else {
				error = MessageErrorProperties.getValueByKey("ER015");
			}
			req.setAttribute("error", error);
			
			RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.ADM006);
			requestDispatcher.forward(req, resp);
		} catch(Exception e) {
			//ghi log
			System.out.println("SuccessController doGet"+e.getMessage());
			req.setAttribute("errorMsg",MessageErrorProperties.getValueByKey("ER015"));
			//chuyển sang trang System_Error.jsp
			RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.PAGE_ERROR);
			requestDispatcher.forward(req, resp);
		}
	}
}
