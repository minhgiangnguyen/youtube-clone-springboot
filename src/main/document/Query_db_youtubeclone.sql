CREATE TABLE Users (
	user_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	channel_name VARCHAR(64) UNIQUE NOT NULL,
	password VARCHAR(256),
	email VARCHAR(64) UNIQUE NOT NULL,
	photo_url VARCHAR(512),
	role VARCHAR(20) NOT NULL,
	create_at TIMESTAMP NOT NULL,
	update_at TIMESTAMP NOT NULL
);

CREATE TABLE Videos (
	video_id VARCHAR(36) PRIMARY KEY,
	title VARCHAR(256),
	description TEXT,
	thumbnail VARCHAR(512),
	video_url VARCHAR(512),
	user_id INT,
	create_at TIMESTAMP NOT NULL,
	update_at TIMESTAMP NOT NULL,
	CONSTRAINT fk_users_videos
      FOREIGN KEY(user_id) 
        REFERENCES Users(user_id)
		
);



ALTER TABLE Videos
ALTER COLUMN video_id TYPE VARCHAR(16);

-- Get channel with number video of channel
SELECT u.channel_name, Count(u.user_id)
FROM  Users u
LEFT JOIN  Videos v ON u.user_id= v.user_id
WHERE u.user_id = 32
GROUP BY u.channel_name

CREATE INDEX idx_title_desc ON videos (MD5(title), MD5(description));

SELECT * FROM videos WHERE  title LIKE '%A%';






