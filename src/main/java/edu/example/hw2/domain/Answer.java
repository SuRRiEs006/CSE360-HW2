package edu.example.hw2.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Answer {
    private final UUID id;
    private final UUID questionId;
    private String body;
    private String author;
    private boolean isAccepted = false;
    private final Instant createdAt;
    private Instant updatedAt;

    public Answer(UUID id, UUID questionId, String body, String author) {
        this.id = id == null ? UUID.randomUUID() : id;
        this.questionId = questionId;
        this.body = body;
        this.author = author;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public UUID getId() { return id; }
    public UUID getQuestionId() { return questionId; }
    public String getBody() { return body; }
    public String getAuthor() { return author; }
    public boolean isAccepted() { return isAccepted; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void setBody(String body) { this.body = body; touch(); }
    public void setAuthor(String author) { this.author = author; touch(); }
    public void setAccepted(boolean accepted) { isAccepted = accepted; touch(); }

    private void touch() { this.updatedAt = Instant.now(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;
        Answer answer = (Answer) o;
        return id.equals(answer.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
