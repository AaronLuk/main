package mams.model;

import javafx.collections.ObservableList;
import mams.model.appeal.Appeal;
import mams.model.student.Student;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyMams {

    /**
     * Returns an unmodifiable view of the students list.
     * This list will not contain any duplicate students.
     */
    ObservableList<Student> getStudentList();

    /**
     * Returns an unmodifiable view of the appeals list.
     * This list will not contain any duplicate appeals.
     */
    ObservableList<Appeal> getAppealList();

}
