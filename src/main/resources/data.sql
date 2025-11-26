-- Users
INSERT INTO users(username, password, role) VALUES
('user1', '$2a$10$H8QLwaxD8sAxnbsRNcyLxuly7hsODUgUSCAxSdfAN.9LEyvqzpDTu', 'ROLE_USER'),
('user2', '$2a$10$H8QLwaxD8sAxnbsRNcyLxuly7hsODUgUSCAxSdfAN.9LEyvqzpDTu', 'ROLE_USER'),
('admin', '$2a$10$H8QLwaxD8sAxnbsRNcyLxuly7hsODUgUSCAxSdfAN.9LEyvqzpDTu', 'ROLE_USER');
-- string 비밀번호

-- Books
INSERT INTO book(title, author, available) VALUES
('Spring Boot 입문', '홍길동', false),
('JPA 완전 정복', '김철수', false),
('Java 21 핵심', '이영희', false),
('Docker & Kubernetes', '박민수', false),
('리액트 쉽게 배우기', '최유진', false),
('AWS 클라우드 마스터', '김민호', false),
('알고리즘 문제 해결 전략', '강지훈', false),
('데이터베이스 실습', '서지현', false);

-- Rental Records
INSERT INTO rental_record(user_id, rental_date, return_date, book_id) VALUES
(1, NOW() - INTERVAL 10 DAY, NOW() - INTERVAL 3 DAY, 1),
(1, NOW() - INTERVAL 7 DAY, NOW() - INTERVAL 1 DAY, 2),
(1, NOW() - INTERVAL 5 DAY, NULL, 3),
(2, NOW() - INTERVAL 8 DAY, NOW() - INTERVAL 2 DAY, 4),
(2, NOW() - INTERVAL 6 DAY, NULL, 5),
(3, NOW() - INTERVAL 4 DAY, NULL, 6),
(3, NOW() - INTERVAL 12 DAY, NOW() - INTERVAL 5 DAY, 7);
