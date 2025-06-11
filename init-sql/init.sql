INSERT IGNORE INTO `neutec-blog`.users (email, password, name, `role`)
VALUES ('admin@gmail.com', '$2a$10$kH7vKL7X/ZjpHnDdIzP8cuRYmtzX7yUDjLaDxC5/u5vBHaeS5siwW', 'Admin', 'ADMIN');

INSERT IGNORE INTO `neutec-blog`.users (email, password, name, `role`)
VALUES ('user@gmail.com', '$2a$10$kH7vKL7X/ZjpHnDdIzP8cuRYmtzX7yUDjLaDxC5/u5vBHaeS5siwW', 'User', 'USER');
