# News App

This API uses various HTTP [request methods](https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods) to communicate
and HTTP [response codes](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status) to indicate status and errors.
All responses come in standard JSON. All requests must include a `content-type` of `application/json` and the body must
be valid JSON. It uses PostgreSQL for persistence of data.

## Get started
### Prerequisites
The application use `Docker` to run container for `PostgreSQL` database and `Gradle` for building the application.

Start the database by using
```shell
$ docker-compose up
```

Start the application by using
```shell
$ gradle bootRun
```


## Available API resources

### Return all articles
**Example request:**
```json
GET /articles HTTP/2
Accept: application/json
Content-Type: application/json
```
**Example response:**
```json
HTTP/2 200 OK
Content-Type: application/json
[
    {
        "id": 1,
        "title": "Bad weather",
        "body": "-10 degress in April...",
        "authorName": "Test Testson",
        "topics": []
    }
]
```

### Return a specific article based on the provided id
**Example request:**
```json
GET /articles/1 HTTP/2
Accept: application/json
Content-Type: application/json
```
**Example response:**
```json
HTTP/2 200 OK
Content-Type: application/json
{
    "id": 1,
    "title": "Bad weather",
    "body": "-10 degress in April...",
    "authorName": "Test Testson",
    "topics": []
}
```

### Create a new article
**Example request:**
```json
POST /articles HTTP/2
Accept: application/json
Content-Type: application/json
{
    "title": "Bad weather",
    "body": "-10 degress in April...",
    "authorName": "Test Testson"
}
```
**Example response:**
```json
HTTP/2 201 Created
Content-Type: application/json
{
    "id": 1,
    "title": "Bad weather",
    "body": "-10 degress in April...",
    "authorName": "Test Testson",
    "topics": null
}
```

### Update the given article
**Example request:**
```json
PUT /articles/1 HTTP/2
Accept: application/json
Content-Type: application/json
{
    "title": "Bad weather",
    "body": "0 degress in April...",
    "authorName": "Test Testson"
}
```
**Example response:**
```json
HTTP/2 200 OK
Content-Type: application/json
{
    "id": 1,
    "title": "Bad weather",
    "body": "0 degress in April...",
    "authorName": "Test Testson",
    "topics": []
}
```

### Delete the given article
**Example request:**
```json
DELETE /articles/1 HTTP/2
Accept: application/json
Content-Type: application/json
```
**Example response:**
```json
HTTP/2 200 OK
```

### Return all comments on article given by articleId
**Example request:**
```json
GET /articles/1/comments HTTP/2
Accept: application/json
Content-Type: application/json
```
**Example response:**
```json
HTTP/2 200 OK
Content-Type: application/json
[
    {
        "id": 1,
        "body": "This article is very well written",
        "authorName": "John Smith",
        "article": {
            "id": 1,
            "title": "Bad weather",
            "body": "0 degress in April...",
            "authorName": "Test Testson",
            "topics": []
        }
    }
]
```

### Return all comments made by author given by authorName
**Example request:**
```json
GET /comments?authorName=John%20Smith HTTP/2
Accept: application/json
Content-Type: application/json
```
**Example response:**
```json
HTTP/2 200 OK
Content-Type: application/json
[
    {
        "id": 1,
        "body": "This article is very well written",
        "authorName": "John Smith",
        "article": {
            "id": 1,
            "title": "Bad weather",
            "body": "0 degress in April...",
            "authorName": "Test Testson",
            "topics": []
        }
    }
]
```

### Create a new comment on article given by articleId
**Example request:**
```json
POST /articles/1/comments HTTP/2
Accept: application/json
Content-Type: application/json
{
    "body": "This article is very well written",
    "authorName": "John Smith"
}
```
**Example response:**
```json
HTTP/2 201 Created
Content-Type: application/json
[
    {
        "id": 1,
        "body": "This article is very well written",
        "authorName": "John Smith",
        "article": {
            "id": 1,
            "title": "Bad weather",
            "body": "0 degress in April...",
            "authorName": "Test Testson",
            "topics": []
        }
    }
]
```

### Update the given comment
**Example request:**
```json
PUT /comments/1 HTTP/2
Accept: application/json
Content-Type: application/json
{
    "body": "This article is very well written. Good job!",
    "authorName": "John Smith"
}
```
**Example response:**
```json
HTTP/2 200 Ok
Content-Type: application/json
[
    {
        "id": 1,
        "body": "This article is very well written. Good job!",
        "authorName": "John Smith",
        "article": {
            "id": 1,
            "title": "Bad weather",
            "body": "0 degress in April...",
            "authorName": "Test Testson",
            "topics": []
        }
    }
]
```

### Delete the given comment.
**Example request:**
```json
DELETE /comments/1 HTTP/2
Accept: application/json
Content-Type: application/json
```
**Example response:**
```json
HTTP/2 200 Ok
```