package my.mimos.mdc.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import my.mimos.mdc.enums.StatusType; 
/**
 * 
 * @author beaula.fernandez
 *
 */

@ControllerAdvice
@RestController  
public class GlobalExceptionHandler {
	
	 	@ExceptionHandler
	    @ResponseBody
	 	@ResponseStatus(HttpStatus.BAD_REQUEST)
	    public ErrorResponseResource handleException(MethodArgumentNotValidException exception) {

	        String errorMsg = exception.getBindingResult().getFieldErrors().stream()
	                .map(DefaultMessageSourceResolvable::getDefaultMessage)
	                .findFirst()
	                .orElse(exception.getMessage());
	        
	        ErrorResponseResource response = new ErrorResponseResource();
	        response.setStatus(errorMsg);
	        response.setStatusCode("E400");
	        response.setStatusType(StatusType.ERROR);
	        exception.printStackTrace();
	        
	        return response;
	    }
	 	
	 	@ExceptionHandler(MultipartException.class)
	 	@ResponseBody
	    public ResponseEntity<ErrorResponseResource> handleException(MultipartException exception) {

	 		exception.printStackTrace();
	        String errorMsg = exception.getMessage();	        
	        ErrorResponseResource response = new ErrorResponseResource();
	        response.setStatus(errorMsg);
	        response.setStatusCode("E400");
	        response.setStatusType(StatusType.ERROR);
	        
	        return new ResponseEntity<ErrorResponseResource>(response, HttpStatus.BAD_REQUEST);

	    }
}
