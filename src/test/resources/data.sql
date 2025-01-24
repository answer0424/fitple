INSERT INTO User (username, password, email, nickname, address, birth, profile_image, authority, created_at)
VALUES
    ('user1', 'password1', 'user1@example.com', 'Runner1', 'Seoul, Korea', '1990-01-01', '/img/profile1.png', 'ROLE_USER', NOW()),
    ('user2', 'password2', 'user2@example.com', 'Runner2', 'Busan, Korea', '1991-02-02', '/img/profile2.png', 'ROLE_USER', NOW()),
    ('user3', 'password3', 'user3@example.com', 'Sprinter3', 'Daegu, Korea', '1992-03-03', '/img/profile3.png', 'ROLE_USER', NOW()),
    ('user4', 'password4', 'user4@example.com', 'Jumper4', 'Incheon, Korea', '1993-04-04', '/img/profile4.png', 'ROLE_USER', NOW()),
    ('user5', 'password5', 'user5@example.com', 'Cyclist5', 'Daejeon, Korea', '1994-05-05', '/img/profile5.png', 'ROLE_USER', NOW()),
    ('user6', 'password6', 'user6@example.com', 'Swimmer6', 'Gwangju, Korea', '1995-06-06', '/img/profile6.png', 'ROLE_USER', NOW()),
    ('user7', 'password7', 'user7@example.com', 'Lifter7', 'Suwon, Korea', '1996-07-07', '/img/profile7.png', 'ROLE_USER', NOW()),
    ('user8', 'password8', 'user8@example.com', 'Rowing8', 'Ulsan, Korea', '1997-08-08', '/img/profile8.png', 'ROLE_USER', NOW()),
    ('user9', 'password9', 'user9@example.com', 'Skater9', 'Jeju, Korea', '1998-09-09', '/img/profile9.png', 'ROLE_USER', NOW()),
    ('user10', 'password10', 'user10@example.com', 'Hiker10', 'Gangwon, Korea', '1999-10-10', '/img/profile10.png', 'ROLE_USER', NOW());

SELECT * FROM User WHERE username = 'user1';
SELECT * FROM User;
SELECT * FROM HBTI;
SELECT * FROM HBTI WHERE user_id = '1';

