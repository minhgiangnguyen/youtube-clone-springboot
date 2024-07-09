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
ALTER COLUMN video_id TYPE VARCHAR(16)




