package edu.example.hw2;

import edu.example.hw2.domain.Question;
import edu.example.hw2.exceptions.ValidationException;
import edu.example.hw2.repo.Answers;
import edu.example.hw2.repo.Questions;
import edu.example.hw2.service.QuestionsService;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionsServiceTest {
    @Test
    void Q1_createValid_succeeds() {
        var svc = new QuestionsService(new Questions(), new Answers());
        Question q = svc.create("How to sort?", "I need help with arrays.", "alice", Set.of("java"));
        assertNotNull(q.getId());
        assertEquals(Question.Status.OPEN, q.getStatus());
    }

    @Test
    void Q2_createInvalidTitle_fails() {
        var svc = new QuestionsService(new Questions(), new Answers());
        assertThrows(ValidationException.class, () -> svc.create("", "body", "bob", Set.of()));
    }
}
