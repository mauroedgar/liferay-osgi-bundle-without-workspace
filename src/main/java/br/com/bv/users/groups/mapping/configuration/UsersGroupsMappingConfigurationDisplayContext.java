package br.com.bv.users.groups.mapping.configuration;

import br.com.bv.users.groups.mapping.model.EntryDefinitions;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mauro.edgar
 */
public class UsersGroupsMappingConfigurationDisplayContext {

    private String definitionsJSON;

    public UsersGroupsMappingConfigurationDisplayContext(
            UsersGroupsMappingConfiguration usersGroupsMappingConfiguration) {
        super();
        this.definitionsJSON = usersGroupsMappingConfiguration.definitionsJSON();
    }

    public List<EntryDefinitions> getEntriesDefinitions() {
        JSONObject definitions;
        try {
            definitions = JSONFactoryUtil.createJSONObject(definitionsJSON);
        } catch (JSONException e) {
            _log.error(e);
            return new ArrayList<EntryDefinitions>();
        }

        return definitions.keySet().stream()
                .map(key ->
                        new EntryDefinitions(definitions.getJSONObject(key), key))
                .sorted((defA, defB) -> defA.liferayRoleK.compareTo(defB.liferayRoleK))
                .collect(Collectors.toList());
    }

    private final Log _log = LogFactoryUtil.getLog(
            UsersGroupsMappingConfigurationDisplayContext.class);
}
