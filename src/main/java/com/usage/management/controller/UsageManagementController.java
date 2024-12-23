package com.usage.management.controller;

import com.usage.common.dto.ApiResponse;
import com.usage.common.dto.SystemPolicyDTO;
import com.usage.common.dto.SystemStatusDTO;
import com.usage.management.dto.UsageUpdateDTO;
import com.usage.management.service.UsageManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용량 관리 API", description = "사용량 데이터를 관리하는 API")
@RestController
@RequestMapping("/api/management/usage")
@RequiredArgsConstructor
public class UsageManagementController {
    private final UsageManagementService usageManagementService;

    @Operation(summary = "사용량 업데이트", description = "사용자의 사용량 데이터를 업데이트합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> updateUsage(@RequestBody UsageUpdateDTO usageData) {
        usageManagementService.updateUsage(usageData);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "시스템 상태 조회", description = "캐시, 큐, DB의 현재 상태를 조회합니다.")
    @GetMapping("/status")
    public ResponseEntity<ApiResponse<SystemStatusDTO>> getSystemStatus() {
        SystemStatusDTO status = usageManagementService.getSystemStatus();
        return ResponseEntity.ok(ApiResponse.success(status));
    }

    @Operation(summary = "시스템 정책 조회", description = "현재 적용된 시스템 정책을 조회합니다.")
    @GetMapping("/policy")
    public ResponseEntity<ApiResponse<SystemPolicyDTO>> getSystemPolicy() {
        SystemPolicyDTO policy = usageManagementService.getSystemPolicy();
        return ResponseEntity.ok(ApiResponse.success(policy));
    }

    @Operation(summary = "시스템 정책 업데이트", description = "시스템 정책을 업데이트합니다.")
    @PutMapping("/policy")
    public ResponseEntity<ApiResponse<Void>> updateSystemPolicy(@RequestBody SystemPolicyDTO policy) {
        usageManagementService.updateSystemPolicy(policy);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @Operation(summary = "Dead Letter Queue 수동 처리", description = "Dead Letter Queue의 메시지를 수동으로 재처리합니다.")
    @PostMapping("/dead-letter/process")
    public ResponseEntity<ApiResponse<Void>> processDeadLetterQueue() {
        usageManagementService.processDeadLetterQueue();
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
