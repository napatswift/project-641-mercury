package ku.cs.models.utils;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDate;

public class ImageUploader {
    private File uploadedFile;
    private String destDir;
    private Path destinationFile;
    private final Window window;

    public ImageUploader(Window window, String destDir) {
        this.destDir = destDir;
        this.window = window;
    }

    public boolean show(String description){
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(description, "*.jpg", "*.jpeg"));

        uploadedFile = chooser.showOpenDialog(this.window);

        if (uploadedFile == null)
            return false;

        String filename = LocalDate.now()
                + "_" + System.currentTimeMillis()
                + ".jpeg";

        File destDir = new File(this.destDir);

        destinationFile = FileSystems.getDefault().getPath(
                destDir.getAbsolutePath() + "/" + filename);
        return true;
    }

    public boolean show(){
        return show(".jpg or .jpeg file");
    }

    public void saveImageFile() throws IOException{
        File destDir = new File(this.destDir);

        if (!destDir.exists())
            if (!destDir.mkdirs()) throw new IOException();

        ImageUtil.resizeImage(uploadedFile, destinationFile.toString());
    }

    public File getUploadedFile() {
        return uploadedFile;
    }

    public Path getDestinationFile() {
        return destinationFile;
    }

    public void setDestDir(String destDir) {
        this.destDir = destDir;
    }
}
