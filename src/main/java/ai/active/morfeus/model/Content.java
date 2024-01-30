package ai.active.morfeus.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.StringJoiner;

public class Content {
  private Journeys journeys;
  private Variables variables;
  private Object[] apis;
  private Object[] imports;
  private Settings settings;
  @JsonProperty("meta_info")
  private MetaInfo metaInfo;

  public Journeys getJourneys() { return journeys; }
  public void setJourneys(Journeys value) { this.journeys = value; }

  public Variables getVariables() { return variables; }
  public void setVariables(Variables value) { this.variables = value; }

  public Object[] getApis() { return apis; }
  public void setApis(Object[] value) { this.apis = value; }

  public Object[] getImports() { return imports; }
  public void setImports(Object[] value) { this.imports = value; }

  public Settings getSettings() { return settings; }
  public void setSettings(Settings value) { this.settings = value; }
  @JsonProperty("meta_info")
  public MetaInfo getMetaInfo() { return metaInfo; }
  @JsonProperty("meta_info")
  public void setMetaInfo(MetaInfo value) { this.metaInfo = value; }

  @Override
  public String toString() {
    return new StringJoiner(", ", Content.class.getSimpleName() + "[", "]")
            .add("journeys=" + journeys)
            .add("variables=" + variables)
            .add("apis=" + Arrays.toString(apis))
            .add("imports=" + Arrays.toString(imports))
            .add("settings=" + settings)
            .add("metaInfo=" + metaInfo)
            .toString();
  }
}
