package backend.infrastructure.externalapi;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import reactor.core.publisher.Mono;
import backend.domain.model.Todo;

@HttpExchange(url = "/api")
public interface RedashClient {
    @PostExchange(url = "queries/{id}/refresh")
    Mono<Todo> refresh(@PathVariable("id") String id,
            @RequestParam("p_id") MultiValueMap<String, String> params);

    @PostExchange
    Mono<Todo> postTodo(@RequestBody Todo todo);
}