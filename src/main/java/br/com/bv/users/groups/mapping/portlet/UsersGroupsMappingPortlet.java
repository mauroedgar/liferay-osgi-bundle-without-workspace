package br.com.bv.users.groups.mapping.portlet;

import br.com.bv.users.groups.mapping.configuration.UsersGroupsMappingConfigurationFormRenderer;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * 
 * Hidden portlet created so that 
 * {@link UsersGroupsMappingConfigurationFormRenderer} works.
 * 
 * @author mauro.edgar
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.hidden"
	},
	service = Portlet.class
)
public class UsersGroupsMappingPortlet extends MVCPortlet {
}
