// Generated by delombok at Fri Jan 12 10:14:29 SGT 2018
package my.mimos.misos.domain.integrationservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhoneMobileResource {
	@JsonProperty("PhoneMobile")
	private String phoneMobile;

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getPhoneMobile() {
		return this.phoneMobile;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setPhoneMobile(final String phoneMobile) {
		this.phoneMobile = phoneMobile;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public java.lang.String toString() {
		return "PhoneMobileResource(phoneMobile=" + this.getPhoneMobile() + ")";
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof PhoneMobileResource)) return false;
		final PhoneMobileResource other = (PhoneMobileResource) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$phoneMobile = this.getPhoneMobile();
		final java.lang.Object other$phoneMobile = other.getPhoneMobile();
		if (this$phoneMobile == null ? other$phoneMobile != null : !this$phoneMobile.equals(other$phoneMobile)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof PhoneMobileResource;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $phoneMobile = this.getPhoneMobile();
		result = result * PRIME + ($phoneMobile == null ? 43 : $phoneMobile.hashCode());
		return result;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public PhoneMobileResource() {
	}
}
