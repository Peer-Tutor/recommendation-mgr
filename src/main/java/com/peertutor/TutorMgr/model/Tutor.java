package com.peertutor.TutorMgr.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tutor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_name", nullable = false, unique = true)
    private String accountName;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "subjects")
    private String subjects;

    @Column(name = "certificates")
    private String certificates;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getCertificates() {
        return certificates;
    }

    public void setCertificates(String certificates) {
        this.certificates = certificates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Tutor account = (Tutor) o;
        if (account.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), account.getId());
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Tutor{" +
                "id=" + id +
                ", accountName='" + accountName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", subjects='" + subjects + '\'' +
                ", certificates='" + certificates + '\'' +
                '}';
    }
}