module hellofx {
    requires transitive javafx.controls;
    requires javafx.fxml;
 
    opens dtu.ProjectManagementApp.businessLogic to javafx.fxml; // Gives access to fxml files
    exports dtu.ProjectManagementApp.businessLogic; // Exports the class inheriting from javafx.application.Application
}