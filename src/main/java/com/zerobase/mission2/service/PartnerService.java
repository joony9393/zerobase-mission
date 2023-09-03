package com.zerobase.mission2.service;

import com.zerobase.mission2.domain.Partner;
import com.zerobase.mission2.domain.Store;
import com.zerobase.mission2.dto.ReservationDto;
import com.zerobase.mission2.dto.StoreDto;
import com.zerobase.mission2.dto.form.LoginForm;
import com.zerobase.mission2.dto.form.PartnerSignUpForm;
import com.zerobase.mission2.dto.form.SignInForm;
import com.zerobase.mission2.exception.CustomException;
import com.zerobase.mission2.repository.PartnerRepository;
import com.zerobase.mission2.repository.ReservationRepository;
import com.zerobase.mission2.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.zerobase.mission2.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartnerService {

    private final PartnerRepository partnerRepository;
    private final StoreRepository storeRepository;
    private final ReservationRepository reservationRepository;

    // 파트너 회원가입
    public String partnerSignUp(PartnerSignUpForm form) {

        if (partnerRepository.findById(form.getPartner().getId()).isPresent()) {
            throw new CustomException(ID_ALREADY_EXIST);
        } else {
            Partner partner = Partner.builder()
                    .phone(form.getPartner().getPhone())
                    .id(form.getPartner().getId())
                    .password(form.getPassword())
                    .username(form.getPartner().getUsername())
                    .build();
            partnerRepository.save(partner);
            log.info(partner.toString());

            return "파트너 회원가입에 성공했습니다";
        }
    }

    // 파트너 로그인
    public LoginForm partnerSignIn(SignInForm form) {
        Partner partner = partnerRepository.findById(form.getId()).orElseThrow(() -> new CustomException(ID_UNMATCHED));
        if (!partner.getPassword().equals(form.getPassword())) {
            throw new CustomException(PASSWORD_UNMATCHED);
        }
        return LoginForm.builder()
                .id(partner.getId())
                .loginTime(LocalDateTime.now())
                .build();
    }

    // 파트너 매장 등록
    public String setPartnerStore(StoreDto storeDto) {
        if (storeRepository.findByName(storeDto.getName()).isPresent()) {
            throw new CustomException(STORE_ALREADY_EXIST);
        }
        Partner partner = partnerRepository.findById(storeDto.getPartnerId())
                .orElseThrow(() -> new CustomException(PARTNER_NOT_FOUND));
        Store store = Store.builder()
                .name(storeDto.getName())
                .location(storeDto.getLocation())
                .description(storeDto.getDescription())
                .partner(partner)
                .tableNum(storeDto.getTableNum())
                .build();
        storeRepository.save(store);
        return "매장 등록을 완료했습니다.";
    }

    // 파트너와 연결된 매장 목록 보여주기
    public List<StoreDto> showStoreList(String partnerId) {
        return StoreDto.fromEntityList(storeRepository.findByPartnerId(partnerId));
    }

    // 예약 정보 확인
    public List<ReservationDto> getStoreReservation(Long storeId) {
        return ReservationDto.fromEntityList(reservationRepository.findByStoreId(storeId));
    }

    // 매장 정보 수정
    public StoreDto updateStore(StoreDto storeDto) {
        Store store = storeRepository.findById(storeDto.getId())
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));
        Partner partner = partnerRepository.findById(storeDto.getPartnerId())
                .orElseThrow(() -> new CustomException(PARTNER_NOT_FOUND));
        Store updateStore = Store.builder()
                .id(storeDto.getId())
                .name(storeDto.getName())
                .location(storeDto.getLocation())
                .description(storeDto.getDescription())
                .partner(partner)
                .tableNum(storeDto.getTableNum())
                .build();
        storeRepository.save(updateStore);
        return StoreDto.fromEntity(updateStore);
    }


//    public void sellerVerify(String email, String code){
//        sellerService.verifyEmail(email, code);
//    }
//
//    public String sellerSignUp(SignUpForm form){
//        if(sellerService.isEmailExist(form.getEmail())){
//            // exception
//            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
//        }else{
//            Seller s = sellerService.signUp(form);
//
//            String code = getRandomCode();
//            SendMailForm sendMailForm = SendMailForm.builder()
//                    .from("tester@dannymytester.com")
//                    .to(form.getEmail())
//                    .subject("Verification Email!")
//                    .text(getVerificationEmailBody(form.getEmail(), form.getName(), "seller", code))
//                    .build();
//            mailgunClient.sendEmail(sendMailForm);
//            sellerService.changeSellerValidateEmail(s.getId(), code);
//            return "회원 가입에 성공하였습니다.";
//        }
//    }

}
