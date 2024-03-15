package com.jpa_audit.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAudit {

    @CreatedBy
    @Column(columnDefinition = "bigint default 1", updatable = false)
    protected Long createdBy;

    @CreatedDate
    @Column(columnDefinition = "timestamp default '2020-04-10'", updatable = false)
    protected Date createdAt;

    @LastModifiedBy
    @Column(columnDefinition = "bigint default 1")
    protected Long updatedBy;

    @LastModifiedDate
    @Column(columnDefinition = "timestamp default '2020-04-10'")
    protected Date updatedAt;

}
