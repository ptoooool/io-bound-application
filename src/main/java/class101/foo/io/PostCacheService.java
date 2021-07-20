package class101.foo.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PostCacheService {

    @Autowired
    PostRepository postRepository;

    private Page<Post> firstPostPage;

    //cron 표현식은 강의노트를 참고하고 해당 방식으로 작성하면 1초에 한번씩 메소드가 실행되며
    // 0번째 페이지가 1번째 페이지가 되며 그 결과를 firstPostPage에 저장한다.
    // 즉 1초마다 최신의 1페이지 정보가 갱신되는것이며 이런 구조를 가지면 매번 요청이 들어올 때 마다
    // 새로운 쿼리를 DB로 날릴 필요가 없어진다.
    // 그냥 여기 변수에 저장되어 있는 값을 사용자에게 던져주면 됩니다. 이러면 DB의 부하가 확실히 줄어든다.
    @Scheduled(cron = "* * * * * *")
    public void updateFirstPostPage(){
        firstPostPage = postRepository.findAll(
                PageRequest.of(0, 20, Sort.by("id").descending())
        );
    }

    public Page<Post> getFirstPostPage(){
        return this.firstPostPage;
    }
}
