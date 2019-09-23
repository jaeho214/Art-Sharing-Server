package kr.ac.skuniv.service;

import kr.ac.skuniv.domain.dto.RentDto;
import kr.ac.skuniv.domain.entity.Art;
import kr.ac.skuniv.domain.entity.Member;
import kr.ac.skuniv.domain.entity.Rent;
import kr.ac.skuniv.repository.ArtRepository;
import kr.ac.skuniv.repository.MemberRepository;
import kr.ac.skuniv.repository.RentRepository;
import kr.ac.skuniv.security.JwtProvider;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RentService {

    private RentRepository rentRepository;
    private MemberRepository memberRepository;
    private ArtRepository artRepository;
    private JwtProvider jwtProvider;

    private List<RentDto> rentDtos = new ArrayList<>();

    private String token = null;

    public RentService(RentRepository rentRepository, MemberRepository memberRepository, ArtRepository artRepository, JwtProvider jwtProvider) {
        this.rentRepository = rentRepository;
        this.memberRepository = memberRepository;
        this.artRepository = artRepository;
        this.jwtProvider = jwtProvider;
    }

    public void clearList(){
        //중복된 코드를 없애기 위한 코드
        //rentDtos를 전역변수로 놓고 계속 비워주면서 작업할 수 있도록 비워주는 메소드 작성
        rentDtos.clear();
    }

    public void createRentHistory(HttpServletRequest request, RentDto rentDto) {

        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("user")){
                token = cookie.getValue();
            }
        }

        //회원 정보 찾기
        String userId = jwtProvider.getUserIdByToken(token);
        Member member = memberRepository.findById(userId);

        Art art = artRepository.findById(rentDto.getArtNo()).get();

        Rent rent = rentDto.toEntity(member, art);

        rentRepository.save(rent);
    }

    public List<RentDto> getArtRentHistory(Long artNo) {
        List<Rent> rentList = rentRepository.findByArt_Id(artNo);

        clearList();

        for(Rent rent : rentList){
            rentDtos.add(
                    RentDto.builder()
                        .artNo(rent.getArt().getId())
                        .rentDate(rent.getRentDate())
                        .returnDate(rent.getReturnDate())
                        .price(rent.getPrice())
                        .member(rent.getMember().getId())
                        .build()
            );
        }

        return rentDtos;
    }

    public List<RentDto> getMemberRentHistory(String userId) {
        List<Rent> rentList = rentRepository.findByMember_Id(userId);

        clearList();

        for(Rent rent : rentList){
            rentDtos.add(
                    RentDto.builder()
                            .artNo(rent.getArt().getId())
                            .rentDate(rent.getRentDate())
                            .returnDate(rent.getReturnDate())
                            .price(rent.getPrice())
                            .member(rent.getMember().getId())
                            .build()
            );
        }

        return rentDtos;
    }

    public void postRent(RentDto rentDto) {
        Member member = memberRepository.findById(rentDto.getMember());
        Optional<Art> art = artRepository.findById(rentDto.getArtNo());

        Rent rent = Rent.builder()
                .rentDate(rentDto.getRentDate())
                .returnDate(rentDto.getReturnDate())
                .price(rentDto.getPrice()) // 대여할때 하루당 가격에 기간 곱해서 프론트에서 request 해주길
                .art(art.get())
                .member(member)
                .build();

        rentRepository.save(rent);
    }
}
