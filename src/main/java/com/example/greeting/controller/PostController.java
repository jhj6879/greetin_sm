package com.example.greeting.controller;

import com.example.greeting.com.Search;
import com.example.greeting.dao.PostDao;
import com.example.greeting.dto.FileDto;
import com.example.greeting.dto.PostDto;
import com.example.greeting.dto.ReplyDto;
import com.example.greeting.service.FileService;
import com.example.greeting.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;

    // 관리자 공지사항 작성
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String Wriet(){
        return "write";
    }

    // 공지사항 업로드
    @PostMapping("/write")
    public String writePost(Model model, PostDto dto,
                            @RequestParam(value="file", required=false) MultipartFile[] files) { //파일 가져오는 클래스 적용해서 첨부파일 기능 적용 // 여러 파일 적용하기 위해 배열로 변경

        // 파일 업로드 경로
        final String path = "D:\\koreait\\JAVA-work\\greeting\\src\\main\\resources\\";

        //게시글 저장
        dto = postService.putPost(dto);
        model.addAttribute("post", dto);
        //게시판 정보
        PostDto postDto = postService.getBoard(dto.getPost_no());
        model.addAttribute("post", postDto);

        // 파일 업로드 처리 (!file.isEmpty() 에러 발생해서 변겅)
        if (files != null) {
            for (MultipartFile file : files) { // 파일 개수만큼 돌려서 file로 넣어줌
                try {
                    FileDto fileDto = new FileDto();
                    String org_file_name = file.getOriginalFilename();
                    String file_name = UUID.randomUUID().toString().substring(0, 8) + "_" + org_file_name;
                    fileDto.setFile_name(file_name);
                    fileDto.setFile_path(path);
                    fileDto.setOrg_file_name(org_file_name);
                    fileDto.setUserid(dto.getUser_id());
                    fileDto.setPost_no(dto.getPost_no());

                    // 실제 파일 저장위치에 파일 업로드
                    file.transferTo(new File(path + file_name)); // 오브젝트로 넘기기
                    fileService.fileUpload(fileDto);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            List<FileDto> file_list = fileService.fileDownloadList(dto.getPost_no());
            model.addAttribute("file_list", file_list);
        }
        return "view";
    }

    // 파일 링크 선택시 다운로드
    @GetMapping("/download/{file_id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("file_id") int file_id){
        try {
            FileDto dto = fileService.fileDownload(file_id);
            Path filePath = Paths.get(dto.getFile_path()).resolve(dto.getFile_name()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                String encodeFileName = URLEncoder.encode(dto.getOrg_file_name(), StandardCharsets.UTF_8.toString());
                String contentDisposition = "attachment; filename=\"" + encodeFileName + "\"";
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                        .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch(IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    // 공지사항 페이지, 검색 기능 추가
    @GetMapping("/notice")
    public String detailPage(Model model,
                             @RequestParam(value="keyword", defaultValue="") String keyword) {
        Search search = new Search(5, 5);

        // 검색어를 로그로 출력하여 확인
        System.out.println("검색어: " + keyword);

        // 검색어가 있다면 해당 검색어로 검색 결과를 가져옴
        if (!keyword.isEmpty()) {
            search.setKeyword(keyword);
            List<PostDto> list = postService.getPostListByKeyword(search);
            model.addAttribute("list", list);
        } else {
            // 검색어가 없을 경우 전체 게시물 표시
            List<PostDto> list = postService.getInoutNoticePage();
            model.addAttribute("list", list);
        }

        // 페이징에 대한 것도 같이 가야함
        model.addAttribute("page", search);
        return "notice";
    }

    // 게시글과 댓글 보기
    @GetMapping("/view/{post_no}")
    public String viewPost(@PathVariable("post_no") int post_no, Model model) {
        PostDto dto = postService.getPost(post_no);
        //조회수 늘리기
        postService.cntHitCnt(post_no);
        model.addAttribute("post", dto);

        //게시판 정보
        PostDto post_list = postService.getPost(dto.getPost_no());
        model.addAttribute("post_list", post_list);

        //댓글
        List<ReplyDto> reply_list = postService.getReply(dto.getPost_no());
        model.addAttribute("reply_list", reply_list);

        // 파일 취득
        List<FileDto> file_list = fileService.fileDownloadList(post_no);
        model.addAttribute("file_list",file_list);

        return "view";
    }

    //게시글 삭제
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{post_no}")
    public String delPost(@PathVariable("post_no") int post_no) {
        int post = postService.getPostNo(post_no);
        postService.delPost(post_no);
        return "redirect:/notice";
    }

    //게시글 수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit/{post_no}")
    public String editForm(@PathVariable("post_no") int post_no, Model model) {
        //게시글 내용 가져오기
        PostDto dto = postService.getPost(post_no);
        model.addAttribute("post", dto);

        //게시판 정보
        PostDto post_list = postService.getPost(dto.getPost_no());
        model.addAttribute("post_list", post_list);

        return "edit";
    }

    @PostMapping("/edit")
    public String editPost(Model model, PostDto dto) {
        dto = postService.editPost(dto);
        model.addAttribute("post", dto);

        //게시판 정보
        PostDto post_list = postService.getPost(dto.getPost_no());
        model.addAttribute("post_list", post_list);

        //댓글 전체 불러오기
        List<ReplyDto> reply_list = postService.getReply(dto.getPost_no());
        model.addAttribute("reply_list", reply_list);

        return "redirect:/view/" + dto.getPost_no();
    }

    //댓글 작성
    @PostMapping("/reply")
    public String setreply(ReplyDto reply, Model model) {
        //댓글 등록
        postService.putReply(reply);
        List<ReplyDto> reply_list = postService.getReply(reply.getPost_no()); //해당 게시물에 있는 모든 댓글 get
        model.addAttribute("reply_list", reply_list);
        //게시글 취득
        PostDto dto = postService.getPost(reply.getPost_no());
        model.addAttribute("post", dto);

        //게시판 정보
        PostDto post = postService.getPost(dto.getPost_no());
        model.addAttribute("post", post);

        return "redirect:/view/" + dto.getPost_no();
    }

}
