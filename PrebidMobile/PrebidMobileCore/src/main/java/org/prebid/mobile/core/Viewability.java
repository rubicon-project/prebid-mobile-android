/*
 *    Copyright 2016 Prebid.org, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.prebid.mobile.core;

import java.util.ArrayList;

/**
 * Viewability class holds viewability data set by the publisher.
 */
public class Viewability {

    private double score;
    private ArrayList<Prebid.ViewabilityVendor> vendors;

    Viewability() {
        vendors = new ArrayList<Prebid.ViewabilityVendor>();
    }

    /**
     * Creates a Viewability object with the specified values
     *
     */
    Viewability(ArrayList<Prebid.ViewabilityVendor> vendors, float score) {
        this.vendors = vendors;
        this.score = score;
    }

    public static void updateScore(double score, String adUnitCode) {
        AdUnit adUnit = BidManager.getAdUnitByCode(adUnitCode);
        if (adUnit != null) {
            Viewability viewability = adUnit.getViewability();
            viewability.setScore(score);
            adUnit.setViewability(viewability);
        }
    }

    public static void updateVendors(ArrayList<Prebid.ViewabilityVendor> vendor, String adUnitCode) {
        AdUnit adUnit = BidManager.getAdUnitByCode(adUnitCode);
        if (adUnit != null) {
            Viewability viewability = adUnit.getViewability();
            viewability.setVendors(vendor);
            adUnit.setViewability(viewability);
        }
    }

    public double getScore() {
        return this.score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public ArrayList<Prebid.ViewabilityVendor> getVendors() {
        return this.vendors;
    }

    public void setVendors(ArrayList<Prebid.ViewabilityVendor> vendor) {
        this.vendors = vendor;
    }
}