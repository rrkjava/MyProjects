// Generated by delombok at Fri Jan 12 10:14:29 SGT 2018
package my.mimos.misos.domain.channel;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 
 */
/**
 * @author krishna.redabotu
 */
public class PublicPortalHeaderRequest {
	@JsonProperty("DateTime")
	private String dateTime;
	@JsonProperty("RequestId")
	private String requestId;
	@JsonProperty("TransCode")
	private String transCode;

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public PublicPortalHeaderRequest() {
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getDateTime() {
		return this.dateTime;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getRequestId() {
		return this.requestId;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getTransCode() {
		return this.transCode;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setDateTime(final String dateTime) {
		this.dateTime = dateTime;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setRequestId(final String requestId) {
		this.requestId = requestId;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setTransCode(final String transCode) {
		this.transCode = transCode;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public java.lang.String toString() {
		return "PublicPortalHeaderRequest(dateTime=" + this.getDateTime() + ", requestId=" + this.getRequestId() + ", transCode=" + this.getTransCode() + ")";
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof PublicPortalHeaderRequest)) return false;
		final PublicPortalHeaderRequest other = (PublicPortalHeaderRequest) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$dateTime = this.getDateTime();
		final java.lang.Object other$dateTime = other.getDateTime();
		if (this$dateTime == null ? other$dateTime != null : !this$dateTime.equals(other$dateTime)) return false;
		final java.lang.Object this$requestId = this.getRequestId();
		final java.lang.Object other$requestId = other.getRequestId();
		if (this$requestId == null ? other$requestId != null : !this$requestId.equals(other$requestId)) return false;
		final java.lang.Object this$transCode = this.getTransCode();
		final java.lang.Object other$transCode = other.getTransCode();
		if (this$transCode == null ? other$transCode != null : !this$transCode.equals(other$transCode)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof PublicPortalHeaderRequest;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $dateTime = this.getDateTime();
		result = result * PRIME + ($dateTime == null ? 43 : $dateTime.hashCode());
		final java.lang.Object $requestId = this.getRequestId();
		result = result * PRIME + ($requestId == null ? 43 : $requestId.hashCode());
		final java.lang.Object $transCode = this.getTransCode();
		result = result * PRIME + ($transCode == null ? 43 : $transCode.hashCode());
		return result;
	}
}
