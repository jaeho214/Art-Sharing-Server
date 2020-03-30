package kr.ac.skuniv.artsharing.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of="id", callSuper = false)
@Getter
public class JpaBasePersistable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "deleted", nullable = false, columnDefinition = "BIT default 0")
    protected Boolean deleted = false;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    protected LocalDateTime createdAt = null;

    @LastModifiedDate
    @Column(name = "last_modified_at", nullable = false)
    protected LocalDateTime lastModifiedAt = null;


    public void delete(){
        deleted = true;
    }
}
