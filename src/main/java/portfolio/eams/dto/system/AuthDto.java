package portfolio.eams.dto.system;

import lombok.Data;
import portfolio.eams.entity.system.Menu;

public record AuthDto(Long id, Character type, Menu menu) {
}
