package org.example.hall_university_menu.service;

import org.example.hall_university_menu.model.Feedback;
import org.example.hall_university_menu.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    @Autowired private FeedbackRepository feedbackRepo;

    public Feedback saveFeedback(Feedback feedback){
        return feedbackRepo.save(feedback);
    }
}
