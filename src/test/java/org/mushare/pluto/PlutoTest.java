package org.mushare.pluto;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PlutoTest {

    @Test
    public void testAuth() {
        Pluto.setup("https://staging.easyjapanese-api-gateway.mushare.cn/pluto/", "org.mushare.easyjapanese");
        String scope = "easyjapanese.admin";
        String token = "eyJ0eXBlIjoiand0IiwiYWxnIjoicnNhIn0.eyJ0eXBlIjoiYWNjZXNzIiwiY3JlYXRlX3RpbWUiOjE1ODQ3MjIyMjQsImV4cGlyZV90aW1lIjoxNTg0NzI1ODI0LCJ1c2VySWQiOjMsImRldmljZUlkIjoiQ0Y0Mzc5MzMtQ0MyMS00QUFCLTgxNjEtMUU1MTVCNjQxQTU5IiwiYXBwSWQiOiJvcmcubXVzaGFyZS5lYXN5amFwYW5lc2UiLCJzY29wZXMiOlsiZWFzeWphcGFuZXNlLmFkbWluIl0sImxvZ2luX3R5cGUiOiJtYWlsIn0.V6HVNGsRvzLkgCzPxV3JH9H1GqF/ycNrKQDp/mzq/OdCOy1v8cu/O9HCjtW2J5NZuYgdtletEuoEdIQBUqtRHAseRPcyUmddWf3NxCHcqpAQZW2hJzp8WRgfP2Gug+O++IBoysntYz6FWSLUqw0HzGlU46J4jQnmzWvW/MR/mvM/7IWS5mtPM8DftlI3hBeeXH6FRKMtRbzr4PD0sTb/rSpJPtcs2YXmlj8C5DOhZd2XQjqnA7HhA3MZ2PAy6hkeZ99qyDUZav5SoOQQ8qCNbGDg/nQbeIaUHcg4Fx0F5lIlFAyHHaEkyhwSN4dLI+4ZD1rIxIXLH+uzcWOCYTIxag";
        PlutoUser user = Pluto.auth(token);
        assertTrue("testAuth should return 'true'", user.getScopes().contains(scope));
    }

}
