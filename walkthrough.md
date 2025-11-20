# FitFusion - Intelligent Gym & Fitness Management System

FitFusion is a comprehensive fitness management application built with Spring Boot, PostgreSQL, and TailwindCSS. It features workout tracking, nutrition management with local Pakistani food support, and AI-powered recommendations using the Google Gemini API.

## 🚀 Getting Started

### Prerequisites
-   **Java 17+**
-   **Maven**
-   **PostgreSQL** (Database: `fitfusion`, User: `postgres`, Pass: `postgres`)
-   **Node.js & npm** (for TailwindCSS)

### Installation & Running

1.  **Database Setup**:
    Ensure your PostgreSQL server is running and create the database:
    ```sql
    CREATE DATABASE fitfusion;
    ```

2.  **Build CSS**:
    Generate the TailwindCSS output:
    ```bash
    npm install
    npm run build:css
    ```

3.  **Run Application**:
    Start the Spring Boot application:
    ```bash
    mvn spring-boot:run
    ```
    Access the app at `http://localhost:8080`.

## 🌟 Key Features

### 1. User Authentication & Profile
-   **Secure Login/Register**: Users can create accounts and log in securely.
-   **Profile Setup**: Users input their age, height, weight, goals, and activity level to personalize their experience.

### 2. Workout Management
-   **Exercise Library**: Browse exercises by muscle group with detailed instructions, tips, and videos.
-   **Workout Builder**: Create custom workout plans by selecting exercises and defining sets/reps.
-   **Workout Logging**: Log daily sessions, tracking weights, sets, and reps.

### 3. Nutrition Tracking
-   **Diet Log**: Track daily meals (Breakfast, Lunch, Dinner, Snacks).
-   **Local Foods**: Includes a database of foods, with a focus on local Pakistani options.
-   **Macro Calculation**: Automatically calculates daily calories, protein, carbs, and fats.

### 4. AI Integration (Gemini)
-   **AI Coach**: Get personalized workout routines based on your profile and goals.
-   **AI Nutritionist**: Generate meal plans tailored to your preferences.
-   **Settings**: Configure your own Gemini API Key in the settings menu.

### 5. Admin Dashboard
-   **Content Management**: Admins can add new muscle groups, exercises, and food items to the system.
-   **Access**: The admin dashboard is available at `/admin`.

## 📸 Screenshots & Usage

### Dashboard
The central hub showing your daily progress for workouts and nutrition.
![Dashboard](https://via.placeholder.com/800x450?text=Dashboard+Preview)

### Workout Builder
Create your own routines easily.
![Workout Builder](https://via.placeholder.com/800x450?text=Workout+Builder)

### Diet Tracker
Log your meals and watch your macros.
![Diet Tracker](https://via.placeholder.com/800x450?text=Diet+Tracker)

## 🛠️ Tech Stack
-   **Backend**: Spring Boot (Web, JPA, Security)
-   **Database**: PostgreSQL
-   **Frontend**: Thymeleaf, HTML5, TailwindCSS
-   **AI**: Google Gemini API

## 📝 Notes
-   **API Key**: To use the AI features, you must obtain a free API key from [Google AI Studio](https://aistudio.google.com/) and enter it in the Settings page.
-   **Admin Role**: To access admin features, you may need to manually set a user's role to `ROLE_ADMIN` in the database or modify the code to create a default admin.
