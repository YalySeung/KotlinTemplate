package com.sprinboottemplate.kotlintemplate.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Sample 데이터 전송 객체")
data class SampleDto(

        @field:Schema(description = "ID", example = "1")
        var id: Long? = null,

        @field:Schema(description = "이름", example = "홍길동")
        var name: String,

        @field:Schema(description = "설명", example = "테스트 샘플입니다")
        var description: String
)