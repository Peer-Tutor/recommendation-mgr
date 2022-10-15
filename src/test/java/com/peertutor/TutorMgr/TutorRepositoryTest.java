package com.peertutor.TutorMgr;

import com.peertutor.TutorMgr.model.Tutor;
import com.peertutor.TutorMgr.repository.TutorRepository;
import com.peertutor.TutorMgr.service.dto.TutorDTO;
import com.peertutor.TutorMgr.service.mapper.TutorMapper;
import com.peertutor.TutorMgr.util.dto.AccountDTO;
import com.peertutor.TutorMgr.util.mapper.AccountMapper;
import com.peertutor.TutorMgr.util.model.Account;
import com.peertutor.TutorMgr.util.model.enumeration.UserType;
import com.peertutor.TutorMgr.util.repository.AccountRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TutorRepositoryTest {

    private static final String USER_NAME_1 = "user_1";
    private static final String USER_PASSWORD_1 = "aaaaaa";
    private static final String USER_USERTYPE_1 = "TUTOR";
    private static final String USER_DISPLAY_NAME_1 = "tutor name 1";
    private static final String USER_INTRODUCTION_1 = "proficient in math";
    private static final String USER_SUBJECTS_1 = "Math;English";
    private static final String USER_CERTIFICATES_1 = "O-LEVEL";

    private static final String USER_NAME_2 = "user_2";
    private static final String USER_PASSWORD_2 = "bbbbb";
    private static final String USER_USERTYPE_2 = "TUTOR";
    private static final String USER_DISPLAY_NAME_2 = "tutor name 2";
    private static final String USER_INTRODUCTION_2 = "proficient in math";
    private static final String USER_SUBJECTS_2 = "Math;English";
    private static final String USER_CERTIFICATES_2 = "A-LEVEL";

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private TutorMapper tutorMapper;

    @BeforeAll
    public void injectData() {
        AccountDTO accountDTO = AccountDTO.builder()
                .name(USER_NAME_1)
                .password(USER_PASSWORD_1)
                .userType(UserType.valueOf(USER_USERTYPE_1))
                .build();
        Account account = accountMapper.toEntity(accountDTO);
        Account accountSaved = accountRepository.saveAndFlush(account);

        assertThat(account.equals(accountSaved));
    }

    @AfterAll
    public void cleanUp() {
        List<Account> accountSaved = accountRepository.findByNameIn(new ArrayList<String>(Arrays.asList(USER_NAME_1, USER_NAME_2)));
        accountSaved.stream().forEach((account) -> accountRepository.delete(account));
        accountRepository.flush();

        List<Tutor> tutorSaved = tutorRepository.findByAccountNameIn(new ArrayList<String>(Arrays.asList(USER_NAME_1, USER_NAME_2)));
        tutorSaved.stream().forEach((account) -> tutorRepository.delete(account));
        tutorRepository.flush();
    }

    @Test
    @Order(1)
    public void saveTutorSuccess() {
        TutorDTO tutorDTO = TutorDTO.builder()
                .accountName(USER_NAME_1)
                .displayName(USER_DISPLAY_NAME_1)
                .introduction(USER_INTRODUCTION_1)
                .subjects(USER_SUBJECTS_1)
                .certificates(USER_CERTIFICATES_1)
                .build();
        Tutor tutor = tutorMapper.toEntity(tutorDTO);
        Tutor tutorSaved = tutorRepository.saveAndFlush(tutor);

        assertThat(tutor.equals(tutorSaved));
    }

    @Test
    @Order(2)
    public void saveForeignKeyConstraintFail() {
        TutorDTO tutorDTO2 = TutorDTO.builder()
                .accountName(USER_NAME_2)
                .displayName(USER_DISPLAY_NAME_2)
                .introduction(USER_INTRODUCTION_1)
                .subjects(USER_SUBJECTS_1)
                .certificates(USER_CERTIFICATES_1)
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> {
            tutorRepository.saveAndFlush(tutorMapper.toEntity(tutorDTO2));
        });
    }

    @Test
    @Order(3)
    public void updateTutorSuccess() {
        TutorDTO tutorDTO = TutorDTO.builder()
                .accountName(USER_NAME_1)
                .displayName(USER_DISPLAY_NAME_1)
                .introduction(USER_INTRODUCTION_1)
                .subjects(USER_SUBJECTS_1)
                .certificates(USER_CERTIFICATES_1)
                .build();

        tutorRepository.saveAndFlush(tutorMapper.toEntity(tutorDTO));

        Tutor tutor = tutorRepository.findByAccountName(USER_NAME_1);
        tutor.setSubjects(USER_SUBJECTS_2);
        Tutor tutorSaved = tutorRepository.saveAndFlush(tutor);

        assertThat(tutor.equals(tutorSaved));
    }

    @Test
    @Order(4)
    public void viewTutorSuccess() {
        TutorDTO tutorDTO = TutorDTO.builder()
                .accountName(USER_NAME_1)
                .displayName(USER_DISPLAY_NAME_1)
                .introduction(USER_INTRODUCTION_1)
                .subjects(USER_SUBJECTS_1)
                .certificates(USER_CERTIFICATES_1)
                .build();
        Tutor tutor = tutorMapper.toEntity(tutorDTO);

        Tutor tutorSaved = tutorRepository.saveAndFlush(tutor);
        Tutor tutorRetrieved = tutorRepository.findByAccountName(USER_NAME_1);

        assertThat(tutor.equals(tutorRetrieved));
    }

    @Test
    @Order(5)
    public void deleteTutorSuccess() {
        TutorDTO tutorDTO = TutorDTO.builder()
                .accountName(USER_NAME_1)
                .displayName(USER_DISPLAY_NAME_1)
                .introduction(USER_INTRODUCTION_1)
                .subjects(USER_SUBJECTS_1)
                .certificates(USER_CERTIFICATES_1)
                .build();
        Tutor tutor = tutorMapper.toEntity(tutorDTO);

        Tutor tutorSaved = tutorRepository.saveAndFlush(tutor);
        Tutor tutorRetrieved = tutorRepository.findByAccountName(USER_NAME_1);
        tutorRepository.delete(tutorRetrieved);
        tutorRepository.flush();

        tutorRetrieved = tutorRepository.findByAccountName(USER_NAME_1);

        assertThat(tutorRetrieved == null);
    }
}
