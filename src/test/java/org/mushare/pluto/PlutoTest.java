package org.mushare.pluto;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mushare.pluto.exception.PlutoErrorCode;
import org.mushare.pluto.exception.PlutoException;

import static org.junit.Assert.assertTrue;

public class PlutoTest {

    private void setup() {
        Pluto.setup("https://staging.easyjapanese-api-gateway.mushare.cn/pluto/", "org.mushare.easyjapanese");
    }

    @Test
    public void testAuth() throws PlutoException {
        setup();
        String scope = "easyjapanese.admin";
        String token = "eyJ0eXBlIjoiand0IiwiYWxnIjoicnNhIn0.eyJ0eXBlIjoiYWNjZXNzIiwiY3JlYXRlX3RpbWUiOjE1ODQ4NTQyNjEsImV4cGlyZV90aW1lIjoxNjE2MjE3NDYxLCJ1c2VySWQiOjMsImRldmljZUlkIjoiQ0Y0Mzc5MzMtQ0MyMS00QUFCLTgxNjEtMUU1MTVCNjQxQTU5IiwiYXBwSWQiOiJvcmcubXVzaGFyZS5lYXN5amFwYW5lc2UiLCJzY29wZXMiOlsiZWFzeWphcGFuZXNlLmFkbWluIl0sImxvZ2luX3R5cGUiOiJtYWlsIn0.RlrKt8fy+rXF9h9/H5w8es4Ni9IlCapDkuA8rxqX09Hv3VUFyY3tt+wdGTihNv9zZSm8Sf7m9ILkq0er826V9p1lUHk50pYlwigTvu4lG5g2uU/bAwt7EwPhxfco6XlA4Z0sQUqS49yM+dmVWnl66bhookkipcDZchpFzr3TOdbmPjTgJ8mMUZO46Kx1fSQhzOJdCAjdkMP44oeuTwcfn1NRZBXacF/HzVLpg+R8APv5oXAmvN4A8HSYoVMRaZn7Z1L6mspOyLfkOuNsOYT1iDM6jkAq0Lj/ST/n7fhNw+2+cadGe2aPBkeU8H7qzgQJEqE0iAlTtFQF6O1Qt0duxQ";
        assertTrue("testAuth should return 'true'", Pluto.auth(token).getScopes().contains(scope));
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testAuthExpired() throws PlutoException {
        exceptionRule.expect(PlutoException.class);
        exceptionRule.expectMessage(PlutoErrorCode.expired.toString());

        setup();
        String token = "eyJ0eXBlIjoiand0IiwiYWxnIjoicnNhIn0.eyJ0eXBlIjoiYWNjZXNzIiwiY3JlYXRlX3RpbWUiOjE1ODQ3MTA3NzksImV4cGlyZV90aW1lIjoxNTg0NzE0Mzc5LCJ1c2VySWQiOjMsImRldmljZUlkIjoiQ0Y0Mzc5MzMtQ0MyMS00QUFCLTgxNjEtMUU1MTVCNjQxQTU5IiwiYXBwSWQiOiJvcmcubXVzaGFyZS5lYXN5amFwYW5lc2UiLCJzY29wZXMiOlsiZWFzeWphcGFuZXNlLmFkbWluIl0sImxvZ2luX3R5cGUiOiJtYWlsIn0.TR0A/fen5f+kg4APscFQ7JZtp2zNw5KMUeKBzm/GJg4WZp90ihg/OPfU9fcaJhoVNHWqxQ/OIHfAcXaXV+l8EEsTbh/+/Qs0glujX09Vm5z23wITzC36X+a/9bJJj5J1kXDtyx/CVdMmf8vm81T8PJFJuJtnyzA3IMRUGK0KecJ5MQjaOvh7NxZRhJfsCNYQz4V5hxvtI8urs+gi3/QVN04UQ5i0BX+DDdQ1E4MX8+3v2zDPc3ipQ8r9nZl00wmPdcqW5zx9Xooha6X8eTujgQuLiSFDheZGRR6/N+ZYktt/PMI49KIXVseenTTTzpX1Vmg9PAabPLJotdjcRpKtiA";
        Pluto.auth(token);
    }

    @Test
    public void testScopes() throws PlutoException {
        Pluto.setup("https://easyjapanese-api-gateway.mushare.cn/pluto/", "org.mushare.easyjapanese");
        String token = "eyJ0eXBlIjoiand0IiwiYWxnIjoicnNhIn0.eyJ0eXBlIjoiYWNjZXNzIiwiY3JlYXRlX3RpbWUiOjE1ODU0NDg1MTcsImV4cGlyZV90aW1lIjoxNTg1NDUyMTE3LCJ1c2VySWQiOjQ4LCJkZXZpY2VJZCI6IkM5QTExQTIzLTg1QTMtNEMwRC04RjQxLTE5QURBNEIwOTJFRSIsImFwcElkIjoib3JnLm11c2hhcmUuZWFzeWphcGFuZXNlIiwic2NvcGVzIjpudWxsLCJsb2dpbl90eXBlIjoiZ29vZ2xlIn0.LyYEgEIxc1ghirESmvvpDE/EEd9B0U10BRgbKt3YdfiCb6L3URizHIqJVoc6IhPZOOBNy2x2Dk/sCZ5JV1rtaZ8niMdAb7VL7PwObZiWuWT8TieuV+7DaPxcJrPdzgz46GOOX7o3+fWRr64ucOYZeiY/g0PBCL07dMPRHYyzHK7e8Wrb/59rUjO4GCad5vM5qny9jTXihB+IokBPOYDkhKCinOfMAqOOFOtVRVdxt/mCLhnjkxexwWM/a7vmJQ4qOZYHxAB9mbgDq+mbBfzWafFWhbADdZdSqyKnKUDguBE1fPRODLqTIqB0KL4cttgSHKivlwwiVaaxZUEkwGN4GQ";
        Pluto.auth(token);
    }
    
}
