/**

 * CustomerLevel.java    version: xxx



 * Copyright (c) 2005-2006 Grameen Foundation USA

 * 1029 Vermont Avenue, NW, Suite 400, Washington DC 20005

 * All rights reserved.



 * Apache License
 * Copyright (c) 2005-2006 Grameen Foundation USA
 *

 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the

 * License.
 *
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an explanation of the license

 * and how it is applied.

 *

 */
package org.mifos.application.customer.business;

import org.mifos.application.customer.util.helpers.CustomerLevel;
import org.mifos.application.master.business.MasterDataEntity;
import org.mifos.application.productdefinition.util.helpers.PrdApplicableMaster;

/**
 * This class represents customer level e.g client,center etc
 */
public class CustomerLevelEntity extends MasterDataEntity {

	// Set/gotten via .hbm.xml file
	private CustomerLevelEntity parentCustomerLevel;

	public CustomerLevelEntity(CustomerLevel customerLevel) {
		super(customerLevel.getValue());
	}

	/*
	 * Adding a default constructor is hibernate's requirement and should not be
	 * used to create a valid Object.
	 */
	protected CustomerLevelEntity() {
		super();
	}
	
	public CustomerLevelEntity getParentCustomerLevel() {
		return parentCustomerLevel;
	}

	/**
	 * Based on the customer level , it returns the product applicable type.
	 * This is being used, when savings/loan products are to find as per
	 * customer level.
	 */
	public Short getProductApplicableType() {
		if (getId().equals(CustomerLevel.CLIENT.getValue())) {
			return PrdApplicableMaster.CLIENTS.getValue();
		} else if (getId().equals(CustomerLevel.GROUP.getValue())) {
			return PrdApplicableMaster.GROUPS.getValue();
		} else
			return PrdApplicableMaster.CENTERS.getValue();
	}
	
	public boolean isCenter(){
		return getId().equals(CustomerLevel.CENTER.getValue());
	}
	
	public boolean isGroup(){
		return getId().equals(CustomerLevel.GROUP.getValue());
	}

	public boolean isClient(){
		return getId().equals(CustomerLevel.CLIENT.getValue());
	}

}
