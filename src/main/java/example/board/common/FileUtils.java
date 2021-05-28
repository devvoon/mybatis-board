package example.board.common;

import example.board.dto.BoardFileDto;
import example.board.entity.JpaBoardFile;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class FileUtils {
    public List<BoardFileDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
        if(ObjectUtils.isEmpty(multipartHttpServletRequest)){
            return null;
        }

        List<BoardFileDto> fileList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime localDateTime = LocalDateTime.now();
        String path = "images/"+ localDateTime.format(formatter);
        File file = new File(path);
        if (false == file.exists()){
            file.mkdirs();
        }

        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        String newFileName, originalFileExtension  = "", contentType;

        while(iterator.hasNext()){
            List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
            for (MultipartFile multipartFile : list) {
                if(multipartFile.isEmpty() == false){
                    contentType = multipartFile.getContentType();
                    if (ObjectUtils.isEmpty(contentType)){
                        break;
                    }else {
                        if (contentType.contains("image/jpeg")){
                            originalFileExtension = ".jpg";
                        }else if (contentType.contains("image/png")){
                            originalFileExtension = ".png";
                        }else if (contentType.contains("image/gif")){
                            originalFileExtension = ".gif";
                        }else {
                            break;
                        }
                    }
                }

                newFileName = Long.toString(System.nanoTime()) + originalFileExtension;

                BoardFileDto boardFile = new BoardFileDto();
                boardFile.setBoardIdx(boardIdx);
                boardFile.setFileSize(multipartFile.getSize());
                boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
                boardFile.setStoredFilePath(path+"/"+newFileName);
                fileList.add(boardFile);

                file = new File(path+"/"+newFileName);
                multipartFile.transferTo(file);
            }
        }
        return fileList;
    }

    public List<JpaBoardFile> parseFileInfo(MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        if(ObjectUtils.isEmpty(multipartHttpServletRequest)){
            return null;
        }

        List<JpaBoardFile> fileList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime localDateTime = LocalDateTime.now();
        String path = "images/"+ localDateTime.format(formatter);
        File file = new File(path);
        if (false == file.exists()){
            file.mkdirs();
        }

        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        String newFileName, originalFileExtension  = "", contentType;

        while(iterator.hasNext()){
            List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
            for (MultipartFile multipartFile : list) {
                if(multipartFile.isEmpty() == false){
                    contentType = multipartFile.getContentType();
                    if (ObjectUtils.isEmpty(contentType)){
                        break;
                    }else {
                        if (contentType.contains("image/jpeg")){
                            originalFileExtension = ".jpg";
                        }else if (contentType.contains("image/png")){
                            originalFileExtension = ".png";
                        }else if (contentType.contains("image/gif")){
                            originalFileExtension = ".gif";
                        }else {
                            break;
                        }
                    }
                }

                newFileName = Long.toString(System.nanoTime()) + originalFileExtension;

                JpaBoardFile boardFile = new JpaBoardFile();

                boardFile.setFileSize(multipartFile.getSize());
                boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
                boardFile.setStoredFilePath(path+"/"+newFileName);
                boardFile.setCreatorId("admin");
                fileList.add(boardFile);

                file = new File(path+"/"+newFileName);
                multipartFile.transferTo(file);
            }
        }
        return fileList;
    }
}
