// Generated by delombok at Fri Jan 12 10:14:29 SGT 2018
package my.mimos.misos.domain.integrationservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageFormatResponseResource extends BaseResponseResource {
	@JsonProperty("MessageFormat")
	private MessageFormatResource messageFormat;

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public MessageFormatResource getMessageFormat() {
		return this.messageFormat;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setMessageFormat(final MessageFormatResource messageFormat) {
		this.messageFormat = messageFormat;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public java.lang.String toString() {
		return "MessageFormatResponseResource(messageFormat=" + this.getMessageFormat() + ")";
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof MessageFormatResponseResource)) return false;
		final MessageFormatResponseResource other = (MessageFormatResponseResource) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$messageFormat = this.getMessageFormat();
		final java.lang.Object other$messageFormat = other.getMessageFormat();
		if (this$messageFormat == null ? other$messageFormat != null : !this$messageFormat.equals(other$messageFormat)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof MessageFormatResponseResource;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $messageFormat = this.getMessageFormat();
		result = result * PRIME + ($messageFormat == null ? 43 : $messageFormat.hashCode());
		return result;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public MessageFormatResponseResource() {
	}
}
