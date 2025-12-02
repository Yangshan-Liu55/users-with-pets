# Users With Pets – Full Stack Assignment

This project fetches user data from **RandomUser API** and pet images from **Dog API**, aggregates them in a Spring Boot backend, and displays the final result in a **React + Vite** frontend.

## Tech Stack

### Backend
- Java 17  
- Spring Boot  
- Maven  
- REST Controllers  
- External API integration  
- Docker  

### Frontend
- React (Vite)
- TypeScript
- Fetch API client
- Responsive grid UI
- Docker (Nginx production build)

### Dev Tools
- Docker & Docker Compose
- VS Code / IntelliJ

---

#### Backend runs at:
http://localhost:8080/api/users-with-pet

Build backend Docker image
cd uerspet
docker build -t userspet .
docker run -p 8080:8080 userspet

#### Frontend runs at:
npm run dev
http://localhost:5173

From project root:
cd UsersPets
docker compose up --build
URLs:
Backend: http://localhost:8080/api/users-with-pet
Frontend: http://localhost:3000
