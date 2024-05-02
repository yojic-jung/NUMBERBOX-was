//package com.numberbox.common.handler;
//
//import lombok.val;
//import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.MissingServletRequestParameterException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    public ResponseEntity<Object> handlingModelValidException(MethodArgumentNotValidException ex): ResponseEntity<CustomResTmpl<ExceptionMsgTmpl>> {
//        BasicErrorController
//        // 클라이언트에 전달할 메시지(@Valid에 적용된 message)
//        var clientMsg = ex.bindingResult.fieldError?.defaultMessage ?: "입력양식이 올바르지 않습니다."
//        if (ex.bindingResult.fieldError?.isBindingFailure == true) clientMsg = "요청 형식이 올바르지 않습니다."
//        // 예외 메시지 템플릿 작성
//        val exCode = ExceptionCode.INVALID_OBJECT_PROPERTY
//        val exInfo = ExceptionMsgTmpl.makeExceptionMsg(exCode)
//        // 예외 로그 찍기
//        println("errInfo : $exInfo \nerrDesc : $ex")
//        // 응답 객체 전달
//        val resBody = CustomResTmpl<ExceptionMsgTmpl>(showMsgAlert = true, message = clientMsg, data = exInfo)
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resBody)
//    }
//
//    @ExceptionHandler(value = [MissingServletRequestParameterException::class])
//    fun handlingMissingRequestParameterException(ex:MissingServletRequestParameterException): ResponseEntity<CustomResTmpl<ExceptionMsgTmpl>> {
//        // 클라이언트에 전달할 메시지(@Valid에 적용된 message)
//        val clientMsg = "유효한 요청이 아닙니다."
//        // 예외 메시지 템플릿 작성
//        val exCode = ExceptionCode.MISSING_REQUEST_PARAM
//        val exInfo = ExceptionMsgTmpl.makeExceptionMsg(exCode)
//        // 예외 로그 찍기
//        println("errInfo : $exInfo \nerrDesc : $ex")
//        // 응답 객체 전달
//        val resBody = CustomResTmpl<ExceptionMsgTmpl>(showMsgAlert = true, message = clientMsg, data = exInfo)
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resBody)
//    }
//
//    @ExceptionHandler(value = [MethodArgumentTypeMismatchException::class])
//    fun handlingMethodArgumentTypeMismatchException(ex:MethodArgumentTypeMismatchException): ResponseEntity<CustomResTmpl<ExceptionMsgTmpl>> {
//        // 클라이언트에 전달할 메시지(@Valid에 적용된 message)
//        val clientMsg = "유효한 요청이 아닙니다."
//        // 예외 메시지 템플릿 작성
//        val exCode = ExceptionCode.PARAM_TYPE_MISS_MATCH
//        val exInfo = ExceptionMsgTmpl.makeExceptionMsg(exCode)
//        // 예외 로그 찍기
//        println("errInfo : $exInfo \nerrDesc : $ex")
//        // 응답 객체 전달
//        val resBody = CustomResTmpl<ExceptionMsgTmpl>(showMsgAlert = true, message = clientMsg, data = exInfo)
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resBody)
//    }
//
//    @ExceptionHandler(value = [BusinessRuleInvalidException::class])
//    fun handlingBusinessException(ex: BusinessRuleInvalidException): ResponseEntity<CustomResTmpl<ExceptionMsgTmpl>> {
//        // 클라이언트에 전달할 메시지(@Valid에 적용된 message)
//        val clientMsg = ex.msg
//        // 예외 메시지 템플릿 작성
//        val exInfo = ExceptionMsgTmpl.makeExceptionMsg(ExceptionCode.BUSINESS_RULE_INVALID)
//        // 예외 로그 찍기
//        println("errInfo : $exInfo \nerrDesc : $ex")
//        // 응답 객체 전달
//        val resBody = CustomResTmpl<ExceptionMsgTmpl>(showMsgAlert = false, message = clientMsg, data = exInfo)
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resBody)
//    }
//}
