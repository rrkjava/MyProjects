// Generated by delombok at Fri Jan 12 10:14:29 SGT 2018
package my.mimos.misos.domain.integrationservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationTypeResponseResource extends BaseResponseResource {
	@JsonProperty("NotificationType")
	private NotificationTypeResource notificationType;

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public NotificationTypeResource getNotificationType() {
		return this.notificationType;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setNotificationType(final NotificationTypeResource notificationType) {
		this.notificationType = notificationType;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public java.lang.String toString() {
		return "NotificationTypeResponseResource(notificationType=" + this.getNotificationType() + ")";
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof NotificationTypeResponseResource)) return false;
		final NotificationTypeResponseResource other = (NotificationTypeResponseResource) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$notificationType = this.getNotificationType();
		final java.lang.Object other$notificationType = other.getNotificationType();
		if (this$notificationType == null ? other$notificationType != null : !this$notificationType.equals(other$notificationType)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof NotificationTypeResponseResource;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $notificationType = this.getNotificationType();
		result = result * PRIME + ($notificationType == null ? 43 : $notificationType.hashCode());
		return result;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public NotificationTypeResponseResource() {
	}
}
