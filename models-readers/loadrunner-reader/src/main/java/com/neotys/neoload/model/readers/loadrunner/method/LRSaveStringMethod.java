package com.neotys.neoload.model.readers.loadrunner.method;

import com.google.common.base.Preconditions;
import com.neotys.neoload.model.core.Element;
import com.neotys.neoload.model.parsers.CPP14Parser.MethodcallContext;
import com.neotys.neoload.model.readers.loadrunner.LoadRunnerVUVisitor;
import com.neotys.neoload.model.readers.loadrunner.MethodCall;
import com.neotys.neoload.model.repository.ImmutableSaveString;

public class LRSaveStringMethod implements LoadRunnerMethod {

	public LRSaveStringMethod() {
		super();
	}

	@Override
	public Element getElement(final LoadRunnerVUVisitor visitor, final MethodCall method, final MethodcallContext ctx) {
		Preconditions.checkNotNull(method);		
		if(method.getParameters() == null || method.getParameters().size()!=2){
			visitor.readSupportedFunctionWithWarn(method.getName(), ctx, method.getName() + " method must have 2 parameters");
			return null;
		} 		
		visitor.readSupportedFunction(method.getName(), ctx);
		String variableValue = method.getParameters().get(0);
		if(variableValue.startsWith("\"") && variableValue.endsWith("\"")){
			variableValue = variableValue.substring(1, variableValue.length()-1);
		}	
		String variableName = method.getParameters().get(1);
		if(variableName.startsWith("\"") && variableName.endsWith("\"")){
			variableName = variableName.substring(1, variableName.length()-1);
		}	
		return ImmutableSaveString.builder().name(method.getName() + " " + variableName).variableName(variableName).variableValue(variableValue).build();
	}
}
