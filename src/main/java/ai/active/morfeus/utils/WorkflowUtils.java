package ai.active.morfeus.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class WorkflowUtils {
  private static final String CLASSPATH = "CLASSPATH:";

  private ResourceLoader resourceLoader;
  private ObjectMapper objectMapper;

  @Autowired
  WorkflowUtils(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
    this.resourceLoader = resourceLoader;
    this.objectMapper = objectMapper;
  }
}
