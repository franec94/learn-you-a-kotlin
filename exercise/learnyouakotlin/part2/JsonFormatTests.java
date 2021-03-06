package learnyouakotlin.part2;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.oneeyedmen.okeydoke.junit.ApprovalsRule;
import learnyouakotlin.part1.Slots;
import learnyouakotlin.part1.Presenter;
import learnyouakotlin.part1.Session;
import org.junit.Rule;
import org.junit.Test;

import static com.oneeyedmen.okeydoke.ApproverFactories.fileSystemApproverFactory;
import static learnyouakotlin.part2.JsonFormat.sessionAsJson;
import static learnyouakotlin.part2.JsonFormat.sessionFromJson;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class JsonFormatTests {
    @Rule
    public final ApprovalsRule approval = ApprovalsRule.fileSystemRule("exercise");

    @Test
    public void session_to_json() {
        Session session = new Session(
                "Learn You a Kotlin For All The Good It Will Do You",
                null,
                new Slots(1,2),
                new Presenter("Duncan McGregor"),
                new Presenter("Nat Pryce"));

        JsonNode json = sessionAsJson(session);

        approval.assertApproved(json, Json::asStableJsonString);
    }

    @Test
    public void session_with_subtitle_to_json() {
        Session session = new Session(
                "Scrapheap Challenge",
                "A Workshop in Postmodern Programming",
                new Slots(3,3),
                new Presenter("Ivan Moore"));

        JsonNode json = sessionAsJson(session);

        approval.assertApproved(json, Json::asStableJsonString);
    }

    @Test
    public void session_to_and_from_json() throws JsonMappingException {
        Session original = new Session(
                "Working Effectively with Legacy Tests",
                null,
                new Slots(1,2),
                new Presenter("Nat Pryce"),
                new Presenter("Duncan McGregor"));

        Session parsed = sessionFromJson(sessionAsJson(original));

        assertThat(parsed, equalTo(original));
    }
}
