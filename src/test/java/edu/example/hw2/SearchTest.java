package edu.example.hw2;

import edu.example.hw2.domain.Question;
import edu.example.hw2.repo.Answers;
import edu.example.hw2.repo.Questions;
import edu.example.hw2.service.QuestionsService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SearchTest {
    @Test
    void L2_searchQuestions_keyword() {
        var qs = new QuestionsService(new Questions(), new Answers());
        qs.create("Hash map usage", "details", "a", Set.of("java"));
        qs.create("Graphs", "shortest path", "b", Set.of("algorithms"));
        List<Question> result = qs.search("hash", null, Set.of());
        assertEquals(1, result.size());
        assertTrue(result.get(0).getTitle().toLowerCase().contains("hash"));
    }
}
