package ai.active.morfeus.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class Items {
  private String id;
  private String title;
  @JsonProperty("isDynamic")
  private boolean isDynamic;
  private String iteratorElementPath;
  private List<Map<String, Object>> options;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
  @JsonProperty("isDynamic")
  public boolean isDynamic() {
    return isDynamic;
  }
  @JsonProperty("isDynamic")
  public void setDynamic(boolean dynamic) {
    isDynamic = dynamic;
  }

  public String getIteratorElementPath() {
    return iteratorElementPath;
  }

  public void setIteratorElementPath(String iteratorElementPath) {
    this.iteratorElementPath = iteratorElementPath;
  }

  public List<Map<String, Object>> getOptions() {
    return options;
  }

  public void setOptions(List<Map<String, Object>> options) {
    this.options = options;
  }

  @Override
  public String toString() {
    return "Items{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", isDynamic=" + isDynamic + ", iteratorElementPath='" + iteratorElementPath + '\'' + ", options=" + options + '}';
  }
}
