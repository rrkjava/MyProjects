package my.mimos.misos.admin.misos_admin.vo;

public class AdUser {

	private String givenName;
	private String displayName;
	private String misosRoles;
	private String mobileRoles;
	private String isActivated;
	
	public String getGivenName() {
		return givenName;
	}
	public String getMobileRoles() {
		return mobileRoles;
	}
	public void setMobileRoles(String mobileRoles) {
		this.mobileRoles = mobileRoles;
	}
	public String getIsActivated() {
		return isActivated;
	}
	public void setIsActivated(String isActivated) {
		this.isActivated = isActivated;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getMisosRoles() {
		return misosRoles;
	}
	public void setMisosRoles(String misosRoles) {
		this.misosRoles = misosRoles;
	}
}
