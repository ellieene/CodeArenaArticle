--liquibase formatted sql

-- changeset ellieene:edit-buyer
ALTER TABLE buyer DROP CONSTRAINT buyer_pkey;

ALTER TABLE buyer ADD PRIMARY KEY (buyer_id, article_id);

-- rollback ALTER TABLE buyer DROP CONSTRAINT (buyer_id, article_id);
-- rollback ALTER TABLE buyer ADD PRIMARY KEY (buyer_id);