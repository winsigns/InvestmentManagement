DROP TABLE IF EXISTS external_capital_account_type_to_currency;
DROP TABLE IF EXISTS external_capital_account_type_to_external_open_organization_type;
DROP TABLE IF EXISTS external_trade_account_to_investment_scope;
DROP TABLE IF EXISTS external_trade_account;
DROP TABLE IF EXISTS external_capital_account;
DROP TABLE IF EXISTS external_open_organization;
DROP TABLE IF EXISTS external_open_organization_type;
DROP TABLE IF EXISTS investment_scope;
DROP TABLE IF EXISTS external_trade_account_type;
DROP TABLE IF EXISTS currency;
DROP TABLE IF EXISTS external_capital_account_type;

--建表
CREATE TABLE external_capital_account_type 
(
	id BIGINT NOT NULL, 
	name VARCHAR(255), 
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

CREATE TABLE currency 
(
	id BIGINT NOT NULL, 
	name VARCHAR(255), 
	chinese_name VARCHAR(255),
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

CREATE TABLE external_capital_account_type_to_currency 
(
	currency_id BIGINT NOT NULL, 
	external_capital_account_type_id BIGINT NOT NULL
)CHARACTER SET = utf8;

CREATE TABLE external_open_organization_type 
(
	id BIGINT NOT NULL, 
	name VARCHAR(255), 
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

CREATE TABLE external_open_organization 
(
	id BIGINT NOT NULL, 
	name VARCHAR(255), 
	short_name VARCHAR(255), 
	external_open_organization_type_id BIGINT,
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

CREATE TABLE external_capital_account_type_to_external_open_organization_type 
(
	external_capital_account_type_id BIGINT NOT NULL, 
	external_open_organization_type_id BIGINT NOT NULL
)CHARACTER SET = utf8;

CREATE TABLE external_capital_account 
(
	id BIGINT NOT NULL auto_increment, 
	external_capital_account VARCHAR(255), 
	fund_id BIGINT,
	external_capital_account_type_id BIGINT, 
	external_open_organization_id BIGINT,
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

CREATE TABLE external_trade_account_to_investment_scope 
(
	external_trade_account_id BIGINT NOT NULL, 
	investment_scope_id BIGINT NOT NULL
)CHARACTER SET = utf8;

CREATE TABLE investment_scope 
(
	id BIGINT NOT NULL, 
	name VARCHAR(255), 
	external_trade_account_type_id BIGINT, 
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

CREATE TABLE external_trade_account_type 
(
	id BIGINT NOT NULL, 
	name VARCHAR(255), 
	external_capital_account_type_id BIGINT, 
	support_external_capital_account_currency_id BIGINT, 
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

CREATE TABLE external_trade_account 
(
	id BIGINT NOT NULL auto_increment, 
	external_trade_account VARCHAR(255), 
	external_trade_account_type_id BIGINT, 
	external_capital_account_id BIGINT,
	PRIMARY KEY (id)
)CHARACTER SET = utf8;

--外键
ALTER TABLE external_capital_account_type_to_currency ADD CONSTRAINT fk_external_capital_account FOREIGN KEY (external_capital_account_type_id) REFERENCES external_capital_account_type (id);
ALTER TABLE external_capital_account_type_to_currency ADD CONSTRAINT fk_currency FOREIGN KEY (currency_id) REFERENCES currency (id);
ALTER TABLE external_open_organization ADD CONSTRAINT fk_external_open_organization_type FOREIGN KEY (external_open_organization_type_id) REFERENCES external_open_organization_type (id);
ALTER TABLE external_capital_account_type_to_external_open_organization_type ADD CONSTRAINT fk_open_organization_type FOREIGN KEY (external_open_organization_type_id) REFERENCES external_open_organization_type (id);
ALTER TABLE external_capital_account_type_to_external_open_organization_type ADD CONSTRAINT fk_capital_account_type FOREIGN KEY (external_capital_account_type_id) REFERENCES external_capital_account_type (id);
ALTER TABLE external_capital_account ADD CONSTRAINT fk_external_capital_account_type FOREIGN KEY (external_capital_account_type_id) REFERENCES external_capital_account_type (id);
ALTER TABLE external_capital_account ADD CONSTRAINT fk_external_open_organization FOREIGN KEY (external_open_organization_id) REFERENCES external_open_organization (id);
ALTER TABLE external_trade_account ADD CONSTRAINT fk_external_trade_account_type FOREIGN KEY (external_trade_account_type_id) REFERENCES external_trade_account_type (id);
ALTER TABLE external_trade_account ADD CONSTRAINT fk_ext_capital_account foreign key (external_capital_account_id) references external_capital_account (id);
ALTER TABLE external_trade_account_type ADD CONSTRAINT fk_belong_to_capital_account_type FOREIGN KEY (external_capital_account_type_id) REFERENCES external_capital_account_type (id);
ALTER TABLE external_trade_account_type ADD CONSTRAINT fk_support_currency FOREIGN KEY (support_external_capital_account_currency_id) REFERENCES currency (id);
ALTER TABLE investment_scope ADD CONSTRAINT fk_support_external_trade_account_type FOREIGN KEY (external_trade_account_type_id) REFERENCES external_trade_account_type (id);

--初始化数据
INSERT INTO external_capital_account_type(id,name) 
	VALUES(1,'普通资金账户');
INSERT INTO external_capital_account_type(id,name) 
	VALUES(2,'信用资金账户');
INSERT INTO external_capital_account_type(id,name) 
	VALUES(3,'衍生品保证金账户');
INSERT INTO external_capital_account_type(id,name) 
	VALUES(4,'期货保证金账户');
INSERT INTO external_capital_account_type(id,name) 
	VALUES(5,'中债登结算资金专户');
INSERT INTO external_capital_account_type(id,name) 
	VALUES(6,'上海清算所结算资金专户');
INSERT INTO external_capital_account_type(id,name) 
	VALUES(7,'黄金交易账户');
INSERT INTO external_capital_account_type(id,name) 
	VALUES(8,'外汇存款账户');
INSERT INTO external_capital_account_type(id,name) 
	VALUES(9,'香港专用信托户口');
INSERT INTO external_capital_account_type(id,name)  
	VALUES(10,'美国证券经纪账户');
INSERT INTO external_capital_account_type(id,name) 
	VALUES(11,'自定义标的账户');

INSERT INTO currency(id, name, chinese_name) 
	VALUES(1,'ALL','全币种');
INSERT INTO currency(id,name, chinese_name) 
	VALUES(2,'CNY','人民币');
INSERT INTO currency(id,name, chinese_name) 
	VALUES(3,'USD','美元');
INSERT INTO currency(id,name, chinese_name) 
	VALUES(4,'HKD','港币');
	
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(1,2);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(1,3);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(1,4);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(2,2);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(3,2);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(4,2);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(5,2);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(6,2);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(7,2);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(8,2);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(9,4);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(10,3);
INSERT INTO external_capital_account_type_to_currency(external_capital_account_type_id,currency_id)
	VALUES(11,1);	

INSERT INTO external_open_organization_type(id,name) 
	VALUES(1,'核心机构');
INSERT INTO external_open_organization_type(id,name) 
	VALUES(2,'证券公司');
INSERT INTO external_open_organization_type(id,name) 
	VALUES(3,'期货公司');
INSERT INTO external_open_organization_type(id,name) 
	VALUES(4,'基金公司');

INSERT INTO external_open_organization(id, name, short_name, external_open_organization_type_id) 
	VALUES(1,'上海证券交易所', '上交所', 1);
INSERT INTO external_open_organization(id, name, short_name, external_open_organization_type_id) 
	VALUES(2,'爱建证券有限责任公司', '爱建证券', 2);
INSERT INTO external_open_organization(id,name, short_name, external_open_organization_type_id) 
	VALUES(3,'安粮期货有限公司', '安粮期货', 3);

INSERT INTO external_capital_account_type_to_external_open_organization_type(external_capital_account_type_id,external_open_organization_type_id)
	VALUES(1,2);
INSERT INTO external_capital_account_type_to_external_open_organization_type(external_capital_account_type_id,external_open_organization_type_id)
	VALUES(2,2);
INSERT INTO external_capital_account_type_to_external_open_organization_type(external_capital_account_type_id,external_open_organization_type_id)
	VALUES(3,2);
INSERT INTO external_capital_account_type_to_external_open_organization_type(external_capital_account_type_id,external_open_organization_type_id)
	VALUES(4,3);
INSERT INTO external_capital_account_type_to_external_open_organization_type(external_capital_account_type_id,external_open_organization_type_id)
	VALUES(11,1);
INSERT INTO external_capital_account_type_to_external_open_organization_type(external_capital_account_type_id,external_open_organization_type_id)
	VALUES(11,2);
INSERT INTO external_capital_account_type_to_external_open_organization_type(external_capital_account_type_id,external_open_organization_type_id)
	VALUES(11,3);	
INSERT INTO external_capital_account_type_to_external_open_organization_type(external_capital_account_type_id,external_open_organization_type_id)
	VALUES(11,4);

INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (1, '沪市A股账户', 1, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (2, '沪市B股账户', 1, 3);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (3, '沪市信用证券账户', 2, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (4, '深市A股账户', 1, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (5, '深市B股账户', 1, 4);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (6, '深市信用证券账户', 2, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (7, '全国中小企业股份转让系统账户', 1, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (8, '上期所交易编码', 4, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (9, '大商所交易编码', 4, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (10, '郑商所交易编码', 4, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (11, '金融期货账户', 4, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (12, '香港证券账户', 9, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (13, '美国证券经纪账户', 10, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (14, '中债登丙类债券账户', 5, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (15, '上海清算所债券账户', 6, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (16, '黄金交易账户', 7, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (17, '外汇存款账户', 8, 2);
INSERT INTO external_trade_account_type (id, name, external_capital_account_type_id, support_external_capital_account_currency_id)
VALUES (18, '自定义标的账户', 11, 1);
	
INSERT INTO investment_scope (id, name, external_trade_account_type_id)
VALUES (1, '上证A股竞价交易服务', 1);