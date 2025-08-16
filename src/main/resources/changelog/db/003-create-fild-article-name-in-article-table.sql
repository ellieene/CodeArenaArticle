--liquibase formatted sql

-- changeset author:create-title-in-article
ALTER TABLE article
    ADD COLUMN title VARCHAR(256);

-- rollback ALTER TABLE article DROP COLUMN title