import com.avaje.ebean.Ebean;
import models.TestModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.db.ebean.Transactional;
import play.test.WithApplication;

/**
 * Created by Administrator.
 * 2015/6/16 21:29
 */
public class EbeanTest extends WithApplication {

    private static final Logger logger = LoggerFactory.getLogger(EbeanTest.class);
    TestModel tm = new TestModel();

    @Before
    public void create() {
        Ebean.beginTransaction();
        tm.setResult(true);
        try {
            tm.save();
            Ebean.commitTransaction();
        } catch (Exception e) {
            logger.error(e.getMessage());
            Ebean.rollbackTransaction();
        } finally {
            Ebean.endTransaction();
        }
    }

//    @Test
    @Transactional
    public void read() {
        tm = Ebean.find(TestModel.class).findList().get(0);
        logger.debug(tm.isResult() + "");
    }

    @Test
    @Transactional
    public void update() {
        new EbeanTest().read();
        tm.setResult(true);
        tm.update();
    }

    @After
    @Transactional
    public void delete() {
        tm.delete();
    }
}
