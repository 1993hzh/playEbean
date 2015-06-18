package models;


import javax.persistence.*;

/**
 * Created by Administrator on 2015/6/15.
 */
@Entity
@Table(name = "T_TEST")
public class TestModel extends AbstractModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private boolean result = false;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
