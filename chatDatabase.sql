CREATE TABLE users
(
    user_id  INT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(255)               NOT NULL,
    username VARCHAR(255) UNIQUE        NOT NULL,
    password VARCHAR(255)               NOT NULL,
    status   ENUM ('online', 'offline') NOT NULL DEFAULT 'offline'
);

CREATE TABLE contacts
(
    contact_id       INT PRIMARY KEY AUTO_INCREMENT,
    user_id          INT                          NOT NULL,
    contact_username VARCHAR(255)                 NOT NULL,
    status           ENUM ('approved', 'pending') NOT NULL DEFAULT 'pending',
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    UNIQUE KEY (user_id, contact_username)
);

CREATE TABLE chatrooms
(
    room_id   INT PRIMARY KEY AUTO_INCREMENT,
    room_type ENUM ('one-to-one', 'one-to-many') NOT NULL,
    room_name VARCHAR(255)                       NOT NULL
);

CREATE TABLE chatroom_users
(
    chatroom_user_id INT PRIMARY KEY AUTO_INCREMENT,
    room_id          INT                         NOT NULL,
    user_id          INT                         NOT NULL,
    status           ENUM ('active', 'inactive') NOT NULL DEFAULT 'active',
    FOREIGN KEY (room_id) REFERENCES chatrooms (room_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    UNIQUE KEY (room_id, user_id)
);

CREATE TABLE chat_messages
(
    message_id     INT PRIMARY KEY AUTO_INCREMENT,
    room_id        INT  NOT NULL,
    sender_user_id INT  NOT NULL,
    message_text   TEXT NOT NULL,
    timestamp      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES chatrooms (room_id),
    FOREIGN KEY (sender_user_id) REFERENCES users (user_id)
);
