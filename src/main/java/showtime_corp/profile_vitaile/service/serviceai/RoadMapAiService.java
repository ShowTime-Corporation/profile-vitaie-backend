package showtime_corp.profile_vitaile.service.serviceai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import showtime_corp.profile_vitaile.entity.RoadMap;
import showtime_corp.profile_vitaile.entity.User;
import showtime_corp.profile_vitaile.repository.RoadMapRepository;

@Service
@RequiredArgsConstructor
public class RoadMapAiService {

    private final ChatClient chatClient;
    private final RoadMapRepository repository;
    private final AiPromptFactory promptFactory;

    public RoadMap generate(User user, String cvText) {

        String response = chatClient
                .prompt()
                .user(promptFactory.roadmapPrompt(cvText))
                .call()
                .content();

        return repository.save(
                RoadMap.builder()
                        .user(user)
                        .analysis(AiResponseParser.extract(response, "ROAD_ANALYSIS"))
                        .proposal(AiResponseParser.extract(response, "ROAD_PROPOSAL"))
                        .ideas(AiResponseParser.extract(response, "ROAD_IDEAS"))
                        .keep(AiResponseParser.extract(response, "ROAD_KEEP"))
                        .build()
        );
    }
}

