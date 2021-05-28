package example.board.service;

import example.board.common.FileUtils;
import example.board.entity.JpaBoard;
import example.board.entity.JpaBoardFile;
import example.board.repository.JpaBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaBoardServiceImpl implements JpaBoardService{

    private final JpaBoardRepository jpaBoardRepository;
    private final FileUtils fileUtils;

    @Override
    public List<JpaBoard> selectBoardList() throws Exception {
        return jpaBoardRepository.findAllByOrderByBoardIdxDesc();
    }

    @Override
    public void saveBoard(JpaBoard board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        board.setCreatorId("admin");
        List<JpaBoardFile> list = fileUtils.parseFileInfo(multipartHttpServletRequest);
        if (CollectionUtils.isEmpty(list) == false){
            board.setFileList(list);
        }
        jpaBoardRepository.save(board);
    }

    @Override
    public JpaBoard selectBoardDetail(int boardIdx) throws Exception {
        Optional<JpaBoard> optional = jpaBoardRepository.findById(boardIdx);
        if(optional.isPresent()){
            JpaBoard board = optional.get();
            board.setHitCount(board.getHitCount()+1);
            jpaBoardRepository.save(board);

            return board;
        }else {
            throw new NullPointerException();
        }
    }

    @Override
    public void deleteBoard(int boardIdx) {
        jpaBoardRepository.deleteById(boardIdx);
    }

    @Override
    public JpaBoardFile selectBoardFileInformation(int boardIdx, int idx) throws Exception {
        JpaBoardFile boardFile = jpaBoardRepository.findBoardFile(boardIdx, idx);
        return boardFile;
    }
}


