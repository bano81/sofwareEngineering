package dtu.ProjectManagementApp.businessLogic;

import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
        // Initialize SystemStorage and BLController
        SystemStorage systemStorage = new SystemStorage();
        ProjectManagementAppBL blController = new ProjectManagementAppBL(systemStorage);

        UIConsole uiConsole = new UIConsole(blController, systemStorage);

        systemStorage.initiateTestUsers(blController);

        uiConsole.run();
    }
}