package class101.foo.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PostRepository postRepository;

    @RabbitListener(queues = "CREATE_POST_QUEUE") //이 큐를 계속 컨슘하고 있는 상태로 큐에 메세지가 들어오면 이 메소드사 실행된다.
    public void handler(String message) throws JsonProcessingException {
        //문자열을 읽어와서 json으로 올텐데 Post타입으로 변경해주며 컨트롤러 있던 것처럼 save를 해주면 메세지 큐에 있는 내용이 DB에 들어간다.
        Post post = objectMapper.readValue(message, Post.class);
        postRepository.save(post);
    }
}
