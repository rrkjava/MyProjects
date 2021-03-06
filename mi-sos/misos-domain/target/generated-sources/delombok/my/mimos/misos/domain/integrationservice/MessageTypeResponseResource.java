// Generated by delombok at Fri Jan 12 10:14:29 SGT 2018
package my.mimos.misos.domain.integrationservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageTypeResponseResource extends BaseResponseResource {
	@JsonProperty("MessageType")
	private MessageTypeResource messageType;

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public MessageTypeResource getMessageType() {
		return this.messageType;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setMessageType(final MessageTypeResource messageType) {
		this.messageType = messageType;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public java.lang.String toString() {
		return "MessageTypeResponseResource(messageType=" + this.getMessageType() + ")";
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof MessageTypeResponseResource)) return false;
		final MessageTypeResponseResource other = (MessageTypeResponseResource) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$messageType = this.getMessageType();
		final java.lang.Object other$messageType = other.getMessageType();
		if (this$messageType == null ? other$messageType != null : !this$messageType.equals(other$messageType)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof MessageTypeResponseResource;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $messageType = this.getMessageType();
		result = result * PRIME + ($messageType == null ? 43 : $messageType.hashCode());
		return result;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public MessageTypeResponseResource() {
	}
}
