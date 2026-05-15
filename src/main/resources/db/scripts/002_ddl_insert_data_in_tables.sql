-- 1. Файлы (постеры)
INSERT INTO files (name, path) VALUES
('inception.jpg',    'posters/inception.jpg'),
('godfather.jpg',    'posters/godfather.jpg'),
('interstellar.jpg', 'posters/interstellar.jpg'),
('view.jpg',         'posters/view.jpg'),
('bicentennial_man.jpg',    'posters/bicentennial_man.jpg'),
('shawshank_redemption.jpg','posters/shawshank_redemption.jpg'),
('ghost.jpg',               'posters/ghost.jpg');

-- 2. Жанры
INSERT INTO genres (name) VALUES
('Фантастика'),
('Драма'),
('Криминал'),
('Комедия'),
('Мелодрама');

-- 3. Залы
INSERT INTO halls (name, row_count, place_count, description) VALUES
('Зал №1 Стандарт', 10, 15, 'Стандартные кресла, обычный экран'),
('Зал №2 VIP',      6,  8,  'Кожаные кресла, повышенный комфорт'),
('Зал №3 IMAX',     12, 20, 'Огромный экран, объемный звук Dolby Atmos');

-- 4. Фильмы
INSERT INTO films (name, description, "year", genre_id, minimal_age, duration_in_minutes, file_id) VALUES
('Начало',          'Сон внутри сна. Профессиональные воры крадут секреты из подсознания.',       2010, (SELECT id FROM genres WHERE name = 'Фантастика'), 12, 148, (SELECT id FROM files WHERE name = 'inception.jpg')),
('Крестный отец',   'Сага о могущественной мафиозной семье Корлеоне.',                           1972, (SELECT id FROM genres WHERE name = 'Криминал'),    18, 175, (SELECT id FROM files WHERE name = 'godfather.jpg')),
('Интерстеллар',    'Группа исследователей путешествует через червоточину в поисках нового дома.', 2014, (SELECT id FROM genres WHERE name = 'Фантастика'), 12, 169, (SELECT id FROM files WHERE name = 'interstellar.jpg')),
('Двухсотлетний человек', 'История робота Эндрю, который стремится стать человеком. Трогательная сага о любви, свободе и поиске себя на протяжении 200 лет.', 1999, (SELECT id FROM genres WHERE name = 'Фантастика'), 12, 132, (SELECT id FROM files WHERE name = 'bicentennial_man.jpg')),
('Побег из Шоушенка', 'Банкир Энди Дюфрейн осуждён за убийство жены и её любовника, которого не совершал. За 20 лет тюрьмы он не теряет надежды на свободу.', 1994, (SELECT id FROM genres WHERE name = 'Драма'), 18, 142, (SELECT id FROM files WHERE name = 'shawshank_redemption.jpg')),
('Призрак', 'Молодая пара Молли и Сэм счастлива вместе, но трагическая случайность разлучает их. Сэм становится призраком и пытается предупредить возлюбленную об опасности. В главной роли Патрик Суэйзи.', 1990, (SELECT id FROM genres WHERE name = 'Драма'), 16, 127, (SELECT id FROM files WHERE name = 'ghost.jpg'));

-- 5. Киносеансы
INSERT INTO film_sessions (film_id, halls_id, start_time, end_time, price) VALUES
-- Существующие сеансы
((SELECT id FROM films WHERE name = 'Начало'),          (SELECT id FROM halls WHERE name = 'Зал №3 IMAX'),     '2026-05-15 18:00:00', '2026-05-15 20:28:00', 450),
((SELECT id FROM films WHERE name = 'Крестный отец'),   (SELECT id FROM halls WHERE name = 'Зал №2 VIP'),      '2026-05-15 20:00:00', '2026-05-15 22:55:00', 800),
((SELECT id FROM films WHERE name = 'Интерстеллар'),    (SELECT id FROM halls WHERE name = 'Зал №1 Стандарт'), '2026-05-16 15:00:00', '2026-05-16 17:49:00', 300),
((SELECT id FROM films WHERE name = 'Начало'),          (SELECT id FROM halls WHERE name = 'Зал №1 Стандарт'), '2026-05-16 19:00:00', '2026-05-16 21:28:00', 300),
-- Новые сеансы для "Двухсотлетнего человека"
((SELECT id FROM films WHERE name = 'Двухсотлетний человек'), (SELECT id FROM halls WHERE name = 'Зал №1 Стандарт'), '2026-05-20 19:00:00', '2026-05-20 21:12:00', 350),
((SELECT id FROM films WHERE name = 'Двухсотлетний человек'), (SELECT id FROM halls WHERE name = 'Зал №3 IMAX'),     '2026-05-21 15:00:00', '2026-05-21 17:12:00', 400),
-- Новые сеансы для "Побега из Шоушенка"
((SELECT id FROM films WHERE name = 'Побег из Шоушенка'),     (SELECT id FROM halls WHERE name = 'Зал №2 VIP'),      '2026-05-20 20:30:00', '2026-05-20 22:52:00', 500),
((SELECT id FROM films WHERE name = 'Побег из Шоушенка'),     (SELECT id FROM halls WHERE name = 'Зал №1 Стандарт'), '2026-05-22 18:00:00', '2026-05-22 20:22:00', 380),
-- Новые сеансы для "Призрака"
((SELECT id FROM films WHERE name = 'Призрак'),               (SELECT id FROM halls WHERE name = 'Зал №2 VIP'),      '2026-05-21 20:00:00', '2026-05-21 22:07:00', 450),
((SELECT id FROM films WHERE name = 'Призрак'),               (SELECT id FROM halls WHERE name = 'Зал №1 Стандарт'), '2026-05-23 16:30:00', '2026-05-23 18:37:00', 320);