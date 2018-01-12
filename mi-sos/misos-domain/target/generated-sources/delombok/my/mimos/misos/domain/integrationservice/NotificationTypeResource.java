// Generated by delombok at Fri Jan 12 10:14:29 SGT 2018
package my.mimos.misos.domain.integrationservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationTypeResource {
	@JsonProperty("Status")
	private String status;
	@JsonProperty("NotificationType")
	private String notificationType;
	@JsonProperty("NotificationTypeCode")
	private String notificationTypeCode;

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getStatus() {
		return this.status;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getNotificationType() {
		return this.notificationType;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getNotificationTypeCode() {
		return this.notificationTypeCode;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setStatus(final String status) {
		this.status = status;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setNotificationType(final String notificationType) {
		this.notificationType = notificationType;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setNotificationTypeCode(final String notificationTypeCode) {
		this.notificationTypeCode = notificationTypeCode;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public java.lang.String toString() {
		return "NotificationTypeResource(status=" + this.getStatus() + ", notificationType=" + this.getNotificationType() + ", notificationTypeCode=" + this.getNotificationTypeCode() + ")";
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof NotificationTypeResource)) return false;
		final NotificationTypeResource other = (NotificationTypeResource) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$status = this.getStatus();
		final java.lang.Object other$status = other.getStatus();
		if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
		final java.lang.Object this$notificationType = this.getNotificationType();
		final java.lang.Object other$notificationType = other.getNotificationType();
		if (this$notificationType == null ? other$notificationType != null : !this$notificationType.equals(other$notificationType)) return false;
		final java.lang.Object this$notificationTypeCode = this.getNotificationTypeCode();
		final java.lang.Object other$notificationTypeCode = other.getNotificationTypeCode();
		if (this$notificationTypeCode == null ? other$notificationTypeCode != null : !this$notificationTypeCode.equals(other$notificationTypeCode)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof NotificationTypeResource;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $status = this.getStatus();
		result = result * PRIME + ($status == null ? 43 : $status.hashCode());
		final java.lang.Object $notificationType = this.getNotificationType();
		result = result * PRIME + ($notificationType == null ? 43 : $notificationType.hashCode());
		final java.lang.Object $notificationTypeCode = this.getNotificationTypeCode();
		result = result * PRIME + ($notificationTypeCode == null ? 43 : $notificationTypeCode.hashCode());
		return result;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public NotificationTypeResource() {
	}
}