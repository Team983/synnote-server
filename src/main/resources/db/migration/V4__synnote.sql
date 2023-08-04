ALTER TABLE note
    ADD COLUMN recording_id BIGINT;

ALTER TABLE note
    ADD CONSTRAINT FK_note_recording_id_recording_id FOREIGN KEY (recording_id)
        REFERENCES recording (id) ON DELETE RESTRICT ON UPDATE RESTRICT;
