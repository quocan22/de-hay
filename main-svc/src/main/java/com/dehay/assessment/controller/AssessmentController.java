package com.dehay.assessment.controller;

import com.dehay.assessment.entity.Assessment;
import com.dehay.assessment.repository.AssessmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    private final AssessmentRepository assessmentRepository;

    public AssessmentController(AssessmentRepository assessmentRepository) {
        this.assessmentRepository = assessmentRepository;
    }

    @GetMapping
    public List<Assessment> getAllAssessments() {
        return assessmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assessment> getAssessment(@PathVariable UUID id) {
        return assessmentRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Assessment createAssessment(@RequestBody Assessment assessment) {
        return assessmentRepository.save(assessment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assessment> updateAssessment(@PathVariable UUID id, @RequestBody Assessment assessment) {
        return assessmentRepository.findById(id).map(existing -> {
            existing.setTitle(assessment.getTitle());
            existing.setDescription(assessment.getDescription());
            existing.setDurationMinutes(assessment.getDurationMinutes());
            existing.setPassingScore(assessment.getPassingScore());
            existing.setStatus(assessment.getStatus());

            return ResponseEntity.ok(assessmentRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable UUID id) {
        if (!assessmentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        assessmentRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
