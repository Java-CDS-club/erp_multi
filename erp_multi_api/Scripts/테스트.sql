select * from employee;

select * from employee where dept=2;

select e.emp_no, e.emp_name, t.title_name 
  from employee e left join title t on e.title = t.title_no
 where dept = 1 or e.manager is null
order by t.title_no;
;

select e.emp_no, e.emp_name, e.title, title_name, e.manager as manager_no, m.emp_name as manager_name , e.salary, dept_no, dept_name, e.hire_date   from employee e left join title t on e.title = t.title_no left join employee m on e.manager = m.emp_no  left join department d on e.dept = d.dept_no
