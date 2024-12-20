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
    /*
    Builder 패턴 사용하는 경우 NoArgsConstructor 필요
     */

    // 일반 조회
    private Long id;
    private String userId;
    private String userNm;
    private Character useYn;
    private Character admYn;
    private String tel;
    private String email;
    private LocalDate joinYmd;
    private LocalDate quitYmd;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InsertReq {
        private String userId;
        private String userNm;
        private String userPw;
        private String tel;
        private String email;
    }


    // update
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateReq {
        private String userNm;
        private Character useYn;
        private Character admYn;
        private String tel;
        private String email;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdatePwReq {
        private Long id;
        private String userPw;
        private String pwConfirm;
        private LocalDate pwModYmd;
    }

    @Data
    @AllArgsConstructor
    public static class DeleteReq {
        private Long id;
        private String userPw;
        private String pwConfirm;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchReq {
        private Long id;
        private String userId;
        private String userNm;
        private Character useYn;
        private Character admYn;
        private String tel;
        private String email;
        private LocalDate joinYmd;
        private LocalDate quitYmd;
    }
}
