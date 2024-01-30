package ai.active.morfeus.model;

import java.util.Map;

public class PaginationConfig {
  private boolean enabled;
  private Map<String, String> labels;

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Map<String, String> getLabels() {
    return labels;
  }

  public void setLabels(Map<String, String> labels) {
    this.labels = labels;
  }
}
