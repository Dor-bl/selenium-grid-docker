# Selenium Grid Docker

![Docker](https://img.shields.io/badge/Docker-Compose-blue?logo=docker)
![Java](https://img.shields.io/badge/Java-11+-orange?logo=openjdk)
![Selenium](https://img.shields.io/badge/Selenium-4.10.0-green?logo=selenium)
![Maven](https://img.shields.io/badge/Maven-3.6+-red?logo=apache-maven)

A containerized Selenium Grid setup with automated testing using Docker Compose. This project provides a complete testing environment with Selenium Hub and browser nodes (Chrome and Firefox) running in Docker containers, along with Java-based test automation.

## 🚀 Features

- **Selenium Grid 4** with Hub and Node architecture
- **Multi-browser support**: Chrome and Firefox nodes
- **Docker Compose** for easy orchestration
- **Java test automation** using JUnit 5 and Selenium WebDriver
- **RemoteWebDriver** configuration for distributed testing
- **Configurable timeouts** and session management

## 📋 Prerequisites

Before running this project, make sure you have the following installed:

- [Docker](https://docs.docker.com/get-docker/) (version 20.10 or higher)
- [Docker Compose](https://docs.docker.com/compose/install/) (v2.0 or higher - uses `docker compose` command)
- [Java JDK](https://adoptium.net/) (version 11 or higher)
- [Apache Maven](https://maven.apache.org/download.cgi) (version 3.6 or higher)

## 🛠️ Installation & Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Dor-bl/selenium-grid-docker.git
   cd selenium-grid-docker
   ```

2. **Start the Selenium Grid:**
   ```bash
   docker compose up -d
   ```

3. **Verify the Grid is running:**
   - Open your browser and navigate to http://localhost:4444
   - You should see the Selenium Grid Console

4. **Compile the test project:**
   ```bash
   mvn clean compile test-compile
   ```

## 🎯 Usage

### Starting the Selenium Grid

To start the complete Selenium Grid infrastructure:

```bash
# Start all services in detached mode
docker compose up -d

# View running containers
docker compose ps

# Check logs
docker compose logs -f
```

### Running Tests

Execute the automated tests against the running Grid:

```bash
# Run all tests
mvn test

# Run specific test
mvn test -Dtest=DockerGridTest#testGoogleSearchOnChrome
```

**Note**: Make sure the Selenium Grid is running (`docker compose up -d`) before executing tests, otherwise they will fail with connection errors.

### Stopping the Grid

```bash
# Stop all services
docker compose down

# Stop and remove volumes (if needed)
docker compose down -v
```

## 🏗️ Architecture

The project consists of the following components:

### Docker Services

| Service | Image | Port | Description |
|---------|-------|------|-------------|
| **selenium-hub** | selenium/hub:4.35.0 | 4444, 4442, 4443 | Central hub that manages test sessions |
| **chrome** | selenium/node-chrome:140.0-20250828 | - | Chrome browser node |
| **firefox** | selenium/node-firefox:4.10.0 | - | Firefox browser node |

### Configuration

- **Session timeout**: 5 minutes (300 seconds)
- **Session request timeout**: 1 minute (60 seconds)
- **Session retry timeout**: 10 seconds
- **Session poll interval**: 5 seconds

### Test Structure

```
src/
└── test/
    └── java/
        └── DockerGridTest.java    # Main test class with Chrome and Firefox tests
```

## 🧪 Test Details

The `DockerGridTest` class includes:

- **Chrome Test**: `testGoogleSearchOnChrome()`
- **Firefox Test**: `testGoogleSearchOnFirefox()`

Both tests perform the following actions:
1. Navigate to Google.com
2. Verify page title
3. Search for "Selenium Docker Java"
4. Verify search results are displayed

### Grid URL Configuration

Tests connect to the Selenium Grid at: `http://localhost:4444/wd/hub`

## 📊 Monitoring

You can monitor your Selenium Grid through:

- **Grid Console**: http://localhost:4444 - View active sessions and nodes
- **Docker Logs**: `docker-compose logs -f` - Monitor container logs
- **Node Status**: Check individual node health and capabilities

## 🔧 Troubleshooting

### Common Issues

1. **Port conflicts**: Ensure ports 4442, 4443, and 4444 are not in use
2. **Docker not running**: Make sure Docker daemon is started
3. **Browser nodes not connecting**: Check that containers can communicate via Docker network
4. **Tests failing**: Verify Grid is accessible at http://localhost:4444

### Useful Commands

```bash
# Check Docker container status
docker compose ps

# View logs for specific service
docker compose logs selenium-hub
docker compose logs chrome
docker compose logs firefox

# Restart specific service
docker compose restart chrome

# Scale browser nodes
docker compose up -d --scale chrome=2 --scale firefox=2
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Make your changes and test them
4. Commit your changes: `git commit -am 'Add your feature'`
5. Push to the branch: `git push origin feature/your-feature`
6. Submit a pull request

## 📝 Project Structure

```
selenium-grid-docker/
├── docker-compose.yml          # Docker Compose configuration
├── pom.xml                     # Maven project configuration
├── src/
│   └── test/
│       └── java/
│           └── DockerGridTest.java  # Test automation class
├── .gitignore                  # Git ignore rules
└── README.md                   # This documentation
```

### Dependencies

The project uses the following key dependencies:

- **Selenium Java**: 4.10.0 - WebDriver API and RemoteWebDriver
- **JUnit Jupiter**: 5.9.3 - Test framework for assertions and test lifecycle
- **Maven Surefire Plugin**: For test execution and reporting

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 🆘 Support

If you encounter any issues or have questions:

1. Check the [troubleshooting section](#-troubleshooting)
2. Review Docker and Selenium Grid logs
3. Open an issue in this repository

---

**Happy Testing! 🎉**