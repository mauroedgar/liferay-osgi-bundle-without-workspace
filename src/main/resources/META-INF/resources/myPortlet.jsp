<%@page import="br.com.bv.users.groups.mapping.configuration.UsersGroupsMappingConfigurationDisplayContext" %>
<%@page import="br.com.bv.users.groups.mapping.model.EntryDefinitions" %>
<%@page import="java.util.List" %>
<%@ include file="init.jsp" %>

<%
    String curPortletNameSpace = themeDisplay.getPortletDisplay().getNamespace();
%>

<hr>
<br>
<h4><liferay-ui:message key="bancobv-users-groups-mapping-title"/></h4>
<div id="<portlet:namespace />configuration-container">
    <div class="definitions-wrapper">
        <%
            UsersGroupsMappingConfigurationDisplayContext displayContext =
                    (UsersGroupsMappingConfigurationDisplayContext) request.getAttribute("displayContext");

            List<EntryDefinitions> entriesDefinitions = displayContext.getEntriesDefinitions();

            if (entriesDefinitions.isEmpty()) entriesDefinitions.add(new EntryDefinitions());

            int index = 0; // used on the inputs names, so that they can be unique (for form validation)
            for (EntryDefinitions entryDefinition : entriesDefinitions) {
                index++;
        %>
        <div class="definition-wrapper pb-1">
            <div class="deleted-definition d-none">
                <div class="alert alert-info text-center">
                    <p><liferay-ui:message key="deleted-definition"/></p>
                    <clay:button label="recover-definition" style="primary"
                                 onclick="<%= curPortletNameSpace + "recoverDefinition(event)" %>"/>
                </div>
            </div>
            <div class="active-definition">
                <div class="text-right">
                    <clay:button icon="times" onclick="<%= curPortletNameSpace + "deleteDefinition(event)" %>"
                                 style="secondary"/>
                </div>

                <aui:input name="<%="liferayRole"+index%>" label="liferayRole-label"
                           value="<%= entryDefinition.liferayRole %>"
                           required="true"/>

                <aui:input name="<%="azureRole"+index%>" label="azureRole-label"
                           value="<%= entryDefinition.azureRole %>"
                           required="true"/>

            </div>
            <hr>
        </div>

        <% } %>
    </div>
    <div class="text-right">
        <clay:button icon="plus" monospaced="<%= true %>" style="primary"
                     onclick="<%= curPortletNameSpace + "createNewDefinition(event)" %>"/>
    </div>

    <aui:input id="definitions" name="definitions" type="hidden"/>
</div>

<script>

    function <portlet:namespace />recoverDefinition(event) {
        <portlet:namespace />toggleDefinition(event, true);
    }

    function <portlet:namespace />deleteDefinition(event) {
        <portlet:namespace />toggleDefinition(event, false);
    }

    function <portlet:namespace />toggleDefinition(event, isActive) {
        var wrapper = event.target.closest(".definition-wrapper");
        var deletedDefinition = wrapper.querySelector(".deleted-definition");
        var activeDefinition = wrapper.querySelector(".active-definition");

        if (isActive) {
            deletedDefinition.classList.add("d-none");
            activeDefinition.classList.remove("d-none");
        } else {
            deletedDefinition.classList.remove("d-none");
            activeDefinition.classList.add("d-none");
        }
    }

    function <portlet:namespace />createNewDefinition() {
        var container = <portlet:namespace />getContainer();

        var definition = container.querySelector(".definition-wrapper").cloneNode(true);

        definition.querySelectorAll('input:not([type="checkbox"])').forEach(input => input.value = "");
        definition.querySelectorAll('input[type="checkbox"]').forEach(checkbox => checkbox.checked = true);
        definition.querySelectorAll("select").forEach(select => select.selectedIndex = 0);

        container.querySelector(".definitions-wrapper").appendChild(definition);
    }

    (function () {

            var container = <portlet:namespace />getContainer();

            var getDefinitionsInput = () => container.querySelector("#<portlet:namespace />definitions");
            var getForm = () => getDefinitionsInput().form;

            function handleFormSubmition(event) {
                if (!validateForm(event)) {
                    event.preventDefault();
                    return false;
                }

                var definitions = getDefinitionsFromForm();

                getDefinitionsInput().value = definitions;
            }

            function getDefinitionsFromForm() {
                var activeDefinitions = container.querySelectorAll(".definition-wrapper > .active-definition:not(.d-none)")

                var definitions = {};

                activeDefinitions.forEach(definition => {
                    var json = {
                        liferayRole: definition.querySelector('input[name^="<portlet:namespace />liferayRole"]').value,
                        azureRole: definition.querySelector('input[name^="<portlet:namespace />azureRole"]').value
                    };

                    var liferayRoleK = json.liferayRole;
                    var azureRoleK = json.azureRole;

                    var key = liferayRoleK + ":" + azureRoleK;

                    definitions[key] = json;
                });

                return JSON.stringify(definitions);
            }

            function validateForm(event) {
                var form = event.target;

                var formValidator = Liferay.Form.get(form.id).formValidator;
                formValidator.validate();

                var isValid = !formValidator.hasErrors();

                return isValid;
            }

            function splitStringArray(stringArray) {
                if (!stringArray) return [];

                return stringArray.split(",").map(s => s.trim()).filter(String);
            }

            getForm().addEventListener("submit", handleFormSubmition);

        }

    )
    ();

    function <portlet:namespace />getContainer() {
        return document.querySelector("#<portlet:namespace />configuration-container");
    }
</script>
