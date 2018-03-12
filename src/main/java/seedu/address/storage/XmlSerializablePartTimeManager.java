package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.PartTimeManager;
import seedu.address.model.ReadOnlyPartTimeManager;

/**
 * An Immutable PartTimeManager that is serializable to XML format
 */
@XmlRootElement(name = "parttimemanager")
public class XmlSerializablePartTimeManager {

    @XmlElement
    private List<XmlAdaptedEmployee> employees;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializablePartTimeManager.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializablePartTimeManager() {
        employees = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializablePartTimeManager(ReadOnlyPartTimeManager src) {
        this();
        employees.addAll(src.getEmployeeList().stream().map(XmlAdaptedEmployee::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this parttimemanager into the model's {@code PartTimeManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedEmployee} or {@code XmlAdaptedTag}.
     */
    public PartTimeManager toModelType() throws IllegalValueException {
        PartTimeManager partTimeManager = new PartTimeManager();
        for (XmlAdaptedTag t : tags) {
            partTimeManager.addTag(t.toModelType());
        }
        for (XmlAdaptedEmployee p : employees) {
            partTimeManager.addEmployee(p.toModelType());
        }
        return partTimeManager;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializablePartTimeManager)) {
            return false;
        }

        XmlSerializablePartTimeManager otherAb = (XmlSerializablePartTimeManager) other;
        return employees.equals(otherAb.employees) && tags.equals(otherAb.tags);
    }
}
