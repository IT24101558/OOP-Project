package DSA;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // For pretty printing JSON
    }

    public static List<Member> readMembersFromFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>(); // Return empty list if file doesn't exist or is empty
            }
            return objectMapper.readValue(file, new TypeReference<List<Member>>() {});
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception appropriately in a real app
            return new ArrayList<>();
        }
    }

    public static void writeMembersToFile(String filePath, List<Member> members) {
        try {
            objectMapper.writeValue(new File(filePath), members);
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }
}
