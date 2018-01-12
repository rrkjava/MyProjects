// Generated by delombok at Fri Jan 12 10:14:29 SGT 2018
/**
 * 
 */
package my.mimos.misos.domain.geo;

import java.util.List;

/**
 * @author Shaiful Hisham Mat Jali
 */
public class Polygon {
	private List<Coordinate> boundary;

	public Polygon(List<Coordinate> boundary) {
		this.boundary = boundary;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public List<Coordinate> getBoundary() {
		return this.boundary;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setBoundary(final List<Coordinate> boundary) {
		this.boundary = boundary;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof Polygon)) return false;
		final Polygon other = (Polygon) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$boundary = this.getBoundary();
		final java.lang.Object other$boundary = other.getBoundary();
		if (this$boundary == null ? other$boundary != null : !this$boundary.equals(other$boundary)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof Polygon;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $boundary = this.getBoundary();
		result = result * PRIME + ($boundary == null ? 43 : $boundary.hashCode());
		return result;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public java.lang.String toString() {
		return "Polygon(boundary=" + this.getBoundary() + ")";
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public Polygon() {
	}
}