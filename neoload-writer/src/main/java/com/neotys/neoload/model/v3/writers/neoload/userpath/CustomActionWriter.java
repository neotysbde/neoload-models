package com.neotys.neoload.model.v3.writers.neoload.userpath;

import com.neotys.neoload.model.v3.project.userpath.CustomAction;
import com.neotys.neoload.model.v3.writers.neoload.ElementWriter;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class CustomActionWriter extends ElementWriter {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomActionWriter.class);
	public static final String XML_TAG_NAME = "custom-action";
	public static final String XML_ACTION_TYPE_ATT = "actionType";
	public static final String XML_IS_HIT_ATT = "isHit";

	public CustomActionWriter(final CustomAction customAction) {
		super(customAction);
	}

	public static CustomActionWriter of(final CustomAction customAction) {
		return new CustomActionWriter(customAction);
	}

	@Override
	public void writeXML(final Document document, final Element currentElement, final String outputFolder) {
		final Element customAction = document.createElement(XML_TAG_NAME);
		super.writeXML(document, customAction, outputFolder);
		customAction.setAttribute(XML_ACTION_TYPE_ATT, ((CustomAction) element).getType());
		customAction.setAttribute(XML_IS_HIT_ATT, String.valueOf(((CustomAction) element).asRequest()));
		((CustomAction) element).getParameters().forEach(p -> CustomActionParameterWriter.writeXML(document, customAction, p));
		currentElement.appendChild(customAction);
		((CustomAction) element).getLibraryPath().ifPresent(p -> copyFileToExtlib(p.toFile(), Paths.get(outputFolder, "lib", "extlib").toFile()));
	}
	
	private static void copyFileToExtlib(final File source, final File target){
		try {
			target.mkdirs();
			FileUtils.copyFile(source,  new File(target, source.getName()));
		} catch (final IOException e) {
			LOGGER.error("Cannot copy file " + source.getAbsolutePath() + " to " + target.getAbsolutePath(), e);
		}
	}
}
