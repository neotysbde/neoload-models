package com.neotys.neoload.model.repository;

import org.immutables.value.Value;

/**
 * @deprecated As of v3, replaced by an associated class from v3 version.
 */
@Value.Immutable
@Deprecated
public interface Condition {
	String getOperand1();
	String getOperand2();
	Operator getOperator();
	
	enum Operator{
		EQUALS, 
		NOT_EQUALS, 
		CONTAINS, 
		NOT_CONTAINS, 
		STARTS_WITH, 
		NOT_STARTS_WITH, 
		ENDS_WITH, 
		NOT_ENDS_WITH, 
		MATCHREGEXP, 
		GREATER_THAN, 
		GREATER_EQUAL, 
		LESSER_THAN, 
		LESSER_EQUAL, 
		EXISTS,      
		FINDREGEXP, 
		NOT_EXISTS, 
	}
}
