-- 1. Файлы (нет зависимостей)
INSERT INTO files (name, path) VALUES
('inception.jpg',    '/posters/inception.jpg'),
('godfather.jpg',    '/posters/godfather.jpg'),
('interstellar.jpg', '/posters/interstellar.jpg');

-- 2. Жанры (нет зависимостей)
INSERT INTO genres (name) VALUES
('Фантастика'),
('Драма'),
('Криминал'),
('Комедия');

-- 3. Залы (нет зависимостей)
INSERT INTO halls (name, row_count, place_count, description) VALUES
('Зал №1 Стандарт', 10, 15, 'Стандартные кресла, обычный экран'),
('Зал №2 VIP',      6,  8,  'Кожаные кресла, повышенный комфорт'),
('Зал №3 IMAX',     12, 20, 'Огромный экран, объемный звук Dolby Atmos');

-- 4. Фильмы (зависит от genres.id и files.id)
INSERT INTO films (name, description, "year", genre_id, minimal_age, duration_in_minutes, file_id) VALUES
('Начало',          'Сон внутри сна. Профессиональные воры крадут секреты из подсознания.',       2010, 1, 12, 148, 1),
('Крестный отец',   'Сага о могущественной мафиозной семье Корлеоне.',                           1972, 3, 18, 175, 2),
('Интерстеллар',    'Группа исследователей путешествует через червоточину в поисках нового дома.', 2014, 1, 12, 169, 3);

-- 5. Киносеансы (зависит от films.id и halls.id)
INSERT INTO film_sessions (film_id, halls_id, start_time, end_time, price) VALUES
(1, 3, '2026-05-15 18:00:00', '2026-05-15 20:28:00', 450),
(2, 2, '2026-05-15 20:00:00', '2026-05-15 22:55:00', 800),
(3, 1, '2026-05-16 15:00:00', '2026-05-16 17:49:00', 300),
(1, 1, '2026-05-16 19:00:00', '2026-05-16 21:28:00', 300);