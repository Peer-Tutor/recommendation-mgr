package com.peertutor.TutorMgr.service.dto;

import com.peertutor.TutorMgr.model.Tutor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.peertutor.TutorMgr.model.Tutor} entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TutorDTO implements Serializable {

    private Long id;

    private String accountName;

    private String displayName;

    private String introduction;

    private String subjects;

    private String certificates;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountName;
    }

    public void setAccountId(String accountName) {
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

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tutor {" +
                "id=" + id +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
