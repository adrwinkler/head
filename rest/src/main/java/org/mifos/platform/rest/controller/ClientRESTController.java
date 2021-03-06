/*
 * Copyright (c) 2005-2011 Grameen Foundation USA
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
package org.mifos.platform.rest.controller;

import org.mifos.application.servicefacade.CenterServiceFacade;
import org.mifos.application.servicefacade.ClientServiceFacade;
import org.mifos.customers.client.business.ClientBO;
import org.mifos.customers.persistence.CustomerDao;
import org.mifos.dto.domain.CustomerChargesDetailsDto;
import org.mifos.dto.screen.ClientInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ClientRESTController {

    @Autowired
    private ClientServiceFacade clientServiceFacade;

    @Autowired
    private CenterServiceFacade centerServiceFacade;

    @Autowired
    private CustomerDao customerDao;

    @RequestMapping(value = "client/num-{globalCustNum}", method = RequestMethod.GET)
    public @ResponseBody
    ClientInformationDto getClientByNumber(@PathVariable String globalCustNum) {
        return clientServiceFacade.getClientInformationDto(globalCustNum);
    }

    @RequestMapping(value = "client/num-{globalCustNum}/charges", method = RequestMethod.GET)
    public @ResponseBody
    CustomerChargesDetailsDto getClientChargesByNumber(@PathVariable String globalCustNum) {
        ClientBO clientBO = customerDao.findClientBySystemId(globalCustNum);

        CustomerChargesDetailsDto clientCharges = centerServiceFacade.retrieveChargesDetails(clientBO.getCustomerId());
        clientCharges.addActivities(centerServiceFacade.retrieveRecentActivities(clientBO.getCustomerId(), 3));

        return clientCharges;
    }
}
