package com.peertutor.TutorMgr.controller;

import com.peertutor.TutorMgr.model.viewmodel.response.TutorProfileRes;
import com.peertutor.TutorMgr.repository.TutorRepository;
import com.peertutor.TutorMgr.service.AuthService;
import com.peertutor.TutorMgr.service.RecService;
import com.peertutor.TutorMgr.service.dto.TutorDTO;
import com.peertutor.TutorMgr.util.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.personalizeruntime.PersonalizeRuntimeClient;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsRequest;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsResponse;
import software.amazon.awssdk.services.personalizeruntime.model.PredictedItem;

import java.util.List;

@Controller
@RequestMapping(path = "/rec-mgr")
public class RecController {
    @Autowired
    AppConfig appConfig;
    @Autowired
    private TutorRepository tutorRepository;// = new CustomerRepository();
    @Autowired
    private RecService recService;
    @Autowired
    private AuthService authService;

    @GetMapping(path = "/health")
    public @ResponseBody String healthCheck() {
        return "Ok 2";
    }

    @GetMapping(path = "/rec")
    public @ResponseBody ResponseEntity<List<TutorDTO>> getTutorProfile(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "sessionToken") String sessionToken,
            @RequestParam(name = "id") Long id
    ) {
        boolean result = authService.getAuthentication(name, sessionToken);
        if (!result) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        List<TutorDTO> tutors = recService.getTutorRec(id);

        return ResponseEntity.ok().body(tutors);
    }
}
