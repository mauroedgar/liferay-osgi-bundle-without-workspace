package br.com.bv.users.groups.mapping.configuration;

import com.liferay.configuration.admin.display.ConfigurationFormRenderer;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author mauro.edgar
 */
@Component(
		configurationPid = "br.com.bv.users.groups.mapping.configuration.UsersGroupsMappingConfiguration",
		immediate = true, service = ConfigurationFormRenderer.class
)
public class UsersGroupsMappingConfigurationFormRenderer
	implements ConfigurationFormRenderer {

	@Override
	public String getPid() {
		return "br.com.bv.users.groups.mapping.configuration.UsersGroupsMappingConfiguration";
	}

	@Override
	public Map<String, Object> getRequestParameters(HttpServletRequest httpServletRequest) {
		Map<String, Object> params = new HashMap<>();

		String definitions = ParamUtil.getString(httpServletRequest, "definitions");
		params.put("definitionsJSON", definitions);
		
		return params;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
		throws IOException {
		
		httpServletRequest.setAttribute(
				"displayContext", new UsersGroupsMappingConfigurationDisplayContext(
                        _usersGroupsMappingConfiguration));

		_jspRenderer.renderJSP(_servletContext, httpServletRequest, httpServletResponse,
				"/myPortlet.jsp");
	}
	
	@Activate
	@Modified
	public void activate(Map<String, Object> properties) {
		_usersGroupsMappingConfiguration =
				ConfigurableUtil.createConfigurable(
						UsersGroupsMappingConfiguration.class, properties);
	}

	private volatile UsersGroupsMappingConfiguration _usersGroupsMappingConfiguration;
	
	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(target = "(osgi.web.symbolicname=br.com.bv.users.groups.mapping)", unbind = "-")
	private ServletContext _servletContext;

}
