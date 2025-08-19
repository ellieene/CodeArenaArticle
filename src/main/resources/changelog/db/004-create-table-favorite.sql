--liquibase formatted sql

-- changeset ellieene:create-favorite
CREATE TABLE favorite
(
    user_id UUID NOT NULL,
    article_id UUID NOT NULL,
    PRIMARY KEY (user_id, article_id),
    FOREIGN KEY (article_id) REFERENCES article(id) ON DELETE CASCADE
);

-- rollback DROP TABLE favorite;