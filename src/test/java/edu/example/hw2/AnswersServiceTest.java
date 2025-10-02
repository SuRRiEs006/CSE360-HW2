package edu.example.hw2;

import edu.example.hw2.domain.Answer;
import edu.example.hw2.domain.Question;
import edu.example.hw2.exceptions.ValidationException;
import edu.example.hw2.repo.Answers;
import edu.example.hw2.repo.Questions;
import edu.example.hw2.service.AnswersService;
import edu.example.hw2.service.QuestionsService;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AnswersServiceTest {
    @Test
    void A1_createValidAnswer_succeeds() {
        var qrepo = new Questions();
        var arepo = new Answers();
        var qs = new QuestionsService(qrepo, arepo);
        var as = new AnswersService(qrepo, arepo);

        Question q = qs.create("Title", "Body", "alice", Set.of());
        Answer a = as.create(q.getId(), "An answer", "bob");
        assertNotNull(a.getId());
        assertEquals(q.getId(), a.getQuestionId());
        assertFalse(a.isAccepted());
    }

    @Test
    void A2_createAnswer_badQuestion_fails() {
        var qrepo = new Questions();
        var arepo = new Answers();
        var as = new AnswersService(qrepo, arepo);

        assertThrows(ValidationException.class, () -> as.create(UUID.randomUUID(), "x", "y"));
    }
}
