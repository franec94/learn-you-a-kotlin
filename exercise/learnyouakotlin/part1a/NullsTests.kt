package learnyouakotlin.part1a

import learnyouakotlin.part1.Session
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull


class NullsTests {

    @Test fun nulls() {
        val session: Session? = Sessions.sessionWithTitle("learn you a kotlin")
        val notNullSession = session as Session
        assertEquals("for all the good it will do you", notNullSession.subtitle)
        assertEquals("for all the good it will do you", session.subtitle)
    }

    @Test fun elvis() {
        assertEquals("for all the good it will do you", subtitleOf(learnYouAKotlin))
        assertNull(learnyouakotlin.solution.part1a.subtitleOf(null))
    }

    @Test fun questionmark_thingy() {
        assertEquals("for all the good it will do you", subtitleOrPrompt(learnYouAKotlin))
        assertEquals("click to enter subtitle", subtitleOrPrompt(refactoringToStreams))
        assertEquals("click to enter subtitle", subtitleOrPrompt(null))
    }

}

private val learnYouAKotlin = Session("Learn you a kotlin", "for all the good it will do you", null)
private val refactoringToStreams = Session("Refactoring to Streams", null, null)

fun subtitleOf(session: Session?): String? {
    if (session == null) return null
    return session.subtitle
}

fun subtitleOrPrompt(session: Session?): String {
    if (session == null || session.subtitle== null) return "click to enter subtitle"
    return session.subtitle
}

object Sessions {
    fun sessionWithTitle(s: String): Session? {
        if (s.toLowerCase() == learnYouAKotlin.title.toLowerCase())
            return learnYouAKotlin
        if (s.toLowerCase() == refactoringToStreams.title.toLowerCase())
            return refactoringToStreams
        else return null
    }
}

