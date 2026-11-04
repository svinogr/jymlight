package info.upump.database

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.sql.DriverManager

class Migration2To3Test {

    private val connection = DriverManager.getConnection("jdbc:sqlite::memory:")

    @Before
    fun createV2Schema() {
        connection.createStatement().execute(
            """
            CREATE TABLE sets (
                _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                comment TEXT,
                weight REAL,
                reps INTEGER,
                start_date TEXT NOT NULL,
                finish_date TEXT NOT NULL,
                parent_id INTEGER,
                past_set REAL
            )
            """.trimIndent()
        )
        // строка, оставшаяся со времен версии 1: past_set = NULL после миграции 1->2
        connection.createStatement().execute(
            """
            INSERT INTO sets (comment, weight, reps, start_date, finish_date, parent_id, past_set)
            VALUES ('old set', 80.5, 10, '01.01.2024', '02.01.2024', 42, NULL)
            """.trimIndent()
        )
        // строка с уже заполненным past_set — не должна меняться
        connection.createStatement().execute(
            """
            INSERT INTO sets (comment, weight, reps, start_date, finish_date, parent_id, past_set)
            VALUES ('new set', 60.0, 8, '03.01.2024', '04.01.2024', 43, 55.5)
            """.trimIndent()
        )
    }

    @After
    fun closeConnection() {
        connection.close()
    }

    @Test
    fun migration_2_to_3_fills_null_past_set_and_keeps_existing_values() {
        connection.createStatement().execute(RoomDB.MIGRATION_2_TO_3_SQL)

        val oldPastSet = connection.createStatement()
            .executeQuery("SELECT past_set FROM sets WHERE _id = 1")
            .let { it.next(); it.getDouble(1) }
        assertEquals(0.0, oldPastSet, 0.001)

        val newPastSet = connection.createStatement()
            .executeQuery("SELECT past_set FROM sets WHERE _id = 2")
            .let { it.next(); it.getDouble(1) }
        assertEquals(55.5, newPastSet, 0.001)

        val nullCount = connection.createStatement()
            .executeQuery("SELECT COUNT(*) FROM sets WHERE past_set IS NULL")
            .let { it.next(); it.getLong(1) }
        assertEquals(0, nullCount)
    }

    @Test
    fun migration_2_to_3_is_idempotent() {
        connection.createStatement().execute(RoomDB.MIGRATION_2_TO_3_SQL)
        connection.createStatement().execute(RoomDB.MIGRATION_2_TO_3_SQL)

        val nullCount = connection.createStatement()
            .executeQuery("SELECT COUNT(*) FROM sets WHERE past_set IS NULL")
            .let { it.next(); it.getLong(1) }
        assertEquals(0, nullCount)
    }
}
