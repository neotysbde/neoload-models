package com.neotys.neoload.model.readers;

import com.neotys.neoload.model.v3.project.ImmutableProject;
import com.neotys.neoload.model.v3.project.Project;
import com.neotys.neoload.model.v3.project.userpath.*;
import com.neotys.neoload.model.v3.writers.neoload.NeoLoadWriter;
import org.apache.jmeter.control.SwitchController;
import org.apache.jmeter.timers.ConstantTimer;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void testCreateProject() {
        ImmutableProject project = Project.builder()
                .name("project")
                .addUserPaths(createUp())
                .build();

        new NeoLoadWriter(project, System.getProperty("user.home") + "/nlproject", Collections.emptyMap()).write(false);
        createUp();
    }

    private UserPath createUp() {
        return UserPath.builder()
                .name("My UP")
                .description("test")
                .actions(Container.builder()
                        .name("actions")
                        .addSteps(Container.builder()
                                .name("transaction")
                                .addSteps(Loop.builder()
                                        .name("looper")
                                        .description("a simple loop")
                                        .times("20")
                                        .addSteps(Container.builder()
                                                .name("Transaction in looper")
                                                .build())
                                        .addSteps(Delay.builder()
                                                .value("200")
                                                .name("Delay in looper")
                                                .build())
                                        .build())
                                .addSteps(While.builder()
                                        .addConditions(Condition.builder()
                                                .operand1("1")
                                                .operator(Condition.Operator.GREATER)
                                                .operand2("2")
                                                .build())
                                        .addSteps(ThinkTime.builder()
                                                .value("300")
                                                .build())
                                        .build()
                                )
                                .addSteps(Request.builder()
                                        .method("GET")
                                        .name("http request")
                                        .server("MyServer")
                                        .build())
                                .build())
                        .build())
                .description("test")
                .build();
    }
}