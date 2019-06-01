package com.sca.sca_application.ScaFileLoader;

import com.sca.sca_application.ScaFileInformation.ScaFileInformation;

import java.util.Iterator;
import java.util.List;

/**
 * Loading files to inspect objects.
 */
public interface ScaFilesLoader extends Iterable<ScaFileInformation>{
    /**
     * Init method (to store stuff generally)
     * @param parameters parameters from the configuration object.
     */
    void init(List<String> parameters);

    /**
     * @return True if there is another file to inspect.
     */
    boolean hasNextFile();

    /**
     * Method that returns the next file to inspect
     * @return next file that should be inspected.
     */
    ScaFileInformation getNextFileInformation();

    @Override
    default Iterator<ScaFileInformation> iterator(){
        return new Iterator<ScaFileInformation>() {
            @Override
            public boolean hasNext() {
                return hasNextFile();
            }

            @Override
            public ScaFileInformation next() {
                return getNextFileInformation();
            }
        };
    }
}
