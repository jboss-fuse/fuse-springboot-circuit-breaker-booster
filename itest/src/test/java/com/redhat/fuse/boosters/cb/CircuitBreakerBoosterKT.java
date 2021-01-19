package com.redhat.fuse.boosters.cb;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.openshift.client.OpenShiftClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class CircuitBreakerBoosterKT {

    @ArquillianResource
    OpenShiftClient client;

    @Test
    public void templateTest() throws Exception {
        HashMap<String,String> templateParameters = new HashMap<String,String>(){
            {put("SOURCE_REPOSITORY_URL","https://github.com/jboss-fuse/fuse-springboot-circuit-breaker-booster");}
        };

        File template = new File("../greetings-service/.openshiftio/application.yaml");
        assertTrue(template.exists());
        List<HasMetadata> resources = client.templates().load(template).process(templateParameters).getItems();

        for(HasMetadata res : resources){
            client.resource(res).createOrReplace();
        }

        File template2 = new File("../name-service/.openshiftio/application.yaml");
        assertTrue(template2.exists());
        List<HasMetadata> resources2 = client.templates().load(template2).process(templateParameters).getItems();

        for(HasMetadata res : resources2){
            client.resource(res).createOrReplace();
        }

        assertEquals(2, client.buildConfigs().list().getItems().size());
    }
}
