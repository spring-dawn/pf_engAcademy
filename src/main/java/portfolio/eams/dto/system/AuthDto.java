package portfolio.eams.dto.system;


public record AuthDto(Long id, Character type, String menuUrl) {

    // insert, update
    public record Req(Character type, String menuUrl) {
    }
}
