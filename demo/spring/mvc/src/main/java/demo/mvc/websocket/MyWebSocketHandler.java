package demo.mvc.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/8/14
 */
public class MyWebSocketHandler extends AbstractWebSocketHandler{
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //处理文本消息
        System.out.println("收到消息"+message.getPayload());
        //模拟延时
        Thread.sleep(2000);
        System.out.println("发送消息：hello world!");
        session.sendMessage(new TextMessage("hello world!"));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("建立连接");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("关闭连接");
    }
}
