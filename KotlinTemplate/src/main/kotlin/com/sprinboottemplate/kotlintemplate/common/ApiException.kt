package com.sprinboottemplate.kotlintemplate.common

import org.springframework.http.HttpStatus

class ApiException(
        val resultCode: ApiResultCode,
        override val message: String = resultCode.message
) : RuntimeException(message) {

    fun getHttpStatus(): HttpStatus {
        return resultCode.httpStatus
    }
}