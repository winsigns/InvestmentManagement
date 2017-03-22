DROP TABLE IF EXISTS fund_account_capital_serial;
DROP TABLE IF EXISTS eca_cash_serial;
DROP TABLE IF EXISTS fund_account_capital_detail;
DROP TABLE IF EXISTS fund_account_capital;
DROP TABLE IF EXISTS external_capital_account_cash_pool;
DROP TABLE IF EXISTS position;

--建表
--产品账户资金
CREATE TABLE fund_account_capital
(
	id BIGINT NOT NULL auto_increment,
	fund_account_id BIGINT NOT NULL,
	investment_limit DOUBLE PRECISION,
	currency VARCHAR(3) NOT NULL,
	external_capital_account_type VARCHAR(255) NOT NULL,
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--产品账户资金明细
CREATE TABLE fund_account_capital_detail
(
	id BIGINT NOT NULL auto_increment,
	external_capital_account_id BIGINT NOT NULL,
	fund_account_capital_id BIGINT NOT NULL,
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--产品账户资金流水
CREATE TABLE fund_account_capital_serial
(
	id BIGINT NOT NULL auto_increment,
	fund_account_capital_detail_id BIGINT NOT NULL,
	assigned_cash DOUBLE PRECISION,
	assigned_date DATE NOT NULL,
	eca_cash_pool_id BIGINT,
	linked_serial_id BIGINT,
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

--外部账户资金流水
CREATE TABLE eca_cash_serial
(
	id BIGINT NOT NULL auto_increment,
	eca_cash_pool_id BIGINT NOT NULL,
	assigned_cash DOUBLE PRECISION,
	assigned_date DATE NOT NULL,
	linked_eca_serial_id BIGINT,
	linked_fa_serial_id BIGINT,
	operator_sequence VARCHAR(20),
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--持仓
CREATE TABLE position
(
	id BIGINT NOT NULL auto_increment,
	portfolio_id BIGINT NOT NULL,
	external_trade_account_id BIGINT NOT NULL,
	security_id BIGINT NOT NULL,
	position_type VARCHAR(255) NOT NULL,
	PRIMARY KEY (id)
)CHARACTER SET = utf8;
--外键
ALTER TABLE fund_account_capital_detail ADD CONSTRAINT fk_fund_account_capital FOREIGN KEY (fund_account_capital_id) REFERENCES fund_account_capital (id);
ALTER TABLE fund_account_capital_serial ADD CONSTRAINT fk_fund_account_capital_detail FOREIGN KEY (fund_account_capital_detail_id) REFERENCES fund_account_capital_detail (id);
ALTER TABLE eca_cash_serial ADD CONSTRAINT fk_eca_cash_serial FOREIGN KEY (eca_cash_pool_id) REFERENCES external_capital_account_cash_pool (id);
