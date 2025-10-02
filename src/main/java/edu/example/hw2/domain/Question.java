package edu.example.hw2.domain;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Question {
    public enum Status { OPEN, ANSWERED, CLOSED }

    private final UUID id;
    private String title;
    private String body;
    private String author;
    private final Set<String> tags = new HashSet<>();
    private Status status = Status.OPEN;
    private final Instant createdAt;
    private Instant updatedAt;

    public Question(UUID id, String title, String body, String author, Set<String> tags) {
        this.id = id == null ? UUID.randomUUID() : id;
        this.title = title;
        this.body = body;
        this.author = author;
        if (tags != null) this.tags.addAll(tags);
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getBody() { return body; }
    public String getAuthor() { return author; }
    public Set<String> getTags() { return tags; }
    public Status getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void setTitle(String title) { this.title = title; touch(); }
    public void setBody(String body) { this.body = body; touch(); }
    public void setAuthor(String author) { this.author = author; touch(); }
    public void setStatus(Status status) { this.status = status; touch(); }

    private void touch() { this.updatedAt = Instant.now(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return id.equals(question.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
