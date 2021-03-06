/*
 * Copyright (C) 2010 Paul Watts (paulcwatts@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onebusaway.android.io.test;

import org.junit.Test;
import org.onebusaway.android.io.elements.ObaShape;
import org.onebusaway.android.io.elements.ObaStop;
import org.onebusaway.android.io.elements.ObaStopGrouping;
import org.onebusaway.android.io.request.ObaStopsForRouteRequest;
import org.onebusaway.android.io.request.ObaStopsForRouteResponse;

import java.util.List;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Tests requests and parsing JSON responses from /res/raw for the OBA server API
 * to get stops served by a specific route
 */
public class StopsForRouteRequestTest extends ObaTestCase {

    @Test
    public void testKCMRoute() {
        ObaStopsForRouteRequest.Builder builder =
                new ObaStopsForRouteRequest.Builder(getTargetContext(), "1_44");
        ObaStopsForRouteRequest request = builder.build();
        ObaStopsForRouteResponse response = request.call();
        assertOK(response);
        final List<ObaStop> stops = response.getStops();
        assertNotNull(stops);
        assertTrue(stops.size() > 0);
        // Check one
        final ObaStop stop = stops.get(0);
        assertEquals("1_10911", stop.getId());
        final ObaStopGrouping[] groupings = response.getStopGroupings();
        assertNotNull(groupings);

        final ObaShape[] shapes = response.getShapes();
        assertNotNull(shapes);
        assertTrue(shapes.length > 0);
    }

    @Test
    public void testNoShapes() {
        ObaStopsForRouteResponse response =
                new ObaStopsForRouteRequest.Builder(getTargetContext(), "1_44")
                        .setIncludeShapes(false)
                        .build()
                        .call();
        assertOK(response);
        final ObaShape[] shapes = response.getShapes();
        assertNotNull(shapes);
        assertEquals(0, shapes.length);
    }
}
