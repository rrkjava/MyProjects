package my.mimos.misos.admin.misos_admin.vo;


public class AuthResponseResource {

	private Result result;
	private String code;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result  result) {
		this.result = result;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
