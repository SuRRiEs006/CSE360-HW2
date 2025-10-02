package edu.example.hw2;

import edu.example.hw2.domain.Question;
import edu.example.hw2.repo.Answers;
import edu.example.hw2.repo.Questions;
import edu.example.hw2.service.AnswersService;
import edu.example.hw2.service.QuestionsService;

import java.util.*;

public class App {
    private final QuestionsService qs;
    private final AnswersService as;
    private final Scanner in = new Scanner(System.in);

    public App() {
        Questions qrepo = new Questions();
        Answers arepo = new Answers();
        this.qs = new QuestionsService(qrepo, arepo);
        this.as = new AnswersService(qrepo, arepo);
    }

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        while (true) {
            System.out.println("\nHW2 Menu: 1)CreateQ 2)ListQ 3)ViewQ 4)AddA 5)AcceptA 6)DeleteQ 7)Exit");
            String choice = in.nextLine().trim();
            try {
                switch (choice) {
                    case "1": createQuestion(); break;
                    case "2": listQuestions(); break;
                    case "3": viewQuestion(); break;
                    case "4": addAnswer(); break;
                    case "5": acceptAnswer(); break;
                    case "6": deleteQuestion(); break;
                    case "7": return;
                    default: System.out.println("Choose 1-7");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void createQuestion() {
        System.out.print("Title: "); String title = in.nextLine();
        System.out.print("Body: "); String body = in.nextLine();
        System.out.print("Author: "); String author = in.nextLine();
        Question q = qs.create(title, body, author, new HashSet<>());
        System.out.println("Created Q id=" + q.getId());
    }

    private void listQuestions() {
        List<Question> all = qs.listAll();
        System.out.println("Questions (" + all.size() + ")");
        all.forEach(q -> System.out.println(q.getId()+" | "+q.getTitle()+" | "+q.getStatus()));
    }

    private void viewQuestion() {
        System.out.print("Question id: "); UUID id = UUID.fromString(in.nextLine());
        Question q = qs.get(id).orElseThrow(() -> new RuntimeException("Not found"));
        System.out.println(q.getTitle()+"\n"+q.getBody());
    }

    private void addAnswer() {
        System.out.print("Question id: "); UUID qid = UUID.fromString(in.nextLine());
        System.out.print("Body: "); String body = in.nextLine();
        System.out.print("Author: "); String author = in.nextLine();
        var a = as.create(qid, body, author);
        System.out.println("Answer id=" + a.getId());
    }

    private void acceptAnswer() {
        System.out.print("Answer id: "); UUID aid = UUID.fromString(in.nextLine());
        as.accept(aid);
        System.out.println("Accepted.");
    }

    private void deleteQuestion() {
        System.out.print("Question id: "); UUID id = UUID.fromString(in.nextLine());
        qs.delete(id);
        System.out.println("Deleted question and its answers.");
    }
}
