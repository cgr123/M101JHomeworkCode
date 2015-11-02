package course.homework.week2;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import com.mongodb.dao.MongoCollectionUtils;
import com.mongodb.dao.MongoDbClientFactory;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.LinkedList;
import java.util.List;

/**
 * Class to provide the code for the homework assignment for M101J 2.3
 */
public class Homework2_3 {

    private static final String STUDENTS_DATABASE = "students";
    private static final String GRADES_COLLECTION = "grades";
    public static final String STUDENT_ID = "student_id";
    public static final String SCORE = "score";
    public static final String TYPE_HOMEWORK = "homework";
    public static final String TYPE = "type";
    public static final String OBJECT_ID = "_id";
    private final MongoClient dbClient = MongoDbClientFactory.getMongoClient();

    public void getGradesCount() {
        MongoCollection<Document> gradesCollection = MongoCollectionUtils.getCollection(dbClient, STUDENTS_DATABASE, GRADES_COLLECTION);
        List<Document> documentList = gradesCollection.find(new Document("type","homework")).into(new LinkedList<Document>());
        System.out.println("gradesCollection has " + documentList.size() + " records");
    }

    public void removeLowestHomeworkScoreByStudent() {
        MongoCollection<Document> gradesCollection = MongoCollectionUtils.getCollection(dbClient, STUDENTS_DATABASE, GRADES_COLLECTION);
        List<Document> documentList = gradesCollection.find(new Document(TYPE, TYPE_HOMEWORK)).sort(Sorts.ascending(STUDENT_ID, SCORE)).into(new LinkedList<Document>());
        int previousStudentId = -1;

        for (Document doc : documentList) {
            ObjectId docId = (ObjectId) doc.get(OBJECT_ID);
            if (previousStudentId != (int) doc.get(STUDENT_ID)) {
                gradesCollection.deleteOne(new Document(OBJECT_ID, docId));
                System.out.println("DELETEING " +doc.toString() + " ID= " + docId);
            } else {
                System.out.println("NOT DELETING " + doc.toString());
            }
            previousStudentId = (int) doc.get(STUDENT_ID);
        }
    }
}
