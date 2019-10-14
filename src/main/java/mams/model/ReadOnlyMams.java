package mams.model;

import javafx.collections.ObservableList;

import mams.model.appeal.Appeal;

import mams.model.module.Module;

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
<<<<<<< HEAD
     * Returns an unmodifiable view of the appeals list.
     * This list will not contain any duplicate appeals.
     */
    ObservableList<Appeal> getAppealList();

     /** Returns an unmodifiable view of the module list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();

}
