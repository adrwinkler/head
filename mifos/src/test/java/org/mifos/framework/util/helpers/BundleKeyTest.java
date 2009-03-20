/*
 * Copyright (c) 2005-2009 Grameen Foundation USA
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an
 * explanation of the license and how it is applied.
 */
 
package org.mifos.framework.util.helpers;

import java.util.Locale;

import org.mifos.framework.MifosIntegrationTest;
import org.mifos.framework.exceptions.ApplicationException;
import org.mifos.framework.exceptions.SystemException;

public class BundleKeyTest extends MifosIntegrationTest {

	public BundleKeyTest() throws SystemException, ApplicationException {
        super();
    }

    private BundleKey bundleKey = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Locale locale = new Locale("EN");
		bundleKey = new BundleKey(locale, "Key");
	}

	public void testHashCode() {
		assertEquals(905023, bundleKey.hashCode());
	}

	public void testEqualsObject() {
		assertTrue(bundleKey.equals(bundleKey));
		assertFalse(bundleKey.equals(null));
		Locale locale = new Locale("EN");
		assertFalse(bundleKey.equals(new BundleKey(locale, "wrongKey")));
		locale = new Locale("SP");
		assertFalse(bundleKey.equals(new BundleKey(locale, "Key")));
	}

}
