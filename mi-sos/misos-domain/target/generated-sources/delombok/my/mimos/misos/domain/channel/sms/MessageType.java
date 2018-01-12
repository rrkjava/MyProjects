// Generated by delombok at Fri Jan 12 10:14:29 SGT 2018
/**
 * 
 */
package my.mimos.misos.domain.channel.sms;

import java.io.Serializable;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

/**
 * @author Shaiful Hisham Mat Jali
 */
@XStreamConverter(value = ToAttributedValueConverter.class, strings = {"message"})
public class MessageType implements Serializable {
	@XStreamAlias("Type")
	private String type;
	private String message;

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public MessageType() {
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getType() {
		return this.type;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getMessage() {
		return this.message;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setType(final String type) {
		this.type = type;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setMessage(final String message) {
		this.message = message;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof MessageType)) return false;
		final MessageType other = (MessageType) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$type = this.getType();
		final java.lang.Object other$type = other.getType();
		if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
		final java.lang.Object this$message = this.getMessage();
		final java.lang.Object other$message = other.getMessage();
		if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof MessageType;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $type = this.getType();
		result = result * PRIME + ($type == null ? 43 : $type.hashCode());
		final java.lang.Object $message = this.getMessage();
		result = result * PRIME + ($message == null ? 43 : $message.hashCode());
		return result;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public java.lang.String toString() {
		return "MessageType(type=" + this.getType() + ", message=" + this.getMessage() + ")";
	}
}