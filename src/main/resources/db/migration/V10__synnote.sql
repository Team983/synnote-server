CREATE TABLE summary
(
    `id`            BIGINT       NOT NULL    AUTO_INCREMENT,
    `note_id`       BIGINT       NOT NULL,
    `text`          TEXT         NOT NULL,
    `created_date`  TIMESTAMP    NOT NULL,
    `updated_date`  TIMESTAMP    NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE summary
    ADD CONSTRAINT FK_summary_note_id_note_id FOREIGN KEY (note_id)
        REFERENCES note (id) ON DELETE RESTRICT ON UPDATE RESTRICT;
