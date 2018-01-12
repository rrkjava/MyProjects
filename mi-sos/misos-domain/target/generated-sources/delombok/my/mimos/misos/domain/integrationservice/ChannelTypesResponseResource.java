// Generated by delombok at Fri Jan 12 10:14:29 SGT 2018
package my.mimos.misos.domain.integrationservice;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChannelTypesResponseResource extends BaseResponseResource {
	@JsonProperty("ListTargetChannelType")
	private List<ChannelTypeResource> channelTypes;

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public List<ChannelTypeResource> getChannelTypes() {
		return this.channelTypes;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setChannelTypes(final List<ChannelTypeResource> channelTypes) {
		this.channelTypes = channelTypes;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public java.lang.String toString() {
		return "ChannelTypesResponseResource(channelTypes=" + this.getChannelTypes() + ")";
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof ChannelTypesResponseResource)) return false;
		final ChannelTypesResponseResource other = (ChannelTypesResponseResource) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$channelTypes = this.getChannelTypes();
		final java.lang.Object other$channelTypes = other.getChannelTypes();
		if (this$channelTypes == null ? other$channelTypes != null : !this$channelTypes.equals(other$channelTypes)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof ChannelTypesResponseResource;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $channelTypes = this.getChannelTypes();
		result = result * PRIME + ($channelTypes == null ? 43 : $channelTypes.hashCode());
		return result;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public ChannelTypesResponseResource() {
	}
}
