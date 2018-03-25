package seedu.ptman.storage;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.ptman.commons.exceptions.IllegalValueException;
import seedu.ptman.model.employee.Employee;
import seedu.ptman.model.outlet.Capacity;
import seedu.ptman.model.outlet.Day;
import seedu.ptman.model.outlet.Shift;
import seedu.ptman.model.outlet.Time;

/**
 * JAXB-friendly version of the Employee.
 */
public class XmlAdaptedShift {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Shifts's %s field is missing!";

    @XmlElement(required = true)
    private String day;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;
    @XmlElement(required = true)
    private String capacity;

    @XmlElement
    private List<XmlAdaptedEmployee> employees = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedEmployee.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedShift() {}

    /**
     * Constructs an {@code XmlAdaptedEmployee} with the given employee details.
     */
    public XmlAdaptedShift(String day, String startTime, String endTime,
                           String capacity, List<XmlAdaptedEmployee> employees) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        if (employees != null) {
            this.employees = new ArrayList<>(employees);
        }
    }

    /**
     * Converts a given Employee into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEmployee
     */
    public XmlAdaptedShift(Shift source) {
        day = source.getDay().toString();
        startTime = source.getStartTime().toString();
        endTime = source.getEndTime().toString();
        capacity = source.getCapacity().toString();
        employees = new ArrayList<>();
        for (Employee employee : source.getEmployeeList()) {
            employees.add(new XmlAdaptedEmployee(employee));
        }
    }

    /**
     * Converts this jaxb-friendly adapted employee object into the model's Employee object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted employee
     */
    public Shift toModelType() throws IllegalValueException {
        final List<Employee> employees = new ArrayList<>();
        for (XmlAdaptedEmployee employee : this.employees) {
            employees.add(employee.toModelType());
        }

        if (this.day == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DayOfWeek.class.getSimpleName()));
        }
        if (!Day.isValidDay(this.day)) {
            throw new IllegalValueException(Day.MESSAGE_DAY_CONSTRAINTS);
        }
        final Day day = new Day(this.day);

        if (this.startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(this.startTime)) {
            throw new IllegalValueException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        final Time startTime = new Time(this.startTime);

        if (this.endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(this.endTime)) {
            throw new IllegalValueException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        final Time endTime = new Time(this.endTime);


        if (this.capacity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Capacity.class.getSimpleName()));
        }
        if (!Capacity.isValidCapacity(this.capacity)) {
            throw new IllegalValueException(Capacity.MESSAGE_CAPACITY_CONSTRAINTS);
        }
        final Capacity capacity = new Capacity(this.capacity);

        return new Shift(day, startTime, endTime, capacity, employees);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedShift)) {
            return false;
        }

        XmlAdaptedShift otherShift = (XmlAdaptedShift) other;
        return Objects.equals(day, otherShift.day)
                && Objects.equals(startTime, otherShift.startTime)
                && Objects.equals(endTime, otherShift.endTime)
                && Objects.equals(capacity, otherShift.capacity)
                && employees.equals(otherShift.employees);
    }
}
