package kr.ac.skuniv.artsharing.service.art;

import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtUpdateDto;
import kr.ac.skuniv.artsharing.domain.entity.art.Art;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.exception.art.ArtNotFoundException;
import kr.ac.skuniv.artsharing.repository.art.ArtRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

@Service
@RequiredArgsConstructor
@Transactional
public class ArtUpdateService {

    private final CommonService commonService;
    private final ArtRepository artRepository;

    public ArtGetDto updateArt(Cookie cookie, ArtUpdateDto artUpdateDto){
        Member member = commonService.getMemberByCookie(cookie);

        Art art = artRepository.findById(artUpdateDto.getId())
                .orElseThrow(ArtNotFoundException::new);

        commonService.checkAuthority(member.getUserId(), art.getMember().getUserId());

        art.updateArt(artUpdateDto);

        return ArtGetDto.of(art);

    }

}
