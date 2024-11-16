

SET @sql = N'select * into ##tempWorkingTime
                    from OPENQUERY(
                            [SRV_Timetac],''';
        SET @sql = @sql + @query + ')';




		SET @sql = N'select * into ##tempWorkingTime
                    from OPENQUERY(
                            [SRV_Timetac],''';
        SET @sql = @sql + @query + ')';





sp_help employee

alter procedure spSearchEmployee @query varchar(200), @pageIndex varchar(100), @pageSize varchar(100) 
as 
begin
    set transaction isolation level read uncommitted;

    declare @sql nvarchar(max); 
    set @sql = N'select e.id, r.role_id, r.name as role_name, e.age, e.name, e.emp_class, e.subjects, e.attendance, isnull(e.email,'') email,
                    convert(date, e.client_date_created) as client_date_created
                from employee e
                join roles r on r.role_id = e.role_id  -- will return only records with a role (either admin or user)
				where isnull(e.deleted, 0) <> 1
                ' + @query + 
                N' order by e.client_date_created desc  -- to sort the records by getting new records on top

                -- for pagination
                OFFSET ((cast(@pageIndex as int) - 1) * cast(@pageSize as int)) ROWS
                FETCH NEXT cast(@pageSize as int) ROWS ONLY;'; 

    print @sql;

    EXEC sp_executesql 
        @sql, 
        N'@pageIndex INT, @pageSize INT', 
        @pageIndex, 
        @pageSize;
end




update employee set deleted= 1 where id =9

select * from employee where isnull(deleted, 0) <> 1 order by 1 desc



INSERT INTO roles (name, description)
VALUES ('admin', 'Administrator role with full privileges');

INSERT INTO roles (name, description)
VALUES ('employee', 'Employee role for operations');



