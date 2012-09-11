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

package org.om.dao.genericdao;

import org.om.core.api.annotation.Entity;
import org.om.core.api.annotation.Id;
import org.om.core.api.annotation.Property;

@Entity
public class TestEntity {
	@Id
	@Property
	private String id;
	@Property
	private String foobar;
	@Property(defaultValue = "3131", name = "mycoolfield")
	private int blargh;

	public TestEntity() {
	}

	public int getBlargh() {
		return blargh;
	}

	public String getFoobar() {
		return foobar;
	}

	public String getId() {
		return id;
	}

	public void setBlargh(int blargh) {
		this.blargh = blargh;
	}

	public void setFoobar(String foobar) {
		this.foobar = foobar;
	}

	public void setId(String id) {
		this.id = id;
	}
}
