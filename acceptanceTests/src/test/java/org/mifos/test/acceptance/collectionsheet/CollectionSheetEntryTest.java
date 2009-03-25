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
 
package org.mifos.test.acceptance.collectionsheet;

import org.dbunit.dataset.IDataSet;
import org.mifos.test.acceptance.framework.DbUnitUtilities;
import org.mifos.test.acceptance.framework.MifosPage;
import org.mifos.test.acceptance.framework.TimeMachine;
import org.mifos.test.acceptance.framework.UiTestCaseBase;
import org.mifos.test.acceptance.framework.collectionsheet.CollectionSheetEntryConfirmationPage;
import org.mifos.test.acceptance.framework.collectionsheet.CollectionSheetEntryEnterDataPage;
import org.mifos.test.acceptance.framework.collectionsheet.CollectionSheetEntryPreviewDataPage;
import org.mifos.test.acceptance.framework.collectionsheet.CollectionSheetEntrySelectPage;
import org.mifos.test.acceptance.framework.collectionsheet.CollectionSheetEntrySelectPage.SubmitFormParameters;
import org.mifos.test.acceptance.framework.testhelpers.CollectionSheetEntryTestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.joda.time.DateTime;

@ContextConfiguration(locations={"classpath:ui-test-context.xml"})
@Test(sequential=true, groups={"CollectionSheetEntryTest","acceptance","ui"})
public class CollectionSheetEntryTest extends UiTestCaseBase {

    private static final String FEE_TRXN_DETAIL = "FEE_TRXN_DETAIL";
    private static final String FINANCIAL_TRXN = "FINANCIAL_TRXN";
    private static final String CUSTOMER_ACCOUNT_ACTIVITY = "CUSTOMER_ACCOUNT_ACTIVITY";
    private static final String CUSTOMER_TRXN_DETAIL = "CUSTOMER_TRXN_DETAIL";
    private static final String ACCOUNT_TRXN = "ACCOUNT_TRXN";
    private static final String LOAN_TRXN_DETAIL = "LOAN_TRXN_DETAIL";
    private static final String ACCOUNT_PAYMENT = "ACCOUNT_PAYMENT";
    private static final String LOAN_SUMMARY = "LOAN_SUMMARY";
    private static final String LOAN_SCHEDULE = "LOAN_SCHEDULE";
    private static final String LOAN_ACTIVITY_DETAILS = "LOAN_ACTIVITY_DETAILS";
    private static final String ACCOUNT_STATUS_CHANGE_HISTORY = "ACCOUNT_STATUS_CHANGE_HISTORY";
    private static final String CUSTOMER_ATTENDANCE = "CUSTOMER_ATTENDANCE";
    
    
    
    @Autowired
    private DriverManagerDataSource dataSource;
    @Autowired
    private DbUnitUtilities dbUnitUtilities;
    
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    // one of the dependent methods throws Exception
    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();
        TimeMachine timeMachine = new TimeMachine(selenium);
        DateTime targetTime = new DateTime(2009,2,23,2,0,0,0);
        timeMachine.setDateTime(targetTime);
    }

    @AfterMethod
    public void logOut() {
        (new MifosPage(selenium)).logout();
        new TimeMachine(selenium).resetDateTime();       
    }
  
    @SuppressWarnings("PMD.SignatureDeclareThrowsException") // one of the dependent methods throws Exception
    public void defaultAdminUserSelectsValidCollectionSheetEntryParameters() throws Exception {
        SubmitFormParameters formParameters = new SubmitFormParameters();
        formParameters.setBranch("Office1");
        formParameters.setLoanOfficer("Bagonza Wilson");
        formParameters.setCenter("Center1");
        formParameters.setPaymentMode("Cash");
        
        dbUnitUtilities.loadDataFromFile("acceptance_small_001_dbunit.xml.zip", dataSource);
        
        CollectionSheetEntrySelectPage selectPage = 
            new CollectionSheetEntryTestHelper(selenium).loginAndNavigateToCollectionSheetEntrySelectPage();
        selectPage.verifyPage();
        CollectionSheetEntryEnterDataPage enterDataPage = 
            selectPage.submitAndGotoCollectionSheetEntryEnterDataPage(formParameters);
        enterDataPage.enterAccountValue(0,0,99.0);
        enterDataPage.enterAccountValue(1,1,0.0);
        enterDataPage.enterAccountValue(2,0,0.0);
        CollectionSheetEntryPreviewDataPage previewPage = 
            enterDataPage.submitAndGotoCollectionSheetEntryPreviewDataPage();
        previewPage.verifyPage(formParameters);
        CollectionSheetEntryConfirmationPage confirmationPage = 
            previewPage.submitAndGotoCollectionSheetEntryConfirmationPage();
        confirmationPage.verifyPage();
        
        verifyCollectionSheetData("acceptance_small_002_dbunit.xml.zip");
    }
    
    @SuppressWarnings("PMD.SignatureDeclareThrowsException") // one of the dependent methods throws Exception
    public void twoLoansWithSameProductHasMergedLoanAmount() throws Exception {
        SubmitFormParameters formParameters = getFormParametersForTestOffice();
        dbUnitUtilities.loadDataFromFile("acceptance_small_003_dbunit.xml.zip", dataSource);
        CollectionSheetEntryEnterDataPage enterDataPage = navigateToCollectionSheetEntryEnterData(formParameters);
        //check amount due for client who has two loan accounts on the same product
        enterDataPage.verifyLoanAmountValue(3, 0, 2088.0);
        enterDataPage.cancel();

    }

    @SuppressWarnings("PMD.SignatureDeclareThrowsException") // one of the dependent methods throws Exception
    public void defaultAmountsForMediumCenterSavedToDatabase() throws Exception {
        try {
            SubmitFormParameters formParameters = new SubmitFormParameters();
            formParameters.setBranch("MyOffice1233266931234");
            formParameters.setLoanOfficer("Joe1233266933656 Guy1233266933656");
            formParameters.setCenter("MyCenter1233266935468");
            formParameters.setPaymentMode("Cash");

            dbUnitUtilities.loadDataFromFile("acceptance_medium_005_dbunit.xml.zip", dataSource);
            CollectionSheetEntryConfirmationPage confirmPage = new CollectionSheetEntryTestHelper(selenium).submitDefaultCollectionSheetEntryData(formParameters);
            confirmPage.verifyPage();
            verifyCollectionSheetData("ColSheetLoanTest_001_result_dbunit.xml.zip");
        } catch (Exception e) {
            dbUnitUtilities.dumpDatabaseToTimestampedFileInConfigurationDirectory(dataSource);
            throw e;
        }
    }

    @SuppressWarnings("PMD.SignatureDeclareThrowsException") // one of the dependent methods throws Exception
    private void verifyCollectionSheetData(String filename) throws Exception {
        IDataSet expectedDataSet = dbUnitUtilities.getDataSetFromFile(filename);
        IDataSet databaseDataSet = dbUnitUtilities.getDataSetForTables(dataSource, new String[] { FEE_TRXN_DETAIL,
                ACCOUNT_TRXN, 
                LOAN_TRXN_DETAIL, 
                ACCOUNT_PAYMENT, 
                LOAN_SUMMARY, 
                LOAN_SCHEDULE, 
                LOAN_ACTIVITY_DETAILS,
                ACCOUNT_STATUS_CHANGE_HISTORY,
                FINANCIAL_TRXN,
                CUSTOMER_ACCOUNT_ACTIVITY,
                CUSTOMER_ATTENDANCE,
                CUSTOMER_TRXN_DETAIL });


        dbUnitUtilities.verifyTablesWithoutSorting(expectedDataSet, databaseDataSet);   
        dbUnitUtilities.verifyTransactionsAfterSortingTables(expectedDataSet, databaseDataSet);

    }


    private CollectionSheetEntryEnterDataPage navigateToCollectionSheetEntryEnterData(SubmitFormParameters formParameters) {
        CollectionSheetEntrySelectPage selectPage = 
            new CollectionSheetEntryTestHelper(selenium).loginAndNavigateToCollectionSheetEntrySelectPage();
        selectPage.verifyPage();
        CollectionSheetEntryEnterDataPage enterDataPage = selectPage.submitAndGotoCollectionSheetEntryEnterDataPage(formParameters);
        enterDataPage.verifyPage();
        return enterDataPage;
    }
    
 
    private SubmitFormParameters getFormParametersForTestOffice() {
        SubmitFormParameters formParameters = new SubmitFormParameters();
      formParameters.setBranch("MyOffice1233265929385");
      formParameters.setLoanOfficer("Joe1233265931256 Guy1233265931256");
      formParameters.setCenter("MyCenter1233265933427");
      formParameters.setPaymentMode("Cash");
        return formParameters;
    }

}

