insert into roles (name, description, created_at, updated_at)
values ('CUSTOMER', 'Default customer role', current_timestamp, current_timestamp);

insert into roles (name, description, created_at, updated_at)
values ('SELLER', 'Default seller role', current_timestamp, current_timestamp);

insert into roles (name, description, created_at, updated_at)
values ('ADMIN', 'Default administrator role', current_timestamp, current_timestamp);

-- insert into carts default values;
--
-- insert into customers (first_name, last_name, mobile, email, username, password, created_at, updated_at, role_id,
--                       cart_id)
-- values ('testFirstName1', 'testLastName1', '1111111111', 'testEmail1@yopmail.com', 'customer1',
--         '$2a$12$qALwLvX91mgpmuHOTwn0YOTKnNjydmTXGTtKvrcXXiPJtP5k/Cc6u', current_timestamp, current_timestamp, 1, 1);
--
-- insert into addresses (type, address1, address2, city, province, zip_code, customer_id)
-- values ('HOME', 'testAddress1', 'testAddress2', 'testCity', 'testProvince', '1111', 1);
--
-- insert into addresses (type, address1, address2, city, province, zip_code, customer_id)
-- values ('OFFICE', 'testAddress1', 'testAddress2', 'testCity', 'testProvince', '1111', 1);
--
-- insert into credit_cards (card_number, card_holder_name, expiration_date, cvc, customer_id)
-- values ('111111111111111', 'testFirstName1 testLastName1', '2030-01-01', '111', 1);
--
-- insert into credit_cards (card_number, card_holder_name, expiration_date, cvc, customer_id)
-- values ('222222222222222', 'testFirstName1 testLastName1', '2030-01-01', '111', 1);
--
-- insert into sellers (first_name, last_name, mobile, email, username, password, created_at, updated_at, role_id)
-- values ('testSellerFirstName1', 'testSellerLastName1', '1111111111', 'testSellerEmail1@yopmail.com', 'seller1',
--         '$2a$12$qALwLvX91mgpmuHOTwn0YOTKnNjydmTXGTtKvrcXXiPJtP5k/Cc6u', current_timestamp, current_timestamp, 2);
--
-- insert into admins (first_name, last_name, mobile, email, username, password, created_at, updated_at, role_id)
-- values ('testAdminFirstName1', 'testAdminLastName1', '1111111111', 'testAdminEmail1@yopmail.com', 'admin',
--         '$2a$12$4MQsNhmcIkCI/sCyAk.R5eN.bR8nfxfulm5hc9tmvRagowkx5wqOC', current_timestamp, current_timestamp, 3);
--
-- insert into products (product_name, price, description, manufacturer, quantity, category, status, created_at,
--                      updated_at, seller_id)
-- values ('testProductName1', 101.00, 'This is the description for testProductName1', 'testManufacturer1', 11,
--         'ELECTRONICS', 'AVAILABLE', current_timestamp, current_timestamp, 1);
--
-- insert into products (product_name, price, description, manufacturer, quantity, category, status, created_at,
--                      updated_at, seller_id)
-- values ('testProductName2', 102.00, 'This is the description for testProductName2', 'testManufacturer2', 22,
--         'ELECTRONICS', 'AVAILABLE', current_timestamp, current_timestamp, 1);
--
-- insert into products (product_name, price, description, manufacturer, quantity, category, status, created_at,
--                      updated_at, seller_id)
-- values ('testProductName3', 103.00, 'This is the description for testProductName3', 'testManufacturer3', 33,
--         'CLOTHING', 'AVAILABLE', current_timestamp, current_timestamp, 1);
--
-- insert into cart_items (quantity, product_id, cart_id)
-- values (1, 1, 1);
--
-- insert into cart_items (quantity, product_id, cart_id)
-- values (2, 2, 1);
--
-- insert into cart_items (quantity, product_id, cart_id)
-- values (3, 3, 1);