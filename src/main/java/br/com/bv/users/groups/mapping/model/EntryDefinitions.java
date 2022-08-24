package br.com.bv.users.groups.mapping.model;

import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author mauro.edgar
 */
public class EntryDefinitions {

    public String liferayRole;
    public String azureRole;

    public String liferayRoleK;
    public String azureRoleK;

    public EntryDefinitions() {
    }

    public EntryDefinitions(String liferayRole, String azureRole) {
        this.liferayRole = liferayRole;
        this.azureRole = azureRole;
    }

    public EntryDefinitions(JSONObject entryJSON, String definitionsJSONKey) {
        this.liferayRole = (String) entryJSON.get("liferayRole");
        this.azureRole = (String) entryJSON.get("azureRole");

        String[] parts = definitionsJSONKey.split(":");
        this.liferayRoleK = parts[0];
        this.azureRoleK = parts[1];
    }

    public String getLiferayRole() {
        return liferayRole;
    }

    public void setLiferayRole(String liferayRole) {
        this.liferayRole = liferayRole;
    }

    public String getAzureRole() {
        return azureRole;
    }

    public void setAzureRole(String azureRole) {
        this.azureRole = azureRole;
    }
}