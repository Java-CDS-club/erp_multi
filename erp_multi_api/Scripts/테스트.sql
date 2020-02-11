select * from employee;

select * from employee where dept=2;

select e.emp_no, e.emp_name, t.title_name 
  from employee e left join title t on e.title = t.title_no
order by t.title_no;
;