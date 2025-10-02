package edu.example.hw2.service;

import edu.example.hw2.domain.Answer;
import edu.example.hw2.domain.Question;
import edu.example.hw2.domain.Question.Status;
import edu.example.hw2.exceptions.NotFoundException;
import edu.example.hw2.exceptions.ValidationException;
import edu.example.hw2.repo.Answers;
import edu.example.hw2.repo.Questions;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static edu.example.hw2.service.Validation.requireRange;

public class AnswersService {
    private final Questions questions;
    private final Answers answers;

    public AnswersService(Questions questions, Answers answers) {
        this.questions = questions;
        this.answers = answers;
    }

    public Answer create(UUID questionId, String body, String author) {
        if (questions.get(questionId).isEmpty()) {
            throw new ValidationException("Question does not exist: " + questionId);
        }
        requireRange("body", body, 1, 2000);
        requireRange("author", author, 1, 200);
        return answers.create(questionId, body.trim(), author.trim());
    }

    public List<Answer> listByQuestion(UUID qid) { return answers.listByQuestion(qid); }

    public Answer update(UUID id, String body) {
        if (body != null) requireRange("body", body, 1, 2000);
        return answers.update(id, body == null ? null : body.trim());
    }

    public void delete(UUID id) { answers.delete(id); }

    public void accept(UUID answerId) {
        Answer a = answers.get(answerId).orElseThrow(() -> new NotFoundException("Answer not found"));
        UUID qid = a.getQuestionId();
        answers.unacceptAll(qid);
        answers.setAccepted(answerId, true);
        questions.setStatus(qid, Status.ANSWERED);
    }
}
