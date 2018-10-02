package com.neotys.neoload.model.scenario;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonInclude(value=Include.NON_ABSENT)
@JsonPropertyOrder({"users", "duration", "start_after", "rampup", "stop_after"})
@JsonDeserialize(as = ImmutableConstantLoadPolicy.class)
@Value.Immutable
public interface ConstantLoadPolicy extends LoadPolicy {
    int getUsers();
}
