package backend.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import backend.application.service.RedashService;
import backend.domain.model.Todo;

@RestController
public class SearchController {

  @Autowired
  RedashService redashService;

  @CrossOrigin
  @GetMapping("/")
  public String root(@RequestParam(value = "name", defaultValue = "World") String name) {
    return String.format("Hello! %s!", name);
  }

  @CrossOrigin
  @GetMapping("/hello")
  public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
    return String.format("Hello! %s!", name);
  }

  @CrossOrigin
  @GetMapping("/list")
  public Mono<Todo> list(@RequestParam(value = "name", defaultValue = "") String name) {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
    map.add("p_id", "test");
    map.add("api_key", "test");
    map.add("p_name", "test");
    return redashService.begin("1065", map);

  }
}