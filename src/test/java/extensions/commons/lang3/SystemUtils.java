package extensions.commons.lang3;

public final class SystemUtils {

    public static String currentDirectory() {
        // TODO: confirm how to get the current directory
        return org.apache.commons.lang3.SystemUtils.getUserDir().getAbsolutePath();
    }

}
