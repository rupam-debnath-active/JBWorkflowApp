package ai.active.morfeus.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Variables {
  @JsonProperty("var_local")
  private List<VarLocal> varLocal;
  @JsonProperty("var_campaign")
  private List<String> varCampaign;

  public List<VarLocal> getVarLocal() {
    return varLocal;
  }

  public void setVarLocal(List<VarLocal> varLocal) {
    this.varLocal = varLocal;
  }

  public List<String> getVarCampaign() {
    return varCampaign;
  }

  public void setVarCampaign(List<String> varCampaign) {
    this.varCampaign = varCampaign;
  }
}
