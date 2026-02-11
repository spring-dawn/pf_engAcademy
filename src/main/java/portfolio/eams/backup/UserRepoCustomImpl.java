//package portfolio.eams.repo.system;
//
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Repository;
//import portfolio.eams.dto.system.UserDto;
//import portfolio.eams.entity.system.QUser;
//import portfolio.eams.entity.system.User;
//
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.springframework.util.StringUtils.hasText;
//
//@Slf4j
//@Repository
//@RequiredArgsConstructor
//public class UserRepoCustomImpl implements UserRepoCustom {
//    // Querydsl 선언
//    private final JPAQueryFactory factory;
//    QUser user = QUser.user;
//
//
//    @Override
//    public List<UserDto> selectList(UserDto.SearchReq req) {
//        return factory
//                .selectFrom(user)
//                .where(
//                        // and 조건 적용
//                        containUserId(req.getUserId())
//                        , containUserNm(req.getUserNm())
//                        , containEmail(req.getEmail())
//                        , eqUseYn(req.getUseYn())
//                )
//                .fetch()
//                .stream().map(User::toRes)
//                .sorted(Comparator.comparing(UserDto::getJoinYmd))
//                .collect(Collectors.toList());
//    }
//
//
//
//    /*
//    QueryDsl expressions 분리
//     */
//    private BooleanExpression containUserId(String userId) {
//        return hasText(userId) ? user.userId.contains(userId) : null;
//    }
//
//    private BooleanExpression containUserNm(String userNm) {
//        return hasText(userNm) ? user.userNm.contains(userNm) : null;
//    }
//
//    private BooleanExpression containEmail(String email) {
//        return hasText(email) ? user.email.contains(email) : null;
//    }
//
////    private BooleanExpression eqAdmYn(String admYn) {
////        return hasText(admYn) ? user.admYn.eq(admYn) : null;
////    }
//
//    private BooleanExpression eqUseYn(Character useYn) {
//        return hasText(String.valueOf(useYn)) ? user.useYn.eq(useYn) : null;
//    }
//
//}
