// Generated by delombok at Fri Jan 12 10:14:29 SGT 2018
/**
 * 
 */
package my.mimos.misos.domain.integrationservice;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author krishna.redabotu
 */
public class SocialAccountRequestResource extends BaseRequestResource {
	@JsonProperty("Type")
	private String type;

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getType() {
		return this.type;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setType(final String type) {
		this.type = type;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public java.lang.String toString() {
		return "SocialAccountRequestResource(type=" + this.getType() + ")";
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof SocialAccountRequestResource)) return false;
		final SocialAccountRequestResource other = (SocialAccountRequestResource) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$type = this.getType();
		final java.lang.Object other$type = other.getType();
		if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof SocialAccountRequestResource;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $type = this.getType();
		result = result * PRIME + ($type == null ? 43 : $type.hashCode());
		return result;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public SocialAccountRequestResource() {
	}
}
