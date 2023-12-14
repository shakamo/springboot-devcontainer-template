package backend.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import reactor.core.publisher.Mono;
import backend.domain.model.Todo;
import backend.infrastructure.externalapi.RedashClient;

@Repository
public class RedashService {
    @Autowired
    RedashClient redashClient;

    public Mono<Todo> begin(String query_id, MultiValueMap<String, String> params) {
        return redashClient.refresh(query_id, params);
    }
}
