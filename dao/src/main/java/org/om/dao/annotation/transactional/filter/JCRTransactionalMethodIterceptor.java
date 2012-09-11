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
package org.om.dao.annotation.transactional.filter;

import java.lang.reflect.Method;

import javax.jcr.Session;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.jackrabbit.rmi.client.ClientXASession;
import org.om.dao.annotation.transactional.Transactional;
import org.om.dao.exception.DAOException;
import org.om.dao.util.SessionUtil;

/**
 * @author tome
 */
public class JCRTransactionalMethodIterceptor implements MethodInterceptor {
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		try {
			Object result;
			final Method method = methodInvocation.getMethod();
			/*
			 * get @Transactional annotation on field
			 */
			final Transactional transactional = method.getAnnotation(Transactional.class);
			if (null != transactional) {
				/*
				 * get JCR session. ugh. Sorry.
				 */
				final Session session = SessionUtil.getJCRSession();
				if (null != session) {
					/*
					 * upcast
					 */
					final ClientXASession xaSession = (ClientXASession) session;
					/*
					 * transaction id
					 */
					final Xid xid = new OMXid();
					try {
						/*
						 * start tx
						 */
						xaSession.start(xid, XAResource.TMNOFLAGS);
						/*
						 * invoke
						 */
						result = methodInvocation.proceed();
						/*
						 * commit!
						 */
						xaSession.commit(xid, true);
					} catch (final Exception e) {
						/*
						 * uh-oh, rollback
						 */
						xaSession.rollback(xid);
						throw new RuntimeException("Exception in invoke ", e);
					}
				} else {
					throw new Exception("Unable to get JCR Session");
				}
			} else {
				result = methodInvocation.proceed();
			}
			return result;
		} catch (final Exception e) {
			throw new DAOException("Exception in invoke", e);
		}
	}
}
