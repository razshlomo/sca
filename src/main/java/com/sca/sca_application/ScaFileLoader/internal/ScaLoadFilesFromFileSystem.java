package com.sca.sca_application.ScaFileLoader.internal;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;
import com.sca.sca_application.ScaFileLoader.ScaFilesLoader;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * SCA files loader.
 * Load files from the machine file system by specific given files.
 */
@Component
public class ScaLoadFilesFromFileSystem implements ScaFilesLoader {

    private List<String> filesToInspect = new ArrayList<>();
    private Iterator<String> iterator;

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
            filesToInspect.add(parameter);
        }
        iterator = filesToInspect.iterator();
    }

    @Override
    public boolean hasNextFile() {
        if(iterator == null){
            throw new RuntimeException("File loader wasn't initialized");
        }
        return iterator.hasNext();
    }

    @Override
    public ScaFileInformation getNextFileInformation() {

        if(!iterator.hasNext()){
            return null;
        }

        String fileToInspect = iterator.next();
        return new ScaFileInformation() {
            @Override
            public InputStream getFileInputSteam() {
                    return getClass().getClassLoader().getResourceAsStream(fileToInspect);
            }

            @Override
            public String getFilePath() {
                return fileToInspect;
            }

            @Override
            public String getFileExtension() {
                return FilenameUtils.getExtension(fileToInspect);
            }
        };
    }
}
