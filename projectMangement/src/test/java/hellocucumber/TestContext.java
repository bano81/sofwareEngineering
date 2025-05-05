package hellocucumber;

import dtu.ProjectManagementApp.businessLogic.SystemStorage;
import io.cucumber.java.Before;

public class TestContext {
    private static SystemStorage systemStorage;

    @Before
    public void setUp() {
        if (systemStorage == null) {
            systemStorage = new SystemStorage();
        }
    }

    public static SystemStorage getSystemStorage() {
        if (systemStorage == null) {
            systemStorage = new SystemStorage();
        }
        return systemStorage;
    }
}
