/**
 * 
 */
package manageuser.entities;

/**
 * @author nguyenbahuy
 *
 */
public class UserInfor {
	

	@Override
	public String toString() {
		return "UserInfor [userId=" + userId + ", groupId=" + groupId + ", loginName=" + loginName + ", fullNameKana="
				+ fullNameKana + ", password=" + password + ", passwordComfirm=" + passwordComfirm + ", codeLevel="
				+ codeLevel + ", fullName=" + fullName + ", birthday=" + birthday + ", endDate=" + endDate
				+ ", startDate=" + startDate + ", yearBirthday=" + yearBirthday + ", monthBirthday=" + monthBirthday
				+ ", dayBirthday=" + dayBirthday + ", groupName=" + groupName + ", email=" + email + ", tel=" + tel
				+ ", nameLevel=" + nameLevel + ", yearStart=" + yearStart + ", monthStart=" + monthStart + ", dayStart="
				+ dayStart + ", yearEnd=" + yearEnd + ", monthEnd=" + monthEnd + ", dayEnd=" + dayEnd + ", total="
				+ total + "]";
	}
	private int userId;
	private int groupId;
	private String loginName;
	private String fullNameKana;
	private String password;
	private String passwordComfirm;
	private String codeLevel;
	private String fullName;
	private String birthday;
	private String endDate;
	private String startDate;
	private int yearBirthday;
	private int monthBirthday;
	private int dayBirthday;
	private String groupName;
	private String email;
	private String tel;
	private String nameLevel;
	private int yearStart;
	private int monthStart;
	private int dayStart;
	private int yearEnd;
	private int monthEnd;
	private int dayEnd;
	private String total;
	
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the groupId
	 */
	public int getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * @return the fullNameKana
	 */
	public String getFullNameKana() {
		return fullNameKana;
	}
	/**
	 * @param fullNameKana the fullNameKana to set
	 */
	public void setFullNameKana(String fullNameKana) {
		this.fullNameKana = fullNameKana;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the passwordComfirm
	 */
	public String getPasswordComfirm() {
		return passwordComfirm;
	}
	/**
	 * @param passwordComfirm the passwordComfirm to set
	 */
	public void setPasswordComfirm(String passwordComfirm) {
		this.passwordComfirm = passwordComfirm;
	}
	/**
	 * @return the codeLevel
	 */
	public String getCodeLevel() {
		return codeLevel;
	}
	/**
	 * @param codeLevel the codeLevel to set
	 */
	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the yearBirthday
	 */
	public int getYearBirthday() {
		return yearBirthday;
	}
	/**
	 * @param yearBirthday the yearBirthday to set
	 */
	public void setYearBirthday(int yearBirthday) {
		this.yearBirthday = yearBirthday;
	}
	/**
	 * @return the monthBirthday
	 */
	public int getMonthBirthday() {
		return monthBirthday;
	}
	/**
	 * @param monthBirthday the monthBirthday to set
	 */
	public void setMonthBirthday(int monthBirthday) {
		this.monthBirthday = monthBirthday;
	}
	/**
	 * @return the dayBirthday
	 */
	public int getDayBirthday() {
		return dayBirthday;
	}
	/**
	 * @param dayBirthday the dayBirthday to set
	 */
	public void setDayBirthday(int dayBirthday) {
		this.dayBirthday = dayBirthday;
	}
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * @return the nameLevel
	 */
	public String getNameLevel() {
		return nameLevel;
	}
	/**
	 * @param nameLevel the nameLevel to set
	 */
	public void setNameLevel(String nameLevel) {
		this.nameLevel = nameLevel;
	}
	/**
	 * @return the yearStart
	 */
	public int getYearStart() {
		return yearStart;
	}
	/**
	 * @param yearStart the yearStart to set
	 */
	public void setYearStart(int yearStart) {
		this.yearStart = yearStart;
	}
	/**
	 * @return the monthStart
	 */
	public int getMonthStart() {
		return monthStart;
	}
	/**
	 * @param monthStart the monthStart to set
	 */
	public void setMonthStart(int monthStart) {
		this.monthStart = monthStart;
	}
	/**
	 * @return the dayStart
	 */
	public int getDayStart() {
		return dayStart;
	}
	/**
	 * @param dayStart the dayStart to set
	 */
	public void setDayStart(int dayStart) {
		this.dayStart = dayStart;
	}
	/**
	 * @return the yearEnd
	 */
	public int getYearEnd() {
		return yearEnd;
	}
	/**
	 * @param yearEnd the yearEnd to set
	 */
	public void setYearEnd(int yearEnd) {
		this.yearEnd = yearEnd;
	}
	/**
	 * @return the monthEnd
	 */
	public int getMonthEnd() {
		return monthEnd;
	}
	/**
	 * @param monthEnd the monthEnd to set
	 */
	public void setMonthEnd(int monthEnd) {
		this.monthEnd = monthEnd;
	}
	/**
	 * @return the dayEnd
	 */
	public int getDayEnd() {
		return dayEnd;
	}
	/**
	 * @param dayEnd the dayEnd to set
	 */
	public void setDayEnd(int dayEnd) {
		this.dayEnd = dayEnd;
	}
	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	
	
	
}
	
