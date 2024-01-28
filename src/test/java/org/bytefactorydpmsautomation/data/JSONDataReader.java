package org.bytefactorydpmsautomation.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class JSONDataReader {
        public List<HashMap<String, String>> getJsonDataToMap(String fileName) throws IOException {
            String filePath = System.getProperty("user.dir")+"/src/test/java/org/bytefactorydpmsautomation/data/"+fileName;
            System.out.println(filePath);
            String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
            System.out.println("JSON Content: " + jsonContent);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
        }
}
