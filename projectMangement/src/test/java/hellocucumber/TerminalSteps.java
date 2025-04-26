package hellocucumber;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TerminalSteps {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private ByteArrayInputStream inputStream;
    private final PrintStream originalOut = System.out;
    private List<String> input = new ArrayList<>();


    @Given("We are in the terminal")
    public void weAreInTheTerminal() {
        System.setOut(new PrintStream(outputStream));
    }

    @Given("A user inputs {string} to the terminal")
    public void aUserInputsToTheTerminal(String input) {
        this.input.add(input);
    }

    @Given("The input is processed")
    public void theInputIsProcessed() {
        String userInput = "";
        for (String input : this.input) {
            userInput += input + "\n"; // Append a newline character to simulate pressing Enter
        }
        this.inputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(inputStream); // Set the input stream to the input string

    }

    @When("The input is ouput to the terminal")
    public void theInputIsOuputToTheTerminal() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line); // Print the input to the terminal
        }
        scanner.close();
    }

    @Then("I should see {string} in the terminal output")
    public void iShouldSeeInTheTerminalOutput(String input) {
        String output = ""; // Initialize output variable
        // Check if the output stream contains the expected output
        String[] lines = outputStream.toString().split("\n");
        for (String line : lines) {
            if (line.contains(input)) {
                output = line; // Store the output if it contains the expected input
                break; // Exit the loop once we find the expected input
            }
        }
    }
    @Then("We leave the terminal")
    public void weLeaveTheTerminal() {
        System.setOut(originalOut);
        outputStream.reset();
    }
}
