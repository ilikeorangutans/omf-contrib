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

import java.util.UUID;

import org.om.core.api.path.Path;
import org.om.dao.exception.DAOException;

/**
 * @author tome
 * @param <T>
 */
public interface GenericDAO<T> {
	/**
	 * delete
	 */
	void delete(T t) throws DAOException;

	/**
	 * get
	 */
	T get(Path path) throws DAOException;

	/**
	 * get
	 */
	T get(String path) throws DAOException;

	/**
	 * get
	 */
	T get(UUID uuid) throws DAOException;

	/**
	 * save
	 */
	void save(T t) throws DAOException;
}