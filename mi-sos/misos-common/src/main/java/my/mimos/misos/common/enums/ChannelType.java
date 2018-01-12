/**
 * 
 */
package my.mimos.misos.common.enums;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
public enum ChannelType {
	CH_SMS {
	    @Override public String toString() {return "CH_01";}
	  },
	CH_EMAIL {
	    @Override public String toString() {return "CH_02";}
	  },
	CH_FAX {
	    @Override public String toString() {return "CH_03";}
	  },
	CH_MOBILE_APP {
	    @Override public String toString() {return "CH_04";}
	  },
	CH_FACEBOOK {
	    @Override public String toString() {return "CH_05";}
	  },	
	CH_TWITTER {
	    @Override public String toString() {return "CH_06";}
	  },
	CH_PUBLIC_PORTAL {
	    @Override public String toString() {return "CH_07";}
	  },
	CH_MAP {
	    @Override public String toString() {return "CH_08";}
	  }, 
	CH_SIREN {
	    @Override public String toString() {return "CH_09";}
	  },
	CH_CAP {
		@Override public String toString() {return "CH_10";}
	  };
	
	public static String nameByChannelType(String type) {
		for (ChannelType channel : ChannelType.values()) {
			if (channel.toString().equals(type)) {
				return channel.name();
			}
		}
		return null;
	}
	
}
