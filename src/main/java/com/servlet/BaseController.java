/**
 * File: BaseController.java
 * Date: 04 Jun 2013
 * Author: Anton Van Zyl
 */
package com.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anton Van Zyl
 * 
 */
public class BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);
	
	private static int time = (int) System.currentTimeMillis();

	protected static int getUnique() {
		return ++time;
	}


}
