/*
 * Copyright 2005-2016 the original author or authors.
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
package org.springframework.ldap.transaction.compensating.manager;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextProxy;

import javax.naming.directory.DirContext;
import javax.naming.ldap.LdapContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link TransactionAwareContextSourceProxy}.
 * 
 * @author Mattias Hellborg Arthursson
 */
public class TransactionAwareContextSourceProxyTest {
	private ContextSource contextSourceMock;
	private TransactionAwareContextSourceProxy tested;
	private LdapContext ldapContextMock;
	private DirContext dirContextMock;

	@Before
	public void setUp() throws Exception {
		contextSourceMock = mock(ContextSource.class);
		ldapContextMock = mock(LdapContext.class);
		dirContextMock = mock(DirContext.class);

		tested = new TransactionAwareContextSourceProxy(contextSourceMock);
	}

	@Test
	public void testGetReadWriteContext_LdapContext() {
		when(contextSourceMock.getReadWriteContext()).thenReturn(ldapContextMock);

		DirContext result = tested.getReadWriteContext();

		assertThat(result).isNotNull();
		assertThat(result instanceof LdapContext).isTrue();
		assertThat(result instanceof DirContextProxy).isTrue();
	}

	@Test
	public void testGetReadWriteContext_DirContext() {
		when(contextSourceMock.getReadWriteContext()).thenReturn(dirContextMock);

		DirContext result = tested.getReadWriteContext();

		assertThat(result).as("Result should not be null").isNotNull();
		assertThat(result instanceof DirContext).isTrue();
		assertThat(result instanceof LdapContext).isFalse();
		assertThat(result instanceof DirContextProxy).isTrue();
	}

	@Test
	public void testGetReadOnlyContext_LdapContext() {
		when(contextSourceMock.getReadWriteContext()).thenReturn(ldapContextMock);

		DirContext result = tested.getReadOnlyContext();

		assertThat(result).as("Result should not be null").isNotNull();
		assertThat(result instanceof LdapContext).isTrue();
		assertThat(result instanceof DirContextProxy).isTrue();
	}
}
