package org.mushare.pluto;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mushare.pluto.exception.PlutoErrorCode;
import org.mushare.pluto.exception.PlutoException;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class PlutoTest {

    private void setup() {
        Pluto.setup("https://beta-pluto.kaboocha.com/", "org.mushare.easyjapanese");
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
        setup();
        String token = "eyJ0eXBlIjoiand0IiwiYWxnIjoiUlMyNTYifQ.eyJ0eXBlIjoiYWNjZXNzIiwiaWF0IjoxNjAxNTUxMjU1LCJleHAiOjE2MDE1NTQ4NTUsInN1YiI6NTIwMywiaXNzIjoib3JnLm11c2hhcmUuZWFzeWphcGFuZXNlIiwic2NvcGVzIjpbIiJdfQ.S3ZTAFFlwmV3DJCYQrN_N0GDbpAfIPZlwXd1uxlDdMxXw_vkkLDffYjJjEA-nQ2Q1wgImb3YqA-SI3mbv_giiqo83NgiCeLVYTRDqVxj-ZJYJQRllD0o09DeeI_v5_Mll2toWF3cQaF8yEkzPR-AmOph-0eOw7vC5VJoMYy1SiVIf04wOR7wByidhkwbkPnn7X3cjFytOLKA2ZE_ikMpZ02NfRllddxK9IBa-NYVd_1cIleU-a8WSz8xPKCDDrq5He0M2ZCN0DpXdi4p2FI4wCGRSi3YvcSHwszJWV8dQQcgNhVi9T_oPhWSSTG_dZjQB9TTLq4IkJj6XYyQv8kI6Q";
        Pluto.auth(token);
    }

    @Test
    public void testFetchUserInfos() {
        setup();
        Pluto.fetUserInfos(Stream.of(1L, 2L, 3L).collect(Collectors.toList())).forEach(plutoUserInfo -> {
            System.out.println(plutoUserInfo);
        });
    }
    
}
