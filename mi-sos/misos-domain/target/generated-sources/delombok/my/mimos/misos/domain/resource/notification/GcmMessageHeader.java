// Generated by delombok at Fri Jan 12 10:14:29 SGT 2018
/**
 * 
 */
package my.mimos.misos.domain.resource.notification;

/**
 * @author Shaiful Hisham Mat Jali
 */
public class GcmMessageHeader {
	private String to;
	private GcmNotificationMessage notification;

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public GcmMessageHeader() {
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getTo() {
		return this.to;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public GcmNotificationMessage getNotification() {
		return this.notification;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setTo(final String to) {
		this.to = to;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setNotification(final GcmNotificationMessage notification) {
		this.notification = notification;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof GcmMessageHeader)) return false;
		final GcmMessageHeader other = (GcmMessageHeader) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$to = this.getTo();
		final java.lang.Object other$to = other.getTo();
		if (this$to == null ? other$to != null : !this$to.equals(other$to)) return false;
		final java.lang.Object this$notification = this.getNotification();
		final java.lang.Object other$notification = other.getNotification();
		if (this$notification == null ? other$notification != null : !this$notification.equals(other$notification)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof GcmMessageHeader;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $to = this.getTo();
		result = result * PRIME + ($to == null ? 43 : $to.hashCode());
		final java.lang.Object $notification = this.getNotification();
		result = result * PRIME + ($notification == null ? 43 : $notification.hashCode());
		return result;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public java.lang.String toString() {
		return "GcmMessageHeader(to=" + this.getTo() + ", notification=" + this.getNotification() + ")";
	}
}
