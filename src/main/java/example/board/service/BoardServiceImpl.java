package example.board.service;

import example.board.common.FileUtils;
import example.board.dto.BoardDto;
import example.board.dto.BoardFileDto;
import example.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;
    private final FileUtils fileUtils;


    @Override
    public List<BoardDto> selectBoardList() throws Exception {
        return boardMapper.selectBoardList();
    }

    @Override
    public void insertBoard(BoardDto boardDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        boardMapper.insertBoard(boardDto);

        List<BoardFileDto> list = fileUtils.parseFileInfo(boardDto.getBoardIdx(), multipartHttpServletRequest);
        if (CollectionUtils.isEmpty(list) == false){
            boardMapper.inertBoardFileList(list);
        }

        /*if (ObjectUtils.isEmpty(multipartHttpServletRequest) == false){
            Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
            String name;
            while(iterator.hasNext()){
                name = iterator.next();
                System.out.println("name : " + name);
                List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
                for (MultipartFile multipartFile : list) {
                    System.out.println("start file information");
                    System.out.println("file name : " + multipartFile.getName());
                    System.out.println("file size : " + multipartFile.getSize());
                    System.out.println("file content type : " + multipartFile.getContentType());
                    System.out.println("end file information.");
                }
            }
        }*/
    }

    @Override
    public BoardDto selectBoardDetail(int boardIdx) throws Exception {
        //int i = 10/0; // 에러테스트
        BoardDto board = boardMapper.selectBoardDetail(boardIdx);
        List<BoardFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
        board.setFileList(fileList);
        boardMapper.updateHitCount(boardIdx);
        return board;
    }

    @Override
    public BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception {
        return boardMapper.selectBoardFileInformation(idx, boardIdx);
    }

    @Override
    public void updateBoard(BoardDto board) {
        boardMapper.updateBoard(board);
    }

    @Override
    public void deleteBoard(int boardIdx) {
        boardMapper.deleteBoard(boardIdx);
    }
}


