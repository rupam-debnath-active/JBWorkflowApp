package ai.active.morfeus.service;

import ai.active.morfeus.model.AiRequest;
import ai.active.morfeus.model.AiResponse;
import ai.active.morfeus.model.WorkflowRequest;
import ai.active.morfeus.utils.ApplicationLogger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class AiService {

  private static final String AI_URL_1 = "https://sniper.active.ai/hackathon/generate_flow/";
  private static final String AI_URL_2 = "https://autobot.active.ai/hackathon/generate_flow_v3/";

  @Autowired
  private ObjectMapper objectMapper;

  @Cacheable(value = "prompt", key = "'autobot:prompt:' + #aiRequest.description")
  public List<WorkflowRequest> getAIResponse(AiRequest aiRequest, String urlId) {
    ApplicationLogger.logInfo("<<<<<<<<<<<<<<<<<<<<<<<<Entered API call>>>>>>>>>>>>>>>>>>>>>>>>>>>>", this.getClass());
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    String URL = AI_URL_1;
    if (null != urlId && urlId.equalsIgnoreCase("2")) {
      URL = AI_URL_2;
    }
    List<WorkflowRequest> request= null;
    try {
      HttpEntity<String> requestHttpEntity = new HttpEntity<>(objectMapper.writeValueAsString(aiRequest), headers);
      ResponseEntity<String> aiResponse =
          restTemplate.postForEntity(URL, requestHttpEntity, String.class);
      if (aiResponse.getStatusCode().equals(HttpStatus.OK) && aiResponse.getBody() != null) {
        if (URL.equalsIgnoreCase(AI_URL_1)) {
          request = objectMapper.readValue(aiResponse.getBody(), new TypeReference<List<WorkflowRequest>>() {
          });
        } else {
          request = objectMapper.readValue(aiResponse.getBody(), AiResponse.class).getJourney();
        }
      }
    } catch (Exception e) {
      ApplicationLogger.logError("Error while workflow response : ", e);
    }
    ApplicationLogger.logInfo("AI Response : " + request, this.getClass());
    ApplicationLogger.logInfo("<<<<<<<<<<<<<<<<<<<<<<<<Exited API call>>>>>>>>>>>>>>>>>>>>>>>>>>>>", this.getClass());
    return request;
  }
}
