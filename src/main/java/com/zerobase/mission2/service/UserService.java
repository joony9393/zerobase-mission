package com.zerobase.mission2.service;

import com.zerobase.mission2.domain.Reservation;
import com.zerobase.mission2.domain.Review;
import com.zerobase.mission2.domain.Store;
import com.zerobase.mission2.domain.Uesr;
import com.zerobase.mission2.dto.*;
import com.zerobase.mission2.dto.form.LoginForm;
import com.zerobase.mission2.dto.form.SignInForm;
import com.zerobase.mission2.dto.form.UserSignUpForm;
import com.zerobase.mission2.exception.CustomException;
import com.zerobase.mission2.repository.ReservationRepository;
import com.zerobase.mission2.repository.ReviewRepository;
import com.zerobase.mission2.repository.StoreRepository;
import com.zerobase.mission2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.zerobase.mission2.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;

    // 유저 회원가입
    public String userSignUp(UserSignUpForm form) {
        if (userRepository.findById(form.getId()).isPresent()) {
            throw new CustomException(ID_ALREADY_EXIST);
        } else {
            Uesr uesr = Uesr.builder()
                    .phone(form.getPhone())
                    .id(form.getId())
                    .password(form.getPassword())
                    .username(form.getUsername())
                    .build();
            userRepository.save(uesr);
            log.info(uesr.toString());
            return "유저 회원가입에 성공했습니다";
        }
    }

    // 유저 로그인
    public LoginForm userSignIn(SignInForm form){
        Uesr uesr = userRepository.findById(form.getId()).orElseThrow(() -> new CustomException(ID_UNMATCHED));
        if(!uesr.getPassword().equals(form.getPassword())){
            throw new CustomException(PASSWORD_UNMATCHED);
        }
        return LoginForm.builder()
                .id(uesr.getId())
                .loginTime(LocalDateTime.now())
                .build();
    }

    // 앱에서 가게정보를 보여주기
    public List<StoreDto> showStoreList() {
        return StoreDto.fromEntityList(storeRepository.findAll());
    }

    // 세부 가게정보 보여주기
    public StoreDetailDto showStoreDetail(Long storeId) {
        StoreDto store = StoreDto.fromEntity(storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND)));
        List<ReservationDto> reservations = ReservationDto.fromEntityList(reservationRepository.findByStoreId(storeId));
        List<ReviewDto> reviews = ReviewDto.fromEntityList(reviewRepository.findByStoreId(storeId));
        return StoreDetailDto.builder()
                .store(store)
                .reservations(reservations)
                .reviews(reviews)
                .build();
    }

    // 가게정보 검색하기
    public StoreDetailDto searchStore(String name) {
        return showStoreDetail(storeRepository.findByName(name)
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND)).getId());
    }

    // 리뷰 작성하기
    public String setReview(ReviewDto review){
        Uesr uesr = userRepository.findByUsername(review.getUserName()).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Store store = storeRepository.findByName(review.getStoreName()).orElseThrow(() -> new CustomException(STORE_NOT_FOUND));
        reviewRepository.save(Review.builder()
                        .uesr(uesr)
                        .store(store)
                        .comment(review.getComment())
                        .rating(review.getRating())
                .build());
        return "리뷰를 작성하였습니다.";
    }

    // 예약하기
    @Transactional
    public String reserve(ReserveRequestDto reserveInfo){
        // 해당 가게의 예약정보 가져오고 정렬
        List<ReservationDto> reservations = ReservationDto.fromEntityList(reservationRepository.findByStoreId(reserveInfo.getStoreId()));
        Collections.sort(reservations, Comparator.comparing(ReservationDto::getReservationTime));

        LocalDateTime now = LocalDateTime.now();

        // 가게정보
        Store store = storeRepository.findById(reserveInfo.getStoreId()).orElseThrow(() -> new CustomException(STORE_NOT_FOUND));
        LocalDateTime local_time = reserveInfo.getTime();

        // 유저정보
        Uesr uesr = userRepository.findByUsername(reserveInfo.getUserName())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        String date = local_time.format(DateTimeFormatter.ISO_LOCAL_DATE);
        int time = local_time.getHour();

        // 해당 날짜, 시간, 테이블에 이미 예약이 진행되었는지 확인
        for(ReservationDto reservation : reservations){
            String checkDate = reservation.getReservationTime().format(DateTimeFormatter.ISO_LOCAL_DATE);
            int checkTime = reservation.getReservationTime().getHour();
            if(checkDate.equals(date) && checkTime==time && Objects.equals(reservation.getTableNum(), reserveInfo.getTableNum())){
                return "해당 테이블은 이미 예약이 되어있는 상태입니다.";
            }else if(reservation.getReservationTime().isBefore(now)){ // 지난 예약은 지우기
                reservationRepository.deleteById(reservation.getReservationId());
            }
        }

        // 없으면 저장
        reservationRepository.save(Reservation.builder()
                        .uesr(uesr)
                        .store(store)
                        .reservationTime(local_time)
                        .tableNum(reserveInfo.getTableNum())
                        .build());

        return "예약이 완료되었습니다.";
    }

    // 예약 확인
    public String confirmVisit(Long reservationId){
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));
        LocalDateTime now = LocalDateTime.now();

        if (reservation.getReservationTime().minusMinutes(10).isBefore(now)) {
            reservationRepository.deleteById(reservation.getId());
            return "방문이 확인되었습니다.";
        } else {
            return "예약 시간 10분 전에 도착해야 방문을 확인할 수 있습니다.";
        }
    }

//    public void customerVerify(String email, String code){
//        signUpCustomerService.verifyEmail(email, code);
//    }
//
//    public String customerSignUp(SignUpForm form){
//        if(signUpCustomerService.isEmailExist(form.getEmail())){
//            // exception
//            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
//        }else{
//            Customer c = signUpCustomerService.signUp(form);
//
//            String code = getRandomCode();
//            SendMailForm sendMailForm = SendMailForm.builder()
//                    .from("tester@dannymytester.com")
//                    .to(form.getEmail())
//                    .subject("Verification Email!")
//                    .text(getVerificationEmailBody(form.getEmail(), form.getName(), "customer", code))
//                    .build();
//            mailgunClient.sendEmail(sendMailForm);
//            signUpCustomerService.changeCustomerValidateEmail(c.getId(), code);
//            return "회원 가입에 성공하였습니다.";
//        }
//    }

}
