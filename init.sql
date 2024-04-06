CREATE TABLE IF NOT EXISTS tb_person (
    id SERIAL PRIMARY KEY,
    nm_name VARCHAR(100) NOT NULL,
    dt_birth_date TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_address (
    id SERIAL PRIMARY KEY,
    ds_street VARCHAR(200) NOT NULL,
    ds_cep VARCHAR(8) NOT NULL,
    nr_number VARCHAR(4) NOT NULL,
    ds_city VARCHAR(100) NOT NULL,
    ds_state VARCHAR(2) NOT NULL,
    ds_address_type VARCHAR(9) NOT NULL,
    person_id BIGINT,
    CONSTRAINT fk_person FOREIGN KEY (person_id) REFERENCES tb_person(id)
);