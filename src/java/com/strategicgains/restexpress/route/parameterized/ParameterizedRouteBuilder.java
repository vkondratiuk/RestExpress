/*
    Copyright 2011, Strategic Gains, Inc.

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/
package com.strategicgains.restexpress.route.parameterized;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.netty.handler.codec.http.HttpMethod;

import com.strategicgains.restexpress.domain.metadata.RouteMetadata;
import com.strategicgains.restexpress.route.RouteBuilder;

/**
 * @author toddf
 * @since Jan 13, 2011
 */
public class ParameterizedRouteBuilder
extends RouteBuilder
{
	private List<String> aliases = new ArrayList<String>();

	/**
	 * @param uri
	 * @param controller
	 * @param routeType
	 */
	public ParameterizedRouteBuilder(String uri, Object controller)
	{
		super(uri, controller);
	}

	/**
	 * Associate another URI pattern to this route, essentially making an alias for the route.
	 * There may be multiple alias URIs for a given route.  Note that new parameter nodes (e.g. {id})
	 * in the URI will be available within the method.  Parameter nodes that are missing from
	 * the alias will not be available in the action method.
	 * 
	 * @param uri the alias URI.
	 * @return the ParameterizedRouteBuilder instance (this).
	 */
	public ParameterizedRouteBuilder alias(String uri)
	{
		if (!aliases.contains(uri))
		{
			aliases.add(uri);
		}

		return this;
	}
	
	@Override
	public RouteMetadata asMetadata()
	{
		RouteMetadata metadata = super.asMetadata();

		for (String alias : aliases)
		{
			metadata.addAlias(alias);
		}

		return metadata;
	}

    @Override
    protected ParameterizedRoute newRoute(String pattern, Object controller, Method action,
        HttpMethod method, boolean shouldSerializeResponse, boolean shouldUseWrappedResponse,
        String name, List<String> supportedFormats, String defaultFormat, Set<String> flags,
        Map<String, Object> parameters)
    {
    	ParameterizedRoute r = new ParameterizedRoute(pattern, controller, action, method, shouldSerializeResponse, shouldUseWrappedResponse, name, flags, parameters);
    	r.addAliases(aliases);
    	return r;
    }
}
