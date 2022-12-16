package com.jsframe.wadizit.service;

import com.jsframe.wadizit.entity.Board;

import com.jsframe.wadizit.entity.Member;
import com.jsframe.wadizit.repository.BoardRepository;
import com.jsframe.wadizit.repository.MemberRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@org.springframework.stereotype.Service
@Log
public class BoardService {

    @Autowired
    private BoardRepository bRepo;


    @Transactional
        public String create(Board board, Member member) {
        log.info("create()");
        String msg = null;

        log.info(""+board.getBoardNum());
        log.info(board.getContent());
        log.info(board.getTitle());

        try{
            board.setMemberNum(member);
            bRepo.save(board);
            msg = "게시물 등록 성공";
        } catch (Exception e){
            log.info(e.getMessage());
            msg = "게시물 등록 실패";
        }

        return msg;

    }

    public Board read(Long fBuyNum) {
        log.info("read()");

        Board boa2 = bRepo.findById(fBuyNum).get();
        log.info("출력 : "+ boa2.getContent());
        return boa2;

    }

    public String delete(Long boardNum, Member member) {
        log.info("deleteBoard()");
        String msg = null;

        Board bData = bRepo.findById(boardNum).get();
        //게시판 작성자 번호
        long boardWriter = (bData.getMemberNum()).getMemberNum();
        long loginPerson = member.getMemberNum();
        log.info("게시판 작성자 번호 : " + boardWriter);
        log.info("로그인한 사람 정보 : " + loginPerson);


        if(boardWriter==loginPerson){
            try{
                bRepo.deleteById( boardNum);
                msg = "삭제 성공";
            } catch (Exception e){
                log.info(e.getMessage());
                msg = "삭제 실패";
            }
        }else{
            msg = "작성자만 삭제 가능합니다.";
        }
        return msg;
    }

    public Iterable<Board> getList(Board board) {
        log.info("getList()");
        Iterable<Board> bList = bRepo.findAll();
        log.info("" +bList);
        return bList;
    }

        public String update(Board board, Long boardNum, Member member) {
        log.info("update()");
        String msg = null;

        //로그인한 사람의 memberNum
        long loginPerson = member.getMemberNum();
        //게시판 작성자의 memberNum
        Board board3 = bRepo.findById(boardNum).get();
        long boardWriter = (board3.getMemberNum()).getMemberNum();

        log.info(""+board3.getMemberNum().getMemberNum());
        if(loginPerson==boardWriter){

            board3.setTitle(board.getTitle());
            board3.setContent(board.getContent());

            try{
                bRepo.save(board3);
                msg = "수정 성공";
            }catch (Exception e){
                msg = "수정 실패";
            }

        } else {
            msg = "작성자만 수정 가능합니다.";
        }

        return msg;
    }
}
