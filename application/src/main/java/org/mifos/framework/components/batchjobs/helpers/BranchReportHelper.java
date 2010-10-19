/*
 * Copyright (c) 2005-2010 Grameen Foundation USA
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

package org.mifos.framework.components.batchjobs.helpers;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.mifos.customers.business.service.CustomerBusinessService;
import org.mifos.customers.office.business.OfficeBO;
import org.mifos.customers.office.business.service.OfficeBusinessService;
import org.mifos.framework.components.batchjobs.TaskHelper;
import org.mifos.framework.components.batchjobs.exceptions.BatchJobException;
import org.mifos.framework.exceptions.ServiceException;
import org.mifos.framework.hibernate.helper.StaticHibernateUtil;
import org.mifos.reports.branchreport.BranchReportBO;
import org.mifos.reports.business.service.BranchReportConfigService;
import org.mifos.reports.business.service.BranchReportService;
import org.mifos.reports.business.service.IBranchReportService;
import org.mifos.reports.business.service.ReportServiceFactory;

import java.util.Date;
import java.util.List;

public class BranchReportHelper extends TaskHelper {

    private CustomerBusinessService customerBusinessService;
    private OfficeBusinessService officeBusinessService;
    private IBranchReportService branchReportService;
    private BranchReportConfigService branchReportConfigService;

    public BranchReportHelper() {
        super();
        customerBusinessService = new CustomerBusinessService();
        officeBusinessService = new OfficeBusinessService();
        branchReportService = new BranchReportService();
        branchReportConfigService = ReportServiceFactory.getBranchReportConfigService();
    }

    @Override
    public void execute(long timeInMillis) throws BatchJobException {
        Session session = StaticHibernateUtil.getSessionTL();
        StaticHibernateUtil.startTransaction();
        Date runDate = new Date(timeInMillis);

        try {
            removeExistingBranchReportsForGivenRunDate(runDate);
            populateBranchReportBatch(session, runDate);
            StaticHibernateUtil.commitTransaction();
        } catch (HibernateException e) {
            StaticHibernateUtil.rollbackTransaction();
            throw new BatchJobException(e);
        } catch (ServiceException e) {
            throw new BatchJobException(e);
        }
    }

    void populateBranchReportBatch(Session session, Date runDate) throws BatchJobException, ServiceException {
        List<OfficeBO> branchOffices = officeBusinessService.getBranchOffices();
        if (branchOffices == null) {
            return;
        }
        for (OfficeBO branchOffice : branchOffices) {
            createBranchReport(session, branchOffice, runDate);
        }
    }

    BranchReportBO createBranchReport(Session session, OfficeBO branchOffice, Date runDate) throws BatchJobException {
        BranchReportBO branchReport = new BranchReportBO(branchOffice.getOfficeId(), runDate);

        new BranchReportClientSummaryHelper(customerBusinessService, branchReportService, branchReportConfigService)
                .populateClientSummary(branchReport, branchOffice);
        new BranchReportLoanArrearsAgingHelper(branchReport, branchReportService, branchReportConfigService)
                .populateLoanArrearsAging();
        new BranchReportStaffSummaryHelper(branchReport, branchReportService, branchReportConfigService)
                .populateStaffSummary();
        new BranchReportStaffingLevelSummaryHelper(branchReport, branchReportService).populateStaffingLevelSummary();
        new BranchReportLoanDetailsHelper(branchReport, branchReportService, branchReportConfigService)
                .populateLoanDetails();
        new BranchReportLoanArrearsProfileHelper(branchReport, branchReportService, branchReportConfigService)
                .populateLoanArrearsProfile();
        session.save(branchReport);
        session.flush();
        session.clear();
        return branchReport;
    }

    void removeExistingBranchReportsForGivenRunDate(Date runDate) throws ServiceException {

        if (!branchReportService.isReportDataPresentForRundate(runDate)) {
            return;
        }

        branchReportService.removeBranchReports(branchReportService.getBranchReports(runDate));
    }

}
