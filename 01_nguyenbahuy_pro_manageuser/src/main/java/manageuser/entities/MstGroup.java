/**
 * copyright(C) 2022 Luvina JSC
 * MstGroup.java May 3, 2022 nguyenbahuy
 */
package manageuser.entities;

/**
 * @author nguyenbahuy
 *	lớp javabean tương ứng với bảng mst_group
 */
public class MstGroup {
	private int ID;
	private String groupName;
	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}
	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
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
}
