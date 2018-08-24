package demo.aop.proxy;

/**
 * @Description: 接口具体业务操作类
 * @author: 01369674
 * @date: 2018/7/16
 */
public class ForumServiceImpl implements ForumService {
    @Override
    public void removeTopic(int topic) {
        System.out.println("模拟删除Topic记录："+topic);
        try {
            Thread.currentThread().sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeForum(int forumId) {
        System.out.println("模拟删除Forum记录："+forumId);
        try {
            Thread.currentThread().sleep(40);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
