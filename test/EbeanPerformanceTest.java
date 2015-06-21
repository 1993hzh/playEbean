import com.avaje.ebean.Ebean;
import models.TestModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Logger;
import play.test.WithApplication;

import java.util.Random;

/**
 * Created by Administrator.
 * 2015/6/21 19:21
 */
public class EbeanPerformanceTest extends WithApplication {

    public static final Integer TIMES = 9999;
    public static final Integer SEARCH_TIMES = 500;

    @Before
    public void insert() {
        try {
            Ebean.beginTransaction();
            long before = System.currentTimeMillis();
            for (int i = 1; i <= TIMES; i++) {
                TestModel tm = new TestModel();
                tm.setNum(i);
                tm.save();
            }
            long after = System.currentTimeMillis();
            Logger.debug("Insert " + TIMES + " times costs: " + (after - before));
            Ebean.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            Ebean.rollbackTransaction();
        }
    }

    @Test
    public void list() {
        try {
            Ebean.beginTransaction();
            Random random = new Random();
            long before = System.currentTimeMillis();
            for (int i = 0; i < SEARCH_TIMES; i++) {
                int num = random.nextInt(TIMES) + 1;
                TestModel tm = Ebean.find(TestModel.class, num);
            }
            long after = System.currentTimeMillis();
            Logger.debug("Query " + SEARCH_TIMES + " times costs: " + (after - before));
            Ebean.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            Ebean.rollbackTransaction();
        }
    }

    @After
    public void delete() {
        try {
            Ebean.beginTransaction();
            long before = System.currentTimeMillis();
            Ebean.createQuery(TestModel.class).findList().stream().forEach(Ebean::delete);
            long after = System.currentTimeMillis();
            Logger.debug("Delete costs: " + (after - before));
            Ebean.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            Ebean.rollbackTransaction();
        }
    }
}
