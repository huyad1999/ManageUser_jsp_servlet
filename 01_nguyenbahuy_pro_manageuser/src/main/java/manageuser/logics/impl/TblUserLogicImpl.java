/**
 * copyright(C) 2022 Luvina JSC
 * TblUserLogicImpl.java May 3, 2022 nguyenbahuy
 */
package manageuser.logics.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import java.sql.Connection;

import manageuser.dao.BaseDao;
import manageuser.dao.TblDetailUserJapanDao;
import manageuser.dao.TblUserDao;
import manageuser.dao.impl.BaseDaoImpl;
import manageuser.dao.impl.TblDetailUserJapanDaoImpl;
import manageuser.dao.impl.TblUserDaoImpl;
import manageuser.entities.MstGroup;
import manageuser.entities.MstJapan;
import manageuser.entities.TblDetailUserJapan;
import manageuser.entities.TblUser;
import manageuser.entities.UserInfor;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * @author nguyenbahuy
 * lớp xử lý thông tin User
 */
public class TblUserLogicImpl implements TblUserLogic {

	/**
	 * hàm kiểm tra có tài khoản hay không
	 * @Param truyền vào loginId và mật khẩu
	 * @return đúng khi tài khoản mà mật khẩu đúng,sai khi 1 trong 2 tài khoản và mật khẩu sai
	 */
	@Override
	public boolean existAccount(String loginId,String password) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
		//tạo đối tượng tblUserDaoImpl để lấy thông tin
		TblUserDao tblUserDao = new TblUserDaoImpl();
		
		try {
			//lấy ra tblUser theo LoginId
			TblUser tblUser = tblUserDao.getTblUserByUsername(loginId,Constant.RULE_ADMIN);
			//kiểm tra tblUser có null không
			if(tblUser == null) { 
				//nếu null trả về false
				return false;
			}
			/*
			 * lấy thông tin khi mã hóa password truyền vào password người dùng nhập,salt trong DB,và 
			 * so sánh với password đã mã hóa trong DB
			 */
			String encryptPass = Common.encriptPass(password,tblUser.getSalt());
			//kiểm tra có trùng khớp với mật khẩu trong DB không
			if(!encryptPass.equals(tblUser.getPassword())) {
				//nếu không trùng thì trả về false
				return false;
			}
		} catch (SQLException | NoSuchAlgorithmException | ClassNotFoundException e) {
			//ghi log
			System.out.println("TblUserLogicImpl existAccount"+e.getMessage());
			throw e;
		} 
		
		return true;
	}
	

	/**
	 * hàm lấy danh sách userInfor từ TblUserDaoImpl
	 * @Param offset vị trí data cần lấy nào
	 * @Param limit số lượng lấy
	 * @Param groupId mã nhóm tìm kiếm
	 * @Param fullName Tên tìm kiếm
	 * @Param sortType: Nhận biết xem cột nào được ưu tiên sắp xếp 
	 * @Param sortByFullName,sortByCodeLevel,sortByEndDate: Giá trị sắp xếp của cột Tên(ASC or DESC)
	 * @return danh sách userInfor
	 */
	@Override
	public List<UserInfor> getListUsers(int offset, int limit, int groupID, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate) throws SQLException, ClassNotFoundException {
		//tạo 1 đối tượng tblUserDao
		TblUserDao tblUserDao = new TblUserDaoImpl();
		List<UserInfor> listUserInfor = null;
		try {
			// xử lý ký tự đặc biệt wilcard
			fullName  = Common.replaceWilrcard(fullName);
			listUserInfor = tblUserDao.getListUsers(offset, limit, groupID, fullName, sortType, sortByFullName, sortByCodeLevel, sortByEndDate);
		} catch (SQLException | ClassNotFoundException e) {
			//ghi log
			System.out.println("TblUserLogicImpl getListUsers"+e.getMessage());
			throw e;
		}
		return listUserInfor;
		
	}

	/**
	 * hàm lấy số lượng user từ TblUserDaoImpl
	 * @Param groupId mã nhóm tìm kiếm
	 * @Param fullName Tên tìm kiếm
	 * @return trả về số lượng user
	 */
	@Override
	public int getTotalUsers(int groupId, String fullName) throws SQLException, ClassNotFoundException {
		int total= 0;
		TblUserDao tblUserDao = new TblUserDaoImpl();
		try {
			fullName = Common.replaceWilrcard(fullName);
			
			total = tblUserDao.getTotalUsers(groupId, fullName);

		} catch (ClassNotFoundException | SQLException e) {
			//ghi log
			System.out.println("TblUserLogicImpl getTotalUsers"+e.getMessage());
			throw e;
		} 
		return total;
	}

	/**
	 * hàm check tài khoản đã tồn tại chưa
	 * @Param truyền vào loginName
	 * @return đúng khi loginName chưa tồn tại,sai khi loginName đã tồn tại
	 */
	@Override
	public boolean checkExistLoginName(String loginName) throws ClassNotFoundException, SQLException {
		
		TblUserDao tblUserDao = new TblUserDaoImpl();
		
		try {
			//lấy ra tblUser theo LoginId
			TblUser tblUser = tblUserDao.getTblUserByUsername(loginName,Constant.RULE_USER);
			if(tblUser == null) {
				return true;
			}
		} catch(SQLException | ClassNotFoundException e) {
			System.out.println("TblUserLogicImpl checkExistLoginName "+e.getMessage());
			throw e;
		} 
		return false;
	}
	/**
	 * check email đã tồn tại chưa
	 * @Param truyền vào email
	 * @return đúng khi email tồn tại ,sai khi email chưa tồn tại
	 */
	@Override
	public boolean checkExistEmail(String email) throws ClassNotFoundException, SQLException {

		TblUserDao tblUserDao = new TblUserDaoImpl();
		
		try {
			//lấy ra tblUser theo LoginId
			TblUser tblUser = tblUserDao.getTblUserByEmail(email);
			if(tblUser == null) {
				return false;
			}
		} catch(ClassNotFoundException | SQLException e) {
			System.out.println("TblUserLogicImpl checkExistEmail"+e.getMessage());
			throw e;
		}
		return true;
	}
	/**
	 * hàm kiểm tra xem có chọn trình độ tiếng nhật và check email,loginName tồn tại,groupId,codeLevel không tồn tại
	 * @param userInfor truyền vào đối tượng userInfor
	 * @return trả về true nếu thực hiện insert thành công vào DB và false khi không thoả mãn điều kiện check
	 */
	@Override
	public boolean createUser(UserInfor userInfor) throws NoSuchAlgorithmException,ClassNotFoundException,SQLException {
		BaseDao baseDao = new BaseDaoImpl();
		Connection con = null;
		try {

			TblUserDao tblUserdao = new TblUserDaoImpl();
			TblDetailUserJapanDao detailUserJapanDao = new TblDetailUserJapanDaoImpl();
			TblUser tblUser = new TblUser();
			TblDetailUserJapan detailUserJapan = new TblDetailUserJapan();
			tblUser = Common.getTblUser(userInfor);
			
			con = baseDao.getConnection();
			con.setAutoCommit(false);
			int userId = tblUserdao.insertUser(tblUser,con);
		
			//check xem có chọn trình dộ tiếng nhập k
			if(!"0".equals(userInfor.getCodeLevel()) ) {
				
				detailUserJapan = Common.getTblDetailUserJapan(userInfor);
				detailUserJapan.setUserId(userId);
				//insert vào tbldetailjapan
				detailUserJapanDao.insertDetailUserJapan(detailUserJapan,con);
			}
			con.commit();
			return true;
		} catch ( NoSuchAlgorithmException| ClassNotFoundException | SQLException  e) {
			baseDao.rollback();
			System.out.println("TblUserLogicImpl createUser()"+e.getMessage());
			throw e;
		} finally {
			// đóng kết nối
			baseDao.close();
		}
		
	}
	/**
	 * hàm updateUser theo userInfor truyền vào
	 * @param userInfor được truyền vào
	 * @return trả về đúng nếu update thành công và sai khi update thất bại
	 * @throws NoSuchAlgorithmException 
	 */
	@Override
	public boolean updateUser(UserInfor userInfor) throws SQLException, NoSuchAlgorithmException  {
		BaseDao baseDao = new BaseDaoImpl();
		Connection con = null;
		try {
		
			TblUserDao tblUserdao = new TblUserDaoImpl();
			TblDetailUserJapanDao detailUserJapanDao = new TblDetailUserJapanDaoImpl();
			TblUser tblUser = new TblUser();
			TblDetailUserJapan detailUserJapan = new TblDetailUserJapan();
			//mở kết nối
			con = baseDao.getConnection();
			con.setAutoCommit(false);
			//chuyển từ 
			tblUser = Common.getTblUser(userInfor);
			
			tblUserdao.updateTblUser(tblUser,con);
			String codeLevel = detailUserJapanDao.getCodeLevelDetailJapanByUserId(userInfor.getUserId());
			//nếu xóa bảng detailJapan thì k cần covert từ userInfor sang detailJapan nữa
			if(!userInfor.getCodeLevel().equals("0")) {
				detailUserJapan = Common.getTblDetailUserJapan(userInfor);
			}
			detailUserJapan.setUserId(userInfor.getUserId());

			//check xem có chọn trình dộ tiếng nhập k
			if("0".equals(userInfor.getCodeLevel()) && !Constant.DEFAULT_EMPTY.equals(codeLevel)) {
		
				//xóa 1 bản ghi trong bảng tblDetailUserJapan
				detailUserJapanDao.deleteDetailJapanByUserId(userInfor.getUserId(),con);
				//nếu user thêm trình độ tiếng nhật
			} else if(!"0".equals(userInfor.getCodeLevel()) && Constant.DEFAULT_EMPTY.equals(codeLevel)) {
				
				//insert 1 bản ghi trong bảng tblDetailUserJapan
				detailUserJapanDao.insertDetailUserJapan(detailUserJapan,con);
				//nếu user edit trình độ tiếng nhật
			} else if (!"0".equals(userInfor.getCodeLevel()) && !Constant.DEFAULT_EMPTY.equals(codeLevel)) {
			
				//edit 1 bản ghi trong bảng tblDetailUserJapan
				detailUserJapanDao.updateDetailJapan(detailUserJapan,con);
			}
			con.commit();
			return true;
		} catch (NoSuchAlgorithmException | SQLException  e) {
			baseDao.rollback();
			System.out.println("TblUserLogicImpl updateUser()"+e.getMessage());
			throw e;
		} finally {
			// đóng kết nối
			baseDao.close();
		}
	}

	/**
	 * hàm lấy UserInfor theo userId
	 * @param userId
	 * @return userInfor 
	 * @throws SQLException 
	 * @return trả vê userInfor theo userId truyền vào
	 */
	@Override
	public UserInfor getUserInforByUserId(int userId) throws SQLException {
		UserInfor userInfor = new UserInfor();
		try {
			TblUserDao tblUserDao = new TblUserDaoImpl();
			userInfor = tblUserDao.getUserInforByUserId(userId);
		} catch (SQLException e) {
			System.out.println("TblUserLogicImpl getUserInforById()"+e.getMessage());
			throw e;
		}
		
		return userInfor;
	}

	/**
	 * kiểm tra xem có tồn tại userId
	 * @param userId
	 * @return đúng nếu tìm thấy TblUser theo userId,sai ngược lại
	 * @throws SQLException 
	 */
	@Override
	public boolean checkExistUserId(int userId) throws SQLException {
		try {
			TblUserDao tblUserDao = new TblUserDaoImpl();
			TblUser tblUser = new TblUser();
			tblUser = tblUserDao.getTblUserById(userId);
			if(tblUser != null) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("TblUserLogicImpl checkExistUserId()"+e.getMessage());
			throw e;
		}
		return false;
	}

	/**
	 * xóa UserInfor theo userId
	 * @param userId
	 * @return đúng nếu xoá thành công và false thì xoá không thành công và rollback lại data cũ
	 * @throws SQLException
	 */
	@Override
	public boolean deleteUser(int userId) throws SQLException {
		BaseDao baseDao = new BaseDaoImpl();
		Connection con = null;
		try {

			TblUserDao tblUserDao = new TblUserDaoImpl();
			TblDetailUserJapanDao detailUserJapanDao = new TblDetailUserJapanDaoImpl();
			
			con = baseDao.getConnection();
			con.setAutoCommit(false);
			
			detailUserJapanDao.deleteDetailJapanByUserId(userId,con);
			tblUserDao.deleteUserByUserId(userId,con);

			con.commit();
			return true;
		} catch (SQLException  e) {
			baseDao.rollback();
			System.out.println("TblUserLogicImpl deleteUser()"+e.getMessage());
			throw e;
		} finally {
			// đóng kết nối
			baseDao.close();
		}
		
	}
	/**
	 * kiểm tra email theo userId
	 * @return đúng nếu có email theo user và email cũ giống email mới ,còn sai thì ngược lại
	 * @param userId và emailNew email mới
	 */
	@Override
	public boolean checkEmailByUserId(int userId,String emailNew) throws SQLException {
		try {
			TblUserDao tblUserDao = new TblUserDaoImpl();
			//lấy ra tblUser theo LoginId
			TblUser tblUser = tblUserDao.getEmailByUserId(userId);
			if(tblUser != null && tblUser.getEmail().equals(emailNew)) {
				return true;
			}
		} catch(SQLException e) {
			System.out.println("TblUserLogicImpl checkExistLoginName "+e.getMessage());
			throw e;
		} 
		return false;
	}
	/**
	 * lấy rule theo userId
	 * @param userId
	 * @return rule 
	 * @throws SQLException 
	 */
	@Override
	public int getRuleByUserId(int userId) throws SQLException {
		int rule;
		try {
			TblUserDao tblUserDao = new TblUserDaoImpl();
			//lấy ra rule theo userId
			rule = tblUserDao.getRuleByUserId(userId);
		} catch(SQLException e) {
			System.out.println("TblUserLogicImpl checkExistLoginName "+e.getMessage());
			throw e;
		} 
		return rule;
	}

	/**
	 * lấy rule theo loginName
	 * @param loginName
	 * @return rule
	 * @throws SQLException
	 */
	@Override
	public int getRuleByLoginName(String loginName) throws SQLException {
		int rule;
		try {
			TblUserDao tblUserDao = new TblUserDaoImpl();
			//lấy ra rule theo userId
			rule = tblUserDao.getRuleByLoginName(loginName);
		} catch(SQLException e) {
			System.out.println("TblUserLogicImpl checkExistLoginName "+e.getMessage());
			throw e;
		} 
		return rule;
	}

	
	
	
}
