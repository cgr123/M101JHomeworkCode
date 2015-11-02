package course.homework.week3;

import org.junit.Test;

/**
 * Class to test the homework created for Homework 3.1
 */
public class Homework3_2Test {

    @Test
    public void testPrintStudentCount() {

        Homework3_1 homework3_1 = new Homework3_1();
        homework3_1.printStudentCount();
    }

    @Test
    public void testRemoveLowestHomeworkScoreByStudent() {
        Homework3_1 homework3_1 = new Homework3_1();
        homework3_1.removeLowestHomeworkScoreByStudent();
    }
}
