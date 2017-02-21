DROP TABLE IF EXISTS instruction;


CREATE TABLE instruction 
(
	id BIGINT NOT NULL auto_increment, 
	amount DOUBLE PRECISION, 
	cost_price DOUBLE PRECISION, 
	currency VARCHAR(255), 
	invest_direction VARCHAR(255), 
	invest_svc VARCHAR(255), 
	portfolio_id BIGINT, 
	quantity DOUBLE PRECISION, 
	security_id BIGINT, 
	volume_type VARCHAR(255), 
	PRIMARY KEY (id)
);