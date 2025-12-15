package showtime_corp.profile_vitaile.service.service_ai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import showtime_corp.profile_vitaile.entity.Resumen;
import showtime_corp.profile_vitaile.entity.User;
import showtime_corp.profile_vitaile.repository.ResumenRepository;


@Service
@RequiredArgsConstructor
public class ResumeAiService {

    private final ChatClient chatClient;
    private final ResumenRepository repository;
    private final AiPromptFactory promptFactory;

    public Resumen generate(User user, String cvText) {

        String response = chatClient
                .prompt()
                .user(promptFactory.resumePrompt(cvText))
                .call()
                .content();

        return repository.save(
                Resumen.builder()
                        .user(user)
                        .resumeInfo(AiResponseParser.extract(response, "RESUME_INFO"))
                        .employability(AiResponseParser.extract(response, "RESUME_EMPLOYABILITY"))
                        .simple(AiResponseParser.extract(response, "RESUME_SIMPLE"))
                        .recommendation(AiResponseParser.extract(response, "RESUME_RECOMMENDATION"))
                        .build()
        );
    }
}

