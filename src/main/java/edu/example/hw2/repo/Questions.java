package edu.example.hw2.repo;

import edu.example.hw2.domain.Question;
import edu.example.hw2.domain.Question.Status;
import edu.example.hw2.exceptions.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Questions {
    private final Map<UUID, Question> store = new ConcurrentHashMap<>();

    public Question create(String title, String body, String author, Set<String> tags) {
        Question q = new Question(null, title, body, author, tags);
        store.put(q.getId(), q);
        return q;
    }

    public Optional<Question> get(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public Question update(UUID id, String newTitle, String newBody) {
        Question q = store.get(id);
        if (q == null) throw new NotFoundException("Question not found");
        if (newTitle != null) q.setTitle(newTitle);
        if (newBody != null) q.setBody(newBody);
        return q;
    }

    public void delete(UUID id) {
        if (store.remove(id) == null) throw new NotFoundException("Question not found");
    }

    public List<Question> listAll() {
        return new ArrayList<>(store.values());
    }

    public List<Question> search(String keyword, Status status, Set<String> tags) {
        return store.values().stream().filter(q -> {
            boolean ok = true;
            if (keyword != null && !keyword.isBlank()) {
                String k = keyword.toLowerCase();
                ok &= (q.getTitle().toLowerCase().contains(k) || q.getBody().toLowerCase().contains(k));
            }
            if (status != null) ok &= q.getStatus() == status;
            if (tags != null && !tags.isEmpty()) ok &= q.getTags().containsAll(tags);
            return ok;
        }).sorted(Comparator.comparing(Question::getCreatedAt)).collect(Collectors.toList());
    }

    public void setStatus(UUID id, Status s) {
        Question q = store.get(id);
        if (q == null) throw new NotFoundException("Question not found");
        q.setStatus(s);
    }
}
