package com.neotys.neoload.model.readers.jmeter.extractor;

import com.google.common.collect.ImmutableList;
import com.neotys.neoload.model.readers.jmeter.EventListenerUtils;
import com.neotys.neoload.model.v3.project.userpath.VariableExtractor;
import org.apache.jmeter.extractor.XPath2Extractor;
import org.apache.jorphan.collections.HashTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.BiFunction;

/**
 * This class convert XpathExtractor of JMeter into a Variable Extractor of Neoload
 */
class XPathExtractorConverter2 implements BiFunction<XPath2Extractor, HashTree, List<VariableExtractor>> {

    //Attributs
    private static final Logger LOGGER = LoggerFactory.getLogger(XPathExtractorConverter2.class);

    private static final String XPath_EXTRACTOR = "XPathExtractor";

    //Constructor
    XPathExtractorConverter2() {
    }

    //Methods
    public List<VariableExtractor> apply(XPath2Extractor xPathExtractor, HashTree hashTree) {
        VariableExtractor.Builder variableExtractor = VariableExtractor.builder()
                .description(xPathExtractor.getComment())
                .name(xPathExtractor.getRefName())
                .xpath(xPathExtractor.getXPathQuery())
                .matchNumber(xPathExtractor.getMatchNumber())
                .getDefault(xPathExtractor.getDefaultValue());
        checkUnsupported(xPathExtractor);
        checkApplyTo(xPathExtractor);
        LOGGER.info("Convertion of Xpath2Extractor");
        EventListenerUtils.readSupportedFunction(XPath_EXTRACTOR, "Xpath2 Extractor Converter");
        return ImmutableList.of(variableExtractor.build());
    }

    /**
     * For JMeterExtractor, we can only manage the main sample option, we can apply this element in the same branch
     * This is why me only manage main sample
     * If you want sub sample, you have to manage the subtree too
     *
     * @param xPathExtractor
     */
    @SuppressWarnings("Duplicates")
    static void checkApplyTo(XPath2Extractor xPathExtractor) {
        if ("all".equals(xPathExtractor.fetchScope())) {
            LOGGER.warn("We can't manage the sub-samples conditions");
            EventListenerUtils.readSupportedParameterWithWarn(XPath_EXTRACTOR, "ApplyTo", "Main Sample & Sub-Sample", "Can't check Sub-Sample");
        } else if ("children".equals(xPathExtractor.fetchScope()) || "variable".equals(xPathExtractor.fetchScope())) {
            LOGGER.error("We can't manage the sub-sample and Jmeter Variable Use, so we convert like main sample only");
            EventListenerUtils.readUnsupportedParameter(XPath_EXTRACTOR, "ApplyTo", "Sub-Sample or Jmeter Variable");
        }
    }

    static void checkUnsupported(XPath2Extractor xPathExtractor) {
        if (xPathExtractor.getFragment()) {
            LOGGER.warn("We already use the fragment in own extractor");
            EventListenerUtils.readUnsupportedParameter(XPath_EXTRACTOR, "Fragment Xpath", " textContent");
        } else if (!xPathExtractor.getNamespaces().isEmpty()) {
            LOGGER.warn("We don't manage the namespaces");
            EventListenerUtils.readUnsupportedParameter(XPath_EXTRACTOR, "Namespace Xpath", " textContent");
        }
    }
}
