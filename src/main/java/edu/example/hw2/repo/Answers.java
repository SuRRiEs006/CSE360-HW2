package edu.example.hw2.repo;

import edu.example.hw2.domain.Answer;
import edu.example.hw2.exceptions.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Answers {
    private final Map<UUID, Answer> store = new ConcurrentHashMap<>();

    public Answer create(UUID questionId, String body, String author) {
        Answer a = new Answer(null, questionId, body, author);
        store.put(a.getId(), a);
        return a;
    }

    public Optional<Answer> get(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Answer> listByQuestion(UUID questionId) {
        return store.values().stream()
                .filter(a -> a.getQuestionId().equals(questionId))
                .sorted(Comparator.comparing(Answer::getCreatedAt))
                .collect(Collectors.toList());
    }

    public Answer update(UUID id, String newBody) {
        Answer a = store.get(id);
        if (a == null) throw new NotFoundException("Answer not found");
        if (newBody != null) a.setBody(newBody);
        return a;
    }

    public void delete(UUID id) {
        if (store.remove(id) == null) throw new NotFoundException("Answer not found");
    }

    public void unacceptAll(UUID questionId) {
        store.values().stream().filter(a -> a.getQuestionId().equals(questionId))
                .forEach(a -> a.setAccepted(false));
    }

    public void setAccepted(UUID answerId, boolean accepted) {
        Answer a = store.get(answerId);
        if (a == null) throw new NotFoundException("Answer not found");
        a.setAccepted(accepted);
    }

    public void deleteByQuestion(UUID questionId) {
        store.values().removeIf(a -> a.getQuestionId().equals(questionId));
    }
}
