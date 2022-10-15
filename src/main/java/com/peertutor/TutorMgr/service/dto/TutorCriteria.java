package com.peertutor.TutorMgr.service.dto;

import com.peertutor.TutorMgr.model.viewmodel.request.TutorProfileReq;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;


public class TutorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter displayName;

    private StringFilter accountName;

    private StringFilter subjects;

    private StringFilter introduction;

    private StringFilter certificates;

    public TutorCriteria() {
    }

    public TutorCriteria(Optional<String> displayName, Optional<String> subjects, Optional<String> introduction, Optional<String> certificates) {
        if (displayName.isPresent()) {
            this.displayName = new StringFilter();
            this.displayName.setContains(displayName.get());
        }

        if (subjects.isPresent()) {
            this.subjects = new StringFilter();
            this.subjects.setContains(subjects.get());
        }

        if (introduction.isPresent()) {
            this.introduction = new StringFilter();
            this.introduction.setContains(introduction.get());
        }

        if (certificates.isPresent()) {
            this.certificates = new StringFilter();
            this.certificates.setContains(certificates.get());
        }
    }

    public TutorCriteria(TutorProfileReq req) {
        if (req.displayName != null) {
            this.displayName = new StringFilter();
            this.displayName.setContains(req.displayName);
        }

        if (req.accountName != null) {
            this.accountName = new StringFilter();
            this.accountName.setEquals(req.accountName);
        }

        if (req.subjects != null) {
            this.subjects = new StringFilter();
            this.subjects.setContains(req.subjects);
        }

        if (req.introduction != null) {
            this.introduction = new StringFilter();
            this.introduction.setContains(req.introduction);
        }

        if (req.certificates != null) {
            this.certificates = new StringFilter();
            this.certificates.setContains(req.certificates);
        }
    }

    public TutorCriteria(TutorCriteria tutorCriteria) {

    }

    @Override
    public TutorCriteria copy() {
        return new TutorCriteria(this);
    }


    public StringFilter getDisplayName() {
        return displayName;
    }

    public void setDisplayName(StringFilter displayName) {
        this.displayName = displayName;
    }

    public StringFilter getAccountName() {
        return accountName;
    }

    public void setAccountName(StringFilter accountName) {
        this.accountName = accountName;
    }

    public StringFilter getSubjects() {
        return subjects;
    }

    public void setSubjects(StringFilter subjects) {
        this.subjects = subjects;
    }

    public StringFilter getIntroduction() {
        return introduction;
    }

    public void setIntroduction(StringFilter introduction) {
        this.introduction = introduction;
    }

    public StringFilter getCertificates() {
        return certificates;
    }

    public void setCertificates(StringFilter certificates) {
        this.certificates = certificates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TutorCriteria)) return false;
        TutorCriteria that = (TutorCriteria) o;
        return Objects.equals(getDisplayName(), that.getDisplayName()) && Objects.equals(getAccountName(), that.getAccountName()) && Objects.equals(getSubjects(), that.getSubjects()) && Objects.equals(getIntroduction(), that.getIntroduction()) && Objects.equals(getCertificates(), that.getCertificates());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDisplayName(), getAccountName(), getSubjects(), getIntroduction(), getCertificates());
    }
}
