package mams.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import mams.commons.core.GuiSettings;
import mams.model.appeal.Appeal;
import mams.model.student.Student;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;
    Predicate<Appeal> PREDICATE_SHOW_ALL_APPEALS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getMamsFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setMamsFilePath(Path mamsFilePath);

    /**
     * Replaces address book data with the data in {@code mams}.
     */
    void setMams(ReadOnlyMams mams);

    /** Returns the Mams */
    ReadOnlyMams getMams();

    /**
     * Returns true if a student with the same identity as {@code student} exists in the address book.
     */
    boolean hasStudent(Student student);

    /**
     * Deletes the given student.
     * The student must exist in the address book.
     */
    void deleteStudent(Student target);

    /**
     * Adds the given student.
     * {@code student} must not already exist in the address book.
     */
    void addStudent(Student student);

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in the address book.
     * The student identity of {@code editedStudent} must not be
     * the same as another existing student in the address book.
     */
    void setStudent(Student target, Student editedStudent);

    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getFilteredStudentList();

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);


    /**
     *
     * Returns true if a appeal with the same identity as {@code appeal} exists in the appeal list
     */
    boolean hasAppeal(Appeal appeal);

    /**
     * Adds the given appeal.
     * {@code appeal} must not already exist in the appeal list.
     *
     */
    void addAppeal(Appeal appeal);

    /**
     * Replaces the given appeal {@code target} with {@code editedAppeal}.
     * {@code target} must exist in the appeal list.
     * The appeal identity of {@code editedAppeal} must not be
     * the same as another existing student in the appeal list.
     */
    void setAppeal(Appeal target, Appeal approvedAppeal);

    /**
     * Updates the filter of the filtered appeal list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAppealList(Predicate<Appeal> predicate);

    /** Returns an unmodifiable view of the filtered appeal list */
    ObservableList<Appeal> getFilteredAppealList();
}
