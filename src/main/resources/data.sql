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
INSERT INTO playlist (title, thumbnail_url, user_id)
VALUES ('Chill Vibes', 'http://example.com/image1', 1);
INSERT INTO playlist (title, thumbnail_url, user_id)
VALUES ('Workout Hits', 'http://example.com/image2', 2);
INSERT INTO playlist (title, thumbnail_url, user_id)
VALUES ('Relaxing Tunes', 'http://example.com/image3', 3);
INSERT INTO playlist (title, thumbnail_url, user_id)
VALUES ('Party Mix', 'http://example.com/image4', 4);
INSERT INTO playlist (title, thumbnail_url, user_id)
VALUES ('Study Beats', 'http://example.com/image5', 5);


-- PlaylistTrack
INSERT INTO playlist_track (playlist_id, track_id, name, artist, duration, preview_url,
                            thumbnail_url)
VALUES (1, '0qsv5I5fEnRoX2Enb3mBNr', 'V (Peace) (feat. AKMU)', 'Zion.T', '02:28',
        'https://p.scdn.co/mp3-preview/0518203f58eee6b20829769f51aa11e6ece5bfa9?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b273df146c9e8246ae9f1d7711da'),
       (1, '2Zr0bYRHwWXi7eM2wuE6Aj', 'VVS (Feat. JUSTHIS) (Prod. GroovyRoom)', 'MIRANI', '05:35',
        'https://p.scdn.co/mp3-preview/6ae395b57eb158b6d1317083ee2785a4ceae1c3d?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b2734fc43bf4163c96eca2c640d8'),

       (1, '06uScjJGabF4rIRdejWOaL', 'Congratulations', 'DAY6', '02:45',
        'https://p.scdn.co/mp3-preview/2712bb4423e59ca467b8b628c42d3eef91e0b55d?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b2739989437bcd5115edde791ac8'),

       (1, '3pDGJRDN3p6kCNZcD97FYY4', 'Cosmic', 'Red Velvet', '03:45',
        'https://p.scdn.co/mp3-preview/4391208647f7f1f5362e51143eacaee853417fe6?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b273a54b93bff4827437f56b7896'),
       (1, '02wk5BttM0QL38ERjLPQJB', 'Cool With You', 'NewJeans', '02:27',
        'https://p.scdn.co/mp3-preview/598e05a7fd20278aca5477e1993b252e8fc97daf?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b2730744690248ef3ba7b776ea7b'),
       (1, '2DwUdMJ5uxv20EhAildreg', 'Cookie', 'NewJeans', '03:55',
        'https://p.scdn.co/mp3-preview/d059b8ea163242692099c452cbed3de596c2e624?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b2739d28fd01859073a3ae6ea209'),

       (2, '72cq3rZCIEYaq1TM8y5LBQ', 'Your Dog Loves You (Feat. Crush)', 'Colde', '04:33',
        'https://p.scdn.co/mp3-preview/591687ac1da4e48c3069043664cde3e6bd4ab1b9?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b273b8b1020d37e55d5db307a0a7'),
       (2, '57mWHxgHxZnhFhFiBrpYUV', 'Moon, 12:04am', 'offonoff', '03:43',
        'https://p.scdn.co/mp3-preview/a61371ab2e3a918bb044959a485afab9cf3b999e?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b273d8ab8d3ddd6b3f1dff0ffb88'),
       (2, '6hJ5fh34Zrreygumi7LMAN', '농담 반 진담 반', 'Sugarbowl', '03:23',
        'https://p.scdn.co/mp3-preview/6d625f2240bd8d82421a0c0a6a96dab3c7713be4?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b27303bb541f71bd3a8d30a37da0'),
       (2, '1l3qnaenScIWpAyJRbjLmP', 'PUZZLE', 'Sugarbowl', '03:31',
        'https://p.scdn.co/mp3-preview/efe2a61023c9cebe056b51c76a28b331d864da33?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b2731ad157781a18ed1c8e832670'),

       (3, '72p6J6txwmawWVC9Gc9vwg', '오늘밤', 'Sugarbowl', '3:30',
        'https://p.scdn.co/mp3-preview/5c23ba90529f246c9137299da5bce2dde89a834a?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b2736610b1cd859feae3598e7d22'),
       (3, '33xRp6ZX1DKraRFHR9ZDck', 'Forest', 'Choi Yu Ree', '03:48',
        'https://p.scdn.co/mp3-preview/cfecfbfac21935ed9ff395b1cc12debad3b2a50d?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b2736012b2482accbd98980ad1c5'),
       (3, '12n9WBfLII5EdQFzQiQFqv', '"Wish', 'Choi Yu Ree', '03:57',
        'https://p.scdn.co/mp3-preview/23440e95aea744bc060c0badc4e493d322f9ef4f?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b27316f90eca8734936f3f8ef7dc'),
       (3, '0qsv5I5fEnRoX2Enb3mBNr', 'V (Peace) (feat. AKMU)', 'Zion.T', '02:28',
        'https://p.scdn.co/mp3-preview/0518203f58eee6b20829769f51aa11e6ece5bfa9?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b273df146c9e8246ae9f1d7711da'),
       (3, '2Zr0bYRHwWXi7eM2wuE6Aj', 'VVS (Feat. JUSTHIS) (Prod. GroovyRoom)', 'MIRANI', '05:35',
        'https://p.scdn.co/mp3-preview/6ae395b57eb158b6d1317083ee2785a4ceae1c3d?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b2734fc43bf4163c96eca2c640d8'),

       (3, '06uScjJGabF4rIRdejWOaL', 'Congratulations', 'DAY6', '02:45',
        'https://p.scdn.co/mp3-preview/2712bb4423e59ca467b8b628c42d3eef91e0b55d?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b2739989437bcd5115edde791ac8'),

       (4, '3pDGJRDN3p6kCNZcD97FYY4', 'Cosmic', 'Red Velvet', '03:45',
        'https://p.scdn.co/mp3-preview/4391208647f7f1f5362e51143eacaee853417fe6?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b273a54b93bff4827437f56b7896'),
       (4, '02wk5BttM0QL38ERjLPQJB', 'Cool With You', 'NewJeans', '02:27',
        'https://p.scdn.co/mp3-preview/598e05a7fd20278aca5477e1993b252e8fc97daf?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b2730744690248ef3ba7b776ea7b'),
       (4, '2DwUdMJ5uxv20EhAildreg', 'Cookie', 'NewJeans', '03:55',
        'https://p.scdn.co/mp3-preview/d059b8ea163242692099c452cbed3de596c2e624?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b2739d28fd01859073a3ae6ea209'),

       (4, '72cq3rZCIEYaq1TM8y5LBQ', 'Your Dog Loves You (Feat. Crush)', 'Colde', '04:33',
        'https://p.scdn.co/mp3-preview/591687ac1da4e48c3069043664cde3e6bd4ab1b9?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b273b8b1020d37e55d5db307a0a7'),
       (5, '57mWHxgHxZnhFhFiBrpYUV', 'Moon, 12:04am', 'offonoff', '03:43',
        'https://p.scdn.co/mp3-preview/a61371ab2e3a918bb044959a485afab9cf3b999e?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b273d8ab8d3ddd6b3f1dff0ffb88'),
       (5, '6hJ5fh34Zrreygumi7LMAN', '농담 반 진담 반', 'Sugarbowl', '03:23',
        'https://p.scdn.co/mp3-preview/6d625f2240bd8d82421a0c0a6a96dab3c7713be4?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b27303bb541f71bd3a8d30a37da0'),
       (5, '1l3qnaenScIWpAyJRbjLmP', 'PUZZLE', 'Sugarbowl', '03:31',
        'https://p.scdn.co/mp3-preview/efe2a61023c9cebe056b51c76a28b331d864da33?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b2731ad157781a18ed1c8e832670'),
       (5, '72p6J6txwmawWVC9Gc9vwg', '오늘밤', 'Sugarbowl', '3:30',
        'https://p.scdn.co/mp3-preview/5c23ba90529f246c9137299da5bce2dde89a834a?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b2736610b1cd859feae3598e7d22'),
       (4, '33xRp6ZX1DKraRFHR9ZDck', 'Forest', 'Choi Yu Ree', '03:48',
        'https://p.scdn.co/mp3-preview/cfecfbfac21935ed9ff395b1cc12debad3b2a50d?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b2736012b2482accbd98980ad1c5'),
       (5, '12n9WBfLII5EdQFzQiQFqv', '"Wish', 'Choi Yu Ree', '03:57',
        'https://p.scdn.co/mp3-preview/23440e95aea744bc060c0badc4e493d322f9ef4f?cid=85cc36f6ed374905aebe0d7e5ea27763',
        'https://i.scdn.co/image/ab67616d0000b27316f90eca8734936f3f8ef7dc');


-- POST

INSERT INTO post (title, description, like_count, created_at, modified_at, playlist_id, user_id)
VALUES
('첫 번째 게시글 제목', '첫 번째 게시글 설명', 10, NOW(), NOW(), 1, 1),
('두 번째 게시글 제목', '두 번째 게시글 설명', 5, NOW(), NOW(), 1, 2),
('세 번째 게시글 제목', '세 번째 게시글 설명', 20, NOW(), NOW(), 2, 3),
('네 번째 게시글 제목', '네 번째 게시글 설명',  15, NOW(), NOW(), 2, 4),
('다섯 번째 게시글 제목', '다섯 번째 게시글 설명', 25, NOW(), NOW(), 3, 5),
('여섯 번째 게시글 제목', '여섯 번째 게시글 설명',  2, NOW(), NOW(), 2, 5),
('일곱 번째 게시글 제목', '일곱 번째 게시글 설명',  1, NOW(), NOW(), 2, 5);

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
VALUES ("첫번째 게시글의 댓글1입니다.", 1, 1, NOW(), NOW()),
       ("첫번째 게시글의 댓글2입니다.", 2, 1, NOW(), NOW()),
       ("첫번째 게시글의 댓글3입니다.", 3, 1, NOW(), NOW()),
       ("첫번째 게시글의 댓글4입니다.", 4, 1, NOW(), NOW()),
       ("첫번째 게시글의 댓글5입니다.", 5, 1, NOW(), NOW()),
       ("두번째 게시글의 댓글1입니다.", 1, 2, NOW(), NOW()),
       ("두번째 게시글의 댓글2입니다.", 2, 2, NOW(), NOW()),
       ("두번째 게시글의 댓글3입니다.", 3, 2, NOW(), NOW()),
       ("두번째 게시글의 댓글4입니다.", 4, 2, NOW(), NOW()),
       ("두번째 게시글의 댓글5입니다.", 5, 2, NOW(), NOW()),
       ("세번째 게시글의 댓글1입니다.", 1, 3, NOW(), NOW()),
       ("세번째 게시글의 댓글2입니다.", 2, 3, NOW(), NOW()),
       ("세번째 게시글의 댓글3입니다.", 3, 3, NOW(), NOW()),
       ("세번째 게시글의 댓글4입니다.", 4, 3, NOW(), NOW()),
       ("세번째 게시글의 댓글5입니다.", 5, 3, NOW(), NOW()),
       ("네번째 게시글의 댓글1입니다.", 1, 4, NOW(), NOW()),
       ("네번째 게시글의 댓글2입니다.", 2, 4, NOW(), NOW()),
       ("네번째 게시글의 댓글3입니다.", 3, 4, NOW(), NOW()),
       ("다섯번째 게시글의 댓글1입니다.", 1, 5, NOW(), NOW()),
       ("다섯번째 게시글의 댓글2입니다.", 2, 5, NOW(), NOW()),
       ("다섯번째 게시글의 댓글3입니다.", 3, 5, NOW(), NOW());

-- POSTLIKE
INSERT INTO post_like (user_id, post_id, created_at, modified_at)
VALUES (1, 1, NOW(), NOW()), -- user_id 1번 유저가 post_id 1번 게시글에 좋아요
       (2, 1, NOW(), NOW()), -- user_id 2번 유저가 post_id 1번 게시글에 좋아요
       (1, 2, NOW(), NOW()), -- user_id 1번 유저가 post_id 2번 게시글에 좋아요
       (3, 3, NOW(), NOW()); -- user_id 3번 유저가 post_id 3번 게시글에 좋아요