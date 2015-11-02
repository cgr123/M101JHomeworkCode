package course.homework.week2;

import org.junit.Test;

/**
 * Class to test my homework.
 */
public class Homework2_3Test {

    @Test
    public void testGetGradesCount() throws Exception {

        Homework2_3 homework = new Homework2_3();
        homework.getGradesCount();

    }

    @Test
    public void testRemoveLowestHomeworkScoreByStudent() {
        Homework2_3 homework = new Homework2_3();
        homework.removeLowestHomeworkScoreByStudent();
    }
}
