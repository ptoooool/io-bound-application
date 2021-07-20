package class101.foo.io;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// 글작성이 왔을 때 이 코드를 실행해서 RabbitMQ에다가 메세지를 넣어준다.
@Component
public class Producer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendTo(String message){
        this.rabbitTemplate.convertAndSend("CREATE_POST_QUEUE", message);
    }
}
