package br.com.bv.users.groups.mapping.configuration;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author mauro.edgar
 */
@ExtendedObjectClassDefinition(
    category = "users",
    scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "br.com.bv.users.groups.mapping.configuration.UsersGroupsMappingConfiguration",
	localization = "content/Language",
	name = "bancobv-users-groups-mapping"
)
public interface UsersGroupsMappingConfiguration {

	@Meta.AD(
		description = "json-depara-layout-description",
		name = "definitions-json", required = false
	)
	public String definitionsJSON();
		
}
