


alter procedure spSearchEmployeeById @emp_id varchar (100), @pageIndex int, @pageSize int
as begin
set transaction isolation level read uncommitted
select  e.id, r.role_id, r.name as role_name, e.age ,e.name ,e.emp_class ,e.subjects ,e.attendance , e.client_date_created
		from employee e
		join roles r on r.role_id = e.role_id  --wll return only the records having some role (either admin or user)
		where id = @emp_id 
		and isnull(e.deleted, 0) <> 1	   -- this will not return the deleted result
		and (r.role_id = 1 or r.name = 'Admin')  --assuming admin have id = 1 (Optional to check for role name)
		order by e.client_date_created desc   -- to sort the records by getting new records on the top

		-- for pagination
		OFFSET (@pageIndex - 1) * @pageSize ROWS
		FETCH NEXT @pageSize ROWS ONLY; 
end




alter procedure spSearchEmployeeByName @name varchar (100), @pageIndex int, @pageSize int
as begin
set transaction isolation level read uncommitted
select  e.id, r.role_id, r.name as role_name, e.age ,e.name ,e.emp_class ,e.subjects ,e.attendance , e.client_date_created
		from employee e
		join roles r on r.role_id = e.role_id  --wll return only the records having some role (either admin or user)
		where e.name like '%@name%' 
		and isnull(e.deleted, 0) <> 1	   -- this will not return the deleted result
		and (r.role_id = 1 or r.name = 'Admin')  --assuming admin have id = 1 (Optional to check for role name)
		order by e.client_date_created desc  -- to sort the records by getting new records on the top

		-- for pagination
		OFFSET (@pageIndex - 1) * @pageSize ROWS
		FETCH NEXT @pageSize ROWS ONLY; 
end



create procedure spSearchEmployeeByAge @age varchar (100), @pageIndex int, @pageSize int
as begin
set transaction isolation level read uncommitted
select  e.id, r.role_id, r.name as role_name, e.age ,e.name ,e.emp_class ,e.subjects ,e.attendance , e.client_date_created
		from employee e
		join roles r on r.role_id = e.role_id  --wll return only the records having some role (either admin or user)
		where e.age = @age
		and isnull(e.deleted, 0) <> 1	   -- this will not return the deleted result
		and (r.role_id = 1 or r.name = 'Admin')  --assuming admin have id = 1 (Optional to check for role name)
		order by e.client_date_created desc  -- to sort the records by getting new records on the top

		-- for pagination
		OFFSET (@pageIndex - 1) * @pageSize ROWS
		FETCH NEXT @pageSize ROWS ONLY; 
end