DROP TABLE  IF EXISTS Users;

CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE  IF EXISTS Events;

CREATE TABLE Events (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    theme VARCHAR(100),
    challenge VARCHAR(100),
    goal VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE  IF EXISTS Questions;

CREATE TABLE Questions (
    question_id INT AUTO_INCREMENT PRIMARY KEY,
    question_text TEXT NOT NULL,
    question_type ENUM('multiple-choice', 'open-ended', 'problem-solving') NOT NULL,
    complexity_level ENUM('easy', 'medium', 'hard') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE  IF EXISTS EventQuestions;

CREATE TABLE EventQuestions (
    event_question_id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT,
    question_id INT,
    FOREIGN KEY (event_id) REFERENCES Events(event_id),
    FOREIGN KEY (question_id) REFERENCES Questions(question_id)
);

DROP TABLE  IF EXISTS Participants;

CREATE TABLE Participants (
    participant_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    event_id INT,
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (event_id) REFERENCES Events(event_id)
);

DROP TABLE  IF EXISTS Performances;

CREATE TABLE Performances (
    performance_id INT AUTO_INCREMENT PRIMARY KEY,
    participant_id INT,
    question_id INT,
    response TEXT,
    score DECIMAL(5, 2) NOT NULL,
    response_time TIMESTAMP NOT NULL,
    FOREIGN KEY (participant_id) REFERENCES Participants(participant_id),
    FOREIGN KEY (question_id) REFERENCES Questions(question_id)
);

Drop TABLE IF EXISTS Badges;

CREATE TABLE Badges (
    badge_id INT AUTO_INCREMENT PRIMARY KEY,
    badge_name VARCHAR(50) NOT NULL,
    description TEXT,
    criteria VARCHAR(200),
    badge_type ENUM('participation', 'top-performer', 'skill-mastery') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

Drop TABLE IF EXISTS UserBadges;

CREATE TABLE UserBadges (
    user_badge_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    badge_id INT,
    awarded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (badge_id) REFERENCES Badges(badge_id)
);

Drop TABLE IF EXISTS EventSchedules;

CREATE TABLE EventSchedules (
    schedule_id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT,
    recurrence ENUM('none', 'weekly', 'bi-weekly', 'monthly') NOT NULL,
    FOREIGN KEY (event_id) REFERENCES Events(event_id)
);