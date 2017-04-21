DROP TABLE IF EXISTS instruction_message;
DROP TABLE IF EXISTS instruction;
DROP TABLE IF EXISTS instruction_basket;

CREATE TABLE instruction
(
	id BIGINT NOT NULL auto_increment,
	dtype VARCHAR(32),
	invest_manager_id BIGINT,
	portfolio_id BIGINT,
	security_id BIGINT,
	invest_service VARCHAR(64),
	invest_type VARCHAR(64),
	currency VARCHAR(4),
	cost_price DOUBLE PRECISION,
	volume_type VARCHAR(64),
	quantity BIGINT,
	amount DOUBLE PRECISION,	
	execution_status VARCHAR(64),
	create_date DATE,
	create_time TIMESTAMP,
	instruction_basket_id BIGINT,
	trader_id BIGINT,
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

CREATE TABLE instruction_basket
(
	id BIGINT NOT NULL auto_increment,
	basket_name VARCHAR(255),
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

CREATE TABLE instruction_message
(
	id BIGINT NOT NULL auto_increment,
	instruction_id BIGINT,
	field_name VARCHAR(255),
	message_type VARCHAR(255),
	message_code VARCHAR(255),
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

ALTER TABLE instruction ADD CONSTRAINT fk_basket FOREIGN KEY (instruction_basket_id) REFERENCES instruction_basket (id);
ALTER TABLE instruction_message ADD CONSTRAINT fk_instruction FOREIGN KEY (instruction_id) REFERENCES instruction (id);