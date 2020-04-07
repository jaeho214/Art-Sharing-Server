package kr.ac.skuniv.artsharing.service.rent;

import kr.ac.skuniv.artsharing.domain.dto.rent.RentGetDto;
import kr.ac.skuniv.artsharing.domain.dto.rent.RentSaveDto;
import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.domain.entity.rent.Rent;
import kr.ac.skuniv.artsharing.repository.art.ArtRepository;
import kr.ac.skuniv.artsharing.repository.rent.RentRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.Cookie;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class RentSaveServiceTest {

    @InjectMocks private RentSaveService rentSaveService;
    @Mock private CommonService commonService;
    @Mock private RentRepository rentRepository;
    @Mock private ArtRepository artRepository;


    private Member memberFixture = new EasyRandom().nextObject(Member.class);
    private Art artFixture = new EasyRandom().nextObject(Art.class);
    private Rent rentFixture = new EasyRandom().nextObject(Rent.class);
    private Cookie cookie = new Cookie("user", "token");
    private RentSaveDto rentSaveDtoFixture = new EasyRandom().nextObject(RentSaveDto.class);

    @Test
    void saveRent() {
        //given
        given(commonService.getMemberByCookie(any())).willReturn(memberFixture);
        given(artRepository.findById(anyLong())).willReturn(Optional.ofNullable(artFixture));
        given(rentRepository.save(any(Rent.class))).willReturn(rentFixture);

        //when
        RentGetDto savedRent = rentSaveService.saveRent(cookie, rentSaveDtoFixture, anyLong());

        //then
        assertThat(savedRent.getArt_id()).isEqualTo(rentFixture.getArt().getId());
        assertThat(savedRent.getPrice()).isEqualTo(rentFixture.getPrice());
    }

    @Test
    void returnArt() {
        //given
        given(commonService.getMemberByCookie(any())).willReturn(memberFixture);
        given(rentRepository.findById(anyLong())).willReturn(Optional.ofNullable(rentFixture));

        //when
        RentGetDto rent = rentSaveService.returnArt(cookie, 1L);

        //then
        assertThat(rent.getArt_id()).isEqualTo(rentFixture.getArt().getId());
        assertThat(rent.getPrice()).isEqualTo(rentFixture.getPrice());
    }
}