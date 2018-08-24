package demo.mvcDemo.dao;

import org.springframework.stereotype.Repository;

@Repository("testDao")
public class TestDaoImpl implements TestDao {

    @Override
    public String test() {
        return "../index.html";
    }

}