package com.sca.sca_application.ScaFileLoader.internal;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaFileLoader.ScaFilesLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * SCA files loader.
 * Load files from the machine file system by spesific given files.
 */
@Component
public class ScaLoadFilesFromFileSystem implements ScaFilesLoader {

    private List<File> filesToInspect = new ArrayList<>();
    private Iterator<File> iterator;

    /**
     * Init files objects from the given parameters
     * @param parameters List of files paths to inspect
     */
    public void init(List<String> parameters){
        if(CollectionUtils.isEmpty(parameters)){
            return;
        }
        for (String parameter : parameters) {
            if(StringUtils.isEmpty(parameter)){
                continue;
            }
            filesToInspect.add(new File(parameter));
        }
        iterator = null;
    }

    @Override
    public ScaFileInformation getNextFileInformation() {
        if(iterator == null){
            iterator = filesToInspect.iterator();
        }
        if(!iterator.hasNext()){
            return null;
        }

        File fileToInspect = iterator.next();
        return new ScaFileInformation() {
            @Override
            public InputStream getFileInputSteam() {
                try {
                    return new FileInputStream(fileToInspect);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("Cannot read file " + fileToInspect.getAbsolutePath());
                }
            }

            @Override
            public String getFilePath() {
                return fileToInspect.getAbsolutePath();
            }
        };
    }

}
