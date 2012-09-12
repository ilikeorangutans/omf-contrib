/*
 * Copyright 2012 Tom Everett
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.om.examples.strutsexample.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

/**
 * This action is, essentially, index.html. it binds to no action name and the
 * "/" namespace and pushes the user to the the login
 * 
 * @author tome
 */
@Action("")
@Namespace("/")
@Result(name = "success", location = "/index.jsp")
public class IndexAction extends ActionSupport {
	/**
    * 
    */
	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		try {
			return SUCCESS;
		} catch (final Exception e) {
			throw new Exception("Exception in execute", e);
		}
	}
}
