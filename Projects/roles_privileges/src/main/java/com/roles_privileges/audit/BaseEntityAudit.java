package com.roles_privileges.audit;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntityAudit implements Serializable {

    @CreatedBy
    @Column(columnDefinition = "bigint default 1", updatable = false)
    private Long createdBy;

    @CreatedDate
    @Column(columnDefinition = "timestamp default '2020-04-10'", updatable = false)
    public Date createdAt;

    @LastModifiedBy
    @Column(columnDefinition = "bigint default 1")
    public Long updatedBy;

    @LastModifiedDate
    @Column(columnDefinition = "timestamp default '2020-04-10'")
    public Date updatedAt;


}
