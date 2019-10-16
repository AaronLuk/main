package mams.testutil;

import java.util.Set;

import mams.logic.commands.EditCommand;
import mams.logic.parser.CliSyntax;
import mams.model.student.Student;
import mams.model.tag.Tag;

/**
 * A utility class for Student.
 */
public class StudentUtil {

    /**
     * Returns the part of command string for the given {@code student}'s details.
     */
    public static String getStudentDetails(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_NAME + student.getName().fullName + " ");
        sb.append(CliSyntax.PREFIX_CREDITS + student.getCredits().value + " ");
        sb.append(CliSyntax.PREFIX_PREVMODS + student.getPrevMods().value + " ");
        sb.append(CliSyntax.PREFIX_MATRICID + student.getMatricId().value + " ");
        student.getTags().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditStudentDescriptor}'s details.
     */
    public static String getEditStudentDescriptorDetails(EditCommand.EditStudentDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(CliSyntax.PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getCredits().ifPresent(credits -> sb.append(CliSyntax.PREFIX_CREDITS)
                .append(credits.value).append(" "));
        descriptor.getPrevMods().ifPresent(prevMods -> sb.append(CliSyntax.PREFIX_PREVMODS)
                .append(prevMods.value).append(" "));
        descriptor.getMatricId().ifPresent(address -> sb.append(CliSyntax.PREFIX_MATRICID)
                .append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(CliSyntax.PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(CliSyntax.PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
