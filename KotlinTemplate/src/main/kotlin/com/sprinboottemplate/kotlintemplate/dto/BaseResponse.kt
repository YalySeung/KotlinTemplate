package com.sprinboottemplate.kotlintemplate.dto

import com.sprinboottemplate.kotlintemplate.common.ApiResultCode
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "BaseResponse", description = "공통 API 응답 포맷")
data class BaseResponse<T>(
        @Schema(description = "응답 코드", example = "200")
        val code: Int,

        @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
        val message: String,

        @Schema(description = "응답 데이터")
        val data: T? = null
) {
    companion object {
        fun <T> success(data: T): BaseResponse<T> {
            return BaseResponse(200, "요청이 성공적으로 처리되었습니다.", data)
        }

        fun <T> success(message: String, data: T): BaseResponse<T> {
            return BaseResponse(200, message, data)
        }

        fun <T> error(code: Int, message: String): BaseResponse<T> {
            return BaseResponse(code, message, null)
        }

        fun <T> from(resultCode: ApiResultCode, data: T?): BaseResponse<T> {
            return BaseResponse(resultCode.code, resultCode.message, data)
        }

        fun <T> from(resultCode: ApiResultCode): BaseResponse<T> {
            return from(resultCode, null)
        }
    }
}