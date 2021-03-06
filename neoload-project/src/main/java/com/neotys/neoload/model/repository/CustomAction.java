package com.neotys.neoload.model.repository;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neotys.neoload.model.core.Element;

/**
 * @deprecated As of v3, replaced by an associated class from v3 version.
 */
@Value.Immutable
@JsonDeserialize(as = ImmutableCustomAction.class)
@Deprecated
public interface CustomAction extends Element {
	String getType();
	boolean isHit();
	List<CustomActionParameter> getParameters();
	Optional<Path> getLibraryPath();
}
