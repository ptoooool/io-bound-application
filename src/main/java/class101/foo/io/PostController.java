package class101.foo.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    private static Integer PAGE_SIZE = 20;

    @Autowired
    PostRepository postRepository;

    @Autowired
    Producer producer;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PostCacheService postCacheService;

    // 1. 글을 작성한다.
    @PostMapping("/post")
    public Post createPost(@RequestBody Post post) throws JsonProcessingException {
        String jsonPost = objectMapper.writeValueAsString(post); //String으로 넣어주려면 jackson이라는 라이브러리를 사용해줘야한다.
        producer.sendTo(jsonPost);
        return post; // return은 우선 아이디는 비어있는 null상태로 넘겨지긴한다
    }

    // 2-1. 글 목록을 조회한다.
    // 2-2 글 목록을 페이징하여 반환
    @GetMapping("/posts")
    public Page<Post> getPostList(@RequestParam(defaultValue = "1") Integer page) { //defaultValue는 들어오는값이 없을때 기본값 설정
        if(page.equals(1)){
            return postCacheService.getFirstPostPage();
        }else{
            return postRepository.findAll(
                PageRequest.of(page - 1, PAGE_SIZE, Sort.by("id").descending())
                //페이지가 배열이랑 똑같이 0부터 시작이기 때문에 defaultValue 가 1이 들어오면 -1을 해줘야 0부터시작이라서 -1을 해준다.
            );
        }

    }

    // 3. 글 번호로 조회
    @GetMapping("/post/{id}")
    public Post getPostById(@PathVariable("id") Long id){
        return postRepository.findById(id).get(); // get()을 해주면 Post의 타입으로 나오게된다.
    }
    // 4. 글 내용으로 검색 -> 해당 내용이 포함된 모든 글
    @GetMapping("/search")
    public List<Post> findPostsByContent(@RequestParam String content){
        return postRepository.findByContentContains(content);
    }
}
