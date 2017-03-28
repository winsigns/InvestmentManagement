package com.winsigns.investment.webGateway.webGateway.http.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponents;

@Controller
public class IndexController {
	private static String BROWSER = "/";
	private static String INDEX = "/index.html";

	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET,
	    produces = MediaType.TEXT_HTML_VALUE)
	public View index(HttpServletRequest request) {		  
	  return getRedirectView(request, false);
	}

	private View getRedirectView(HttpServletRequest request, boolean browserRelative) {
	  ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromRequest(request);
	  UriComponents components = builder.build();
	  String path = components.getPath() == null ? "" : components.getPath();
	  if (!browserRelative) {
	    builder.path(BROWSER);
	  }
	  builder.path(INDEX);
	  builder.fragment(browserRelative ? path.substring(0, path.lastIndexOf("/")) : path);
	  return new RedirectView(builder.build().toUriString());
	}
}
