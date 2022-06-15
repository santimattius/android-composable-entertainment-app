# Test your Remote Datasource

Try your remote data source if you use Retrofit, with the following alternatives:

- Mocking
- MockWebServer

### Mocking

Since in retrofit we declare our services as interfaces we can use a mock library, like mockito or mockk, to mock this
interface and give it the behavior we need for our test case.

Set up your test, for example:

```kotlin
@ExperimentalCoroutinesApi
class MovieDataSourceTest {

    private val client: TheMovieDBService = mockk()
    private val movieDataSource = MovieDataSource(client)
}
```

add your test:

```kotlin
@Test
fun `get populars movie on client result is success`() {
    val pictures = TheMovieDBMother.list()

    coEvery {
        client.getMoviePopular(any(), any())
    } returns Response(results = pictures)

    runTest {
        val result = movieDataSource.getPopularMovies()
        assertThat(result.isSuccess, IsEqual(true))
    }

    coVerify { client.getMoviePopular(any(), any()) }
}
```

### MockWebServer

This library makes it easy to test that your app Does The Right Thing when it makes HTTP and HTTPS calls. It lets you
specify which responses to return and then verify that requests were made as expected.

Because it exercises your full HTTP stack, you can be confident that you're testing everything. You can even copy &
paste HTTP responses from your real web server to create representative test cases. Or test that your code survives in
awkward-to-reproduce situations like 500 errors or slow-loading responses.

Set up your test environment:

- create resource directory on test folder.
- add file with your json service response.
- add next util class JsonLoader:

```kotlin
/**
 * read file content.
 */
class JsonLoader {

    fun load(file: String): String {
        val file = this.javaClass.classLoader?.getResourceAsStream(file)
        val loader = InputStreamReader(file)
        val jsonStr = loader.readText()
        loader.close()
        return jsonStr
    }
}
```

Set up your test

```kotlin

@ExperimentalCoroutinesApi
class MovieDataSourceIntegrationTest {

    private val jsonLoader = JsonLoader()
    private lateinit var movieDataSource: MovieDataSource
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val baseUrl = mockWebServer.url("/").toUri().toString()
        val client = service<TheMovieDBService>(baseUrl = baseUrl)
        movieDataSource = MovieDataSource(client)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}

```

Add case success and fail, for example:

```kotlin
@Test
fun success() {
    val response = MockResponse()
        .setResponseCode(HttpURLConnection.HTTP_OK)
        .setBody(jsonLoader.load("movie_popular_response_success"))

    mockWebServer.enqueue(response)

    runBlocking {
        val result = movieDataSource.getPopularMovies()
        assertThat(result.isSuccess, equalTo(true))
        assertThat(result.getOrNull()?.isNotEmpty(), equalTo(true))
    }
}

@Test
fun fail() {
    val response = MockResponse()
        .setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED)
        .setBody(jsonLoader.load("the_movie_db_response_fail"))

    mockWebServer.enqueue(response)

    runBlocking {
        val result = movieDataSource.getPopularMovies()
        assertThat(result.isFailure, equalTo(true))
    }
}
```

Check more information [here](https://github.com/square/okhttp/tree/master/mockwebserver).