# Course Search Service

A Spring Boot application that indexes course data into Elasticsearch and exposes REST APIs to search courses with filters, pagination, sorting, autocomplete, and fuzzy search.




## Table of Contents
- <a href="#overview">Overview</a>
- <a href="#1-launch-elasticsearch">Launch Elasticsearch</a>
- <a href="#2-build-and-run-the-spring-boot-application">Build and Run Application</a>
- <a href="#3-populate-the-index-with-sample-data">Populate Index with Sample Data</a>
- <a href="#4-tech-stack">Tech Stack</a>
- <a href="#5-call-search-endpoints">Search API Usage</a>
- <a href="#6-autocomplete--fuzzy-search-bonus">Autocomplete & Fuzzy Search </a>
- <a href="#7-project-structure">Project Structure</a>



<h2><a class="anchor" id="overview"></a>Overview</h2>

- This project is a Spring Boot application that indexes course data into Elasticsearch and provides REST APIs to search courses efficiently. It supports full-text search, multiple filters, pagination, and sorting based on upcoming sessions or price.  

- As a bonus, the application also implements autocomplete suggestions and fuzzy search to handle partial inputs and small typos. Elasticsearch runs locally using Docker Compose, and sample course data is automatically indexed at application startup.





<h2><a class="anchor" id="1-launch-elasticsearch"></a>1. Launch Elasticsearch</h2>

Elasticsearch is run locally using Docker Compose. Make sure Docker is installed in the system.

### Commands

Open command prompt from the project folder and run this command

```bash
docker-compose up -d

```

#### Verify Elasticsearch:

```bash
curl http://localhost:9200

```

After starting Elasticsearch using Docker Compose, verify that the service is running correctly by sending a request to http://localhost:9200. A successful response will return basic cluster information such as the cluster name and version details in JSON format. This confirms that Elasticsearch is up and accessible on port 9200 and ready to accept indexing and search requests from the Spring Boot application.


#### Expected Response:

```bash
{
  "name": "elasticsearch",
  "cluster_name": "docker-cluster",
  "version": { ... }
}

```

Elasticsearch will be available on port 9200.





<h2><a class="anchor" id="2-build-and-run-the-spring-boot-application"></a>2. Build and Run SpringBoot Application</h2>

- Build the application and run this command on command prompt after the Elasticsearch is up

```bash
mvn clean install

```

- Run the application

```bash
mvn spring-boot:run

```

- The application starts on:

```bash
http://localhost:8080

```






<h2><a class="anchor" id="3-populate-the-index-with-sample-data"></a>3. Populate Index with Sample Data</h2>

At application startup, the service automatically reads the file:

```bash
src/main/resources/sample-courses.json

```

and bulk-indexes all course documents into the Elasticsearch courses index.

### Verification

After the application starts, you can verify indexing by running:

```bash
curl http://localhost:9200/courses/_count

```

### Expected output:

```bash
{
  "count": 50
}

```





<h2><a class="anchor" id="4-tech-stack"></a>4. Tech Stack</h2>

```bash
- Java 17
- Spring Boot
- Spring Data Elasticsearch
- Elasticsearch 8.x
- Docker & Docker Compose
- Maven

```






<h2><a class="anchor" id="5-call-search-endpoints"></a>5. Search API Usage</h2>

### Search Endpoint

```bash
GET /api/search

```

### Call Search Endpoints

- Example: Keyword Search + Sorting

```bash
curl "http://localhost:8080/api/search?q=math&minAge=6&sort=priceAsc"

```

- Example: Filter by Category and Type

```bash
curl "http://localhost:8080/api/search?category=Science&type=COURSE"

```

- Sample Response:

```bash
{
  "total": 12,
  "courses": [
    {
      "id": "course-21",
      "title": "Fun Math Adventures",
      "category": "Math",
      "price": 299.0,
      "nextSessionDate": "2025-06-12T10:00:00Z"
    }
  ]
}
```





<h2><a class="anchor" id="6-autocomplete--fuzzy-search-bonus"></a>6. Autocomplete & Fuzzy Search</h2>


- Autocomplete API Endpoint

```bash
GET /api/search/suggest

```

Example:

```bash
curl "http://localhost:8080/api/search/suggest?q=phy"

```

Response:

```bash
[
  "Physics Basics",
  "Physics Experiments for Kids"
]

```

### Fuzzy Search Example

The search endpoint supports fuzzy matching for typos.

```bash
curl "http://localhost:8080/api/search?q=dinors"

```

#### Expected behavior:

- Matches courses with title "Dinosaurs 101"

- Demonstrates typo tolerance using Elasticsearch fuzziness
Notes

```bash
Notes

- Elasticsearch runs on port 9200

- Spring Boot runs on port 8080

- Data is bulk indexed at application startup

- Search supports fuzzy matching for typos

```



<h2><a class="anchor" id="7-project-structure"></a>7. Project Structure</h2>

```text
src/main/java
└── com.course.search.main
    ├── CourseSearchApplication.java
    ├── config
    │   ├── ElasticsearchConfig.java
    │   └── JacksonConfig.java
    ├── controller
    │   └── CourseSearchController.java
    ├── document
    │   └── CourseDocument.java
    ├── dto
    │   ├── CourseResponseDTO.java
    │   ├── SearchRequestDTO.java
    │   └── SearchResponseDTO.java
    ├── mapper
    │   └── CourseMapper.java
    ├── repository
    │   └── CourseRepository.java
    └── service
        ├── CourseIndexService.java
        └── CourseSearchService.java

src/main/resources
├── application.yml
└── sample-courses.json

├── docker-compose.yml
├── pom.xml


```

The project follows a layered architecture where controllers handle API requests,
services contain business logic, repositories interact with Elasticsearch, and
DTOs are used for clean request and response models.