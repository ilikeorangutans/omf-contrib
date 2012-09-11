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
package com.om.examples.example3.pojo;

import org.om.core.api.annotation.Entity;
import org.om.core.api.annotation.Id;
import org.om.core.api.annotation.Property;

/**
 * 
 * @author tome
 * 
 */
@Entity
public class MyPojo {
	@Id
	@Property
	private String id;

	@Property
	private double rate;

	@Property
	private int count;

	public int getCount() {
		return count;
	}

	public String getId() {
		return id;
	}

	public double getRate() {
		return rate;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

}
