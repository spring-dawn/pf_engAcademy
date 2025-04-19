package portfolio.eams.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class CommonEntity {
    /*
    상속 받을 공통 컬럼
     */

    @Basic
    @Comment("생성자")
    @Column(updatable = false, length = 20, name = "CRT_ID")
    protected String createId;

    @Basic
    @Comment("생성일시")
    @Column(updatable = false, name = "CRT_DTM")
    protected LocalDateTime createDtm;

    @Basic
    @Comment("수정자")
    @Column(length = 20, name = "MOD_ID")
    protected String updateId;

    @Basic
    @Comment("수정일시")
    @Column(name = "MOD_DTM")
    protected LocalDateTime updateDtm;

    /*
     */
    @PrePersist
    protected void onPersist() {
        this.createDtm = this.updateDtm = LocalDateTime.now();
        this.createId = this.updateId = setSessionId();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateDtm = LocalDateTime.now();
        this.updateId = setSessionId();
    }


    /**
     * 데이터 생성/수정 시 세션 아이디 기입
     *
     * @return 세션이 없거나 익명 사용자인 경우 null
     */
    protected String setSessionId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return null;
        if (authentication.getPrincipal().toString().equals("anonymousUser")) return null;
        return authentication.getPrincipal().toString();
    }

}
