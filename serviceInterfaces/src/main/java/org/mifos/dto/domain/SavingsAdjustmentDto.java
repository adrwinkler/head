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

package org.mifos.dto.domain;

public class SavingsAdjustmentDto {

    private final Long savingsId;
    private final Double adjustedAmount;
    private final String note;
    private final Integer paymentId;

    public SavingsAdjustmentDto(Long savingsId, Double adjustedAmount, String note, Integer paymentId) {
        this.savingsId = savingsId;
        this.adjustedAmount = adjustedAmount;
        this.note = note;
        this.paymentId = paymentId;
    }

    public Long getSavingsId() {
        return this.savingsId;
    }

    public Double getAdjustedAmount() {
        return this.adjustedAmount;
    }

    public String getNote() {
        return this.note;
    }

    public Integer getPaymentId() {
        return paymentId;
    }
}