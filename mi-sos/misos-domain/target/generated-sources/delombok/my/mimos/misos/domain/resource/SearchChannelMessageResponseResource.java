// Generated by delombok at Fri Jan 12 10:14:29 SGT 2018
/**
 * 
 */
package my.mimos.misos.domain.resource;

import java.util.List;

/**
 * @author krishna.redabotu
 */
public class SearchChannelMessageResponseResource extends BaseResponseResource {
	private List<SearchChannelMessageResource> channelMessageList;

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public List<SearchChannelMessageResource> getChannelMessageList() {
		return this.channelMessageList;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setChannelMessageList(final List<SearchChannelMessageResource> channelMessageList) {
		this.channelMessageList = channelMessageList;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public java.lang.String toString() {
		return "SearchChannelMessageResponseResource(channelMessageList=" + this.getChannelMessageList() + ")";
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof SearchChannelMessageResponseResource)) return false;
		final SearchChannelMessageResponseResource other = (SearchChannelMessageResponseResource) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$channelMessageList = this.getChannelMessageList();
		final java.lang.Object other$channelMessageList = other.getChannelMessageList();
		if (this$channelMessageList == null ? other$channelMessageList != null : !this$channelMessageList.equals(other$channelMessageList)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof SearchChannelMessageResponseResource;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $channelMessageList = this.getChannelMessageList();
		result = result * PRIME + ($channelMessageList == null ? 43 : $channelMessageList.hashCode());
		return result;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public SearchChannelMessageResponseResource() {
	}
}
