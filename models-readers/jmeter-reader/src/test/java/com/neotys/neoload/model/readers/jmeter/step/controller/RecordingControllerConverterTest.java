package com.neotys.neoload.model.readers.jmeter.step.controller;

import com.neotys.neoload.model.listener.TestEventListener;
import com.neotys.neoload.model.readers.jmeter.EventListenerUtils;
import com.neotys.neoload.model.readers.jmeter.step.StepConverters;
import com.neotys.neoload.model.v3.project.Element;
import com.neotys.neoload.model.v3.project.userpath.Container;
import com.neotys.neoload.model.v3.project.userpath.Delay;
import com.neotys.neoload.model.v3.project.userpath.Step;
import org.apache.jmeter.protocol.http.control.RecordingController;
import org.apache.jmeter.timers.ConstantTimer;
import org.apache.jorphan.collections.HashTree;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class RecordingControllerConverterTest {
    private TestEventListener spy;

    @Before
    public void before()   {
        spy = spy(new TestEventListener());
        EventListenerUtils.setEventListener(spy);
    }

    @Test
    public void testAApplySteps() {

        RecordingControllerConverter testcontrol = new RecordingControllerConverter(new StepConverters());
        RecordingController recordingController = Mockito.mock(RecordingController.class);
        when(recordingController.getName()).thenReturn("my thread group");
        when(recordingController.getComment()).thenReturn("My comment");
        HashTree hashTree = Mockito.mock(HashTree.class);
        ConstantTimer constantTimer = new ConstantTimer();
        List collections = new ArrayList();
        collections.add(constantTimer);
        HashTree subTree = Mockito.mock(HashTree.class);
        when(subTree.list()).thenReturn(collections);
        when(hashTree.get(eq(recordingController))).thenReturn(subTree);
        List<Step> result = testcontrol.apply(recordingController, hashTree);
        List<Step> expected = new ArrayList<>();
        expected.add(Container.builder()
                .addSteps(Delay.builder().name(constantTimer.getName()).value(constantTimer.getDelay()).build())
                .name("my thread group")
                .description("My comment")
                .build());
        assertEquals(result, expected);

    }

}