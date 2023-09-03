package com.zerobase.mission2.service;

import com.zerobase.mission2.dto.form.UserSignUpForm;
import com.zerobase.mission2.exception.CustomException;
import com.zerobase.mission2.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UesrServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void userSignup() {
        String password = "qwer1234";
        UserSignUpForm signUp = UserSignUpForm.builder()
                .id("zero123")
                .username("홍길동")
                .password(password)
                .phone("010-0000-0000")
                .build();

        assertEquals(userService.userSignUp(signUp), "유저 회원가입에 성공했습니다");

        CustomException exception = assertThrows(CustomException.class,
                () -> userService.userSignUp(signUp));

        assertEquals(ErrorCode.ID_ALREADY_EXIST, exception.getErrorCode());
    }
}