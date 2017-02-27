DROP TABLE IF EXISTS portfolio;
DROP TABLE IF EXISTS fund_account;
DROP TABLE IF EXISTS external_trade_account;
DROP TABLE IF EXISTS external_capital_account;
DROP TABLE IF EXISTS fund;

--建表
--基金产品
CREATE TABLE fund 
(
	id BIGINT NOT NULL auto_increment, 
	code VARCHAR(255), 
	name VARCHAR(255), 
	short_name VARCHAR(255), 
	total_shares BIGINT, 
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--基金产品账户
CREATE TABLE fund_account 
(
	id BIGINT NOT NULL auto_increment, 
	name VARCHAR(255), 
	fund_id BIGINT NOT NULL, 
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--投资组合
CREATE TABLE portfolio 
(
	id BIGINT NOT NULL auto_increment, 
	create_date DATE, 
	name VARCHAR(255), 
	fund_account_id BIGINT NOT NULL, 
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--外部资金账户
CREATE TABLE external_capital_account 
(
	id BIGINT NOT NULL auto_increment, 
	account_no VARCHAR(255), 
	fund_id BIGINT NOT NULL, 
	account_type VARCHAR(255),
	external_open_organization VARCHAR(255),
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--外部交易账户
CREATE TABLE external_trade_account 
(
	id BIGINT NOT NULL auto_increment, 
	account_no VARCHAR(255), 
	account_type VARCHAR(255), 
	external_capital_account_id BIGINT NOT NULL,
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--外键
ALTER TABLE fund_account ADD CONSTRAINT fk_fund FOREIGN KEY (fund_id) REFERENCES fund (id);
ALTER TABLE portfolio ADD CONSTRAINT fk_fund_account FOREIGN KEY (fund_account_id) REFERENCES fund_account (id);
ALTER TABLE external_capital_account ADD CONSTRAINT fk_fund_for_extcapital FOREIGN KEY (fund_id) REFERENCES fund (id);
ALTER TABLE external_trade_account ADD CONSTRAINT fk_ext_capital_account foreign key (external_capital_account_id) references external_capital_account (id);