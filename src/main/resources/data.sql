-- hashtag
INSERT INTO hashtag (created_at, modified_at, name, total_likes)
VALUES (NOW(), NOW(), 'Pop', 0),
       (NOW(), NOW(), 'K-Pop', 0),
       (NOW(), NOW(), 'Hip Hop', 0),
       (NOW(), NOW(), 'R&B', 0),
       (NOW(), NOW(), 'Rock', 0),
       (NOW(), NOW(), 'Metal', 0),
       (NOW(), NOW(), 'Indie', 0),
       (NOW(), NOW(), 'Electronic', 0),
       (NOW(), NOW(), 'EDM', 0),
       (NOW(), NOW(), 'House', 0),
       (NOW(), NOW(), 'Techno', 0),
       (NOW(), NOW(), 'Trance', 0),
       (NOW(), NOW(), 'Dubstep', 0),
       (NOW(), NOW(), 'Jazz', 0),
       (NOW(), NOW(), 'Punk', 0),
       (NOW(), NOW(), 'Blues', 0),
       (NOW(), NOW(), 'Soul', 0),
       (NOW(), NOW(), 'Classical', 0),
       (NOW(), NOW(), 'Opera', 0),
       (NOW(), NOW(), 'Ballad', 0),
       (NOW(), NOW(), 'Folk', 0),
       (NOW(), NOW(), 'Country', 0),
       (NOW(), NOW(), 'Reggae', 0),
       (NOW(), NOW(), 'World Music', 0),
       (NOW(), NOW(), 'CCM', 0),
       (NOW(), NOW(), 'OST', 0),
       (NOW(), NOW(), 'New Age', 0),
       (NOW(), NOW(), 'Ambient', 0),
       (NOW(), NOW(), 'Chillout', 0),
       (NOW(), NOW(), 'Lo-Fi', 0),
       (NOW(), NOW(), 'Synthwave', 0),
       (NOW(), NOW(), 'Trap', 0),
       (NOW(), NOW(), 'Funk', 0),
       (NOW(), NOW(), 'Soulful House', 0),
       (NOW(), NOW(), 'Progressive House', 0),
       (NOW(), NOW(), 'Hardstyle', 0);

-- user_hashtag
INSERT INTO user_hashtag (user_id, hashtag_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (3, 6),
       (4, 7),
       (4, 8),
       (5, 9),
       (5, 10);


-- playlist
INSERT INTO playlist (title, image_url, user_id)
VALUES ('Chill Vibes', 'http://example.com/image1', 1);
INSERT INTO playlist (title, image_url, user_id)
VALUES ('Workout Hits', 'http://example.com/image2', 2);
INSERT INTO playlist (title, image_url, user_id)
VALUES ('Relaxing Tunes', 'http://example.com/image3', 3);
INSERT INTO playlist (title, image_url, user_id)
VALUES ('Party Mix', 'http://example.com/image4', 4);
INSERT INTO playlist (title, image_url, user_id)
VALUES ('Study Beats', 'http://example.com/image5', 5);


-- PlaylistTrack
INSERT INTO playlist_track (playlist_id, track_id, name, artist, duration, preview_url,
                            thumbnail_url)
VALUES (1, 'track1', 'Song One', 'Artist One', '3:30', 'http://example.com/preview1',
        'http://example.com/thumbnail1'),
       (1, 'track2', 'Song Two', 'Artist Two', '4:00', NULL, 'http://example.com/thumbnail2'),
       (1, 'track3', 'Song Three', 'Artist Three', '2:45', 'http://example.com/preview3',
        'http://example.com/thumbnail3'),
       (1, 'track4', 'Song Four', 'Artist Four', '3:50', 'http://example.com/preview4',
        'http://example.com/thumbnail4'),
       (1, 'track5', 'Song Five', 'Artist Five', '3:20', 'http://example.com/preview5',
        'http://example.com/thumbnail5'),
       (1, 'track6', 'Song Six', 'Artist Six', '3:10', NULL, 'http://example.com/thumbnail6'),
       (2, 'track7', 'Song Seven', 'Artist Seven', '4:10', 'http://example.com/preview7',
        'http://example.com/thumbnail7'),
       (2, 'track8', 'Song Eight', 'Artist Eight', '3:00', 'http://example.com/preview8',
        'http://example.com/thumbnail8'),
       (2, 'track9', 'Song Nine', 'Artist Nine', '3:40', 'http://example.com/preview9',
        'http://example.com/thumbnail9'),
       (2, 'track10', 'Song Ten', 'Artist Ten', '2:50', NULL, 'http://example.com/thumbnail10'),
       (3, 'track11', 'Song Eleven', 'Artist Eleven', '3:30', 'http://example.com/preview11',
        'http://example.com/thumbnail11'),
       (3, 'track12', 'Song Twelve', 'Artist Twelve', '4:00', NULL,
        'http://example.com/thumbnail12'),
       (3, 'track13', 'Song Thirteen', 'Artist Thirteen', '2:45', 'http://example.com/preview13',
        'http://example.com/thumbnail13'),
       (3, 'track14', 'Song Fourteen', 'Artist Fourteen', '3:50', 'http://example.com/preview14',
        'http://example.com/thumbnail14'),
       (3, 'track15', 'Song Fifteen', 'Artist Fifteen', '3:20', 'http://example.com/preview15',
        'http://example.com/thumbnail15'),
       (3, 'track16', 'Song Sixteen', 'Artist Sixteen', '3:10', NULL,
        'http://example.com/thumbnail16'),
       (4, 'track17', 'Song Seventeen', 'Artist Seventeen', '4:10', 'http://example.com/preview17',
        'http://example.com/thumbnail17'),
       (4, 'track18', 'Song Eighteen', 'Artist Eighteen', '3:00', 'http://example.com/preview18',
        'http://example.com/thumbnail18'),
       (4, 'track19', 'Song Nineteen', 'Artist Nineteen', '3:40', 'http://example.com/preview19',
        'http://example.com/thumbnail19'),
       (4, 'track20', 'Song Twenty', 'Artist Twenty', '2:50', NULL,
        'http://example.com/thumbnail20'),
       (5, 'track21', 'Song Twenty-One', 'Artist Twenty-One', '3:30',
        'http://example.com/preview21', 'http://example.com/thumbnail21'),
       (5, 'track22', 'Song Twenty-Two', 'Artist Twenty-Two', '4:00', NULL,
        'http://example.com/thumbnail22'),
       (5, 'track23', 'Song Twenty-Three', 'Artist Twenty-Three', '2:45',
        'http://example.com/preview23', 'http://example.com/thumbnail23'),
       (5, 'track24', 'Song Twenty-Four', 'Artist Twenty-Four', '3:50',
        'http://example.com/preview24', 'http://example.com/thumbnail24'),
       (5, 'track25', 'Song Twenty-Five', 'Artist Twenty-Five', '3:20',
        'http://example.com/preview25', 'http://example.com/thumbnail25'),
       (5, 'track26', 'Song Twenty-Six', 'Artist Twenty-Six', '3:10', NULL,
        'http://example.com/thumbnail26');

-- POST
INSERT INTO post (title, description, post_title_image_url, like_count, is_deleted, created_at,
                  modified_at, playlist_id, user_id)
VALUES ('첫 번째 게시글 제목', '첫 번째 게시글 설명', 'https://via.placeholder.com/150', 10, FALSE, NOW(), NOW(), 1,
        1),
       ('두 번째 게시글 제목', '두 번째 게시글 설명', 'https://via.placeholder.com/150', 5, FALSE, NOW(), NOW(), 1,
        2),
       ('세 번째 게시글 제목', '세 번째 게시글 설명', 'https://via.placeholder.com/150', 20, FALSE, NOW(), NOW(), 2,
        3),
       ('네 번째 게시글 제목', '네 번째 게시글 설명', NULL, 15, FALSE, NOW(), NOW(), 2, 4),
       ('다섯 번째 게시글 제목', '다섯 번째 게시글 설명', 'https://via.placeholder.com/150', 25, FALSE, NOW(), NOW(),
        3, 5);

-- post_hashtag
INSERT INTO post_hashtag (post_id, hashtag_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (3, 6),
       (4, 7),
       (4, 8),
       (5, 9),
       (5, 10),
       (1, 3),
       (2, 5),
       (3, 7),
       (4, 9),
       (5, 1);

-- Comment
INSERT INTO comment(content, user_id, post_id, created_at, modified_at)
VALUES ("comment1", 1, 1, NOW(), NOW()),
       ("comment2", 2, 1, NOW(), NOW()),
       ("comment3", 3, 1, NOW(), NOW()),
       ("comment4", 4, 1, NOW(), NOW()),
       ("comment5", 5, 1, NOW(), NOW()),
       ("comment1", 1, 2, NOW(), NOW()),
       ("comment2", 2, 2, NOW(), NOW()),
       ("comment3", 3, 2, NOW(), NOW()),
       ("comment4", 4, 2, NOW(), NOW()),
       ("comment5", 5, 2, NOW(), NOW()),
       ("comment1", 1, 3, NOW(), NOW()),
       ("comment2", 2, 3, NOW(), NOW()),
       ("comment3", 3, 3, NOW(), NOW()),
       ("comment4", 4, 3, NOW(), NOW()),
       ("comment5", 5, 3, NOW(), NOW());

-- POSTLIKE
INSERT INTO post_like (user_id, post_id, created_at, modified_at)
VALUES (1, 1, NOW(), NOW()), -- user_id 1번 유저가 post_id 1번 게시글에 좋아요
       (2, 1, NOW(), NOW()), -- user_id 2번 유저가 post_id 1번 게시글에 좋아요
       (1, 2, NOW(), NOW()), -- user_id 1번 유저가 post_id 2번 게시글에 좋아요
       (3, 3, NOW(), NOW()); -- user_id 3번 유저가 post_id 3번 게시글에 좋아요