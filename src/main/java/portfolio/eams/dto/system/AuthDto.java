package portfolio.eams.dto.system;


public record AuthDto(Long id, String menuUrl, Character accessType) {

    // insert, update
    public record Req(String menuUrl, Character accessType) {
    }
}
