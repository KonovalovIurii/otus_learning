package ru.ws.Homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;
import ru.ws.Homework.processor.DataProcessor;

//  http://localhost:8081/clients.html
@RestController
public class ClientDataController {
    private static final Logger log = LoggerFactory.getLogger(ClientDataController.class);
    private final DataProcessor<Mono<StringValue>> dataProcessorStringReactorMono;
    private final WebClient client;

    public ClientDataController(WebClient.Builder builder,
                                @Qualifier("dataProcessorMono") DataProcessor<Mono<StringValue>> dataProcessorMono) {
        this.dataProcessorStringReactorMono = dataProcessorMono;
     /*   DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory("http://localhost:8080");
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT);*/
        client = builder
                //.uriBuilderFactory(factory)
                .baseUrl("http://localhost:8080")
                .build();
      /*  webClient = WebClient
                .builder()
                .uriBuilderFactory(factory)
                .baseUrl("http://localhost:8080")
                .exchangeFunction(exchangeFunction)
                .build();*/
    }

    @GetMapping(value = "/data-mono/clients/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<StringValue> dataMono2(Model model) {
        log.info("request for string data-mono");

        return  client.get().uri("/data-mono/clients/")
                .retrieve()
                .bodyToMono(StringValue.class)
                .doOnNext(val ->log.info("val:{}", val.value()));
    }

    @PostMapping("/data-mono/clients/")
    public RedirectView dataMono(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "address", required = false) String address,
                                 @RequestParam(value = "phone", required = false) String phone){
        log.info("request for string data-mono, name:{}, address:{} , phone{}", name, address, phone);

        var srcRequest=  client.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/data-mono/clients/")
                        .queryParam("name", name)
                        .queryParam("address", address)
                        .queryParam("phone", phone)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

      /*  String uri = String.format("/data-mono/clients/?name=%1$s&address=%2$s&phone=%3$s" ,name, address, phone);

        var srcRequest =  client.post().uri(uri)
                .retrieve()
                .bodyToMono(StringValue.class)
               ;*/

        return new RedirectView("/clients.html", true);

    }
}
