package com.example.greeting.controller;

import com.example.greeting.com.Search;
import com.example.greeting.dao.PostDao;
import com.example.greeting.dto.FileDto;
import com.example.greeting.dto.PostDto;
import com.example.greeting.service.FileService;
import com.example.greeting.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;

    @GetMapping("/write")
    public String Wriet(){
        return "write";
    }

    @PostMapping("/write")
    public String writePost(Model model, PostDto dto,
                            @RequestParam(value="file", required=false) MultipartFile[] files) { //파일 가져오는 클래스 적용해서 첨부파일 기능 적용 // 여러 파일 적용하기 위해 배열로 변경

        // 파일 업로드 경로
        final String path = "D:\\koreait\\JAVA-work\\greeting\\src\\main\\resources";

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
        return "notice";
    }

    @GetMapping("/notice/{post_no}")
    public String notice(Model model, @PathVariable("post_no") int post_no,
                         @RequestParam(value="keyword", defaultValue="") String keyword) {
        Search search = new Search(5, 5);
        search.setKeyword(keyword);
        List<PostDto> list = postService.getPostListByBoard(post_no, search);
        model.addAttribute("list", list);
        model.addAttribute("page", search);
        PostDto post = postService.getBoard(post_no);
        model.addAttribute("post", post);
        return "notice";
    }

    @GetMapping("/notice/{post_no}/{page}")
    public String boardPageWithPagination(Model model, @PathVariable("post_no") int post_no,
                                          @PathVariable("page") int page,
                                          @RequestParam(value="keyword", defaultValue="") String keyword,
                                          @RequestParam(value = "recordSize", defaultValue = "10") int recordSize,
                                          @RequestParam(value = "searchType", required = false) String searchType) {
        Search search = new Search(page, recordSize);
        search.setKeyword(keyword);
        List<PostDto> list = postService.getPostListByBoard(post_no, search);
        int totPostCount = postService.getTotPostCount(post_no, keyword, searchType, page);
        model.addAttribute("list", list);
        model.addAttribute("page", search);
        PostDto post = postService.getBoard(post_no);
        model.addAttribute("post", post);
        return "notice";
    }
}

