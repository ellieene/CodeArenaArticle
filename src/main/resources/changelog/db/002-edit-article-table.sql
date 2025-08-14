--liquibase formatted sql

-- changeset author:change-user_id-to-username
ALTER TABLE article
    DROP COLUMN user_id,
    ADD COLUMN username VARCHAR(50) NOT NULL;

-- changeset author:add-username-constraint
ALTER TABLE article
    ADD CONSTRAINT username_length_check CHECK (LENGTH(username) BETWEEN 5 AND 50);