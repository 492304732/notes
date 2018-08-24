package demo.aop.self_aware;

import org.springframework.core.Ordered;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/7/31
 */
public interface SystemBootAddon extends Ordered {
    /**
     * 系统就绪后调用的方法
     */
    void onReady();
}
