package com.lec.spring.base.controller;

import com.lec.spring.base.domain.EmailMessage;
import com.lec.spring.base.service.ResetPasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class ResetPasswordController {

    private final ResetPasswordService resetPasswordService;

    public ResetPasswordController(ResetPasswordService resetPasswordService) {
        this.resetPasswordService = resetPasswordService;
    }

    // 비밀번호 재설정 이메일 발송
    @PostMapping("/send-reset-email")
    public ResponseEntity<String> sendResetEmail(@RequestBody EmailMessage emailMessage) {
        try {
            // 이메일 존재 여부 체크
            resetPasswordService.isExistEmail(emailMessage.getTo());

            // 이메일 발송
            String result = resetPasswordService.sendEmail(emailMessage);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 비밀번호 재설정
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam Long id,
                                                @RequestParam String newPassword,
                                                @RequestParam String code,
                                                @RequestParam String email) {
        try {
            // 인증 코드 검증
            resetPasswordService.verifyAuthorizationCode(code, email);

            // 비밀번호 변경
            boolean isUpdated = resetPasswordService.updatePassword(id, newPassword);
            if (isUpdated) {
                return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
            } else {
                return ResponseEntity.status(500).body("비밀번호 변경에 실패했습니다.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
