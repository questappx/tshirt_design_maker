//import java.io.File;
//import java.io.IOException;
//import java.util.Map;
//
//import com.fasterxml.jackson.core.JsonGenerationException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class DraftSaver {
//    public static void saveDraft(Map<String, Object> draft, String filePath) throws JsonGenerationException, JsonMappingException, IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.writeValue(new File(filePath), draft);
//    }
//
//    public static Map<String, Object> loadDraft(String filePath) throws JsonGenerationException, JsonMappingException, IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readValue(new File(filePath), new TypeReference<Map<String, Object>>(){});
//    }
//}
