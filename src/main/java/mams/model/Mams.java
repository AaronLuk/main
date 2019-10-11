package mams.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import mams.model.appeal.Appeal;
import mams.model.appeal.UniqueAppealList;
import mams.model.student.Student;
import mams.model.student.UniqueStudentList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameStudent comparison)
 */
public class Mams implements ReadOnlyMams {

    private final UniqueStudentList students;
    private final UniqueAppealList appeals;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        students = new UniqueStudentList();
        appeals = new UniqueAppealList();
    }

    public Mams() {}

    /**
     * Creates an Mams using the Students in the {@code toBeCopied}
     */
    public Mams(ReadOnlyMams toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the student list with {@code students}.
     * {@code students} must not contain duplicate students.
     */
    public void setStudents(List<Student> students) {
        this.students.setStudents(students);
    }

    /**
     * Replaces the contents of the student list with {@code appeals}.
     * {@code students} must not contain duplicate students.
     */
    public void setAppeals(List<Appeal> appeals) {
        this.appeals.setAppeals(appeals);
    }


    /**
     * Resets the existing data of this {@code Mams} with {@code newData}.
     */
    public void resetData(ReadOnlyMams newData) {
        requireNonNull(newData);

        setStudents(newData.getStudentList());
    }

    //// student-level operations

    /**
     * Returns true if a student with the same identity as {@code student} exists in the address book.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Adds a student to the address book.
     * The student must not already exist in the address book.
     */
    public void addStudent(Student p) {
        students.add(p);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the address book.
     * The student identity of {@code editedStudent} must not be the
     * same as another existing student in the address book.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        students.setStudent(target, editedStudent);
    }

    /**
     * Removes {@code key} from this {@code Mams}.
     * {@code key} must exist in the address book.
     */
    public void removeStudent(Student key) {
        students.remove(key);
    }

    /// appeal-level operations
    /**
     * Returns true if a appeal with the same identity as {@code appeal} exists in the appeal list.
     */
    public boolean hasAppeal(Appeal appeal) {
        requireNonNull(appeal);
        return appeals.contains(appeal);
    }

    /**
     * Adds a appeal to the appeal list.
     * The appeal must not already exist in the appeal list.
     */
    public void addAppeal(Appeal p) {
         appeals.add(p);
    }

    /**
     * Replaces the given appeal {@code target} in the list with {@code approvedAppeal}.
     * {@code target} must exist in the address book.
     * The appeal identity of {@code approvedAppeal} must not be the
     * same as another existing appeal in the appeal list.
     */
    public void setAppeal(Appeal target, Appeal approvedAppeal) {
        requireNonNull(approvedAppeal);

        appeals.setAppeal(target, approvedAppeal);
    }


    //// util methods

    @Override
    public String toString() {
        return students.asUnmodifiableObservableList().size() + " students";
        // TODO: refine later
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Appeal> getAppealList() {
        return appeals.asUnmodifiedObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Mams // instanceof handles nulls
                && students.equals(((Mams) other).students));
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }
}
