package kr.ac.skuniv.service;

import kr.ac.skuniv.domain.dto.ArtRequestDto;
import kr.ac.skuniv.domain.entity.Art;
import kr.ac.skuniv.domain.roles.MemberRole;
import kr.ac.skuniv.exception.UserDefineException;
import kr.ac.skuniv.repository.ArtRepository;
import kr.ac.skuniv.repository.MemberRepository;
import kr.ac.skuniv.security.JwtProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtService {

    final private ArtRepository artRepository;
    final private MemberRepository memberRepository;
    final private JwtProvider jwtProvider;

    public ArtService(ArtRepository artRepository, MemberRepository memberRepository, JwtProvider jwtProvider) {
        this.artRepository = artRepository;
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    //작품 등록
    public void registerArt(String token, ArtRequestDto request) {
        String userId = jwtProvider.getUserIdByToken(token);

        //권한 찾기
        String userRole = jwtProvider.getUserRoleByToken(token);

        if(!userRole.equals(MemberRole.ARTIST)){
            request.setUserId(userId);
            Art art = request.toEntity();
            artRepository.save(art);
        }else{
            throw new UserDefineException("작품을 등록할 권한이 없습니다.");
        }
    }

    //작품 수정
    public void updateArt(String token, ArtRequestDto request) {
        String userId = jwtProvider.getUserIdByToken(token);
        //권한 찾기
        String userRole = jwtProvider.getUserRoleByToken(token);

        if(!userRole.equals(MemberRole.ARTIST)){
            Art art = artRepository.findByMemberIdAndId(userId, request.getId());
            art.updateArt(request);
            artRepository.save(art);
        }else{
            throw new UserDefineException("작품을 수정할 권한이 없습니다.");
        }


    }

    //작품 삭제
    public void deleteArt(String token, Long id) {
        String userId = jwtProvider.getUserIdByToken(token);
        //권한 찾기
        String userRole = jwtProvider.getUserRoleByToken(token);

        if(!userRole.equals(MemberRole.ARTIST)){
            Art art = artRepository.findByMemberIdAndId(userId, id);
            artRepository.delete(art);

        }else{
            throw new UserDefineException("작품을 삭제할 권한이 없습니다.");
        }


    }
    //TODO : 페이징처리 해줘야 되는 부분
    //작품 리스트 가져오기
    public List<ArtRequestDto> artList(String token) {
        String userId = jwtProvider.getUserIdByToken(token);

        List<Art> artList =  artRepository.findAllByMemberId(userId);

        List<ArtRequestDto> artRequest = new ArrayList<>();

        for (Art art : artList) {
            artRequest.add(
                ArtRequestDto.builder()
                        .artName(art.getArtName())
                        .date(art.getDate())
                        .price(art.getPrice())
                        .explanation(art.getExplanation())
                        .build()
            );
        }

        return artRequest;

//
//        for(int i=0; i<art.size(); i++) {
//
//            Art findArt = art.get(i);
//            ArtRequestDto findArtDto = new ArtRequestDto();
//            findArtDto.addArt(findArt);
//            artRequest.add(i, findArtDto);
//
//        }
//        return artRequest;
    }

    public List<ArtRequestDto> searchArt(String keyword) {
        return artRepository.searchArt(keyword);
    }
}
