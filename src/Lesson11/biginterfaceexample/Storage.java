package Lesson11.biginterfaceexample;

/**
 * Created by user on 02.08.2017.
 */
public class Storage {
    private File[] files = new  File[5];

    public Storage(File[] files) {
        this.files = files;
    }

    public File[] getFiles() {
        return files;
    }
}
