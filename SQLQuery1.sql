







alter procedure spSearchEmployee @query varchar (200), @pageIndex varchar (100) , @pageSize varchar (100)
as begin
set transaction isolation level read uncommitted

declare @sql nvarchar(max) 
set @sql = 'select  e.id, r.role_id, r.name as role_name, e.age ,e.name ,e.emp_class ,e.subjects ,e.attendance , convert(date, e.client_date_created)
		from employee e
		join roles r on r.role_id = e.role_id  --wll return only the records having some role (either admin or user)
		'
		+ @query + 

		--where e.name like ''%@name%'' 
		--and isnull(e.deleted, 0) <> 1	   -- this will not return the deleted result
		--and (r.role_id = 1 or r.name = ''Admin'')  --assuming admin have id = 1 (Optional to check for role name)
		'order by e.client_date_created desc  -- to sort the records by getting new records on the top

		-- for pagination
		OFFSET (cast(@pageIndex as int) - 1) * cast(@pageSize as int) ROWS
		FETCH NEXT cast(@pageSize as int) ROWS ONLY;' 

		print @sql
		EXEC sp_executesql @sql, 
        N'@pageIndex INT, @pageSize INT', 
        @pageIndex, 
        @pageSize
end





exec spSearchEmployee 'where isnull(e.deleted, 0) <> 1 and e.age = ''30''', '1', '10'

exec spSearchEmployee '', '1', '10'


select * from employee

INSERT INTO roles (name, description)
VALUES ('admin', 'Administrator role with full privileges');

INSERT INTO roles (name, description)
VALUES ('employee', 'Employee role for operations');



