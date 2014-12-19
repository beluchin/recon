package extensions.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.io.File;

public class FilePresenceMatcher extends TypeSafeMatcher<String> {

    private final String directory;

    public FilePresenceMatcher(final String directory) {
        this.directory = directory;
    }

    public static Matcher<? super String> existsIn(final String directory) {
        return new FilePresenceMatcher(directory);
    }

    @Override
    protected void describeMismatchSafely(
            final String filename, final Description mismatchDescription) {
        mismatchDescription.appendText(String.format(
                "file \"%s\" did not exist",
                filename));
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText(String.format("%s to contain output file", directory));
    }

    @Override
    protected boolean matchesSafely(final String filename) {
        final String absolutePath = String.format("%s/%s", directory, filename);
        final File f = new File(absolutePath);
        return f.exists() && f.isFile();
    }

}
