package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.PartTimeManager;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyPartTimeManager;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.exceptions.DuplicateEmployeeException;
import seedu.address.model.employee.exceptions.EmployeeNotFoundException;
import seedu.address.testutil.EmployeeBuilder;

public class AddCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullEmployee_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_employeeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEmployeeAdded modelStub = new ModelStubAcceptingEmployeeAdded();
        Employee validEmployee = new EmployeeBuilder().build();

        CommandResult commandResult = getAddCommandForEmployee(validEmployee, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validEmployee), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEmployee), modelStub.employeesAdded);
    }

    @Test
    public void execute_duplicateEmployee_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateEmployeeException();
        Employee validEmployee = new EmployeeBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_EMPLOYEE);

        getAddCommandForEmployee(validEmployee, modelStub).execute();
    }

    @Test
    public void equals() {
        Employee alice = new EmployeeBuilder().withName("Alice").build();
        Employee bob = new EmployeeBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different employee -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new AddCommand with the details of the given employee.
     */
    private AddCommand getAddCommandForEmployee(Employee employee, Model model) {
        AddCommand command = new AddCommand(employee);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addEmployee(Employee employee) throws DuplicateEmployeeException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyPartTimeManager newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyPartTimeManager getPartTimeManager() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteEmployee(Employee target) throws EmployeeNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateEmployee(Employee target, Employee editedEmployee)
                throws DuplicateEmployeeException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Employee> getFilteredEmployeeList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredEmployeeList(Predicate<Employee> predicate) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateEmployeeException when trying to add a employee.
     */
    private class ModelStubThrowingDuplicateEmployeeException extends ModelStub {
        @Override
        public void addEmployee(Employee employee) throws DuplicateEmployeeException {
            throw new DuplicateEmployeeException();
        }

        @Override
        public ReadOnlyPartTimeManager getPartTimeManager() {
            return new PartTimeManager();
        }
    }

    /**
     * A Model stub that always accept the employee being added.
     */
    private class ModelStubAcceptingEmployeeAdded extends ModelStub {
        final ArrayList<Employee> employeesAdded = new ArrayList<>();

        @Override
        public void addEmployee(Employee employee) throws DuplicateEmployeeException {
            requireNonNull(employee);
            employeesAdded.add(employee);
        }

        @Override
        public ReadOnlyPartTimeManager getPartTimeManager() {
            return new PartTimeManager();
        }
    }

}
