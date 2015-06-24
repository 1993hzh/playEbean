package models;


import com.avaje.ebean.annotation.CacheStrategy;

import javax.persistence.*;

/**
 * Created by Administrator on 2015/6/15.
 */
@CacheStrategy
@Entity
@Table(name = "T_TEST")
public class TestModel extends AbstractModel {


    private boolean result = false;
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

}
