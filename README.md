# best-price


#PostgreSQL
Install PostgreSQL server from https://www.postgresql.org/download/

# hostname: localhost
# port: 1521
# sid: xe
# service name: xe
# username: system
# password: oracle

# Set up DB
DROP TABLE IF EXISTS ITEM_CATEGORY;
DROP TABLE IF EXISTS ITEM;
DROP TABLE IF EXISTS ITEM_PRICE_HISTORY;

CREATE TABLE ITEM_CATEGORY (
id                      UUID DEFAULT gen_random_uuid() PRIMARY KEY,
name                    VARCHAR(999) NOT NULL,
parent_category_id      UUID,
url                     VARCHAR(999) NOT NULL,
CONSTRAINT fk_category_parent
    FOREIGN KEY(parent_category_id) REFERENCES ITEM_CATEGORY(id)
);

CREATE TABLE ITEM (
id              UUID DEFAULT gen_random_uuid() PRIMARY KEY,
name            VARCHAR(999) NOT NULL,
url             VARCHAR(999) NOT NULL,
category_id     UUID NOT NULL,
created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT fk_item_category
    FOREIGN KEY(category_id) REFERENCES ITEM_CATEGORY(id)
);

CREATE TABLE ITEM_PRICE_HISTORY (
id              UUID DEFAULT gen_random_uuid() PRIMARY KEY,
price           REAL NOT NULL,
created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
item_id         UUID NOT NULL,
CONSTRAINT fk_item_price
    FOREIGN KEY(item_id) REFERENCES ITEM(id)
);