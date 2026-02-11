package portfolio.eams.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
    // 조회 제외하고 NoArgsConstructor 필요.
    // DTO 는 클라이언트 노출 데이터.

    // 일반 조회
    private Long id;
    private String userId;
    private String userNm;
    private String nickNm;
    private String tel;
    private String email;
    private Character useYn;
    private Character admYn;
    private Character localYn;
    private LocalDate joinYmd;
    private LocalDate quitYmd;
    private Long roleId;
    private String roleNm;


    // 사용자(직원) 등록
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InsertReq {
        private String userId;
        private String userNm;
        private String userPw;
        private String nickNm;
        private String tel;
        private String email;
        private String joinYmd;
        private Long roleId; // 실제 save 때 role 객체로 변환
    }


    // update
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateReq {
        private Long id;
        private String userNm;
        private String nickNm;
        private Character useYn;
        private String tel;
        private String email;
        private String joinYmd;
        private Long roleId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdatePwReq {
        private Long id;
        private String userPw;
        private String pwConfirm;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuitReq {
        private Long id;
        private String quitYmd;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchReq {
        private Long id;
        private String userId;
        private String userNm;
        private String nickNm;
        private Character localYn;
        private Character useYn;
        private Character admYn;
        private String tel;
        private String email;
        private LocalDate joinYmd;
        private LocalDate quitYmd;
    }
}
