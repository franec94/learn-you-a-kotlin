package learnyouakotlin;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.Collectors;

import static java.util.Spliterators.spliterator;
import static java.util.stream.StreamSupport.stream;
import static learnyouakotlin.Json.*;

public class JsonFormat {
    public static JsonNode sessionAsJson(Session session) {
        return obj(
                prop("code", session.code.toString()),
                prop("title", session.title),
                session.subtitle == null ? null : prop("subtitle", session.subtitle),
                prop("presenters", array(session.presenters, JsonFormat::presenterAsJson)));
    }

    public static Session sessionFromJson(JsonNode json) throws JsonMappingException {
        try {
            SessionCode code = SessionCode.parse(json.path("code").asText());
            String title = nonBlankText(json.path("title"));
            @Nullable String subtitle = optionalNonBlankText(json.path("subtitle"));

            JsonNode authorsNode = json.path("presenters");
            List<Presenter> presenters = stream(spliterator(authorsNode::elements), false)
                    .map(JsonFormat::presenterFromJson)
                    .collect(Collectors.toList());

            return new Session(code, title, subtitle, presenters);

        } catch (ParseException e) {
            throw new JsonMappingException(null, "failed to parse Session from JSON", e);
        }
    }

    private static Spliterator<JsonNode> spliterator(Iterable<JsonNode> elements) {
        return elements.spliterator();
    }

    private static ObjectNode presenterAsJson(Presenter p) {
        return obj(prop("name", p.name));
    }

    private static Presenter presenterFromJson(JsonNode authorNode) {
        return new Presenter(authorNode.path("name").asText());
    }

    private static @Nullable String optionalNonBlankText(JsonNode node) throws JsonMappingException {
        if (node.isMissingNode()) {
            return null;
        } else {
            return nonBlankText(node);
        }
    }

    private static String nonBlankText(JsonNode node) throws JsonMappingException {
        String text = node.asText();
        if (node.isNull() || Objects.equals(text, "")) {
            throw new JsonMappingException(null, "missing or empty text");
        } else {
            return text;
        }
    }
}
