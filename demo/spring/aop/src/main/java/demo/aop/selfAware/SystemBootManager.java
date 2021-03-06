package demo.aop.self_aware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/7/31
 */
@Component
public class SystemBootManager implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<SystemBootAddon> systemBootAddons = Collections.EMPTY_LIST;
    private boolean hasRunOnce = false;

    //注入所有 SystemBootAddon 插件
    @Autowired
    public void setSystemBootAddons(List<SystemBootAddon> systemBootAddons){
        Assert.notEmpty(systemBootAddons);
        OrderComparator.sort(systemBootAddons);
        this.systemBootAddons = systemBootAddons;
    }

    // 触发所有插件
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){
       if(!hasRunOnce){
           for(SystemBootAddon systemBootAddon:systemBootAddons){
               systemBootAddon.onReady();
               if(logger.isDebugEnabled()){
                   logger.debug("执行插件:{}",systemBootAddon.getClass().getCanonicalName());
               }
           }
           hasRunOnce=true;
       }else{
           if(logger.isDebugEnabled()){
               logger.debug("已执行过容器启动插件集，本次忽略之");
           }
       }
    }
}
