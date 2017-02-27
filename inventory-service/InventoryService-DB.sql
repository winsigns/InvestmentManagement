DROP TABLE IF EXISTS fund_account_capital_detail;
DROP TABLE IF EXISTS fund_account_capital;
DROP TABLE IF EXISTS external_capital_account_cash_pool;

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
	cash DOUBLE PRECISION, 
	external_capital_account_id BIGINT NOT NULL, 
	fund_account_capital_id BIGINT NOT NULL, 
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--外部资金账户资金
CREATE TABLE external_capital_account_cash_pool
(
	id BIGINT NOT NULL auto_increment, 
	unassigned_capital DOUBLE PRECISION, 
	currency VARCHAR(3)  NOT NULL,
	external_capital_account_id BIGINT NOT NULL, 
	fund_id BIGINT NOT NULL, 
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--外键
ALTER TABLE fund_account_capital_detail ADD CONSTRAINT fk_fund_account_capital FOREIGN KEY (fund_account_capital_id) REFERENCES fund_account_capital (id);
