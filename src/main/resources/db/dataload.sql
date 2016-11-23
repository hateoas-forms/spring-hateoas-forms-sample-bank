INSERT INTO account VALUES ('mchavira','Mariana', 'Chavira', 'test'); 
INSERT INTO account VALUES ('dhume','Dania', 'Hume', 'test');
INSERT INTO account VALUES ('jleader','Janina', 'Leader', 'test'); 
INSERT INTO account VALUES ('tbogen','Tom', 'Bogen', 'test');
INSERT INTO account VALUES ('skossman','Sherwood', 'Kossman', 'test');
INSERT INTO account VALUES ('dperrett','Dalia', 'Perrett', 'test'); 
INSERT INTO account VALUES ('lmartensen','Liza', 'Martensen', 'test');

INSERT INTO cashaccount VALUES (1,'10669803404133150948', 'tbogen', 3424.32,'Checking Account');
INSERT INTO cashaccount VALUES (2,'00948343154448310446', 'lmartensen', 2479.13,'Checking Account');
INSERT INTO cashaccount VALUES (3,'51846636433522240425', 'mchavira', 2134.10,'Checking Account');
INSERT INTO cashaccount VALUES (4,'46189642115476812615', 'skossman', 6636.32,'Checking Account');
INSERT INTO cashaccount VALUES (5,'20900206748200590230', 'mchavira', 27325.55,'Individual Retirement Accounts (IRAs)');
INSERT INTO cashaccount VALUES (6,'27326583177160445902', 'dhume', 96.11,'Checking account');
INSERT INTO cashaccount VALUES (7,'44419650482966164520', 'jleader', 8173.99,'Checking account');
INSERT INTO cashaccount VALUES (8,'06045487465268010419', 'dhume', 2732.12,'Savings account');
INSERT INTO cashaccount VALUES (9,'54879921962029501674', 'jleader', 59.43,'Savings account');
INSERT INTO cashaccount VALUES (10,'33488450200529764182', 'tbogen', 3204.32,'Checking account');
INSERT INTO cashaccount VALUES (11,'91123204989505683033', 'lmartensen', 31254.12,'Individual Retirement Accounts (IRAs)');
INSERT INTO cashaccount VALUES (12,'99669645033292194432', 'tbogen', 1856.44,'Savings account');
INSERT INTO cashaccount VALUES (13,'20652292646526295334', 'lmartensen', 16.23,'Savings account');
INSERT INTO cashaccount VALUES (14,'63462679101333332937', 'mchavira', 5487.87,'Savings account');
INSERT INTO cashaccount VALUES (15,'30085221218924916057', 'dperrett', 1856.43,'Checking account');
INSERT INTO cashaccount VALUES (16,'81735885429805744235', 'dperrett', 19.01,'Checking account');
INSERT INTO cashaccount VALUES (17,'00311856242639593858', 'dhume', 12398.43,'Individual Retirement Accounts (IRAs)');
INSERT INTO cashaccount VALUES (18,'49199484199877952091', 'jleader', 70568.88,'Individual Retirement Accounts (IRAs)');
INSERT INTO cashaccount VALUES (19,'51916148594478144669', 'dperrett', 9921.05,'Savings account');
INSERT INTO cashaccount VALUES (20,'72712390165024428889', 'tbogen', 33007.4,'Individual Retirement Accounts (IRAs)');
INSERT INTO cashaccount VALUES (21,'14818601306878729745', 'skossman', 6583.24,'Savings account');

INSERT INTO creditaccount VALUES (1,'4024 0071 5848 6471', 'tbogen', 'Visa Gold',3424.32, 1);
INSERT INTO creditaccount VALUES (2,'4929 1294 1877 8806', 'lmartensen', 'Visa Gold', 2479.13, 2);
INSERT INTO creditaccount VALUES (3,'5521 0508 7181 1232', 'mchavira', 'MasterCard', 2134.10, 3);
INSERT INTO creditaccount VALUES (4,'4003 1557 1173 3700', 'jleader', 'Visa Gold', 8173.99, 7);
INSERT INTO creditaccount VALUES (8,'4485 3056 1329 7588', 'dperrett', 'Visa Electron', 1856.43, 15);
INSERT INTO creditaccount VALUES (9,'5563 2783 9123 1248', 'dhume', 'MasterCard', 96.11, 6);
INSERT INTO creditaccount VALUES (5,'4024 0071 5556 6424', 'skossman', 'AmEx Gold', 6636.32, 4);
INSERT INTO creditaccount VALUES (6,'4485 5323 6868 8618', 'mchavira', 'Visa Electron', 5487.87, 14);
INSERT INTO creditaccount VALUES (10,'4485 3133 9036 2088', 'dperrett', 'MasterCard', 19.01, 16);

INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2014-07-13 11:01:10.000000000', 'Pet Store', '00948343154448310446', -42.37, 1407.89);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2014-07-10 11:10:10.000000000', 'Grocery Store', '00948343154448310446', -23.21, 1365.52);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2014-08-03 13:16:10.000000000', 'Sports Store', '00948343154448310446', -43.05, 1322.47);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2014-08-07 14:01:10.000000000', 'Wood Supply', '00948343154448310446', -1210.31, 112.16);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2014-08-10 16:38:10.000000000', 'Pizza Delivery', '00948343154448310446', -25.03, 87.13);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2014-09-17 16:39:10.000000000', 'Wood Supply', '00948343154448310446', -19.01, 68.12);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2014-10-01 19:43:10.000000000', 'Salary', '00948343154448310446', +2454.02, 2522.14);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2014-10-05 19:51:10.000000000', 'WebHosting', '00948343154448310446', -43.01, 2479.13);

INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2012-11-12 11:01:10.000000000', 'Retirement', '91123204989505683033', +234.31, 2479.13);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2013-12-22 14:21:10.000000000', 'Retirement', '91123204989505683033', +4323.42, 6802.55);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2014-10-25 14:32:10.000000000', 'Retirement', '91123204989505683033', +3243.32, 10045.87);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2014-11-13 14:43:10.000000000', 'Retirement', '91123204989505683033', +2642.12, 12687.99);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2014-12-23 14:43:10.000000000', 'Retirement', '91123204989505683033', +10644.28, 23332.27);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2014-12-28 16:54:10.000000000', 'Retirement', '91123204989505683033', +7921.85, 31254.12);

INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2011-04-12 12:01:10.000000000', 'Retirement', '20900206748200590230', +234.31, 2479.13);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2012-05-22 12:11:10.000000000', 'Retirement', '20900206748200590230', +4323.42, 6802.55);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2013-05-25 13:11:10.000000000', 'Retirement', '20900206748200590230', +3243.32, 10045.87);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2014-06-13 13:21:10.000000000', 'Retirement', '20900206748200590230', +2642.12, 12687.99);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2014-10-23 13:21:10.000000000', 'Retirement', '20900206748200590230', +10644.28, 23332.27);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2014-11-28 14:21:10.000000000', 'Retirement', '20900206748200590230', +3992.73, 27325.55);

INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2011-11-12 14:01:10.000000000', 'Computer Store', '51846636433522240425', -800.37, 1407.89);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2012-12-10 14:02:10.000000000', 'Mobile Phone Store', '51846636433522240425', -400.21, 1007.68);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2014-10-01 14:12:10.000000000', 'Salary', '51846636433522240425', +2454.02, 3461.70);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2012-12-13 21:12:10.000000000', 'Restaurant New York', '51846636433522240425', -25.05, 3436.65);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2013-03-07 21:21:10.000000000', 'Computer Store', '51846636433522240425', -439.31, 2997.34);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2013-05-12 21:21:10.000000000', 'Restaurant Madrid', '51846636433522240425', -25.24, 2972.1);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2013-06-12 21:30:10.000000000', 'Wood Supply', '51846636433522240425', -300.34, 2671.76);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2014-12-01 21:32:10.000000000', 'Restaurant New York', '51846636433522240425', -34.02, 2637.74);
INSERT INTO transaction(date, description, number, amount, availablebalance) VALUES ('2014-12-05 21:34:10.000000000', 'Computer Store', '51846636433522240425', -503.63, 2134.10);


INSERT INTO transfer(id, fromAccount, toAccount, description, amount, fee, username, date, type, status, options, email, telephone) VALUES (1, '00948343154448310446', '20900206748200590230', 'Rent', 25, 5, 'lmartensen', '2014-12-05 21:34:10.000000000', 'NATIONAL', 'PENDING', 'DAY_TRANSFER', null, null);
INSERT INTO transfer(id, fromAccount, toAccount, description, amount, fee, username, date, type, status, options, email, telephone) VALUES (2, '00948343154448310446', '20900206748200590230', 'Rent', 25, 5, 'lmartensen', '2014-12-05 21:24:10.000000000', 'NATIONAL', 'PENDING', 'DAY_TRANSFER', 'lmartenser@gmail.com', null);
INSERT INTO transfer(id, fromAccount, toAccount, description, amount, fee, username, date, type, status, options, email, telephone) VALUES (3, '00948343154448310446', '20900206748200590230', 'Rent', 25, 5, 'lmartensen', '2014-12-05 21:34:10.000000000', 'NATIONAL', 'PENDING', 'DAY_TRANSFER', null, '+23 543 453 543');
INSERT INTO transfer(id, fromAccount, toAccount, description, amount, fee, username, date, type, status, options, email, telephone) VALUES (4, '00948343154448310446', '20900206748200590230', 'Rent', 25, 5, 'lmartensen', '2014-12-05 21:24:10.000000000', 'NATIONAL', 'PENDING', 'DAY_TRANSFER', null, null);
INSERT INTO transfer(id, fromAccount, toAccount, description, amount, fee, username, date, type, status, options, email, telephone) VALUES (5, '00948343154448310446', '20900206748200590230', 'Rent', 25, 5, 'lmartensen', '2014-12-05 21:34:10.000000000', 'NATIONAL', 'PENDING', 'DAY_TRANSFER', 'lmartenser@gmail.com', null);
INSERT INTO transfer(id, fromAccount, toAccount, description, amount, fee, username, date, type, status, options, email, telephone) VALUES (6, '00948343154448310446', '20900206748200590230', 'Rent', 25, 5, 'lmartensen', '2014-12-05 21:24:10.000000000', 'NATIONAL', 'PENDING', 'DAY_TRANSFER', null, '+23 543 453 543');
INSERT INTO transfer(id, fromAccount, toAccount, description, amount, fee, username, date, type, status, options, email, telephone) VALUES (7, '00948343154448310446', '20900206748200590230', 'Rent', 25, 5, 'lmartensen', '2014-12-05 21:34:10.000000000', 'NATIONAL', 'PENDING', 'DAY_TRANSFER', null, null);
INSERT INTO transfer(id, fromAccount, toAccount, description, amount, fee, username, date, type, status, options, email, telephone) VALUES (8, '00948343154448310446', '20900206748200590230', 'Rent', 25, 5, 'lmartensen', '2014-12-05 21:24:10.000000000', 'NATIONAL', 'PENDING', 'DAY_TRANSFER', 'lmartenser@gmail.com', null);

INSERT INTO transfer(id, fromAccount, toAccount, description, amount, fee, username, date, type, status, options, email, telephone) VALUES (9, '00948343154448310446', '20652292646526295334', 'Garage', 2, 5, 'lmartensen', '2015-12-05 21:30:10.000000000', 'INTERNATIONAL', 'REFUSED', 'NOTIFIABLE', 'lmartenser@gmail.com', '+23 543 453 543');
INSERT INTO transfer(id, fromAccount, toAccount, description, amount, fee, username, date, type, status, options, email, telephone) VALUES (10, '00948343154448310446', '20652292646526295334', 'Garage', 2, 5, 'lmartensen', '2015-12-05 21:34:10.000000000', 'INTERNATIONAL', 'PENDING', 'DAY_TRANSFER,NOTIFIABLE', 'lmartenser@gmail.com', '+23 543 453 543');

INSERT INTO alert (id, name, type, username, email, telephone) VALUES (1, 'First Alert', 'AND', 'lmartensen', 'lmartenser@gmail.com', '+23 543 453 543');

INSERT INTO alert_conditions (id, alertid, username, account, type, subtype, value) VALUES (1, 1, 'lmartensen', '00948343154448310446', 'BY_AMOUNT', 'HIGHER_THAN', '1000');
INSERT INTO alert_conditions (id, alertid, username, account, type, subtype, value) VALUES (2, 1, 'lmartensen', '91123204989505683033', 'BY_CHARGES', 'NUMBER_HIGHER_THAN', '20');