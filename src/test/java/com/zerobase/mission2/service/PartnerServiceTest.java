package com.zerobase.mission2.service;

import com.zerobase.mission2.dto.PartnerDto;
import com.zerobase.mission2.dto.form.PartnerSignUpForm;
import com.zerobase.mission2.exception.CustomException;
import com.zerobase.mission2.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PartnerServiceTest {

    @Autowired
    private PartnerService partnerService;

    @Test
    void partnerSignUp() {
        String password = "qwer1234";
        PartnerDto partner = PartnerDto.builder()
                .id("zero123")
                .username("홍길동")
                .phone("010-0000-0000")
                .build();
        PartnerSignUpForm signUp = PartnerSignUpForm.builder()
                .partner(partner)
                .password(password)
                .build();

        assertEquals(partnerService.partnerSignUp(signUp), "파트너 회원가입에 성공했습니다");

        CustomException exception = assertThrows(CustomException.class,
                () -> partnerService.partnerSignUp(signUp));

        assertEquals(ErrorCode.ID_ALREADY_EXIST, exception.getErrorCode());
    }
}