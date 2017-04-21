DROP TABLE IF EXISTS capital_detail;
DROP TABLE IF EXISTS capital_serial;
DROP TABLE IF EXISTS fund_account_capital_pool;
DROP TABLE IF EXISTS external_capital_account_cash_pool;
DROP TABLE IF EXISTS position;

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
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--资金明细
CREATE TABLE capital_detail
(
	id BIGINT NOT NULL auto_increment,
	capital_pool_id BIGINT NOT NULL,
	cash_pool_id BIGINT NOT NULL,
	currency VARCHAR(4) NOT NULL,
	cash DOUBLE PRECISION,
	available_capital DOUBLE PRECISION,
	desirable_capital DOUBLE PRECISION,
	PRIMARY KEY (id)
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
	currency VARCHAR(3)  NOT NULL,
	external_capital_account_id BIGINT NOT NULL,
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--持仓
CREATE TABLE position
(
	id BIGINT NOT NULL auto_increment,
	dtype VARCHAR(32),
	portfolio_id BIGINT NOT NULL,
	external_trade_account_id BIGINT NOT NULL,
	security_id BIGINT NOT NULL,
	position_type VARCHAR(255) NOT NULL,
	positionQuantity BIGINT,
	canSellPositionQuantity BIGINT,
	equityPositionQuantity BIGINT,
	PRIMARY KEY (id)
)CHARACTER SET = utf8;
--外键
ALTER TABLE capital_detail ADD CONSTRAINT fk_fa_capital_pool FOREIGN KEY (capital_pool_id) REFERENCES fund_account_capital_pool (id);
ALTER TABLE capital_detail ADD CONSTRAINT fk_eca_cash_pool FOREIGN KEY (cash_pool_id) REFERENCES external_capital_account_cash_pool (id);
