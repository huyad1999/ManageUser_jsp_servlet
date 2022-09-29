/**
 * copyright(C) 2022 Luvina JSC
 * ValidateUser.java May 5, 2022 nguyenbahuy
 */
package manageuser.validates;


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.entities.MstGroup;
import manageuser.entities.MstJapan;
import manageuser.entities.UserInfor;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.utils.MessageErrorProperties;

/**
 * @author nguyenbahuy
 * lớp kiểm tra dữ liệu
 */
public class ValidateUser {
	/*
	 * @Param truyền vào loginId và password từ người dùng nhập
	 * @return trả về kết quả đúng khi người dùng nhập đúng định dạng và đúng tài khoản ,mật khẩu
	 * kiểm tra dữ liệu login
	 */
	public static List<String> validateLogin(String loginId,String password) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException{
		List<String> listError = new ArrayList<String>();
		try {
			
			//tạo đối tượng TblUserLogic
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			//kiểm tra người dùng có nhập thông tin không?
			if("".equals(loginId)) {
				String ER001LoginName = MessageErrorProperties.getValueByKey("ER001_loginName");
				listError.add(ER001LoginName);
			}
			if("".equals(password)) {
				String ER001Password = MessageErrorProperties.getValueByKey("ER001_password");
				listError.add(ER001Password);
			}
			//kiểm tra tài khoản có tồn tại
			if(!tblUserLogic.existAccount(loginId,password)) {
				String ER016 = MessageErrorProperties.getValueByKey("ER016");
					listError.add(ER016);
				
			}
		} catch (NoSuchAlgorithmException | ClassNotFoundException | SQLException e) {//ghi log
				System.out.println(" ValidateUser validateLogin()" + e.getMessage());
				throw e;
		}
		return listError;
	
	}
	/*
	 * hàm validate thông tin của UserInfor
	 * tham số truyền vào là userInfor
	 * trả về danh sách các lỗi
	 */
	public static List<String> validateADM003(UserInfor userInfor) throws SQLException, ClassNotFoundException, UnsupportedEncodingException{
		List<String> listError = new ArrayList<String>();
		try {
			MstGroupLogic mstGroupLogic = new MstGroupLogicImpl();
			MstGroup mstGroup = mstGroupLogic.getMstGroupByGroupId(userInfor.getGroupId());
			MstJapanLogic mstJapanLogic = new MstJapanLogicImpl();
			MstJapan mstJapan = mstJapanLogic.getMstJapanByCodeLevel(userInfor.getCodeLevel());
			//check hạng mục loginName
			//check tồn tại
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
		
		if(userInfor.getUserId() == 0) {
			if(Common.isEmpty(userInfor.getLoginName())) {
				String ER001LoginName = MessageErrorProperties.getValueByKey("ER001_loginName");
				listError.add(ER001LoginName);
			} else if(Common.checkFormat(userInfor.getLoginName(), Constant.FORMATLOGINNAME)) {//check loginName định dạng
				String ER019_LoginName = MessageErrorProperties.getValueByKey("ER019_LoginName");
				listError.add(ER019_LoginName);
			} else if(Common.checkRange(userInfor.getLoginName(),4,15) ){//check loginName range 4-15
				String ER007LoginName = MessageErrorProperties.getValueByKey("ER007_LoginName");
				listError.add(ER007LoginName);
			} else if(!tblUserLogic.checkExistLoginName(userInfor.getLoginName())) {//check loginName 
				String ER003LoginName = MessageErrorProperties.getValueByKey("ER003_LoginName");
				listError.add(ER003LoginName);
			}
		}
		
		//check chon group
		if(userInfor.getGroupId() == 0) {
			String ER002MstGroup = MessageErrorProperties.getValueByKey("ER002_MstGroup");
			listError.add(ER002MstGroup);
		} else if (mstGroup == null) {//check groupId không tồn tại
				String ER004MstGroup = MessageErrorProperties.getValueByKey("ER004_MstGroup");
				listError.add(ER004MstGroup);
		}
			//check hạng mục fullName
			//check fullname lengthMax
			if(Common.isEmpty(userInfor.getFullName())) {
				String ER001FullName = MessageErrorProperties.getValueByKey("ER001_FullName");
				listError.add(ER001FullName);
			} else if(!Common.Maxlength(userInfor.getFullName(), Constant.MAXLENGTHFULLNAME)) {
				String ER006fullName = MessageErrorProperties.getValueByKey("ER006_fullName");
				listError.add(ER006fullName);
			}
			//check hạng mục fullNameKana
			if(!Constant.DEFAULT_EMPTY.equals(userInfor.getFullNameKana())) {
				 if(!Common.checkKata(userInfor.getFullNameKana())) {
					listError.add(MessageErrorProperties.getValueByKey("ER009_fullNameKana"));
				} else if(!Common.Maxlength(userInfor.getFullNameKana(), Constant.MAXLENGTHKANA)) {
					String ER006fullNameKana = MessageErrorProperties.getValueByKey("ER006_fullNameKana");
					listError.add(ER006fullNameKana);
				}
			}

			//check birthday
			if(!Common.checkDate(userInfor.getBirthday())) {
				String ER011Birthday = MessageErrorProperties.getValueByKey("ER011_Birthday");
				listError.add(ER011Birthday);
			}
			//check hạng mục email
			//check maxlength email
			//check format email
			if(Common.isEmpty(userInfor.getEmail())) {
				String ER001Email = MessageErrorProperties.getValueByKey("ER001_Email");
				listError.add(ER001Email);
			} else if(!Common.Maxlength(userInfor.getEmail(), Constant.MAXLENGTHEMAIL)) {
				String ER006Email = MessageErrorProperties.getValueByKey("ER006_Email");
				listError.add(ER006Email);
			} else if(Common.checkFormat(userInfor.getEmail(), Constant.FORMATEMAIL)) {
				String ER005Email = MessageErrorProperties.getValueByKey("ER005_Email");
				listError.add(ER005Email);
			} else if(tblUserLogic.checkExistEmail(userInfor.getEmail())) {
					if(!tblUserLogic.checkEmailByUserId(userInfor.getUserId(),userInfor.getEmail())) {
						
						String ER003Email = MessageErrorProperties.getValueByKey("ER003_Email");
						listError.add(ER003Email);
					}
				
			}
			//check tel đã nhập tel
			//check format tel
			//check kí tự 1 byte tel
			//check tel maxlength
			if(Common.isEmpty(userInfor.getTel())) {
				String ER001Tel = MessageErrorProperties.getValueByKey("ER001_Tel");
				listError.add(ER001Tel);
			} else if(Common.checkFormat(userInfor.getTel(), Constant.FORMATTEL)) {
				String ER005Tel = MessageErrorProperties.getValueByKey("ER005_Tel");
				listError.add(ER005Tel);
			} else if(!Common.Maxlength(userInfor.getTel(),Constant.MAXLENGTHTEL)) {
				String ER006Tel = MessageErrorProperties.getValueByKey("ER006_Tel");
				listError.add(ER006Tel);
			}
			if(userInfor.getUserId() == Constant.DEFAULT_INTEGER) {
				//check không nhập password
				//check password kí tự 1 byte
				//check password in range 5-15
				if(Common.isEmpty(userInfor.getPassword())) {
					String ER001Password = MessageErrorProperties.getValueByKey("ER001_Password");
					listError.add(ER001Password);
				} else if(!Common.checkIs1Byte(userInfor.getPassword())) {
					String ER008Password = MessageErrorProperties.getValueByKey("ER008_Password");
					listError.add(ER008Password);
				} else if(Common.checkRange(userInfor.getPassword(), 5, 15)) {
					String ER007Password = MessageErrorProperties.getValueByKey("ER007_Password");
					listError.add(ER007Password);
				}
				
				//check nhập lại password = password
				if(!userInfor.getPassword().equals(userInfor.getPasswordComfirm())) {
					String ER017 = MessageErrorProperties.getValueByKey("ER017");
					listError.add(ER017);
				}
			}
			
			if(!"0".equals(userInfor.getCodeLevel())) {
				if (mstJapan == null) {//check codeLevel không tồn tại
					String ER004MstJapan = MessageErrorProperties.getValueByKey("ER004_MstJapan");
					listError.add(ER004MstJapan);
				}
			
				//check startdate hợp lệ
				if(!Common.checkDate(userInfor.getStartDate())) {
					String ER011StartDate = MessageErrorProperties.getValueByKey("ER011_StartDate");
					listError.add(ER011StartDate);
				}
				//check enddate hợp lệ
				//check enddate > startdate
				if(!Common.checkDate(userInfor.getEndDate())) {
					String ER011EndDate = MessageErrorProperties.getValueByKey("ER011_EndDate");
					listError.add(ER011EndDate);
				} else if(!Common.checkStartWithEnd(userInfor.getStartDate(), userInfor.getEndDate())) {
					String ER012 = MessageErrorProperties.getValueByKey("ER012");
					listError.add(ER012);
				}
				//check total 
				if(Common.isEmpty(userInfor.getTotal())) {
					String ER001Total = MessageErrorProperties.getValueByKey("ER001_Total");
					listError.add(ER001Total);
				} else if(!Common.checkIsHalfSizeNum(userInfor.getTotal())) {
					String ER018 = MessageErrorProperties.getValueByKey("ER018");
					listError.add(ER018);
				} else if(!Common.Maxlength(userInfor.getTotal(),Constant.MAXLENGTHTOTAL)) {
					String ER006Total = MessageErrorProperties.getValueByKey("ER006_Total");
					listError.add(ER006Total);
				}
			}
				
		} catch (SQLException | ClassNotFoundException | UnsupportedEncodingException e) {
			System.out.println(" ValidateUser validateUser() " + e.getMessage());
			throw e;
		} 
	
		return listError;
	}
	
/**
 * kiểm tra loginName,email tồn tại và groupId, codeLevel không tồn tại
 * trả về mã lỗi
 * @param userInfor
 * @param type
 * @return trả về mã lỗi tương ứng
 * @throws SQLException
 * @throws ClassNotFoundException
 */
	public static String validateADM004(UserInfor userInfor) throws SQLException,ClassNotFoundException {
		try {
			String error = Constant.DEFAULT_EMPTY;
		TblUserLogic tblUserLogic = new TblUserLogicImpl();
		MstGroupLogic mstGroupLogic = new MstGroupLogicImpl();
		MstGroup mstGroup = mstGroupLogic.getMstGroupByGroupId(userInfor.getGroupId());
		MstJapanLogic mstJapanLogic = new MstJapanLogicImpl();
		System.out.println("userInfor,getEmail--"+userInfor.getEmail()+"--"+"userInfor.getgrouId--"+userInfor.getGroupId()+"--"+"userImfor.getCodelevel=="+userInfor.getCodeLevel());
		if(userInfor.getUserId() == Constant.DEFAULT_INTEGER) {
			if (tblUserLogic.checkExistEmail(userInfor.getEmail())) {//check mail tồn tại
				error = MessageErrorProperties.getValueByKey("ER003_Email");
			} else if (!tblUserLogic.checkExistLoginName(userInfor.getLoginName())) {//check loginName tồn tại
				error = MessageErrorProperties.getValueByKey("ER003_LoginName");
			} else if (mstGroup == null) {//check groupId không tồn tại
				error = MessageErrorProperties.getValueByKey("ER004_MstJapan");
			} else if (mstJapanLogic.getMstJapanByCodeLevel(userInfor.getCodeLevel()) == null) {//check codeLevel không tồn tại
				error = MessageErrorProperties.getValueByKey("ER004_MstGroup");
			}
		} else {
			if (tblUserLogic.checkExistEmail(userInfor.getEmail())) {//check mail tồn tại
				if(!tblUserLogic.checkEmailByUserId(userInfor.getUserId(),userInfor.getEmail())) {
					error = MessageErrorProperties.getValueByKey("ER003_Email");
				}
			} else if (mstGroup == null) {//check groupId không tồn tại
				error = MessageErrorProperties.getValueByKey("ER004_MstGroup");
			} else if (!"0".equals(userInfor.getCodeLevel())) {//check codeLevel không tồn tại
				if(mstJapanLogic.getMstJapanByCodeLevel(userInfor.getCodeLevel()) == null) {
					error = MessageErrorProperties.getValueByKey("ER004_MstJapan");
				}
			}
		}

			return error;
			
		}  catch (SQLException | ClassNotFoundException e) {
			System.out.println(" ValidateUser validateUserInfor() " + e.getMessage());
			throw e;
		} 
		
	}
}