package com.example.employeemanagementapp.web;


import com.example.employeemanagementapp.entities.Employee;
import com.example.employeemanagementapp.repository.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class EmployeeController {
    private EmployeeRepository employeeRepository;

    @GetMapping("/user")
    public String home(){
        return "redirect:/user/index";
    }

    @GetMapping("/user/index")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name = "size",defaultValue = "5") int size,
                        @RequestParam(name="keyword",defaultValue = "") String kw){
        Page<Employee> pageEmployees = employeeRepository.findByNameContains(kw, PageRequest.of(page,size));
        model.addAttribute("employeeList",pageEmployees.getContent());
        model.addAttribute("pages", new int[pageEmployees.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        return "employees";
    }

    @GetMapping("/admin/formEmployee")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formEmployee(Model model){
        model.addAttribute("employee",new Employee());
        return "formEmployee";
    }

    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(Long id, String keyword,int page){
        employeeRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/editEmployee")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editEmployee(@RequestParam(name = "id")Long id,Model model){
       Employee employee = employeeRepository.findById(id).get();
       model.addAttribute("employee",employee);
       return "editEmployee" ;
    }

    @PostMapping("/admin/saveEmployee")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveEmployee(@Valid Employee employee, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "editEmployee";
        }
        employeeRepository.save(employee);
        return "redirect:/user/index?keyword="+employee.getName();
    }
}
