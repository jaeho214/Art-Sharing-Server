package kr.ac.skuniv.service;

import kr.ac.skuniv.domain.dto.RentalHistoryDto;
import kr.ac.skuniv.domain.entity.RentalHistory;
import kr.ac.skuniv.repository.RentalHistoryRepository;
import kr.ac.skuniv.security.JwtProvider;
import org.springframework.stereotype.Service;

@Service
public class RentalHistoryService {

    final private RentalHistoryRepository rentalHistoryRepository;
    final private JwtProvider jwtProvider;

    public RentalHistoryService(RentalHistoryRepository rentalHistoryRepository,JwtProvider jwtProvider) {
        this.rentalHistoryRepository = rentalHistoryRepository;
        this.jwtProvider = jwtProvider;
    }

    //대여 상태 확인 역할로 구분?
    public void rentalHistoryCheck(String token, RentalHistoryDto request) {

        String userId = jwtProvider.getUserIdByToken(token);

        //대여 진행 중이 아니라면 token 구분은 어떻게 해야하나?
        //소비자 / 작가 둘다 필요 새로운 정보를 입력해야한다.
        if(request.isHistoryCheck() == false) {
            request.setHistoryCheck(true);
            request.setUserId(token);
            request.setArtistId(token);

            RentalHistory rentalHistory = request.toEntity();
            rentalHistoryRepository.save(rentalHistory);
        } else {
            //대여가 끝난 상태라면 새로 등록 해야 한다.
        }



    }
}
