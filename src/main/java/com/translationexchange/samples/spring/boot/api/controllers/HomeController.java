/**
 * Copyright (c) 2016 Translation Exchange, Inc. All rights reserved.
 *
 *  _______                  _       _   _             ______          _
 * |__   __|                | |     | | (_)           |  ____|        | |
 *    | |_ __ __ _ _ __  ___| | __ _| |_ _  ___  _ __ | |__  __  _____| |__   __ _ _ __   __ _  ___
 *    | | '__/ _` | '_ \/ __| |/ _` | __| |/ _ \| '_ \|  __| \ \/ / __| '_ \ / _` | '_ \ / _` |/ _ \
 *    | | | | (_| | | | \__ \ | (_| | |_| | (_) | | | | |____ >  < (__| | | | (_| | | | | (_| |  __/
 *    |_|_|  \__,_|_| |_|___/_|\__,_|\__|_|\___/|_| |_|______/_/\_\___|_| |_|\__,_|_| |_|\__, |\___|
 *                                                                                        __/ |
 *                                                                                       |___/
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.translationexchange.samples.spring.boot.api.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.translationexchange.core.Session;
import com.translationexchange.core.Utils;
import com.translationexchange.j2ee.servlets.LocalizedServlet;

@RestController
@EnableAutoConfiguration
public class HomeController {

	protected Session getTml(HttpServletRequest request) {
	    return (Session) request.getAttribute(LocalizedServlet.TML_SESSION_KEY);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response)  throws IOException {
		return getTml(request).translate("Hello World");
	}

	@RequestMapping(value = "/numbers", method = RequestMethod.GET)
	public String numbers(HttpServletRequest request, HttpServletResponse response)  throws IOException {
		return getTml(request).translate("You have {count || message}", Utils.buildMap("count", 5));
	}

	@RequestMapping(value = "/genders", method = RequestMethod.GET)
	public String genders(HttpServletRequest request, HttpServletResponse response)  throws IOException {
		return getTml(request).translate("{actor} uploaded {count || photo} to {actor | his, her} photo album.", Utils.buildMap(
				"count", 5,
				"actor", Utils.buildMap(
							"gender", "male",
							"value", "Michael"
						)
				));
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/translate", method = RequestMethod.GET)
	public String translate(HttpServletRequest request, HttpServletResponse response)  throws IOException {
		String label = request.getParameter("label");
		String description = request.getParameter("description");
		Map<String, Object> tokens = null;
		Map<String, Object> options = null;
		
		if (request.getParameter("tokens") != null) {
			tokens = (Map<String, Object>) Utils.parseJSON(request.getParameter("tokens"));
		}
		
		if (request.getParameter("options") != null) {
			options = (Map<String, Object>) Utils.parseJSON(request.getParameter("options"));
		}
		
		return getTml(request).translate(label, description, tokens, options);
	}
	
}