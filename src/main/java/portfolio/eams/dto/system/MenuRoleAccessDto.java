package portfolio.eams.dto.system;

public record MenuRoleAccessDto(Long id, Long roleId, Long menuId, Character readYn, Character writeYn) {
}
