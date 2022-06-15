# Test your Local Datasource
Test your local data source if you use room, with Room inMemoryDatabase.

Set up your test:
```kotlin
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.R])
class RoomDataSourceTest : KoinTest {

    @get:Rule
    val coroutinesTestRule = MainCoroutinesTestRule()

    @get:Rule
    val koinRule = KoinRule.androidx()

    private lateinit var db: AppDataBase
    private lateinit var dataSource: RoomDataSource

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        dataSource = RoomDataSource(db.dao(), coroutinesTestRule.testDispatcher)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.clearAllTables()
        db.close()
    }
}
```

add your test, for example:
```kotlin
@Test
fun `validate that the movie was saved correctly`() =
    runTest(coroutinesTestRule.testDispatcher) {
        val movies = MovieEntityMother.movies()
        val randomMovie = movies.random()

        val result = dataSource.save(movies)
        val findResult = dataSource.find(randomMovie.id)

        assertThat(result.isSuccess, IsEqual(true))
        assertThat(findResult.isSuccess, IsEqual(true))
    }
```

## Documentation

- [Test and debug your database](https://developer.android.com/training/data-storage/room/testing-db)