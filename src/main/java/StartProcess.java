import org.kie.api.KieBase;
import org.kie.api.event.process.*;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;

import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;


import java.util.HashMap;
import java.util.Map;

public class StartProcess {

    public static void main(String[] args) {
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        builder.add(ResourceFactory.newClassPathResource("demo.bpmn"), ResourceType.BPMN2);

        KieBase kieBase = builder.newKieBase();
        KieSession kieSession = kieBase.newKieSession();
        VariableTrackingListener variableTrackingListener = new VariableTrackingListener();
        kieSession.addEventListener(variableTrackingListener);

        HashMap<String, Object> params = new HashMap<>();
        params.put("name", "Francesco");

        ProcessInstance pi = kieSession.startProcess("greeting.process", params);

        System.out.println(variableTrackingListener.getProcessVariables(pi.getId()));
        kieSession.dispose();

    }

    public static class VariableTrackingListener implements ProcessEventListener {
        HashMap<Long, HashMap<String, Object>> variables;

        public VariableTrackingListener() {
            variables = new HashMap<>();
        }

        public Map<String, Object> getProcessVariables(long id) {
            HashMap<String, Object> processVariables =
                    variables.getOrDefault(id, new HashMap<>());
            variables.put(id, processVariables);
            return processVariables;

        }

        public void afterVariableChanged(ProcessVariableChangedEvent e) {
            getProcessVariables(e.getProcessInstance().getId())
                    .put(e.getVariableId(), e.getNewValue());

        }

        public void beforeProcessStarted(ProcessStartedEvent processStartedEvent) {

        }

        public void afterProcessStarted(ProcessStartedEvent processStartedEvent) {

        }

        public void beforeProcessCompleted(ProcessCompletedEvent processCompletedEvent) {

        }

        public void afterProcessCompleted(ProcessCompletedEvent processCompletedEvent) {

        }

        public void beforeNodeTriggered(ProcessNodeTriggeredEvent processNodeTriggeredEvent) {

        }

        public void afterNodeTriggered(ProcessNodeTriggeredEvent processNodeTriggeredEvent) {

        }

        public void beforeNodeLeft(ProcessNodeLeftEvent processNodeLeftEvent) {

        }

        public void afterNodeLeft(ProcessNodeLeftEvent processNodeLeftEvent) {

        }

        public void beforeVariableChanged(ProcessVariableChangedEvent processVariableChangedEvent) {

        }
    }
}
