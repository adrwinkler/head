//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1.2-b01-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.04.03 at 01:24:13 PM PDT 
//


package org.mifos.migration.generated;

import java.math.BigDecimal;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


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
 
@XmlRegistry
public class ObjectFactory {

    private final static QName _WeeksBetweenMeetings_QNAME = new QName("", "weeksBetweenMeetings");
    private final static QName _MeetingWeekDay_QNAME = new QName("", "meetingWeekDay");
    private final static QName _Telephone_QNAME = new QName("", "telephone");
    private final static QName _Address1_QNAME = new QName("", "address1");
    private final static QName _StringValue_QNAME = new QName("", "stringValue");
    private final static QName _DateValue_QNAME = new QName("", "dateValue");
    private final static QName _PostalCode_QNAME = new QName("", "postalCode");
    private final static QName _Address2_QNAME = new QName("", "address2");
    private final static QName _MonthsBetweenMeetings_QNAME = new QName("", "monthsBetweenMeetings");
    private final static QName _MeetingWeekDayOccurence_QNAME = new QName("", "meetingWeekDayOccurence");
    private final static QName _Location_QNAME = new QName("", "location");
    private final static QName _Name_QNAME = new QName("", "name");
    private final static QName _OfficeId_QNAME = new QName("", "officeId");
    private final static QName _LoanOfficerId_QNAME = new QName("", "loanOfficerId");
    private final static QName _MfiJoiningDate_QNAME = new QName("", "mfiJoiningDate");
    private final static QName _CityDistrict_QNAME = new QName("", "cityDistrict");
    private final static QName _Amount_QNAME = new QName("", "amount");
    private final static QName _FeeId_QNAME = new QName("", "feeId");
    private final static QName _Country_QNAME = new QName("", "country");
    private final static QName _NumericValue_QNAME = new QName("", "numericValue");
    private final static QName _DayOfMonth_QNAME = new QName("", "dayOfMonth");
    private final static QName _FieldId_QNAME = new QName("", "fieldId");
    private final static QName _State_QNAME = new QName("", "state");
    private final static QName _Address3_QNAME = new QName("", "address3");
    private final static QName _ExternalId_QNAME = new QName("", "externalId");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.mifos.migration.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Center }
     * 
     */
    public Center createCenter() {
        return new Center();
    }

    /**
     * Create an instance of {@link CustomField }
     * 
     */
    public CustomField createCustomField() {
        return new CustomField();
    }

    /**
     * Create an instance of {@link FeeAmount }
     * 
     */
    public FeeAmount createFeeAmount() {
        return new FeeAmount();
    }

    /**
     * Create an instance of {@link WeeklyMeeting }
     * 
     */
    public WeeklyMeeting createWeeklyMeeting() {
        return new WeeklyMeeting();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link MonthlyMeeting }
     * 
     */
    public MonthlyMeeting createMonthlyMeeting() {
        return new MonthlyMeeting();
    }

    /**
     * Create an instance of {@link MifosDataExchange }
     * 
     */
    public MifosDataExchange createMifosDataExchange() {
        return new MifosDataExchange();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "weeksBetweenMeetings")
    public JAXBElement<Short> createWeeksBetweenMeetings(Short value) {
        return new JAXBElement<Short>(_WeeksBetweenMeetings_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WeekDayChoice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "meetingWeekDay")
    public JAXBElement<WeekDayChoice> createMeetingWeekDay(WeekDayChoice value) {
        return new JAXBElement<WeekDayChoice>(_MeetingWeekDay_QNAME, WeekDayChoice.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "telephone")
    public JAXBElement<String> createTelephone(String value) {
        return new JAXBElement<String>(_Telephone_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "address1")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createAddress1(String value) {
        return new JAXBElement<String>(_Address1_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "stringValue")
    public JAXBElement<String> createStringValue(String value) {
        return new JAXBElement<String>(_StringValue_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "dateValue")
    public JAXBElement<XMLGregorianCalendar> createDateValue(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DateValue_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "postalCode")
    public JAXBElement<String> createPostalCode(String value) {
        return new JAXBElement<String>(_PostalCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "address2")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createAddress2(String value) {
        return new JAXBElement<String>(_Address2_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "monthsBetweenMeetings")
    public JAXBElement<Short> createMonthsBetweenMeetings(Short value) {
        return new JAXBElement<Short>(_MonthsBetweenMeetings_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WeekDayOccurence }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "meetingWeekDayOccurence")
    public JAXBElement<WeekDayOccurence> createMeetingWeekDayOccurence(WeekDayOccurence value) {
        return new JAXBElement<WeekDayOccurence>(_MeetingWeekDayOccurence_QNAME, WeekDayOccurence.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "location")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createLocation(String value) {
        return new JAXBElement<String>(_Location_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "name")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createName(String value) {
        return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "officeId")
    public JAXBElement<Short> createOfficeId(Short value) {
        return new JAXBElement<Short>(_OfficeId_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "loanOfficerId")
    public JAXBElement<Short> createLoanOfficerId(Short value) {
        return new JAXBElement<Short>(_LoanOfficerId_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "mfiJoiningDate")
    public JAXBElement<XMLGregorianCalendar> createMfiJoiningDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_MfiJoiningDate_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cityDistrict")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createCityDistrict(String value) {
        return new JAXBElement<String>(_CityDistrict_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "amount")
    public JAXBElement<BigDecimal> createAmount(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Amount_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "feeId")
    public JAXBElement<Short> createFeeId(Short value) {
        return new JAXBElement<Short>(_FeeId_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "country")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createCountry(String value) {
        return new JAXBElement<String>(_Country_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "numericValue")
    public JAXBElement<Integer> createNumericValue(Integer value) {
        return new JAXBElement<Integer>(_NumericValue_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "dayOfMonth")
    public JAXBElement<Short> createDayOfMonth(Short value) {
        return new JAXBElement<Short>(_DayOfMonth_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fieldId")
    public JAXBElement<Short> createFieldId(Short value) {
        return new JAXBElement<Short>(_FieldId_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "state")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createState(String value) {
        return new JAXBElement<String>(_State_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "address3")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createAddress3(String value) {
        return new JAXBElement<String>(_Address3_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "externalId")
    public JAXBElement<String> createExternalId(String value) {
        return new JAXBElement<String>(_ExternalId_QNAME, String.class, null, value);
    }

}
