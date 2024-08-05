package com.example.greeting.com;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Search {
    /***** 페이징 영역 *****/
    private int page;                 // 현재 페이지
    private int recordSize;           // 페이지당 보여줄 레코드 수
    private int pageSize;             // 한번에 표시할 페이지 수
    private int totDataCnt;           // 전체 데이터 수
    private int totPageCnt;           // 전체 페이지 수
    private boolean existPrevPage;    // 이전 페이지 존재 여부
    private boolean existNextPage;    // 다음 페이지 존재 여부
    private int startPage;            // 페이지 리스트 시작 번호
    private int endPage;              // 페이지 리스트 끝 번호

    /***** 검색 영역 *****/
    private String keyword;           // 검색 키워드
    private String searchType;        // 검색 방법

    // 생성자_1 (비어있는 생성자)
    public Search() {

    }

    // 생성자_2
    public Search(int recordSize, int pageSize) {
        this.page = 1;
        this.recordSize = recordSize;
        this.pageSize = pageSize;
    }

    // 생성자_3 (레코드에 보여지는 페이지 정하기 (예시 : 0 부터 5개 가져오기, 5 부터 5개 가져오기))
    public int getOffset() {
        return (page - 1) * recordSize;
    }

    // 생성자_4 (밑에 보이는 페이지 수)
    public void calcPage(int totDataCnt) {
        this.totDataCnt = totDataCnt;
        totPageCnt = ((totDataCnt -1) / recordSize) + 1;  // 전체 페이지 수 ex)레코드가 100일 경우 5개씩 페이지 보여주면 20페이지가 나와야 하고 거기에 1을 더한다
        startPage = ((page - 1) / pageSize) * pageSize + 1;   // ex) 1 2 3 4 5 페이지 나올때 1 나오게 하는거
        endPage = startPage + pageSize -1;   // ex) 1 2 3 4 5 페이지 나올때 5 나오게 하는거
        if(endPage > totPageCnt) {      // 마지막 페이지가 토탈 페이지 보다 적으면 끝
            endPage = totPageCnt;
        }
        existPrevPage = startPage != 1;   // true 아니면 false
        existNextPage = endPage != totPageCnt;   // true 아니면 false
    }



}
