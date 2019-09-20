package kr.ac.skuniv.service;

import kr.ac.skuniv.domain.dto.ArtDto;
import kr.ac.skuniv.domain.entity.Art;
import kr.ac.skuniv.domain.roles.MemberRole;
import kr.ac.skuniv.exception.UserDefineException;
import kr.ac.skuniv.repository.ArtRepository;
import kr.ac.skuniv.security.JwtProvider;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArtService {

    final private ArtRepository artRepository;
    final private JwtProvider jwtProvider;


    public ArtService(ArtRepository artRepository, JwtProvider jwtProvider) {
        this.artRepository = artRepository;
        this.jwtProvider = jwtProvider;
    }

    private String token = "";

    //작품 등록
    public void registerArt(HttpServletRequest request, ArtDto artDto) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("user")){
                token = cookie.getValue();
            }
        }

        String userId = jwtProvider.getUserIdByToken(token);

        //권한 찾기
        String userRole = jwtProvider.getUserRoleByToken(token);

        if(userRole.equals(MemberRole.ARTIST)){
            artDto.setUserId(userId);
            Art art = artDto.toEntity();
            artRepository.save(art);
        }else{
            throw new UserDefineException("작품을 등록할 권한이 없습니다.");
        }
    }

    //작품 수정
    public void updateArt(HttpServletRequest request, ArtDto artDto) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("user")){
                token = cookie.getValue();
            }
        }
        String userId = jwtProvider.getUserIdByToken(token);
        //권한 찾기
        String userRole = jwtProvider.getUserRoleByToken(token);

        if(userRole.equals(MemberRole.ARTIST)){
            Art art = artRepository.findByMemberIdAndId(userId, artDto.getId());
            art.updateArt(artDto);
            artRepository.save(art);
        }else{
            throw new UserDefineException("작품을 수정할 권한이 없습니다.");
        }


    }

    //작품 삭제
    public void deleteArt(HttpServletRequest request, Long id) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("user")){
                token = cookie.getValue();
            }
        }
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
    public Page<ArtDto> getAllArts(int pageNum) {
        return artRepository.getAllArts(pageNum);
    }

    //작가의 작품 리스트 가져오기
    public Page<ArtDto> getArtsByUserId(HttpServletRequest request, int pageNum) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("user")){
                token = cookie.getValue();
            }
        }

        String userId = jwtProvider.getUserIdByToken(token);
        String userRole = jwtProvider.getUserRoleByToken(token);

        if(!userRole.equals(MemberRole.ARTIST)){
            throw new UserDefineException("작품은 작가만 등록할 수 있습니다.");
        }

        return artRepository.getArtsByUserId(pageNum,userId);
    }

    public Page<ArtDto> searchArt(String keyword, int pageNum) {
        return artRepository.searchArt(keyword,pageNum);
    }

    //작품을 눌렀을 때 상세보기
    public ArtDto getArtDetail(Long artNo) {
        ArtDto artDto = artRepository.getArtDetail(artNo);

        return artDto;
    }
}
