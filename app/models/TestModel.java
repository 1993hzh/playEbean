package models;


import com.avaje.ebean.annotation.CacheStrategy;

import javax.persistence.*;

/**
 * Created by Administrator on 2015/6/15.
 */
@CacheStrategy(readOnly = true)
@Entity
@Table(name = "T_TEST")
public class TestModel extends AbstractModel {


    private boolean result = false;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

}
