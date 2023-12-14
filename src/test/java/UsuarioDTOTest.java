import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioDTOTest {

    private List<User> results;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class User {

        private Login login;
        private Id id;
        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        static class Login {
            private String username;
            private String password;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        static class Id {
            private String value;
        }
    }


//
//
//    @Data
//    class Id {
//        @JsonProperty("value")
//        private String id;
//    }

}
