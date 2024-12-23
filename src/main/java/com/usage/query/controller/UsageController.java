package com.usage.query.controller;

import com.usage.common.dto.ApiResponse;
import com.usage.common.dto.UsageDTO;
import com.usage.query.service.UsageQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용량 조회 API", description = "사용자의 사용량을 조회하는 API")
@RestController
@RequestMapping("/api/usage")
@RequiredArgsConstructor
public class UsageController {
    private final UsageQueryService usageQueryService;

    @Operation(summary = "사용량 조회", description = "사용자의 음성/영상/문자/데이터 사용량을 조회합니다.")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UsageDTO>> getUserUsage(@PathVariable String userId) {
        UsageDTO usage = usageQueryService.getUserUsage(userId);
        return ResponseEntity.ok(ApiResponse.success(usage));
    }
}
