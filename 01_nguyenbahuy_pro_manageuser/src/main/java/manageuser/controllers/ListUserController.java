/**
 * 
 */
package manageuser.controllers;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.entities.MstGroup;
import manageuser.entities.UserInfor;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Config;
import manageuser.utils.Constant;
import manageuser.utils.MessageErrorProperties;
import manageuser.utils.MessageProperties;

/**
 * @author nguyenbahuy
 * xử lý chức năng search,sort,paging
 */
@WebServlet(urlPatterns = "/listUser.do")
public class ListUserController extends HttpServlet{

	private static final long serialVersionUID = 1L;

	/**
	 * hàm xử thực hiện chức năng search,sort,paging khi nhận yêu cầu từ view và xử lý đẩy dữ liệu từ DB lên
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//tạo session
			HttpSession session = req.getSession();
			
			//lấy tham số
			String type = req.getParameter("type");
			//lấy giới hạn hiển thị số trang
			int limitPage = Integer.parseInt(Config.getValueByKey("limitPage"));
			//lấy số lượng record hiển thị trên 1 trang
			int limit = Integer.parseInt(Config.getValueByKey("limit"));
			//khởi tạo tổng user
			int totalUser =Constant.DEFAULT_INTEGER;
			//khởi tạo offset
			int offSet = Constant.DEFAULT_INTEGER;
			//gán page hiện tại mặc định bằng 1
			int currentPage = 1;
			//gán fullName mặc định là rỗng
			String fullName = Constant.DEFAULT_EMPTY;
			//gán groupId mặc định là 0
			int groupId = Constant.DEFAULT_INTEGER;
			//gán sort mặc định là theo trường full_name
			String sortType = Constant.FULLNAME;
			//gán sort trường full_name mặc định là tăng dần
			String sortByFullName = Constant.ASC;
			//gán sort trường code_level mặc định là tăng dần
			String sortByCodeLevel = Constant.ASC;
			//gán sort trường end_date mặc định là giảm dần
			String sortByEndDate = Constant.DESC;
			String msg005 = MessageProperties.getValueByKey("MSG005");
		
			//tạo đối tượng tblUserLogic
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			//tạo danh sách listUser
			List<UserInfor> listUser = new ArrayList<UserInfor>();
			//tạo đối tượng mstGroupLogic
			MstGroupLogic mstGroupLogic = new MstGroupLogicImpl();
			
			//gán totalPage mặc định bằng 0
			int totalPage = Constant.DEFAULT_INTEGER;
			//gán currentGroup mặc định bằng 0
			int currentGroup = Constant.DEFAULT_INTEGER;
			
			//tạo đối tượng listMstGroup
			List<MstGroup> listMstGroup = mstGroupLogic.getAllMstGroup();
			
			if(Constant.PAGING.equals(type) || Constant.SORT.equals(type) || Constant.SEARCH.equals(type)){
				//lấy dữ liệu fullName từ người dùng nhập
				fullName = req.getParameter(Constant.FULLNAME);
				//lấydữ liệu groupId từ người dùng nhập
				groupId = Integer.parseInt(req.getParameter(Constant.GROUPID));
			
				if(Constant.PAGING.equals(type)) {//khi click vào paging
					//lấy trang hiện tại từ view
					currentPage = Integer.parseInt(req.getParameter(Constant.CURRENTPAGE));
				} else if(Constant.SORT.equals(type)) {//khi click vào sort
					//lấy trường ưu tiên từ người dùng
					sortType = req.getParameter(Constant.SORTTYPE);
					//sort theo full_name
					if(Constant.FULLNAME.equals(sortType)) {
						//lấy kiểu sắp xếp trường full_name là tăng hay giảm
						sortByFullName = req.getParameter(Constant.SORTBYFULLNAME);
					} else if(Constant.CODELEVEL.equals(sortType)) {//sort theo name_level
						//lấy kiểu sắp xếp trường name_level là tăng hay giảm
						sortByCodeLevel = req.getParameter(Constant.SORTBYCODELEVEL);
					} else if(Constant.ENDDATE.equals(sortType)) {//sort theo end_date
						//lấy kiểu sắp xếp trường end_date là tăng hay giảm
						sortByEndDate = req.getParameter(Constant.SORTBYENDDATE);
					}	
				}
			} else if(Constant.BACK.equals(type)) {
				
				//lấy giá trị currentPage trên session
				currentPage = (int) session.getAttribute(Constant.CURRENTPAGE); 
				//lấy giá trị name trên session
				fullName = (String) session.getAttribute(Constant.FULLNAME);
				//lấy giá trị groupId trên session
				groupId = (int) session.getAttribute(Constant.GROUPID);
				//lấy giá trị sortByFullName trên session
				sortByFullName = (String) session.getAttribute(Constant.SORTBYFULLNAME);
				//lấy giá trị sortByEndDate trên session
				sortByEndDate = (String) session.getAttribute(Constant.SORTBYENDDATE);
				//lấy giá trị sortByCodeLevel trên session
				sortByCodeLevel = (String) session.getAttribute(Constant.SORTBYCODELEVEL);
				//lấy giá trị sortType trên session
				sortType = (String) session.getAttribute(Constant.SORTTYPE);
			}
			//lấy tổng số người theo điều kiện tìm kiếm
			totalUser = tblUserLogic.getTotalUsers(groupId, fullName);
			if(totalUser > 0) {
				//lấy tổng số page
				totalPage = Common.getTotalPage(totalUser, limit);
				
				//check trường hợp tổng số page có nhỏ hơn số page hiện tại không
				if(totalPage < currentPage) {
					currentPage = totalPage;
				}
				//lấy danh sách paging trả về cho view
				List<Integer> listPaging = Common.getListPaging(totalUser, limit, currentPage);
				//lấy currentGroup hiện tại của currentPage
				currentGroup = Common.getCurrentGroup(currentPage,limitPage);
				//tạo offset lấy vị trí bản ghi
				offSet = Common.getOffset(currentPage,limit);
				//lấy danh sách user theo các điều kiện 
				listUser = tblUserLogic.getListUsers(offSet, limit, groupId, fullName, sortType, sortByFullName, sortByCodeLevel,sortByEndDate);
				//truyền dữ liệu tổng số page lên view
				req.setAttribute(Constant.LISTPAGING, listPaging);
				
				//truyền dữ liệu group hiện tại lên view
				req.setAttribute("currentGroup", currentGroup);
				
				req.setAttribute("totalPage", totalPage);
				//set danh sách user lên view
				
			} else {//kiểm tra mảng rỗng
				//set thông báo lỗi
				req.setAttribute("Msg005", msg005);
			}
			req.setAttribute("listUser", listUser);
			//set dữ liệu của danh sách MstGroup
			req.setAttribute("listMstGroup", listMstGroup);
			//đưa giá trị textbox lên session
			session.setAttribute(Constant.FULLNAME, fullName);
			//đưa giá trị groupId lên session
			session.setAttribute(Constant.GROUPID, groupId);
			//đưa currentPage lên session
			session.setAttribute(Constant.CURRENTPAGE, currentPage);
			//đưa giá trị sortByFullName lên session
			session.setAttribute(Constant.SORTBYFULLNAME, sortByFullName);
			//đưa giá trị sortByFullName lên session
			session.setAttribute(Constant.SORTBYCODELEVEL, sortByCodeLevel);
			//đưa giá trị sortByEndDate lên session
			session.setAttribute(Constant.SORTBYENDDATE, sortByEndDate);
			//đưa giá trị sortType lên session
			session.setAttribute(Constant.SORTTYPE, sortType);

			//hiển thị màn hình ADM002
			RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.ADM002);
			requestDispatcher.forward(req, resp);
			
		} catch(Exception e) {
			//ghi log
			System.out.println("ListUserController doGet "+e.getMessage());
			req.setAttribute("errorMsg",MessageErrorProperties.getValueByKey("ER015"));
			//chuyển sang trang System_Error.jsp
			RequestDispatcher requestDispatcher = req.getRequestDispatcher(Constant.PAGE_ERROR);
			requestDispatcher.forward(req, resp);
		}
	}
}
