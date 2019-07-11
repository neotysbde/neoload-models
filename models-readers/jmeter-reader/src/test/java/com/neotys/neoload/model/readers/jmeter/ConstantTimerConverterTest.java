package com.neotys.neoload.model.readers.jmeter;

import com.neotys.neoload.model.listener.TestEventListener;
import com.neotys.neoload.model.v3.project.userpath.Step;
import org.apache.jmeter.timers.ConstantTimer;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;

public class ConstantTimerConverterTest {

    @Before
    public void before()   {
        TestEventListener spy = spy(new TestEventListener());
        EventListenerUtils.setEventListener(spy);
    }

    @Test
    public void testApply(){
        ConstantTimer constantTimer = new ConstantTimer();
        constantTimer.setDelay("25");
        constantTimer.setName("Test");
        ConstantTimerConverter constantTimerConverter = new ConstantTimerConverter();
        List<Step> teststep = constantTimerConverter.apply(constantTimer, null);
        assertEquals(teststep.size(),1);

    }

}