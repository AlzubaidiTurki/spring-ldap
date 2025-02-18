/*
 * Copyright 2005-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.ldap.transaction.compensating;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ldap.core.LdapOperations;
import org.springframework.ldap.support.LdapUtils;

import javax.naming.ldap.LdapName;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UnbindOperationExecutorTest {
	private LdapOperations ldapOperationsMock;

	@Before
	public void setUp() throws Exception {
		ldapOperationsMock = mock(LdapOperations.class);;
	}

	@Test
	public void testPerformOperation() {
		LdapName expectedOldName = LdapUtils.newLdapName("cn=oldDn");
		LdapName expectedTempName = LdapUtils.newLdapName("cn=newDn");
		UnbindOperationExecutor tested = new UnbindOperationExecutor(
				ldapOperationsMock, expectedOldName, expectedTempName);

		// Perform test
		tested.performOperation();

		verify(ldapOperationsMock).rename(expectedOldName, expectedTempName);
	}

	@Test
	public void testCommit() {
		LdapName expectedOldName = LdapUtils.newLdapName("cn=oldDn");
		LdapName expectedTempName = LdapUtils.newLdapName("cn=newDn");
		UnbindOperationExecutor tested = new UnbindOperationExecutor(
				ldapOperationsMock, expectedOldName, expectedTempName);

		// Perform test
		tested.commit();
		verify(ldapOperationsMock).unbind(expectedTempName);
	}

	@Test
	public void testRollback() {
		LdapName expectedOldName = LdapUtils.newLdapName("cn=oldDn");
		LdapName expectedTempName = LdapUtils.newLdapName("cn=newDn");
		UnbindOperationExecutor tested = new UnbindOperationExecutor(
				ldapOperationsMock, expectedOldName, expectedTempName);



		// Perform test
		tested.rollback();
		verify(ldapOperationsMock).rename(expectedTempName, expectedOldName);
	}
}
