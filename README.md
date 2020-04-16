# 아트쉐어링
예술품 공유 서비스 서버

## 서비스 소개
예술품을 대여 및 관리해주는 웹 서비스

## 구현사항

###Back end

**spring boot(REST API)**
1. Spring Data JPA를 통한 DB 접근 및 관리
2. 주 저장소로 AWS RDS(MySQL) 사용
3. QueryDSL를 통해 쿼리를 메소드화하여 쿼리의 오류를 컴파일 시점에서 처리
4. AOP를 통하여 exception이 발생할 때와 request이 들어올 때를 log로 기록
5. Swagger를 사용하여 API 문서 자동화
6. Spring security와 jwt 방식을 사용하여 토큰을 통한 사용자 인증 구현



리팩토링 후 변경사항
1. 기존에 이미지 파일을 DB에 저장하였는데 AWS S3 저장소로 변경
2. 예외 처리 구체화
3. 임시 변수 및 중복 코드 제거



