package server.rest.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice // 모든 컨트롤러가 실행될때 반드시 해당 어노테이션을 갖고 있는 bean이 자동으로 실행 . 에러가 발생한다고 하면 핸들러에서 등록시켰던 에러 메소드가 실행이될 수 있다.
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // 어떠한 controller class가 실행된다고 하더라도 , 우리가 만든 ExceptionHandler controller가 실행이 된다.
    // 이 클래스 안에서 exception이 발생하게 된다면 해당 method가 실행된다.

    // ExceptionHanlder로 등록하기 위한 어노테이션 추가

    /**
     * controller에서 일반 Exception이 발생하면 실행되는 method
     */
    @ExceptionHandler(Exception.class) // 어떤 에러 클래스에 대한 처리를 할건지에 대해서(안에)정의 - > 일반 500번 오류에 대한 exception handler
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request){

        // 우리가 만들었던 error 형식, 일반 error 문에서는 백엔드 코드가 노출될 위험이 있기 때문에 필요한 에러 데이터만 반환하고자 한다.
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false)); // 부가적은 실명을 하지 않는다고 해서 false

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR); // 500번 error
    }


    /**
     * controller에서 userNotFoundException이 발생하면 이 method가 실행된다.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFountException(Exception ex, WebRequest request){

        // 우리가 만들었던 error 형식, 일반 error 문에서는 백엔드 코드가 노출될 위험이 있기 때문에 필요한 에러 데이터만 반환하고자 한다.
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND); // 404번 error
    }



}
