package backend.config;

import javax.net.ssl.SSLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import backend.infrastructure.externalapi.RedashClient;

@Configuration
public class HttpClientConfig {
    @Bean
    public HttpServiceProxyFactory httpServiceProxyFactory(WebClient.Builder builder) throws SSLException {
        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        HttpClient httpClient = HttpClient.create().wiretap(true)
                .secure(t -> t.sslContext(sslContext));
        WebClient webClient = WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("https://redash1.test.io").filter(logRequest()).build();

        return HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();
    }

    @Bean
    public RedashClient redashClient(HttpServiceProxyFactory proxyFactory) {
        return proxyFactory.createClient(RedashClient.class);
    } // Just example of sending request. This method is NOT part of the answer

    // This method returns filter function which will log request data
    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            System.out.println("Request: {} {}".formatted(clientRequest.method(), clientRequest.url()));
            clientRequest.headers().forEach(
                    (name, values) -> values.forEach(value -> System.out.println("{}={}".formatted(name, value))));
            return Mono.just(clientRequest);
        });
    }
}