package com.sprinboottemplate.kotlintemplate.contoller

import com.sprinboottemplate.kotlintemplate.common.ApiException
import com.sprinboottemplate.kotlintemplate.common.ApiResultCode
import com.sprinboottemplate.kotlintemplate.dto.BaseResponse
import com.sprinboottemplate.kotlintemplate.dto.SampleDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Sample API", description = "샘플 CRUD API 입니다")
@RestController
@RequestMapping("/samples")
internal class SampleController {
    private val database: MutableMap<Long, SampleDto> = HashMap()
    private var idSequence: Long = 1L
    @Operation(summary = "ID로 조회", description = "PathVariable을 사용해 샘플 조회")
    @GetMapping("/{id}")
    fun getSampleById(
            @Parameter(description = "샘플 ID", example = "1") @PathVariable id: Long
    ): ResponseEntity<BaseResponse<SampleDto>> {
        val sample = database[id]
                ?: throw ApiException(ApiResultCode.NOT_FOUND, "샘플 ID가 존재하지 않습니다.")

        return ResponseEntity.ok(BaseResponse.from(ApiResultCode.SUCCESS, sample))
    }

    @Operation(summary = "이름으로 조회", description = "RequestParam을 사용해 샘플 조회")
    @GetMapping
    fun getSampleByName(
            @Parameter(description = "이름", example = "홍길동") @RequestParam name: String
    ): ResponseEntity<BaseResponse<SampleDto>> {
        val sample = database.values.firstOrNull { it.name.equals(name, ignoreCase = true) }
                ?: throw ApiException(ApiResultCode.NOT_FOUND, "해당 이름의 샘플이 없습니다.")

        return ResponseEntity.ok(BaseResponse.from(ApiResultCode.SUCCESS, sample))
    }

    @Operation(summary = "샘플 생성", description = "샘플 데이터를 생성")
    @PostMapping
    fun createSample(@RequestBody request: SampleDto): ResponseEntity<BaseResponse<SampleDto>> {
        request.id = idSequence++
        database[request.id!!] = request

        return ResponseEntity
                .status(ApiResultCode.CREATED.httpStatus)
                .body(BaseResponse.from(ApiResultCode.CREATED, request))
    }

    @Operation(summary = "샘플 수정", description = "샘플 데이터를 수정")
    @PutMapping("/{id}")
    fun updateSample(
            @Parameter(description = "샘플 ID", example = "1") @PathVariable id: Long,
            @RequestBody request: SampleDto
    ): ResponseEntity<BaseResponse<SampleDto>> {
        if (!database.containsKey(id)) {
            throw ApiException(ApiResultCode.NOT_FOUND, "수정할 샘플이 존재하지 않습니다.")
        }

        request.id = id
        database[id] = request
        return ResponseEntity.ok(BaseResponse.from(ApiResultCode.SUCCESS, request))
    }

    @Operation(summary = "샘플 삭제", description = "샘플 데이터를 삭제")
    @DeleteMapping("/{id}")
    fun deleteSample(
            @Parameter(description = "샘플 ID", example = "1") @PathVariable id: Long
    ): ResponseEntity<BaseResponse<Void>> {
        if (!database.containsKey(id)) {
            throw ApiException(ApiResultCode.NOT_FOUND, "삭제할 샘플이 존재하지 않습니다.")
        }

        database.remove(id)
        return ResponseEntity.ok(BaseResponse.from(ApiResultCode.SUCCESS))
    }
}