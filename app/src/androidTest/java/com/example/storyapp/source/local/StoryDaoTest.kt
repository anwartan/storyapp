package com.example.storyapp.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.storyapp.DataDummy
import com.example.storyapp.utils.LiveDataTestUtil.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class StoryDaoTest{
    @get:Rule
    val instantExecutorRule =  InstantTaskExecutorRule()
    private lateinit var database: StoryDatabase
    private lateinit var dao: StoryDao
    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            StoryDatabase::class.java
        ).build()
        dao = database.storyDao()
    }
    @After
    fun closeDb() = database.close()

    @Test
    fun insertStory_Success() = runTest {
        val dummyData = DataDummy.generateDummyStoryEntities()
        dao.insertStory(dummyData)
        val actualNews = dao.findAll().getOrAwaitValue()
        Assert.assertEquals(dummyData.size, actualNews.size)
    }

    @Test
    fun deleteAll_Success() = runTest {
        val dummyData = DataDummy.generateDummyStoryEntities()
        dao.insertStory(dummyData)
        dao.deleteAll()
        val actualNews = dao.findAll().getOrAwaitValue()
        Assert.assertTrue(actualNews.isEmpty())
    }
}