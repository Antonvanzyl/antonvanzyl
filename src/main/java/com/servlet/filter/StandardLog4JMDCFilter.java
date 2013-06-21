package com.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * @author Anton Van Zyl (CP311133)
 * 
 */
public class StandardLog4JMDCFilter implements Filter {

	private static final Logger log = LoggerFactory
			.getLogger(StandardLog4JMDCFilter.class);

	private String mdcServerId = "antonvanzyl";

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {

		addParameters(request, response);

		filterChain.doFilter(request, response);

		MDC.clear();
	}

	protected void addParameters(ServletRequest request,
			ServletResponse response) {

		String mdcSessionID = "<no_session_id>";
		String mdcRequestID = "<no_request_id>";
		String mdcRemoteAddr = "<no_addr>";

		if (request instanceof HttpServletRequest) {

			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpSession session = httpRequest.getSession(false);
			if (session != null) {
				String sessionID = session.getId();
				if (StringUtils.isNotEmpty(sessionID)) {
					mdcSessionID = sessionID;
				}
				String requestID = httpRequest.getRequestedSessionId();
				if (StringUtils.isNotEmpty(requestID)) {
					mdcRequestID = requestID;
				}
			}
		}
		String remoteAddr = request.getRemoteAddr();
		if (StringUtils.isNotEmpty(remoteAddr)) {
			mdcRemoteAddr = remoteAddr;
		}
		MDC.put("ServerID", mdcServerId);
		MDC.put("SessionID", mdcSessionID);
		MDC.put("RequestID", mdcRequestID);
		MDC.put("RemoteAddr", mdcRemoteAddr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		log.info("StandardLog4JMDCFilter loaded [serverId={}]", mdcServerId);
	}
}
