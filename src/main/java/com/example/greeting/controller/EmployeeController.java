package com.example.greeting.controller;

import com.example.greeting.com.Search;
import com.example.greeting.dto.EmployeeDto;
import com.example.greeting.dto.PostDto;
import com.example.greeting.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // 사원관리(관리자용)
    @GetMapping("/employee")
    public String Employee(Model model, @RequestParam(value="keyword", defaultValue="") String keyword) {
        Search search = new Search(5, 5);

        // 검색어를 로그로 출력하여 확인
        System.out.println("검색어: " + keyword);

        // 검색어를 로그로 출력하여 확인
        System.out.println("검색어: " + keyword);

        // 검색어가 있다면 해당 검색어로 검색 결과를 가져옴
        if (!keyword.isEmpty()) {
            search.setKeyword(keyword);
            List<EmployeeDto> list = employeeService.getPostListByKeyword(search);
            model.addAttribute("list", list);
            if (!list.isEmpty()) {
                EmployeeDto dto = employeeService.selectEmployee(list.get(0).getEmployee_id());
                model.addAttribute("dto", dto);
            }
        } else {
            // 검색어가 없을 경우 전체 게시물 표시
            List<EmployeeDto> list = employeeService.getInoutNoticePage();
            model.addAttribute("list", list);
            if (!list.isEmpty()) {
                EmployeeDto dto = employeeService.selectEmployee(list.get(0).getEmployee_id());
                model.addAttribute("dto", dto);
            }
        }

//        List<EmployeeDto> list = employeeService.selectEmployeeList();
//        model.addAttribute("list", list);
//        // 여기서는 기본적으로 첫 번째 사원을 선택해서 표시할 수 있습니다.
//        if (!list.isEmpty()) {
//            EmployeeDto dto = employeeService.selectEmployee(list.get(0).getEmployee_id());
//            model.addAttribute("dto", dto);
//        }

        return "employee";
    }

    // 사원 상세 정보를 가져오는 새로운 메서드 추가
    @GetMapping("/employee/{employee_id}")
    public String getEmployeeDetails(@PathVariable int employee_id, Model model) {
        EmployeeDto dto = employeeService.selectEmployee(employee_id);
        model.addAttribute("dto", dto);
        return "employee :: employeeDetailsFragment";
    }

    @PostMapping("/employee")
    public String updateEmployee(@ModelAttribute EmployeeDto dto) {
        employeeService.updateEmployee(dto);
        return "redirect:/employee";
    }

    // 부서관리 (삭제 예정)
    @GetMapping("/department")
    public String Department(){
        return "department";
    }

    // 직책관리 (삭제 예정)
    @GetMapping("/position")
    public String Position(){
        return "position";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "login";  // src/main/resources/templates/login.html 반환
    }

    /*//사용자 로그인 후 회원정보 요청
    @PreAuthorize("isAuthenticated()")//로그인되지 않은 사용자가 회원정보를 선택했을 때 로그인할 수 있도록 하는 어노테이션 - 로그인이 필요한 기능들에
    @GetMapping("/member")
    public ModelAndView getMemberInfo(Principal principal) { //세션에 기록된 userid를 가져옴
        ModelAndView mav = new ModelAndView("member"); //모델과 뷰를 한꺼번에 제어하는 클래스 1)뷰를 넘겨줌
        MemberDto dto = new MemberDto();
        dto = memberService.getMemberInfo(principal.getName());
        mav.addObject("member", dto);

        //게시판 메뉴
//		List<BoardDto> menu = service.getBoardMenu();
//		mav.addObject("menu", menu);
        return mav;
    }*/

    // 회원가입 페이지
    @GetMapping("/join")
    public String JoinPage(){
        return "join";
    }

    // 회원가입 정보 DB로 넘기기
    @PostMapping("/join") //DB에 저장
    public String joinAply(EmployeeDto dto) {
        employeeService.create(dto);
        return "login";
    }

    @GetMapping("/checkid")
    @ResponseBody
    public String checkId(@RequestParam(value="data") String user_id) {
        return String.valueOf(employeeService.memberId(user_id));
    }

}