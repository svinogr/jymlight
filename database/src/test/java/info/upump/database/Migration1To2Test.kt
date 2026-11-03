package info.upump.database

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.sql.DriverManager

class Migration1To2Test {

    private val connection = DriverManager.getConnection("jdbc:sqlite::memory:")

    @Before
    fun createV1Schema() {
        connection.createStatement().execute(
            """
            CREATE TABLE sets (
                _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                comment TEXT,
                weight REAL,
                reps INTEGER,
                start_date TEXT NOT NULL,
                finish_date TEXT NOT NULL,
                parent_id INTEGER
            )
            """.trimIndent()
        )
        connection.createStatement().execute(
            """
            INSERT INTO sets (comment, weight, reps, start_date, finish_date, parent_id)
            VALUES ('test set', 80.5, 10, '01.01.2024', '02.01.2024', 42)
            """.trimIndent()
        )
    }

    @After
    fun closeConnection() {
        connection.close()
    }

    @Test
    fun migration_1_to_2_adds_nullable_past_set_column_and_keeps_data() {
        connection.createStatement().execute(RoomDB.MIGRATION_1_TO_2_SQL)

        val columns = connection.createStatement().executeQuery("PRAGMA table_info(sets)")
            .let { rs ->
                generateSequence { if (rs.next()) rs.getString("name") to (rs.getInt("notnull") == 1) else null }.toList()
            }
        val pastSet = columns.firstOrNull { it.first == "past_set" }
        assertTrue("past_set column must exist after migration", pastSet != null)
        assertFalse("past_set must be nullable (entity declares Double?)", pastSet!!.second)

        val rowCount = connection.createStatement().executeQuery("SELECT COUNT(*) FROM sets")
            .let { it.next(); it.getLong(1) }
        assertEquals(1, rowCount)

        connection.createStatement().execute("UPDATE sets SET past_set = 75.0 WHERE _id = 1")
        val pastSetValue = connection.createStatement()
            .executeQuery("SELECT past_set FROM sets WHERE _id = 1")
            .let { it.next(); it.getDouble(1) }
        assertEquals(75.0, pastSetValue, 0.001)
    }
}
