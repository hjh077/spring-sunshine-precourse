package study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.dto.ActorsFilms;

import java.util.Map;

@RestController
public class JokeController {

    private static final Logger log = LoggerFactory.getLogger(JokeController.class);
    private final ChatClient client;

    public JokeController(ChatClient.Builder builder) {
        this.client = builder.build();
    }

    @GetMapping("/joke")
    public ChatResponse getJoke(@RequestParam(defaultValue = "fruit") String topic) {
        var template = new PromptTemplate("Tell me a joke about {topic}");
        var prompt = template.render(Map.of("topic", topic));

        return client.prompt(prompt)
                .call()
                .chatResponse();
    }

    @GetMapping("/joke2")
    public ChatResponse getJoke2(@RequestParam String name, @RequestParam String voice) {
        var user = new UserMessage("""
            Tell me about three famous pirates from the Golden Age of Piracy and what they did.
            Write at least one sentence for each pirate.
        """
        );
        var template = new SystemPromptTemplate("""
            You are a helpful AI assistant.
            You are an AI assistant that helps people find information.
            Your name is {name}.
            You should reply to the user's request using your name and in the style of a {voice}.
        """
        );
        var system = template.createMessage(Map.of("name",name,"voice",voice));
        var prompt = new Prompt(user, system);

        return client.prompt(prompt)
                .call()
                .chatResponse();
    }

    @GetMapping("/actors")
    public ActorsFilms getActors(@RequestParam(defaultValue = "최지우") String actor) {
        var beanOutputConverter = new BeanOutputConverter<>(ActorsFilms.class);
        var format = beanOutputConverter.getFormat();
        log.info("format: {}", format);

        var userMessage = """
            Generate the filmography of 5 movies for {actor}.
            {format}
        """;
        var prompt = new PromptTemplate(userMessage)
                .create(Map.of("actor", actor, "format", format));
        var text = client.prompt(prompt)
                .call()
                .chatResponse();
        log.info("text: {}", text);

        return beanOutputConverter.convert(text.getResult().getOutput().getText());
    }

    @GetMapping("/addDays")
    public String addDays(@RequestParam Integer days) {
        var template = new PromptTemplate("오늘 기준으로 {days} 이후 날짜를 계산해줘");
        var prompt = template.render(Map.of("days", days));

        return client.prompt(prompt)
                .tools(new Functions())
                .call()
                .content();

    }

}
