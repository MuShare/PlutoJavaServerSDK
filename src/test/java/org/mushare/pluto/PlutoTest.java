package org.mushare.pluto;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mushare.pluto.exception.PlutoErrorCode;
import org.mushare.pluto.exception.PlutoException;

import java.util.Base64;

import static org.junit.Assert.assertTrue;

public class PlutoTest {

    @Test
    public void testAuth() {
        Pluto.setup("https://staging.easyjapanese-api-gateway.mushare.cn/pluto/", "org.mushare.easyjapanese");
        String scope = "easyjapanese.admin";
        String token = "eyJ0eXBlIjoiand0IiwiYWxnIjoicnNhIn0.eyJ0eXBlIjoiYWNjZXNzIiwiY3JlYXRlX3RpbWUiOjE1ODQ3MjIyMjQsImV4cGlyZV90aW1lIjoxNTg0NzI1ODI0LCJ1c2VySWQiOjMsImRldmljZUlkIjoiQ0Y0Mzc5MzMtQ0MyMS00QUFCLTgxNjEtMUU1MTVCNjQxQTU5IiwiYXBwSWQiOiJvcmcubXVzaGFyZS5lYXN5amFwYW5lc2UiLCJzY29wZXMiOlsiZWFzeWphcGFuZXNlLmFkbWluIl0sImxvZ2luX3R5cGUiOiJtYWlsIn0.V6HVNGsRvzLkgCzPxV3JH9H1GqF/ycNrKQDp/mzq/OdCOy1v8cu/O9HCjtW2J5NZuYgdtletEuoEdIQBUqtRHAseRPcyUmddWf3NxCHcqpAQZW2hJzp8WRgfP2Gug+O++IBoysntYz6FWSLUqw0HzGlU46J4jQnmzWvW/MR/mvM/7IWS5mtPM8DftlI3hBeeXH6FRKMtRbzr4PD0sTb/rSpJPtcs2YXmlj8C5DOhZd2XQjqnA7HhA3MZ2PAy6hkeZ99qyDUZav5SoOQQ8qCNbGDg/nQbeIaUHcg4Fx0F5lIlFAyHHaEkyhwSN4dLI+4ZD1rIxIXLH+uzcWOCYTIxag";
        assertTrue("testAuth should return 'true'", Pluto.auth(token).getScopes().contains(scope));
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testAuthExpired() {
        exceptionRule.expect(PlutoException.class);
        exceptionRule.expectMessage(PlutoErrorCode.expired.toString());

        Pluto.setup("https://staging.easyjapanese-api-gateway.mushare.cn/pluto/", "org.mushare.easyjapanese");
        String token = "eyJ0eXBlIjoiand0IiwiYWxnIjoicnNhIn0.eyJ0eXBlIjoiYWNjZXNzIiwiY3JlYXRlX3RpbWUiOjE1ODQ3MTA3NzksImV4cGlyZV90aW1lIjoxNTg0NzE0Mzc5LCJ1c2VySWQiOjMsImRldmljZUlkIjoiQ0Y0Mzc5MzMtQ0MyMS00QUFCLTgxNjEtMUU1MTVCNjQxQTU5IiwiYXBwSWQiOiJvcmcubXVzaGFyZS5lYXN5amFwYW5lc2UiLCJzY29wZXMiOlsiZWFzeWphcGFuZXNlLmFkbWluIl0sImxvZ2luX3R5cGUiOiJtYWlsIn0.TR0A/fen5f+kg4APscFQ7JZtp2zNw5KMUeKBzm/GJg4WZp90ihg/OPfU9fcaJhoVNHWqxQ/OIHfAcXaXV+l8EEsTbh/+/Qs0glujX09Vm5z23wITzC36X+a/9bJJj5J1kXDtyx/CVdMmf8vm81T8PJFJuJtnyzA3IMRUGK0KecJ5MQjaOvh7NxZRhJfsCNYQz4V5hxvtI8urs+gi3/QVN04UQ5i0BX+DDdQ1E4MX8+3v2zDPc3ipQ8r9nZl00wmPdcqW5zx9Xooha6X8eTujgQuLiSFDheZGRR6/N+ZYktt/PMI49KIXVseenTTTzpX1Vmg9PAabPLJotdjcRpKtiA";
        Pluto.auth(token);
    }

}
