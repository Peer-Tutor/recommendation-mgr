package com.peertutor.TutorMgr.repository;

import com.peertutor.TutorMgr.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long>, JpaSpecificationExecutor<Tutor> {
    Tutor findByAccountName(String accountName);

    List<Tutor> findByAccountNameIn(List<String> accountName);
}

