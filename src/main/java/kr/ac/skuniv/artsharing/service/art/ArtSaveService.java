package kr.ac.skuniv.artsharing.service.art;

import kr.ac.skuniv.artsharing.domain.dto.art.ArtGetDto;
import kr.ac.skuniv.artsharing.domain.dto.art.ArtSaveDto;
import kr.ac.skuniv.artsharing.domain.entity.member.Member;
import kr.ac.skuniv.artsharing.repository.art.ArtRepository;
import kr.ac.skuniv.artsharing.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@Service
@RequiredArgsConstructor
public class ArtSaveService {

    private final CommonService commonService;
    private final ArtRepository artRepository;

    public ArtGetDto saveArt(Cookie cookie, ArtSaveDto artSaveDto){
        final Member member = commonService.getMemberByCookie(cookie);

        return ArtGetDto.of(artRepository.save(artSaveDto.of(member)));
    }

}
