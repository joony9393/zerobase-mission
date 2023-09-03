package com.zerobase.mission2.controller;

import com.zerobase.mission2.dto.PartnerIdDto;
import com.zerobase.mission2.dto.ReservationDto;
import com.zerobase.mission2.dto.StoreDto;
import com.zerobase.mission2.dto.StoreIdDto;
import com.zerobase.mission2.dto.form.LoginForm;
import com.zerobase.mission2.dto.form.PartnerSignUpForm;
import com.zerobase.mission2.dto.form.SignInForm;
import com.zerobase.mission2.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/partner")
public class PartnerController {

    private final PartnerService partnerService;

    // 파트너 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signUpPartner(@RequestBody PartnerSignUpForm form) {
        return ResponseEntity.ok(partnerService.partnerSignUp(form));
    }

    // 파트너 로그인
    @PostMapping("/signin")
    public ResponseEntity<LoginForm> signInPartner(@RequestBody SignInForm form) {
        return ResponseEntity.ok(partnerService.partnerSignIn(form));
    }

    // 파트너 매장 등록
    @PostMapping("/setStore")
    public ResponseEntity<String> setStore(@RequestBody StoreDto storeDto) {
        return ResponseEntity.ok(partnerService.setPartnerStore(storeDto));
    }

    // 매장 정보 수정
    @PutMapping("/store/update")
    public ResponseEntity<StoreDto> modifyStore(@RequestBody StoreDto storeDto) {
        return ResponseEntity.ok(partnerService.updateStore(storeDto));
    }

    // 파트너와 연결된 매장 목록
    @PostMapping("/stores")
    public ResponseEntity<List<StoreDto>> getStoreList(@RequestBody PartnerIdDto partnerId) {
        return ResponseEntity.ok(partnerService.showStoreList(partnerId.getPartnerId()));
    }

    // 예약 정보 확인
    @PostMapping("/reservation")
    public ResponseEntity<List<ReservationDto>> getStoreReservation(@RequestBody StoreIdDto storeId) {
        return ResponseEntity.ok(partnerService.getStoreReservation(storeId.getStoreId()));
    }
}
