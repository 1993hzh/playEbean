import com.avaje.ebean.Ebean;
import com.avaje.ebean.TxScope;
import models.TestModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Logger;
import play.cache.CacheApi;
import play.test.WithApplication;

import java.util.*;

/**
 * Created by Administrator.
 * 2015/6/21 19:21
 */
public class EbeanPerformanceTest extends WithApplication {

    public static final Integer TIMES = 9999;
    public static final Integer SEARCH_TIMES = 5000;

//    private CacheApi cache;

    //        @Before
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

    //    @Test
    public void listWithoutCache() {
        try {
            Ebean.beginTransaction();
            List<Integer> elements = generateRandom();
            long before = System.currentTimeMillis();
            Iterator<Integer> iterator = elements.listIterator();
            while (iterator.hasNext()) {
                TestModel tm = Ebean.find(TestModel.class, iterator.next());
            }
            long after = System.currentTimeMillis();
            Logger.debug("Query " + SEARCH_TIMES + " times costs: " + (after - before));
            Ebean.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            Ebean.rollbackTransaction();
        }
    }

    @Test
    public void listWithCache() {
//        cache = play.Play.application().injector().instanceOf(CacheApi.class);
        try {
            List<Integer> elements = generateRandom();
            Iterator<Integer> iterator = elements.listIterator();
            /*while (iterator.hasNext()) {
                int i = iterator.next();
                TestModel tm;
                if (cache.get(String.valueOf(i)) == null) {
//                    tm = Ebean.getReference(TestModel.class, i);
                    tm = Ebean.find(TestModel.class, i);
                    cache.set(String.valueOf(tm.getId()), tm);
                } else {
                    tm = cache.get(String.valueOf(i));
                }
                tm.getNum();
            }*/
            Iterator<Integer> iterator1 = elements.listIterator();
            long before = System.currentTimeMillis();
            while (iterator1.hasNext()) {
                int i = iterator1.next();
                TestModel tm = Ebean.find(TestModel.class, i);
//                TestModel tm = cache.get(String.valueOf(i));
//                TestModel tm = Ebean.find(TestModel.class).select("id, num").where().eq("num", i).findUnique();
//                int num = Ebean.createSqlQuery("select id, num from T_TEST where num=:num").setParameter("num", i).findUnique().getInteger("num");
//                System.out.println(" " + num);
            }
            long after = System.currentTimeMillis();
            Logger.debug("Query " + SEARCH_TIMES + " times costs: " + (after - before));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> generateRandom() {
        Random random = new Random();
        List<Integer> elements = new ArrayList<>();
        for (int i = 0; i < SEARCH_TIMES; i++) {
            elements.add(random.nextInt(TIMES / 50) + 1);
        }
        int size = elements.size() - new HashSet<>(elements).size();
        Logger.debug(SEARCH_TIMES + " elements have " + size + " duplicate values");
        return elements;
    }

    //    @After
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
