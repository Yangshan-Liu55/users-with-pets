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

_Build backend Docker image_  
cd uerspet  
docker build -t userspet .  
docker run -p 8080:8080 userspet

#### Frontend runs at:
npm run dev  
http://localhost:5173

#### Docker, from project root:
cd UsersPets  
docker compose up --build  
URLs:  
Backend: http://localhost:8080/api/users-with-pet  
Frontend: http://localhost:3000

#### Backend deployed to Render
https://users-with-pets.onrender.com/api/users-with-pet  
> Render free tier blocks outbound HTTP requests for 30–50 seconds while the instance spins up from “sleep” state. So UsersWithPets data is hardcoded just for testing.

#### Frontend deployed to Vercel
https://users-with-pets-qokr.vercel.app

#### Screenshot Collection (PDF)
All app screenshots are available in a single consolidated PDF:  
![View Uers with Pets Screenshots](Users_with_pets_screenshots.pdf)