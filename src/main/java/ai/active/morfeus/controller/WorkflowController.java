package ai.active.morfeus.controller;

import ai.active.morfeus.model.AiRequest;
import ai.active.morfeus.model.WorkflowResponse;
import ai.active.morfeus.service.WorkflowService;
import ai.active.morfeus.utils.ApplicationLogger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/workflow")
public class WorkflowController {
    @Autowired
    private final ObjectMapper objectMapper;

    @Autowired
    private WorkflowService workflowService;

    public WorkflowController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public WorkflowResponse getWorkflowResponse(@RequestBody(required = true) String body,
        @RequestParam(value = "url", required = false, defaultValue = "2") String urlId) throws IOException {
        ApplicationLogger.logInfo("Request : " + body, this.getClass());
        AiRequest request = objectMapper.readValue(body, AiRequest.class);
        return workflowService.getWorkflowResponse(request, urlId);
    }


    @GetMapping(path = "/health", consumes = "application/json", produces = "application/json")
    public String getHealthResponse() {
        return "Workflow Application is Alive";
    }
}
