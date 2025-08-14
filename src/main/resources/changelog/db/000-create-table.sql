--liquibase formatted sql

-- changeset ellieene:create-base-article
CREATE TABLE article
(
    id UUID PRIMARY KEY NOT NULL,
    user_id UUID NOT NULL,
    article TEXT NOT NULL,
    description_article VARCHAR(256) NOT NULL,
    date DATE,
    price INT NOT NULL
);

-- changeset ellieene:create-buyer
CREATE TABLE buyer
(
    buyer_id UUID PRIMARY KEY NOT NULL,
    article_id UUID NOT NULL,
    FOREIGN KEY (article_id) REFERENCES article(id) ON DELETE CASCADE
);

-- rollback DROP TABLE buyer;
-- rollback DROP TABLE article;