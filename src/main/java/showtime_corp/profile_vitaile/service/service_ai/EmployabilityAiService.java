package showtime_corp.profile_vitaile.service.service_ai;


import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import showtime_corp.profile_vitaile.entity.Employability;
import showtime_corp.profile_vitaile.entity.User;
import showtime_corp.profile_vitaile.repository.EmployabilityRepository;

@Service
@RequiredArgsConstructor
public class EmployabilityAiService {

    private final ChatClient chatClient;
    private final EmployabilityRepository repository;
    private final AiPromptFactory promptFactory;

    public Employability generate(User user, String cvText) {

        String response = chatClient
                .prompt()
                .user(promptFactory.employabilityPrompt(cvText))
                .call()
                .content();

        return repository.save(
                Employability.builder()
                        .user(user)
                        .techOffer(AiResponseParser.extract(response, "TECH_OFFER"))
                        .educationOffer(AiResponseParser.extract(response, "EDUCATION_OFFER"))
                        .companyOffer(AiResponseParser.extract(response, "COMPANY_OFFER"))
                        .build()
        );
    }
}



