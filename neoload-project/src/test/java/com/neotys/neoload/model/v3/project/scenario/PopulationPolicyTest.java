package com.neotys.neoload.model.v3.project.scenario;


import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class PopulationPolicyTest {
	@Test
	public void constants() {
		assertEquals("load_policy", PopulationPolicy.LOAD_POLICY);
		assertEquals("constant_load", PopulationPolicy.CONSTANT_LOAD);
		assertEquals("rampup_load", PopulationPolicy.RAMPUP_LOAD);
		assertEquals("peaks_load", PopulationPolicy.PEAKS_LOAD);
		assertEquals("custom_load", PopulationPolicy.CUSTOM_LOAD);
	}
}
