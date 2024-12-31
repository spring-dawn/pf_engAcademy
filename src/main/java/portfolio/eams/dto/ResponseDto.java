package portfolio.eams.dto;

public record ResponseDto(String code, String msg, String time) {
    // Java 17 Record. 내용이 짧고 부분 사용 여지가 없는 dto 의 경우 이 클래스로 작성 시도.
}
