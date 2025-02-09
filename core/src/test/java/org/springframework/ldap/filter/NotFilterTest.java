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

package org.springframework.ldap.filter;

import com.gargoylesoftware.base.testing.EqualsTester;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the NotFilter class.
 * 
 * @author Mattias Hellborg Arthursson
 */
public class NotFilterTest {

	@Test
	public void testConstructor() {
		EqualsFilter filter = new EqualsFilter("a", "b");
		NotFilter notFilter = new NotFilter(filter);

		assertThat(notFilter.encode()).isEqualTo("(!(a=b))");
	}

	@Test
	public void testEquals() {
		EqualsFilter filter = new EqualsFilter("a", "b");
		NotFilter originalObject = new NotFilter(filter);
		NotFilter identicalObject = new NotFilter(filter);
		NotFilter differentObject = new NotFilter(new EqualsFilter("a", "a"));
		NotFilter subclassObject = new NotFilter(filter) {
		};

		new EqualsTester(originalObject, identicalObject, differentObject,
				subclassObject);
	}
}
