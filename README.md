
# Soda Vending Machine Source code

This application includes two main parts:
- **Server**: Contains the backend source code built with Java Spring Boot.
- **Client**: Contains the frontend source code built with ReactJS.


---

## System Requirements

### Server
- **JDK 17**: Ensure that JDK 17 is installed.
- **Maven**: Ensure Maven is installed (if not using an IDE like IntelliJ IDEA or Eclipse that supports Maven).

### Client
- **Node.js**: Ensure Node.js is installed (latest version recommended).
- **NPM**: Ensure npm is available to manage packages for React.

---

## Running the Application

### 1. Running the Server (Java Spring Boot)

1. Navigate to the `server` directory:
    ```bash
    cd server
    ```

2. Open the project in an IDE and run the main file `MomoApplication.java`:
    - File path: `src/main/java/com/momo/MomoApplication.java`.
    - Locate the `main` method and run `MomoApplication.java` from the IDE.

   **Or**, if using Maven from the command line:
   ```bash
   mvn spring-boot:run

### 2. Running the Client (React)

1. **Navigate to the `client` directory**:
    ```bash
    cd client
    ```

2. **Install the required packages using npm**:
    ```bash
    npm install
    ```

3. **Start the React application**:
    ```bash
    npm run dev
    ```

4. The frontend will be available at `http://localhost:5173` (or the configured port).
