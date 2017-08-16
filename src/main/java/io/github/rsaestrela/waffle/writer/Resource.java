package io.github.rsaestrela.waffle.writer;


import java.io.File;
import java.util.Objects;

public class Resource {

    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resource)) {
            return false;
        }
        Resource resource = (Resource) o;
        return Objects.equals(file, resource.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file);
    }
}
