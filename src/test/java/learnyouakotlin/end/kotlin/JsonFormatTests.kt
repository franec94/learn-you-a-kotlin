package learnyouakotlin.end.kotlin

import com.fasterxml.jackson.databind.JsonNode
import com.oneeyedmen.okeydoke.ApproverFactories.fileSystemApproverFactory
import com.oneeyedmen.okeydoke.junit.ApprovalsRule
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Rule
import org.junit.Test
import java.io.File

class JsonFormatTests {
    @Rule
    @JvmField
    val approval = ApprovalsRule(fileSystemApproverFactory(File("src/test/java")))
    
    @Test
    fun session_to_json() {
        val session = Session(SessionCode(1), "Learn You a Kotlin For All The Good It Will Do You",
            Presenter("Duncan McGregor"),
            Presenter("Nat Pryce"))
        
        val json = session.asJson()
        
        approval.assertApproved(json, JsonNode::asStableJsonString)
    }
    
    @Test
    fun session_from_json() {
        val original = Session(SessionCode(2), "Working Effectively with Legacy Tests",
            Presenter("Nat Pryce"),
            Presenter("Duncan McGregor"))
        
        val parsed = original.asJson().toSession()
        
        assertThat(parsed, equalTo(original))
    }
}