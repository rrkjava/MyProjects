--------------DEPARTMENT---------------------------
INSERT INTO public.mdc_mst_dept(
	dept_id, dept_name)
	VALUES (1, 'EMBASSY');
	
INSERT INTO public.mdc_mst_dept(
	dept_id, dept_name)
	VALUES (2, 'KDN');
	
INSERT INTO public.mdc_mst_dept(
	dept_id, dept_name)
	VALUES (3, 'KLN');
	
INSERT INTO public.mdc_mst_dept(
	dept_id, dept_name)
	VALUES (4, 'PDRM');
	
INSERT INTO public.mdc_mst_dept(
	dept_id, dept_name)
	VALUES (5, 'JIM');
	
INSERT INTO public.mdc_mst_dept(
	dept_id, dept_name)
	VALUES (6, 'Penjara');
	
--------------ROLES---------------------------

INSERT INTO public.mdc_mst_role(role_id, role_desc, role_name)
	VALUES (1, 'FDM / Foreign Embassies', 'ROLE_EMBASSY');
	
INSERT INTO public.mdc_mst_role(role_id, role_desc, role_name)
	VALUES (2, 'Focal Point', 'ROLE_FOCAL');
	
INSERT INTO public.mdc_mst_role(role_id, role_desc, role_name)
	VALUES (3, 'Focal Point Supervisors', 'ROLE_FOCAL_SUPERVISOR');
	
INSERT INTO public.mdc_mst_role(role_id, role_desc, role_name)
	VALUES (4, 'KDN/ AB Administrator', 'ROLE_ADMIN');
	
INSERT INTO public.mdc_mst_role(role_id, role_desc, role_name)
	VALUES (5, 'KLN', 'ROLE_KLN');
	

--------------USERS---------------------------
--->EMBASSY
--->password(MecaKDN@123)
INSERT INTO public.mdc_mst_user(
	user_id, actived_status, approval_status, created_date, 
    email_id, first_name, last_name, password_change_date, 
    password, status_comment, updated_date, username, dept_id)
	VALUES ( 1,'ACTIVE', 'ACTIVE', '2017-11-14 12:00:00', 
            'beaula@mimos.my', 'India', 'Embassy', '2017-11-14 12:00:00', 
            '$2a$10$xOwT5oEbIUgPl4DBjfZPF.LcYcOCNiHtaZaELwMfimMWh4dPhcnWi', 
			'APPROVED', '2017-11-14 12:00:00', 
            'India', 1);
			
INSERT INTO public.mdc_mst_user(
	user_id, actived_status, approval_status, created_date, 
    email_id, first_name, last_name, password_change_date, 
    password, status_comment, updated_date, username, dept_id)
	VALUES ( 2,'ACTIVE', 'ACTIVE', '2017-11-14 12:00:00', 
            'beaula@mimos.my', 'China', 'Embassy', '2017-11-14 12:00:00', 
            '$2a$10$xOwT5oEbIUgPl4DBjfZPF.LcYcOCNiHtaZaELwMfimMWh4dPhcnWi', 
			'APPROVED', '2017-11-14 12:00:00', 
            'China', 1);
			
INSERT INTO public.mdc_mst_user(
	user_id, actived_status, approval_status, created_date, 
    email_id, first_name, last_name, password_change_date, 
    password, status_comment, updated_date, username, dept_id)
	VALUES ( 3,'ACTIVE', 'ACTIVE', '2017-11-14 12:00:00', 
            'beaula@mimos.my', 'Australia', 'Embassy', '2017-11-14 12:00:00', 
            '$2a$10$xOwT5oEbIUgPl4DBjfZPF.LcYcOCNiHtaZaELwMfimMWh4dPhcnWi', 
			'APPROVED', '2017-11-14 12:00:00', 
            'Australia', 1);
			
INSERT INTO public.mdc_mst_user(
	user_id, actived_status, approval_status, created_date, 
    email_id, first_name, last_name, password_change_date, 
    password, status_comment, updated_date, username, dept_id)
	VALUES ( 16,'ACTIVE', 'ACTIVE', '2017-11-14 12:00:00', 
            'beaula@mimos.my', 'Spanyol', 'Embassy', '2017-11-14 12:00:00', 
            '$2a$10$xOwT5oEbIUgPl4DBjfZPF.LcYcOCNiHtaZaELwMfimMWh4dPhcnWi', 
			'APPROVED', '2017-11-14 12:00:00', 
            'Spanyol', 1);

INSERT INTO public.mdc_mst_user(
	user_id, actived_status, approval_status, created_date, 
    email_id, first_name, last_name, password_change_date, 
    password, status_comment, updated_date, username, dept_id)
	VALUES ( 17,'ACTIVE', 'ACTIVE', '2017-11-14 12:00:00', 
            'beaula@mimos.my', 'France', 'Embassy', '2017-11-14 12:00:00', 
            '$2a$10$xOwT5oEbIUgPl4DBjfZPF.LcYcOCNiHtaZaELwMfimMWh4dPhcnWi', 
			'APPROVED', '2017-11-14 12:00:00', 
            'France', 1);	

INSERT INTO public.mdc_mst_user(
	user_id, actived_status, approval_status, created_date, 
    email_id, first_name, last_name, password_change_date, 
    password, status_comment, updated_date, username, dept_id)
	VALUES ( 18,'ACTIVE', 'ACTIVE', '2017-11-14 12:00:00', 
            'beaula@mimos.my', 'Russia', 'Embassy', '2017-11-14 12:00:00', 
            '$2a$10$xOwT5oEbIUgPl4DBjfZPF.LcYcOCNiHtaZaELwMfimMWh4dPhcnWi', 
			'APPROVED', '2017-11-14 12:00:00', 
            'Russia', 1);				

	
		INSERT INTO public.mdc_ref_user_role(user_id, role_id)VALUES (1, 1);
		INSERT INTO public.mdc_ref_user_role(user_id, role_id)VALUES (2, 1);
		INSERT INTO public.mdc_ref_user_role(user_id, role_id)VALUES (3, 1);
		
		INSERT INTO public.mdc_ref_user_role(user_id, role_id)VALUES (16, 1);
		INSERT INTO public.mdc_ref_user_role(user_id, role_id)VALUES (17, 1);
		INSERT INTO public.mdc_ref_user_role(user_id, role_id)VALUES (18, 1);
		
		
------------------------------------------------------------------------------------------


INSERT INTO public.mdc_mst_user(
	user_id, actived_status, approval_status, created_date, 
    email_id, first_name, last_name, password_change_date, 
    password, status_comment, updated_date, username, dept_id)
	VALUES ( 4,'ACTIVE', 'ACTIVE', '2017-11-14 12:00:00', 
            'beaula@mimos.my', 'PDRM', 'Malaysia', '2017-11-14 12:00:00', 
            '$2a$10$xOwT5oEbIUgPl4DBjfZPF.LcYcOCNiHtaZaELwMfimMWh4dPhcnWi', 
			'APPROVED', '2017-11-14 12:00:00', 
            'PDRM', 4);
            
INSERT INTO public.mdc_mst_user(
	user_id, actived_status, approval_status, created_date, 
    email_id, first_name, last_name, password_change_date, 
    password, status_comment, updated_date, username, dept_id)
	VALUES ( 5,'ACTIVE', 'ACTIVE', '2017-11-14 12:00:00', 
            'beaula@mimos.my', 'Immigration', 'Malaysia', '2017-11-14 12:00:00', 
            '$2a$10$xOwT5oEbIUgPl4DBjfZPF.LcYcOCNiHtaZaELwMfimMWh4dPhcnWi', 
			'APPROVED', '2017-11-14 12:00:00', 
            'JIM', 5);
            
INSERT INTO public.mdc_mst_user(
	user_id, actived_status, approval_status, created_date, 
    email_id, first_name, last_name, password_change_date, 
    password, status_comment, updated_date, username, dept_id)
	VALUES ( 6,'ACTIVE', 'ACTIVE', '2017-11-14 12:00:00', 
            'beaula@mimos.my', 'Penjara', 'Malaysia', '2017-11-14 12:00:00', 
            '$2a$10$xOwT5oEbIUgPl4DBjfZPF.LcYcOCNiHtaZaELwMfimMWh4dPhcnWi', 
			'APPROVED', '2017-11-14 12:00:00', 
            'Penjara', 6);
           
			
INSERT INTO public.mdc_mst_user(
	user_id, actived_status, approval_status, created_date, 
    email_id, first_name, last_name, password_change_date, 
    password, status_comment, updated_date, username, dept_id)
	VALUES ( 7,'ACTIVE', 'ACTIVE', '2017-11-14 12:00:00', 
            'beaula@mimos.my', 'PDRM SUPERVISOR', 'Malaysia', '2017-11-14 12:00:00', 
            '$2a$10$xOwT5oEbIUgPl4DBjfZPF.LcYcOCNiHtaZaELwMfimMWh4dPhcnWi', 
			'APPROVED', '2017-11-14 12:00:00', 
            'PDRM_SUPERVISOR', 4);
			
		INSERT INTO public.mdc_ref_user_role(user_id, role_id)VALUES (4, 2);
		INSERT INTO public.mdc_ref_user_role(user_id, role_id)VALUES (5, 2);
		INSERT INTO public.mdc_ref_user_role(user_id, role_id)VALUES (6, 2);
        
		INSERT INTO public.mdc_ref_user_role(user_id, role_id)VALUES (7, 3);	
---------------------------------------------------------------
---> Administrator		
			
			
INSERT INTO public.mdc_mst_user(
	user_id, actived_status, approval_status, created_date, 
    email_id, first_name, last_name, password_change_date, 
    password, status_comment, updated_date, username, dept_id)
	VALUES ( 8,'ACTIVE', 'ACTIVE', '2017-11-14 12:00:00', 
            'beaula@mimos.my', 'KDN Admin B', 'Malaysia', '2017-11-14 12:00:00', 
            '$2a$10$xOwT5oEbIUgPl4DBjfZPF.LcYcOCNiHtaZaELwMfimMWh4dPhcnWi', 
			'APPROVED', '2017-11-14 12:00:00', 
            'KDN', 2);

INSERT INTO public.mdc_mst_user(
	user_id, actived_status, approval_status, created_date, 
    email_id, first_name, last_name, password_change_date, 
    password, status_comment, updated_date, username, dept_id)
	VALUES ( 9,'ACTIVE', 'ACTIVE', '2017-11-14 12:00:00', 
            'beaula@mimos.my', 'KDN Admin C', 'Malaysia', '2017-11-14 12:00:00', 
            '$2a$10$xOwT5oEbIUgPl4DBjfZPF.LcYcOCNiHtaZaELwMfimMWh4dPhcnWi', 
			'APPROVED', '2017-11-14 12:00:00', 
            'Admin', 2);	

		INSERT INTO public.mdc_ref_user_role(user_id, role_id)VALUES (8, 4);
		INSERT INTO public.mdc_ref_user_role(user_id, role_id)VALUES (9, 4);
						
---------------------------------------------------------------------

INSERT INTO public.mdc_mst_user(
	user_id, actived_status, approval_status, created_date, 
    email_id, first_name, last_name, password_change_date, 
    password, status_comment, updated_date, username, dept_id)
	VALUES ( 14,'ACTIVE', 'ACTIVE', '2017-11-14 12:00:00', 
            'beaula@mimos.my', 'KLN Admin A', 'Malaysia', '2017-11-14 12:00:00', 
            '$2a$10$xOwT5oEbIUgPl4DBjfZPF.LcYcOCNiHtaZaELwMfimMWh4dPhcnWi', 
			'APPROVED', '2017-11-14 12:00:00', 
            'kln_A',3 );	
			
			
INSERT INTO public.mdc_mst_user(
	user_id, actived_status, approval_status, created_date, 
    email_id, first_name, last_name, password_change_date, 
    password, status_comment, updated_date, username, dept_id)
	VALUES ( 15,'ACTIVE', 'ACTIVE', '2017-11-14 12:00:00', 
            'beaula@mimos.my', 'KLN Admin B', 'Malaysia', '2017-11-14 12:00:00', 
            '$2a$10$xOwT5oEbIUgPl4DBjfZPF.LcYcOCNiHtaZaELwMfimMWh4dPhcnWi', 
			'APPROVED', '2017-11-14 12:00:00', 
            'kln_B', 3);	


		INSERT INTO public.mdc_ref_user_role(user_id, role_id)VALUES (14, 5);
		INSERT INTO public.mdc_ref_user_role(user_id, role_id)VALUES (15, 5);
		
------DEVICES----------------------------------------------------------------------------------------

INSERT INTO public.mdc_mst_device(
	device_id, created_date, device_status, device_token, device_type, updated_date, user_id)
	VALUES (1, '2017-11-14 12:00:00', 'ACTIVE', 'fThXjYHAjds:APA91bHc8-kQXpXZVh9OxEKzIIbUMeadzAoV7GwNKyz1WeyEMeJVyZOg-BF84X8xZfiT0fP4pxHxVDix3M0en5Cf2xOKHX2RI4KB_huvMOLvvjjWnK8HAiBdNXrortqfj5FBp8kOcwlV', 'ANDROID', '2017-11-14 12:00:00', 11);

INSERT INTO public.mdc_mst_device(
	device_id, created_date, device_status, device_token, device_type, updated_date, user_id)
	VALUES (2, '2017-11-14 12:00:00', 'ACTIVE', '55875cc18018109f3823eaef6d2276381e520239d814cca2f831dab40ef8b0fb', 'IOS', '2017-11-14 12:00:00', 12);
	
INSERT INTO public.mdc_mst_device(
	device_id, created_date, device_status, device_token, device_type, updated_date, user_id)
	VALUES (3, '2017-11-14 12:00:00', 'ACTIVE', 'dHJ1hVWjWcE:APA91bHbDNN8tnpX3GB1ZizjFkSoDBq7yk0WCb1WKApw1tn0-SkQcVP7EDBcdWEtY-Ly52Z7430Nbe1kLpQE0HnV_Twgw92rIo6eLD86yYOlvQy0MdZoVDnXrZp1q7ImpoB9oRildVod', 'ANDROID', '2017-11-14 12:00:00', 13);	




------URGENCY master table----------------------------------------------------------------------------------------
 INSERT INTO public.mdc_mst_urgency_level(urgency_level_id,created_date,urgency_level)VALUES (1,'2017-11-14 12:00:00','URGENT');
 INSERT INTO public.mdc_mst_urgency_level(urgency_level_id,created_date, urgency_level)VALUES (2,'2017-11-14 12:00:00','NORMAL');


-----------------------------------------------------------------------------------------------------
---CLEAN DATABASE QUERY

--query delete-
select * from mdc_trx_query;

delete from mdc_trx_query where query_id in ();
delete from mdc_trx_attachement where query_id in ();
delete from mdc_trx_comment where query_id in ();
delete from mdc_trx_acknowledgment where query_id in ();

delete from mdc_trx_response where query_id in ( );
delete from mdc_trx_response_approval where response_id in(
select response_id from mdc_trx_response where query_id in ( ));
delete from mdc_trx_recepient where query_id in ();
 
 
