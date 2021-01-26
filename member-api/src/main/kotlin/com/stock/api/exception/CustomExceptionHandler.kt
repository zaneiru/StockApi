package com.stock.api.exception

import com.stock.api.exception.enums.ErrorMessage
import org.apache.commons.collections.CollectionUtils
import org.springframework.beans.ConversionNotSupportedException
import org.springframework.beans.TypeMismatchException
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.transaction.TransactionSystemException
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.context.request.async.AsyncRequestTimeoutException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import javax.validation.ValidationException

@RestControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

//    @ExceptionHandler(value = [(StockException::class)])
//    fun handleApiException(ex: StockException, request: WebRequest): ResponseEntity<ApiError> {
//        val apiError = ApiError(
//            errorCode = ex.errorCode,
//            message = ex.message,
//            status = HttpStatus.BAD_GATEWAY
//        )
//        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(apiError)
//    }


    @ExceptionHandler(Exception::class)
    protected fun handleCustomException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        var status = HttpStatus.INTERNAL_SERVER_ERROR
        var errorCode: Int = ErrorMessage.ETC_UNKNOWN_ERROR.getErrorCode()
        val message = ex.message

        if (ex is StockException) {
            status = HttpStatus.BAD_REQUEST
            errorCode = (ex as StockException).errorCode
        } else if (ex is AccessDeniedException) {
            status = HttpStatus.FORBIDDEN
            errorCode = ErrorMessage.ETC_FORBIDDEN.getErrorCode()
        } else if (ex is DataAccessException) {
            status = HttpStatus.BAD_REQUEST
            errorCode = ErrorMessage.ETC_BAD_REQUEST.getErrorCode()
        } else if (ex is ConstraintViolationException) {
            return getConstraintViolationObjectResponseEntity(ex, request, ex)
        } else if (ex is TransactionSystemException) {
            val t = ex.rootCause
            if (t is ConstraintViolationException) {
                return getConstraintViolationObjectResponseEntity(ex, request, t)
            }
        } else if (ex is ClassCastException) {
            return handleClassCastException(ex, request)
        } else if (ex is ValidationException) {
            logger.warn(ex.javaClass.name + " : " + ex.message)
        } else if (logger.isWarnEnabled) {
            logger.warn("Unknown error type: " + ex.javaClass.name)
        }
        val apiError = message?.let { ApiError(errorCode, it, status) }
        return handleExceptionInternal(ex, apiError, HttpHeaders(), status, request)
    }

    private fun handleClassCastException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val status = HttpStatus.BAD_REQUEST
        val errorCode: Int = ErrorMessage.ETC_BAD_REQUEST.getErrorCode()
        val errorMessage: String = ErrorMessage.ETC_BAD_REQUEST.getMessage()
        val apiError = ApiError(errorCode, errorMessage, status)
        return handleExceptionInternal(ex, apiError, HttpHeaders(), status, request)
    }

    private fun getConstraintViolationObjectResponseEntity(
        ex: Exception, request: WebRequest,
        t: ConstraintViolationException
    ): ResponseEntity<Any> {
        val status = HttpStatus.BAD_REQUEST
        val errorMessage = t.constraintViolations.stream().findFirst().orElseThrow { RuntimeException() }.message
        try {
            val error: ErrorMessage = ErrorMessage.valueOf(errorMessage)
            val apiError = ApiError(error.getErrorCode(), error.getMessage(), status)
            return handleExceptionInternal(ex, apiError, HttpHeaders(), status, request)
        } catch (e: Exception) {
            // skip
        }
        val apiError = ApiError(ErrorMessage.ETC_DOMAIN_VALIDATION_ERROR.getErrorCode(), errorMessage, status)
        return handleExceptionInternal(ex, apiError, HttpHeaders(), status, request)
    }

    override fun handleExceptionInternal(ex: Exception, body: Any?, headers: HttpHeaders, status: HttpStatus, webRequest: WebRequest): ResponseEntity<Any> {
        return ResponseEntity(body, headers, status)
    }

    override fun handleHttpRequestMethodNotSupported(
        ex: HttpRequestMethodNotSupportedException,
        headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        pageNotFoundLogger.warn(ex.message)
        val supportedMethods = ex.supportedHttpMethods
        if (supportedMethods!!.isNotEmpty()) {
            headers.allow = supportedMethods
        }
        val apiError = ex.message?.let { ApiError(ErrorMessage.ETC_BAD_REQUEST.getErrorCode(), it, status) }
        return handleExceptionInternal(ex, apiError, headers, status, request)
    }

    override fun handleHttpMediaTypeNotSupported(
        ex: HttpMediaTypeNotSupportedException,
        headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val mediaTypes = ex.supportedMediaTypes
        if (!CollectionUtils.isEmpty(mediaTypes)) {
            headers.accept = mediaTypes
        }
        val apiError = ex.message?.let { ApiError(ErrorMessage.ETC_BAD_REQUEST.getErrorCode(), it, status) }
        return handleExceptionInternal(ex, apiError, headers, status, request)
    }

    override fun handleHttpMediaTypeNotAcceptable(
        ex: HttpMediaTypeNotAcceptableException,
        headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = ex.message?.let { ApiError(ErrorMessage.ETC_BAD_REQUEST.getErrorCode(), it, status) }
        return handleExceptionInternal(ex, apiError, headers, status, request)
    }

    override fun handleMissingPathVariable(
        ex: MissingPathVariableException,
        headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = ApiError(ErrorMessage.ETC_INTERNAL_ERROR.getErrorCode(), ex.message, status)
        return handleExceptionInternal(ex, apiError, headers, status, request)
    }

    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException,
        headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = ApiError(ErrorMessage.ETC_BAD_REQUEST.getErrorCode(), ex.message, status)
        return handleExceptionInternal(ex, apiError, headers, status, request)
    }

    override fun handleServletRequestBindingException(
        ex: ServletRequestBindingException,
        headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = ex.message?.let { ApiError(ErrorMessage.ETC_BAD_REQUEST.getErrorCode(), it, status) }
        return handleExceptionInternal(ex, apiError, headers, status, request)
    }

    override fun handleConversionNotSupported(
        ex: ConversionNotSupportedException,
        headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = ex.message?.let { ApiError(ErrorMessage.ETC_INTERNAL_ERROR.getErrorCode(), it, status) }
        return handleExceptionInternal(ex, apiError, headers, status, request)
    }

    override fun handleTypeMismatch(
        ex: TypeMismatchException, headers: HttpHeaders,
        status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = ex.message?.let { ApiError(ErrorMessage.ETC_BAD_REQUEST.getErrorCode(), it, status) }
        return handleExceptionInternal(ex, apiError, headers, status, request)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = ex.message?.let { ApiError(ErrorMessage.ETC_BAD_REQUEST.getErrorCode(), it, status) }
        return handleExceptionInternal(ex, apiError, headers, status, request)
    }

    override fun handleHttpMessageNotWritable(
        ex: HttpMessageNotWritableException,
        headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = ex.message?.let { ApiError(ErrorMessage.ETC_INTERNAL_ERROR.getErrorCode(), it, status) }
        return handleExceptionInternal(ex, apiError, headers, status, request)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        if (ex.bindingResult is BeanPropertyBindingResult) {
            val fieldError = ex.bindingResult.allErrors[0] as FieldError
            val constraintViolation = fieldError.unwrap(
                ConstraintViolation::class.java
            )
            val payloads = constraintViolation.constraintDescriptor.payload
            if (!CollectionUtils.isEmpty(payloads) && payloads.stream().findFirst()
                    .orElseThrow { RuntimeException() } == ErrorMessagePayload::class.java
            ) {
                val errorMessage: ErrorMessage = ErrorMessage.valueOf(constraintViolation.message)
                val apiError = ApiError(errorMessage.getErrorCode(), errorMessage.getMessage(), status)
                return handleExceptionInternal(ex, apiError, HttpHeaders(), status, request)
            }
        }
        val apiError = ApiError(ErrorMessage.ETC_BAD_REQUEST.getErrorCode(), ex.message, status)
        return handleExceptionInternal(ex, apiError, headers, status, request)
    }

    override fun handleMissingServletRequestPart(
        ex: MissingServletRequestPartException,
        headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = ex.message?.let { ApiError(ErrorMessage.ETC_BAD_REQUEST.getErrorCode(), it, status) }
        return handleExceptionInternal(ex, apiError, headers, status, request)
    }

    override fun handleBindException(
        ex: BindException, headers: HttpHeaders,
        status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val apiError =
            ApiError(ErrorMessage.ETC_BAD_REQUEST.getErrorCode(), ErrorMessage.ETC_BAD_REQUEST.getMessage(), status)
        return handleExceptionInternal(ex, apiError, headers, status, request)
    }

    override fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = ex.message?.let { ApiError(ErrorMessage.ETC_BAD_REQUEST.getErrorCode(), it, status) }
        return handleExceptionInternal(ex, apiError, headers, status, request)
    }

    override fun handleAsyncRequestTimeoutException(
        ex: AsyncRequestTimeoutException, headers: HttpHeaders, status: HttpStatus, webRequest: WebRequest
    ): ResponseEntity<Any>? {
        if (webRequest is ServletWebRequest) {
            val request = webRequest.getNativeRequest(HttpServletRequest::class.java)
            val response = webRequest.getNativeResponse(
                HttpServletResponse::class.java
            )
            if (response.isCommitted) {
                logger.error("Async timeout for " + request.method + " [" + request.requestURI + "]")
                return null
            }
        }
        val apiError = ex.message?.let { ApiError(ErrorMessage.ETC_INTERNAL_ERROR.getErrorCode(), it, status) }
        return handleExceptionInternal(ex, apiError, headers, status, webRequest)
    }
}
