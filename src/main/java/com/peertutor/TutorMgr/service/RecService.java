package com.peertutor.TutorMgr.service;

import com.peertutor.TutorMgr.model.Tutor;
import com.peertutor.TutorMgr.repository.TutorRepository;
import com.peertutor.TutorMgr.service.dto.TutorDTO;
import com.peertutor.TutorMgr.service.mapper.TutorMapper;
import com.peertutor.TutorMgr.util.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.personalizeruntime.PersonalizeRuntimeClient;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsRequest;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsResponse;
import software.amazon.awssdk.services.personalizeruntime.model.PredictedItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecService {
    private static final Logger logger = LoggerFactory.getLogger(RecService.class);
    @Autowired
    private final TutorMapper tutorMapper;
    @Autowired
    AppConfig appConfig;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private TutorQueryService tutorQueryService;

    public RecService(TutorRepository tutorRepository, TutorMapper tutorMapper) {
        this.tutorRepository = tutorRepository;
        this.tutorMapper = tutorMapper;
    }

    public List<TutorDTO> getTutorRec(Long id) {
        Boolean useAwsPersonalize = Boolean.valueOf(appConfig.getRecommendationMgr().get("useAwsPersonalize"));
        List<Tutor> result = null;

        if (useAwsPersonalize) {
            result = this.getTutorRecAws(id);
        } else {
            result = this.getTutorRecDummy();
        }

        return tutorMapper.toDto(result).subList(0, Math.min(result.size(), 3));
    }

    public List<Tutor> getTutorRecDummy() {
        Page<Tutor> page = tutorRepository.findAll(
                PageRequest.of(0, 3));
        return page.getContent();
    }

    public List<Tutor> getTutorRecAws(Long id) {
        String recommenderArn = appConfig.getRecommendationMgr().get("recommenderArn");
        String userId = id.toString();
        Region region = Region.AP_SOUTHEAST_1;

        List<Tutor> tutors = new ArrayList<>();

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                appConfig.getRecommendationMgr().get("awsAccessKey"),
                appConfig.getRecommendationMgr().get("awsSecretAccessKey"));

        PersonalizeRuntimeClient personalizeRuntimeClient = PersonalizeRuntimeClient.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();

        try {
            GetRecommendationsRequest recommendationsRequest = GetRecommendationsRequest.builder()
                    .campaignArn(recommenderArn)
                    .numResults(20)
                    .userId(userId)
                    .build();

            GetRecommendationsResponse recommendationsResponse =
                    personalizeRuntimeClient.getRecommendations(recommendationsRequest);
            List<PredictedItem> items = recommendationsResponse.itemList();

            for (PredictedItem item : items) {
                Long tutorId = Long.parseLong(item.itemId());
                Optional<Tutor> tutor = tutorRepository.findById(tutorId);

                if (tutor.isPresent()) {
                    tutors.add(tutor.get());
                }
            }
        } catch (AwsServiceException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }

        personalizeRuntimeClient.close();

        return tutors;
    }
}
