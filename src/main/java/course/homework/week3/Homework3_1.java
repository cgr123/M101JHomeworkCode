package course.homework.week3;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import com.mongodb.dao.MongoCollectionUtils;
import com.mongodb.dao.MongoDbClientFactory;
import org.bson.Document;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to perform the changes for the homework task 3.1.
 */
public class Homework3_1 {

    public static final String SCHOOL_DATABASE = "school";
    public static final String STUDENTS_COLLECTION = "students";
    public static final String SCORES_FIELD = "scores";
    public static final String TYPE_FIELD = "type";
    public static final String SCORE_FIELD = "score";
    public static final String ID_FIELD = "_id";
    private final MongoClient dbClient = MongoDbClientFactory.getMongoClient();

    public void printStudentCount() {
        MongoCollection<Document> studentsCollection = MongoCollectionUtils.getCollection(dbClient, SCHOOL_DATABASE, STUDENTS_COLLECTION);
        System.out.println(studentsCollection.count());
    }

    public void removeLowestHomeworkScoreByStudent() {
        MongoCollection<Document> studentsCollection = MongoCollectionUtils.getCollection(dbClient, SCHOOL_DATABASE, STUDENTS_COLLECTION);
        List<Document> studentDocumentList = studentsCollection.find().sort(Sorts.descending("scores.score")).into(new LinkedList<Document>());
        for (Document studentDoc : studentDocumentList) {
            List<Document> scoreList = (List<Document>) studentDoc.get(SCORES_FIELD);

            System.out.println("Student= " + studentDoc.get("name"));

            List<Document> newScores = new ArrayList<>();
            double bestScore = 0;
            for (Document score : scoreList) {
                System.out.println("BEFORE type= " + score.get(TYPE_FIELD) + " score= " + score.get(SCORE_FIELD));
                if (score.get(TYPE_FIELD).equals("homework")) {
                    if (bestScore < score.getDouble(SCORE_FIELD)) {
                        bestScore = score.getDouble(SCORE_FIELD);
                    }
                }
                else {
                    newScores.add(score);
                }
            }
            Document homeworkScore = new Document(TYPE_FIELD, "homework").append(SCORE_FIELD, bestScore);
            newScores.add(homeworkScore);
            studentDoc.put("scores", newScores);
            Document idSearch = new Document(ID_FIELD, studentDoc.get(ID_FIELD));
            Document setScores = new Document("$set", new Document("scores", newScores));
            studentsCollection.updateOne(idSearch, setScores);
        }
    }

    private void printNewScores(Document studentDoc) {

        List<Document> scoreList = (List<Document>) studentDoc.get(SCORES_FIELD);

        System.out.println("Student= " + studentDoc.get("name"));
        for (Document score : scoreList) {
            System.out.println("AFTER type= " + score.get(TYPE_FIELD) + " score= " + score.get("score"));
        }
    }
}
