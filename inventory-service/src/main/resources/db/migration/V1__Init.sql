DROP TABLE IF EXISTS capital_detail;
DROP TABLE IF EXISTS capital_serial;
DROP TABLE IF EXISTS fund_account_capital_pool;
DROP TABLE IF EXISTS external_capital_account_cash_pool;
DROP TABLE IF EXISTS position_serial;
DROP TABLE IF EXISTS position;
DROP TABLE IF EXISTS resource_application_form;

--建表
--产品账户资金池
CREATE TABLE fund_account_capital_pool
(
	id BIGINT NOT NULL auto_increment,
	dtype VARCHAR(64) NOT NULL,
	fund_account_id BIGINT NOT NULL,
	investment_limit DOUBLE PRECISION,
	currency VARCHAR(4) NOT NULL,
	account_type VARCHAR(64) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE KEY (dtype,fund_account_id,currency) 
)CHARACTER SET = utf8;

--资金明细
CREATE TABLE capital_detail
(
	id BIGINT NOT NULL auto_increment,
	capital_pool_id BIGINT NOT NULL,
	cash_pool_id BIGINT NOT NULL,
	currency VARCHAR(4) NOT NULL,
	float_cash DOUBLE PRECISION,
	float_available_capital DOUBLE PRECISION,
	float_desirable_capital DOUBLE PRECISION,
	cash DOUBLE PRECISION,
	available_capital DOUBLE PRECISION,
	desirable_capital DOUBLE PRECISION,
	PRIMARY KEY (id),
	UNIQUE KEY (capital_pool_id,cash_pool_id) 
)CHARACTER SET = utf8;

--资金流水
CREATE TABLE capital_serial
(
	id BIGINT NOT NULL auto_increment,
	dtype VARCHAR(64),
	source_account_id BIGINT,
	match_account_id BIGINT,
	currency VARCHAR(4),
	occur_amount DOUBLE PRECISION,
	occur_time TIMESTAMP,
	operator_sequence VARCHAR(20),
	PRIMARY KEY (id)
)CHARACTER SET = utf8;
  
--外部资金账户资金
CREATE TABLE external_capital_account_cash_pool
(
	id BIGINT NOT NULL auto_increment,
	unassigned_capital DOUBLE PRECISION,
	currency VARCHAR(4)  NOT NULL,
	external_capital_account_id BIGINT NOT NULL,
	PRIMARY KEY (id),
	UNIQUE KEY (external_capital_account_id,currency) 
)CHARACTER SET = utf8;

--持仓
CREATE TABLE position
(
	id BIGINT NOT NULL auto_increment,
	dtype VARCHAR(32),
	portfolio_id BIGINT NOT NULL,
	security_id BIGINT NOT NULL,
	external_trade_account_id BIGINT,
	position_type VARCHAR(64),
	float_position_quantity BIGINT,
	float_can_sell_position_quantity BIGINT,
	float_equity_position_quantity BIGINT,
	position_quantity BIGINT,
	can_sell_position_quantity BIGINT,
	equity_position_quantity BIGINT,
	PRIMARY KEY (id),
	UNIQUE KEY (portfolio_id,external_trade_account_id,security_id,position_type) 
)CHARACTER SET = utf8;

--持仓流水
CREATE TABLE position_serial
(
	id BIGINT NOT NULL auto_increment,
	dtype VARCHAR(32),
	portfolio_id BIGINT NOT NULL,
	security_id BIGINT NOT NULL,
	external_trade_account_id BIGINT,
	position_type VARCHAR(64),
	occur_position BIGINT,
	occur_time TIMESTAMP,
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--资源申请
CREATE TABLE resource_application_form
(
	id BIGINT NOT NULL auto_increment, 	
	virtual_done_id BIGINT,
	instruction_id BIGINT,
	portfolio_id BIGINT,
	security_id BIGINT,
	currency VARCHAR(4),
	capital_service VARCHAR(64),
	applied_capital DOUBLE PRECISION,
	position_service VARCHAR(64),
	applied_position BIGINT,
	operator_sequence VARCHAR(20),
	applied_time TIMESTAMP,
	status VARCHAR(64),
	language VARCHAR(16),
	message VARCHAR(512),
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--外键
ALTER TABLE capital_detail ADD CONSTRAINT fk_fa_capital_pool FOREIGN KEY (capital_pool_id) REFERENCES fund_account_capital_pool (id);
ALTER TABLE capital_detail ADD CONSTRAINT fk_eca_cash_pool FOREIGN KEY (cash_pool_id) REFERENCES external_capital_account_cash_pool (id);
