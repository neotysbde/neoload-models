package com.neotys.neoload.model.v3.binding.io;


import com.google.common.collect.ImmutableList;
import com.neotys.neoload.model.v3.project.Project;
import com.neotys.neoload.model.v3.project.scenario.Apm;
import com.neotys.neoload.model.v3.project.scenario.ConstantLoadPolicy;
import com.neotys.neoload.model.v3.project.scenario.CustomLoadPolicy;
import com.neotys.neoload.model.v3.project.scenario.CustomPolicyStep;
import com.neotys.neoload.model.v3.project.scenario.DynatraceAnomalyRule;
import com.neotys.neoload.model.v3.project.scenario.ImmutableCustomPolicyStep;
import com.neotys.neoload.model.v3.project.scenario.ImmutableLoadDuration;
import com.neotys.neoload.model.v3.project.scenario.LoadDuration;
import com.neotys.neoload.model.v3.project.scenario.MonitoringParameters;
import com.neotys.neoload.model.v3.project.scenario.PeakLoadPolicy;
import com.neotys.neoload.model.v3.project.scenario.PeaksLoadPolicy;
import com.neotys.neoload.model.v3.project.scenario.PeaksLoadPolicy.Peak;
import com.neotys.neoload.model.v3.project.scenario.PopulationPolicy;
import com.neotys.neoload.model.v3.project.scenario.RampupLoadPolicy;
import com.neotys.neoload.model.v3.project.scenario.RendezvousPolicy;
import com.neotys.neoload.model.v3.project.scenario.Scenario;
import com.neotys.neoload.model.v3.project.scenario.StartAfter;
import com.neotys.neoload.model.v3.project.scenario.StopAfter;
import com.neotys.neoload.model.v3.project.scenario.WhenRelease;

import org.junit.Test;

import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.assertNotNull;


public class IOScenariosTest extends AbstractIOElementsTest {

	private static Project getScenariosOnlyRequired() {
		final PopulationPolicy population11 = PopulationPolicy.builder()
				.name("MyPopulation11")
				.loadPolicy(ConstantLoadPolicy.builder()
						.users(500)
						.build())
				.build();

		final PopulationPolicy population12 = PopulationPolicy.builder()
				.name("MyPopulation12")
				.loadPolicy(RampupLoadPolicy.builder()
						.minUsers(1)
						.incrementUsers(10)
						.incrementEvery(LoadDuration.builder()
								.value(5)
								.type(LoadDuration.Type.TIME)
								.build())
						.build())
				.build();

		final PopulationPolicy population13 = PopulationPolicy.builder()
				.name("MyPopulation13")
				.loadPolicy(PeaksLoadPolicy.builder()
						.minimum(PeakLoadPolicy.builder()
								.users(100)
								.duration(LoadDuration.builder()
										.value(120)
										.type(LoadDuration.Type.TIME)
										.build())
								.build())
						.maximum(PeakLoadPolicy.builder()
								.users(500)
								.duration(LoadDuration.builder()
										.value(120)
										.type(LoadDuration.Type.TIME)
										.build())
								.build())
						.start(Peak.MINIMUM)
						.build())
				.build();

		ImmutableLoadDuration loadDuration = LoadDuration.builder()
				.value(100)
				.type(LoadDuration.Type.TIME)
				.build();
		ImmutableCustomPolicyStep customPolicyStep = CustomPolicyStep.builder()
				.when(loadDuration)
				.users(300)
				.build();

		final PopulationPolicy population14 = PopulationPolicy.builder()
				.name("MyPopulation14")
				.loadPolicy(CustomLoadPolicy.builder()
						.steps(Collections.singletonList(customPolicyStep))
						.build())
				.build();

		final Scenario scenario1 = Scenario.builder()
				.name("MyScenario1")
				.addPopulations(population11, population12, population13, population14)
				.isStoredVariables(true)
				.build();

		final Project project = Project.builder()
				.name("MyProject")
				.addScenarios(scenario1)
				.build();

		return project;
	}

	private static Project getScenariosRequiredAndOptional() {
		RendezvousPolicy rendezvousPolicy = RendezvousPolicy.builder().name("rdv").timeout(100).when(WhenRelease.builder().type(WhenRelease.Type.VU_NUMBER).value("200").build()).build();
		final PopulationPolicy population11 = PopulationPolicy.builder()
				.name("MyPopulation11")
				.loadPolicy(ConstantLoadPolicy.builder()
						.users(500)
						.duration(LoadDuration.builder()
								.value(900)
								.type(LoadDuration.Type.TIME)
								.build())
						.startAfter(StartAfter.builder()
								.value(30)
								.type(StartAfter.Type.TIME)
								.build())
						.rampup(60)
						.stopAfter(StopAfter.builder()
								.value(30)
								.type(StopAfter.Type.TIME)
								.build())
						.build())
				.build();

		final PopulationPolicy population12 = PopulationPolicy.builder()
				.name("MyPopulation12")
				.loadPolicy(RampupLoadPolicy.builder()
						.minUsers(1)
						.maxUsers(500)
						.incrementUsers(10)
						.incrementEvery(LoadDuration.builder()
								.value(1)
								.type(LoadDuration.Type.ITERATION)
								.build())
						.duration(LoadDuration.builder()
								.value(15)
								.type(LoadDuration.Type.ITERATION)
								.build())
						.startAfter(StartAfter.builder()
								.value("MyPopulation11")
								.type(StartAfter.Type.POPULATION)
								.build())
						.rampup(90)
						.stopAfter(StopAfter.builder()
								.type(StopAfter.Type.CURRENT_ITERATION)
								.build())
						.build())
				.build();

		final PopulationPolicy population13 = PopulationPolicy.builder()
				.name("MyPopulation13")
				.loadPolicy(PeaksLoadPolicy.builder()
						.minimum(PeakLoadPolicy.builder()
								.users(100)
								.duration(LoadDuration.builder()
										.value(1)
										.type(LoadDuration.Type.ITERATION)
										.build())
								.build())
						.maximum(PeakLoadPolicy.builder()
								.users(500)
								.duration(LoadDuration.builder()
										.value(1)
										.type(LoadDuration.Type.ITERATION)
										.build())
								.build())
						.start(Peak.MAXIMUM)
						.duration(LoadDuration.builder()
								.value(15)
								.type(LoadDuration.Type.ITERATION)
								.build())
						.startAfter(StartAfter.builder()
								.value(60)
								.type(StartAfter.Type.TIME)
								.build())
						.rampup(15)
						.stopAfter(StopAfter.builder()
								.value(60)
								.type(StopAfter.Type.TIME)
								.build())
						.build())
				.build();

		ImmutableLoadDuration loadDuration = LoadDuration.builder()
				.value(100)
				.type(LoadDuration.Type.TIME)
				.build();
		ImmutableCustomPolicyStep customPolicyStep = CustomPolicyStep.builder()
				.when(loadDuration)
				.users(300)
				.build();

		final PopulationPolicy population14 = PopulationPolicy.builder()
				.name("MyPopulation14")
				.loadPolicy(CustomLoadPolicy.builder()
						.steps(Collections.singletonList(customPolicyStep))
						.startAfter(StartAfter.builder()
								.value(30)
								.type(StartAfter.Type.TIME)
								.build())
						.rampup(60)
						.stopAfter(StopAfter.builder()
								.value(30)
								.type(StopAfter.Type.TIME)
								.build())
						.build())
				.build();

		final Scenario scenario1 = Scenario.builder()
				.name("MyScenario1")
				.description("My scenario 1 with 4 populations")
				.slaProfile("MySlaProfile")
				.addPopulations(population11, population12, population13, population14)
				.apm(Apm.builder()
						.addDynatraceTags("myDynatraceTag")
						.addDynatraceAnomalyRules(DynatraceAnomalyRule.builder()
								.metricId("builtin:host.cpu.usage")
								.operator("ABOVE")
								.value("90")
								.severity("PERFORMANCE")
								.build())
						.build())
				.addRendezvousPolicies(rendezvousPolicy)
				.monitoringParameters(MonitoringParameters.builder().beforeFirstVu(6).afterLastVus(99).build())
				.isStoredVariables(true)
				.excludedUrls(ImmutableList.of(".*\\.abcd"))
				.build();

		final Project project = Project.builder()
				.name("MyProject")
				.addScenarios(scenario1)
				.build();

		return project;
	}

	@Test
	public void readScenariosOnlyRequired() throws IOException {
		final Project expectedProject = getScenariosOnlyRequired();
		assertNotNull(expectedProject);

		read("test-scenarios-only-required", expectedProject);
	}

	@Test
	public void readScenariosRequiredAndOptional() throws IOException {
		final Project expectedProject = getScenariosRequiredAndOptional();
		assertNotNull(expectedProject);

		read("test-scenarios-required-and-optional", expectedProject);
	}

	@Test
	public void writeScenariosOnlyRequired() throws IOException {
		final Project expectedProject = getScenariosOnlyRequired();
		assertNotNull(expectedProject);

		write("test-scenarios-only-required", expectedProject);
	}

	@Test
	public void writeScenariosRequiredAndOptional() throws IOException {
		final Project expectedProject = getScenariosRequiredAndOptional();
		assertNotNull(expectedProject);

		write("test-scenarios-required-and-optional", expectedProject);
	}
}
