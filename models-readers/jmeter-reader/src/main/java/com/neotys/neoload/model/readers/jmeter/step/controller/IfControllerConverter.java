package com.neotys.neoload.model.readers.jmeter.step.controller;

import com.neotys.neoload.model.readers.jmeter.EventListenerUtils;
import com.neotys.neoload.model.readers.jmeter.step.StepConverters;
import com.neotys.neoload.model.v3.project.userpath.Container;
import com.neotys.neoload.model.v3.project.userpath.If;
import com.neotys.neoload.model.v3.project.userpath.Step;
import org.apache.jmeter.control.IfController;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jorphan.collections.HashTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * This class convert the ifController of JMeter into Step of Neoload
 */
public class IfControllerConverter implements BiFunction<IfController, HashTree, List<Step>> {

    //Attributs
    private final StepConverters converter;
    private static final Logger LOGGER = LoggerFactory.getLogger(IfControllerConverter.class);

    ///Constructor
    public IfControllerConverter(StepConverters converters) {
        this.converter = converters;
    }

    //Methods
    public List<Step> apply(IfController ifController, HashTree hashTree) {
        List<Step> containerList = new ArrayList<>();
        convertIfController(containerList, ifController, hashTree);
        LOGGER.info("IfController correctly converted");
        EventListenerUtils.readSupportedFunctionWithWarn("IfController", "Conditions", "We can't convert the conditions, so we put them on the Transaction's description");
        return containerList;
    }

    /**
     * This method have an option to check the conditions for each element in the IfController,
     * If this option is not check we create a simple If in Neoload with all elements
     * Else we have to create an If for each Element in IfController
     * @param containerList
     * @param ifController
     * @param hashTree
     */
    private void convertIfController(List<Step> containerList, IfController ifController, HashTree hashTree) {
        If.Builder ifBuilder = If.builder()
                .name(ifController.getName())
                .description(ifController.getCondition());
        HashTree subTree = hashTree.get(ifController);
        if(ifController.isEvaluateAll()){
            for (Object o : subTree.list()){
                if( o instanceof AbstractTestElement) {
                    AbstractTestElement abstractTestElement = (AbstractTestElement) o;
                    HashTree children = new HashTree();
                    children.add(o);
                    children.get(o).add(subTree.getTree(o));
                    ifBuilder.name(ifController.getName() + " Children: " + abstractTestElement.getName())
                            .then(Container.builder()
                                    .addAllSteps(converter.convertStep(children))
                                    .build());
                    containerList.add(ifBuilder.build());
                }
            }
        }else{
            ifBuilder
                    .then(Container.builder()
                    .addAllSteps(converter.convertStep(subTree))
                    .build());
            containerList.add(ifBuilder.build());
        }
        if(ifController.isUseExpression()){
            LOGGER.warn("We don't convert the Interprete of Variable Expression");
            EventListenerUtils.readSupportedFunctionWithWarn("IfController","Options", "Variable Expression");
        }

    }
}
