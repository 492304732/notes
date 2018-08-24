package demo.aop.annotation;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/8/1
 */

public class TorumService {
    @NeedTest(value = true)
    public void deleteForum(int forumId){
        System.out.println("删除论坛模块:"+forumId);
    }

    @NeedTest(value = true)
    public void deleteTopic(int postId){
        System.out.println("删除论坛主题:"+postId);
    }
}
