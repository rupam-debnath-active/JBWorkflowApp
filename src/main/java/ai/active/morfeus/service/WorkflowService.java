package ai.active.morfeus.service;

import ai.active.morfeus.model.*;
import ai.active.morfeus.utils.ApplicationLogger;
import ai.active.morfeus.utils.WorkflowUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class WorkflowService {

  @Autowired
  private final ObjectMapper objectMapper;

  @Autowired
  private WorkflowUtils workflowUtils;

  @Autowired
  private ResourceLoader resourceLoader;

  @Autowired
  private AiService aiService;

  private static final String CLASSPATH = "classpath:";

  public WorkflowService(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public WorkflowResponse getWorkflowResponse(AiRequest aiRequest, String urlId) {
    List<WorkflowRequest> request = aiService.getAIResponse(aiRequest, urlId);
    WorkflowResponse workflowResponse = getWorkflowJson(request);
    for (WorkflowRequest workflowRequest : request) {
      workflowResponse.getAi().add(workflowRequest.getNode_name());
    }
    ApplicationLogger.logInfo("Response : " + workflowResponse, this.getClass());
    return workflowResponse;

  }

  private WorkflowResponse getWorkflowJson(List<WorkflowRequest> workflowRequests) {
    WorkflowResponse workflowResponse = new WorkflowResponse();
    List<Journey> journeys = new ArrayList<>();
    Journey journey = new Journey();
    Content content = new Content();
    Journeys journeys1 = new Journeys();
    Main main = new Main();
    Map<String, Node> nodeHashMap = new LinkedHashMap<>();
    int xpos = 100;
    int ypos = 131;
    List<VarLocal> varLocals = new ArrayList<>();
    for (int i=0; i<workflowRequests.size(); i++) {
      WorkflowRequest request = workflowRequests.get(i);
      if (i==0) {
        Node node = new Node();
        node.setType("start");
        Canvas canvas = new Canvas();
        canvas.setXPos(xpos);
        canvas.setYPos(ypos);
        node.setCanvas(canvas);
        Map<String, Object> map = new HashMap<>();
        EventTab eventTab = new EventTab();
        eventTab.setEventType("");
        eventTab.setTabID("DEFAULT_TAB");
        eventTab.setID(new Object[0]);
        map.put("eventTabs", eventTab);
        node.setPayload(map);
        node.setNext(Collections.singletonList(request.getNode_name()));
        nodeHashMap.put("start",node);
      }
      if (i == workflowRequests.size()-1 || "".equalsIgnoreCase(request.getNext_node())) {
        Node node = new Node();
        Canvas canvas = new Canvas();
        xpos = xpos + 500;
        canvas.setXPos(xpos);
        canvas.setYPos(ypos);
        node.setCanvas(canvas);
        if (request.getNode_type().equalsIgnoreCase("PromptPhone")) {
          setPhonePrompt(node, request, varLocals);
        } else if (request.getNode_type().equalsIgnoreCase("PromptEmail")) {
          setEmailPrompt(node, request, varLocals);
        } else if (request.getNode_type().equalsIgnoreCase("DisplayList")) {
          setListPrompt(node, request, varLocals);
        } else if (request.getNode_type().equalsIgnoreCase("PromptOptions")) {
          setPromptOptions(node, request, varLocals);
        } else {
          setTextPrompt(node, request, varLocals);
        }
        node.setNext(new ArrayList<>());
        nodeHashMap.put(request.getNode_name(),node);
      } else {
        Node node = new Node();
        Canvas canvas = new Canvas();
        xpos = xpos + 500;
        canvas.setXPos(xpos);
        canvas.setYPos(ypos);
        node.setCanvas(canvas);
        if (request.getNode_type().equalsIgnoreCase("PromptPhone")) {
          processPhonePrompt(workflowRequests, nodeHashMap, varLocals, i, node);
        } else if (request.getNode_type().equalsIgnoreCase("PromptEmail")) {
          processEmailPrompt(workflowRequests, nodeHashMap, varLocals, i, node);
        } else if (request.getNode_type().equalsIgnoreCase("DisplayList")) {
          xpos = processDisplayList(workflowRequests, nodeHashMap, xpos, ypos, varLocals, i, node);
        } else if (request.getNode_type().equalsIgnoreCase("PromptOptions")) {
          processPromptOptions(workflowRequests, nodeHashMap, varLocals, i, node);
        } else {
          xpos = processTextPrompt(workflowRequests, nodeHashMap, xpos, ypos, varLocals, i, node);
        }
      }
    }
    main.setNodes(nodeHashMap);
    journeys1.setMain(main);
    content.setJourneys(journeys1);
    Variables variables = new Variables();
    variables.setVarCampaign(new ArrayList<>());
    variables.setVarLocal(varLocals);
    content.setVariables(variables);
    content.setApis(new Object[0]);
    content.setImports(new Object[0]);
    MetaInfo metaInfo = new MetaInfo();
    metaInfo.setJourneyID("O8LkW6PFG743G864S3HR7SUMHSVQ4XA");
    metaInfo.setJourneyName("AI-Journey");
    metaInfo.setJourneyType("USER_JOURNEY");
    metaInfo.setJourneySubtype(null);
    metaInfo.setTag(new Object[0]);
    content.setMetaInfo(metaInfo);
    Settings settings = new Settings();
    settings.setAIWorkspaceID("d36f7e34-a71b-4c67-a2cd-52c46d431bac");
    settings.setStickyJourney(false);
    SwitchModel switchModel = new SwitchModel();
    switchModel.setType("AUTO_GLOBAL_SWITCH");
    settings.setSwitchModel(switchModel);
    content.setSettings(settings);
    journey.setContent(content);
    journeys.add(journey);
    workflowResponse.setJourneys(journeys);
    return workflowResponse;
  }

  private int processTextPrompt(List<WorkflowRequest> workflowRequests, Map<String, Node> nodeHashMap, int xpos, int ypos, List<VarLocal> varLocals,
      int i, Node node) {
    setTextPrompt(node, workflowRequests.get(i), varLocals);
    node.setNext(Collections.singletonList("waitNode"+ i));
    nodeHashMap.put(workflowRequests.get(i).getNode_name(), node);

    Node node2 = new Node();
    Canvas canvas2 = new Canvas();
    xpos = xpos + 500;
    canvas2.setXPos(xpos);
    canvas2.setYPos(ypos);
    node2.setCanvas(canvas2);
    setWaitEventPrompt(node2, varLocals);
    node2.setNext(Collections.singletonList("codeNode"+ i));
    nodeHashMap.put("waitNode"+ i,node2);

    Node node3 = new Node();
    Canvas canvas3 = new Canvas();
    xpos = xpos + 500;
    canvas3.setXPos(xpos);
    canvas3.setYPos(ypos);
    node3.setCanvas(canvas3);
    setCodeNodePrompt(node3, workflowRequests.get(i), varLocals);
    node3.setNext("".equalsIgnoreCase(workflowRequests.get(i).getNext_node()) ? new ArrayList<>() : Collections.singletonList(
        workflowRequests.get(i).getNext_node()));
    nodeHashMap.put("codeNode"+ i,node3);
    return xpos;
  }

  private void processEmailPrompt(List<WorkflowRequest> workflowRequests, Map<String, Node> nodeHashMap, List<VarLocal> varLocals, int i,
      Node node) {
    setEmailPrompt(node, workflowRequests.get(i), varLocals);
    node.setNext("".equalsIgnoreCase(workflowRequests.get(i).getNext_node()) ? new ArrayList<>() : Collections.singletonList(
        workflowRequests.get(i).getNext_node()));
    nodeHashMap.put(workflowRequests.get(i).getNode_name(), node);
  }

  private void processPhonePrompt(List<WorkflowRequest> workflowRequests, Map<String, Node> nodeHashMap, List<VarLocal> varLocals, int i,
      Node node) {
    setPhonePrompt(node, workflowRequests.get(i), varLocals);
    node.setNext("".equalsIgnoreCase(workflowRequests.get(i).getNext_node()) ? new ArrayList<>() : Collections.singletonList(
        workflowRequests.get(i).getNext_node()));
    nodeHashMap.put(workflowRequests.get(i).getNode_name(), node);
  }

  private int processDisplayList(List<WorkflowRequest> workflowRequests, Map<String, Node> nodeHashMap, int xpos, int ypos, List<VarLocal> varLocals,
      int i, Node node) {
    setListPrompt(node, workflowRequests.get(i), varLocals);
    node.setNext(Collections.singletonList("codeNodeList"+ i));
    nodeHashMap.put(workflowRequests.get(i).getNode_name(), node);

    Node node3 = new Node();
    Canvas canvas3 = new Canvas();
    xpos = xpos + 500;
    canvas3.setXPos(xpos);
    canvas3.setYPos(ypos);
    node3.setCanvas(canvas3);
    setCodeNodePrompt(node3, workflowRequests.get(i), varLocals);
    node3.setNext("".equalsIgnoreCase(workflowRequests.get(i).getNext_node()) ? new ArrayList<>() : Collections.singletonList(
        workflowRequests.get(i).getNext_node()));
    nodeHashMap.put("codeNodeList"+ i,node3);
    return xpos;
  }

  private void processPromptOptions(List<WorkflowRequest> workflowRequests, Map<String, Node> nodeHashMap, List<VarLocal> varLocals, int i,
      Node node) {
    setPromptOptions(node, workflowRequests.get(i), varLocals);
    List<String> next = new ArrayList<>();
    if (!"".equalsIgnoreCase(workflowRequests.get(i).getNext_node())) {
      next.add(workflowRequests.get(i).getNext_node());
    }
    if (!"".equalsIgnoreCase(workflowRequests.get(i).getFalse_node())) {
      next.add(workflowRequests.get(i).getFalse_node());
    } else if (!"".equalsIgnoreCase(workflowRequests.get(i).getNext_node()) &&
        null != workflowRequests.get(i).getOption_2_text() &&
        !"".equalsIgnoreCase(workflowRequests.get(i).getOption_2_text())) {
      next.add(workflowRequests.get(i).getNext_node());
    }
    node.setNext(next);
    nodeHashMap.put(workflowRequests.get(i).getNode_name(), node);
  }

  private void setCodeNodePrompt(Node node, WorkflowRequest workflowRequest, List<VarLocal> varLocals) {
    node.setType("code");
    Map<String, Object> map = new HashMap<>();
    map.put("code","var_local."+workflowRequest.getVariable_name()+"=var_system.user_input;");
    node.setPayload(map);
  }

  private void setWaitEventPrompt(Node node, List<VarLocal> varLocals) {
    node.setType("wait_for_events");
    Map<String, Object> map = new HashMap<>();
    map.put("eventType","user_message");
    map.put("retry",3);
    map.put("timeout",30);
    map.put("enable_timeout",false);
    map.put("unit","minute");
    node.setPayload(map);
  }

  private void setPhonePrompt(Node node, WorkflowRequest workflowRequest, List<VarLocal> varLocals) {
    node.setType("prompt");
    setVariables(workflowRequest, varLocals);
    Map<String, Object> map = new HashMap<>();
    if (null != workflowRequest.getText())
      map.put("text", workflowRequest.getText().replaceAll("\\{\\{", "{{var_local."));
    map.put("regex","^[0-9]{10}$");
    map.put("variable_name","var_local.".concat(workflowRequest.getVariable_name()));
    map.put("retry",3);
    map.put("key","promptPhone");
    map.put("enable_failure_message",true);
    map.put("failure_message",workflowRequest.getFailure_text());
    map.put("skip_node",false);
    node.setPayload(map);
  }

  private void setEmailPrompt(Node node, WorkflowRequest workflowRequest, List<VarLocal> varLocals) {
    node.setType("prompt");
    setVariables(workflowRequest, varLocals);
    Map<String, Object> map = new HashMap<>();
    if (null != workflowRequest.getText())
      map.put("text", workflowRequest.getText().replaceAll("\\{\\{", "{{var_local."));
    map.put("regex","^[a-zA-Z0-9 _.-]+@[a-zA-Z0-9.-]+$");
    map.put("variable_name","var_local.".concat(workflowRequest.getVariable_name()));
    map.put("retry",3);
    map.put("key","promptEmail");
    map.put("enable_failure_message",true);
    map.put("failure_message",workflowRequest.getFailure_text());
    map.put("skip_node",false);
    node.setPayload(map);
  }

  private void setListPrompt(Node node, WorkflowRequest workflowRequest, List<VarLocal> varLocals) {
    node.setType("list");
    setVariables(workflowRequest, varLocals);
    Map<String, Object> map = new HashMap<>();
    map.put("footer", "");
    map.put("body", workflowRequest.getHeader());
    map.put("variable_name","");
    PaginationConfig paginationConfig = new PaginationConfig();
    paginationConfig.setEnabled(false);
    paginationConfig.setLabels(new HashMap<>());
    paginationConfig.getLabels().put("viewNext","");
    paginationConfig.getLabels().put("viewPrevious","");
    paginationConfig.getLabels().put("navigation","");
    map.put("paginationConfig", paginationConfig);
    List<Items> mapList = new ArrayList<>();
    Items items = new Items();
    items.setId("DEFAULT_LIST_NODE_SECTION");
    items.setTitle("");
    items.setDynamic(false);
    items.setIteratorElementPath(null);
    items.setOptions(new ArrayList<>());
    for (int i=0; i<workflowRequest.getList().size(); i++) {
      if (i==0) {
        Map<String, Object> optionsMap = new LinkedHashMap<>();
        optionsMap.put("id", "DEFAULT_LIST_NODE_SECTION_ROW");
        optionsMap.put("title", workflowRequest.getList().get(i).getItemTitle());
        optionsMap.put("isDynamic", false);
        optionsMap.put("iteratorElementPath", null);
        optionsMap.put("description", workflowRequest.getList().get(i).getItemSubtitle());
        optionsMap.put("autofill", true);
        optionsMap.put("payload", workflowRequest.getList().get(i).getItemTitle());
        items.getOptions().add(optionsMap);
      } else {
        Map<String, Object> optionsMap = new LinkedHashMap<>();
        optionsMap.put("id", "ListItem"+i);
        optionsMap.put("title", workflowRequest.getList().get(i).getItemTitle());
        optionsMap.put("iteratorElementPath", null);
        optionsMap.put("description", workflowRequest.getList().get(i).getItemSubtitle());
        optionsMap.put("autofill", true);
        optionsMap.put("payload", workflowRequest.getList().get(i).getItemTitle());
        items.getOptions().add(optionsMap);
      }
    }
    mapList.add(items);
    map.put("items", mapList);
    map.put("message_button","Select");
    node.setPayload(map);
  }

  private void setPromptOptions(Node node, WorkflowRequest workflowRequest, List<VarLocal> varLocals) {
    node.setType("reply");
    setVariables(workflowRequest, varLocals);
    Map<String, Object> map = new HashMap<>();
    Map<String, Object> headerMap = new LinkedHashMap<>();
    headerMap.put("type","");
    headerMap.put("text","");
    headerMap.put("image", new HashMap<>());
    headerMap.put("video",new HashMap<>());
    headerMap.put("document",new HashMap<>());
    map.put("header", headerMap);
    map.put("footer", "");
    if (null != workflowRequest.getText())
      map.put("body", workflowRequest.getText().replaceAll("\\{\\{", "{{var_local."));
    map.put("variable_name","var_local.".concat(workflowRequest.getVariable_name()));

    List<Map<String, Object>> mapList = new ArrayList<>();
    Map<String, Object> optionsMap = new LinkedHashMap<>();
    optionsMap.put("id", "DEFAULT_BUTTON");
    optionsMap.put("text", workflowRequest.getOption_1_text());
    optionsMap.put("autofill", true);
    optionsMap.put("payload", workflowRequest.getOption_1_text());
    mapList.add(optionsMap);

    Map<String, Object> optionsMap1 = new LinkedHashMap<>();
    optionsMap1.put("id", "Button2");
    optionsMap1.put("text", workflowRequest.getOption_2_text());
    optionsMap1.put("autofill", true);
    optionsMap1.put("payload", workflowRequest.getOption_2_text());
    mapList.add(optionsMap1);
    map.put("buttons", mapList);
    node.setPayload(map);
  }

  private void setTextPrompt(Node node, WorkflowRequest workflowRequest, List<VarLocal> varLocals) {
    node.setType("text");
    setVariables(workflowRequest, varLocals);
    Map<String, Object> map = new HashMap<>();
    if (null != workflowRequest.getText())
      map.put("text", workflowRequest.getText().replaceAll("\\{\\{", "{{var_local."));
    node.setPayload(map);
  }

  private static void setVariables(WorkflowRequest workflowRequest, List<VarLocal> varLocals) {
    if ((null != workflowRequest.getVariable_name()) && !"".equals(workflowRequest.getVariable_name())) {
      VarLocal varLocal = new VarLocal();
      varLocal.setDataType("String");
      varLocal.setKey(workflowRequest.getVariable_name());
      varLocal.setName(workflowRequest.getVariable_name());
      varLocal.setDefaultValue("");
      varLocals.add(varLocal);
    }
  }

}
