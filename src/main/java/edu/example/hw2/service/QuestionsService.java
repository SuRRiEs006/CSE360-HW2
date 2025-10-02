package edu.example.hw2.service;

import edu.example.hw2.domain.Question;
import edu.example.hw2.domain.Question.Status;
import edu.example.hw2.exceptions.NotFoundException;
import edu.example.hw2.repo.Answers;
import edu.example.hw2.repo.Questions;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static edu.example.hw2.service.Validation.requireRange;

public class QuestionsService {
    private final Questions questions;
    private final Answers answers;

    public QuestionsService(Questions questions, Answers answers) {
        this.questions = questions;
        this.answers = answers;
    }

    public Question create(String title, String body, String author, Set<String> tags) {
        requireRange("title", title, 1, 120);
        requireRange("body", body, 1, 2000);
        requireRange("author", author, 1, 200);
        return questions.create(title.trim(), body.trim(), author.trim(), tags);
    }

    public Optional<Question> get(UUID id) { return questions.get(id); }

    public Question update(UUID id, String title, String body) {
        if (title != null) requireRange("title", title, 1, 120);
        if (body != null) requireRange("body", body, 1, 2000);
        return questions.update(id, title == null ? null : title.trim(), body == null ? null : body.trim());
    }

    public void delete(UUID id) {
        // cascade delete answers
        answers.deleteByQuestion(id);
        questions.delete(id);
    }

    public List<Question> listAll() { return questions.listAll(); }

    public List<Question> search(String keyword, String statusStr, Set<String> tags) {
        Status status = null;
        if (statusStr != null && !statusStr.isBlank()) {
            try {
                status = Status.valueOf(statusStr.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new edu.example.hw2.exceptions.ValidationException("Invalid status: " + statusStr);
            }
        }
        return questions.search(keyword, status, tags);
    }

    public void setStatus(UUID id, Status s) {
        questions.setStatus(id, s);
    }
}
