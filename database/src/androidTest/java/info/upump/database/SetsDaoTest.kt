package info.upump.database

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import info.upump.database.entities.SetsEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class SetsDaoTest {

    private lateinit var db: RoomDB
    private val dao get() = db.setsDao()

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, RoomDB::class.java).build()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun save_and_getByParent_returns_inserted_rows() = runBlocking {
        val id = dao.save(
            SetsEntity(
                comment = "set 1",
                weight = 100.0,
                reps = 5,
                parent_id = 7,
                past_set = 90.0
            )
        )
        dao.save(SetsEntity(comment = "set 2", parent_id = 7))
        dao.save(SetsEntity(comment = "other parent", parent_id = 99))

        val sets = dao.getByParent(7).first()

        assertEquals(2, sets.size)
        val first = sets.first { it._id == id }
        assertEquals("set 1", first.comment)
        assertEquals(100.0, first.weight!!, 0.001)
        assertEquals(90.0, first.past_set!!, 0.001)
    }

    @Test
    fun past_set_is_nullable() = runBlocking {
        dao.save(SetsEntity(comment = "no past", parent_id = 1))

        val saved = dao.getByParent(1).first().single()

        assertNull(saved.past_set)
    }

    @Test
    fun deleteById_removes_only_target_row() = runBlocking {
        val id1 = dao.save(SetsEntity(parent_id = 3))
        val id2 = dao.save(SetsEntity(parent_id = 3))

        dao.deleteById(id1)

        val remaining = dao.getByParent(3).first()
        assertEquals(listOf(id2), remaining.map { it._id })
    }

    @Test
    fun deleteByParentId_removes_all_children() = runBlocking {
        dao.save(SetsEntity(parent_id = 5))
        dao.save(SetsEntity(parent_id = 5))
        dao.save(SetsEntity(parent_id = 6))

        dao.deleteByParentId(5)

        assertEquals(0, dao.getByParent(5).first().size)
        assertEquals(1, dao.getByParent(6).first().size)
    }
}
