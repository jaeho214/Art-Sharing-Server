package kr.ac.skuniv.service;

import kr.ac.skuniv.domain.dto.ArtRequestDto;
import kr.ac.skuniv.domain.entity.Art;
import kr.ac.skuniv.domain.entity.Member;
import kr.ac.skuniv.repository.ArtRepository;
import kr.ac.skuniv.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtService {

    final private ArtRepository artRepository;
    final private MemberRepository memberRepository;

    public ArtService(ArtRepository artRepository, MemberRepository memberRepository) {
        this.artRepository = artRepository;
        this.memberRepository = memberRepository;
    }

    //작품 등록
    public void registerArt(ArtRequestDto request) {

        Art art = request.toEntity();

        //작가 찾기
        Member member = memberRepository.findById(request.getUserId());

        //작가 정보 같이 등록
        art.setMember(member);

        artRepository.save(art);

    }

    //작품 수정
    public void updateArt(ArtRequestDto request) {

        Art art = artRepository.findById(request.getId()).get();

        art.updateArt(request);

        artRepository.save(art);

    }

    //작품 삭제
    public void deleteArt(Long id) {

        Art art = artRepository.findById(id).get();

        artRepository.delete(art);

    }

    //작품 리스트 가져오기
    public List<ArtRequestDto> artList(String memberId) {

        List<Art> art = artRepository.findByMemberId(memberId);

        List<ArtRequestDto> artRequest = new ArrayList<ArtRequestDto>();

        for(int i=0; i<art.size(); i++) {

            Art findArt = art.get(i);
            ArtRequestDto findArtDto = new ArtRequestDto();
            findArtDto.addArt(findArt);
            artRequest.add(i, findArtDto);

        }
        return artRequest;
    }

}
