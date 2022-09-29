/**
 * copyright(C) 2022 Luvina JSC
 * MstJapan.java May 3, 2022 nguyenbahuy
 */
package manageuser.entities;

/**
 * @author nguyenbahuy
 * lớp javabean tương ứng với bảng mst_japan
 */
public class MstJapan {
	private String codeLevel;
	private String nameLevel;
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
	
}
