package com.r2s.pte.util.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.r2s.pte.config.CloudVardyConfig;
import com.r2s.pte.dto.RequestUserInfoDTO;
import com.r2s.pte.dto.TokenDTO;
import com.r2s.pte.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.r2s.pte.common.CodeCategory;
import com.r2s.pte.common.Text;
import com.r2s.pte.common.TypeError;
import com.r2s.pte.entity.Category;
import com.r2s.pte.entity.QuestionType;
import com.r2s.pte.exception.ErrorMessageException;
import com.r2s.pte.service.QuestionTypeService;
import com.r2s.pte.util.HandleGeneral;

@Component
public class HandleGeneralImpl implements HandleGeneral {
    @Autowired
    private QuestionTypeService questionTypeService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private CloudVardyConfig cloudVardyConfig;


    private Long idTypeFileUpload = (long) 9;
    private Long idTypeParagraph = (long) 4;
    private Long idTypeEmbbedQuestionDropbox = (long) 15;
    private Long idTypeCheckbox = (long) 6;
    private Long idTypeMultipleChoice = (long) 5;
    private Long idTypeEmbeddedQuestionDragDrop = (long) 16;
    private Long idTypeEmbeddedQuestionEmptyBlank = (long) 14;
    private Long idTypeShortAnswer = (long) 2;
    private final String parentCodeReading = "PTE-R";
    private final String parentCodeListening = "PTE-L";

    public static final char SPACE = ' ';
    public static final char TAB = '\t';
    public static final char BREAK_LINE = '\n';
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public QuestionType setQuestionTypeMatchCategrory(Category category) {
        String code = category.getCode();
        if (code.equals(CodeCategory.CODE_RA) || code.equals(CodeCategory.CODE_RS) || code.equals(CodeCategory.CODE_DI) || code.equals(CodeCategory.CODE_RL)
                || code.equals(CodeCategory.CODE_AS))
            return this.questionTypeService.findById(idTypeFileUpload);// file upload
        else if (code.equals(CodeCategory.CODE_ESSAY) || code.equals(CodeCategory.CODE_SWT) || code.equals(CodeCategory.CODE_SST) || code.equals(CodeCategory.CODE_DUALQ)
                || code.equals(CodeCategory.CODE_YN) || code.equals(CodeCategory.CODE_OPENQ))
            return this.questionTypeService.findById(idTypeParagraph);// PARAGRAPH
        else if (code.equals(CodeCategory.CODE_FIB) && category.getParentCode().equals(parentCodeReading))
            return this.questionTypeService.findById(idTypeEmbbedQuestionDropbox);// EMBED_QUESTIONS_DROPBOX
        else if (code.equals(CodeCategory.CODE_MCQMA))
            return this.questionTypeService.findById(idTypeCheckbox);// checkbox
        else if (code.equals(CodeCategory.CODE_MCQSA) || code.equals(CodeCategory.CODE_SMW) || code.equals(CodeCategory.CODE_HCS))
            return this.questionTypeService.findById(idTypeMultipleChoice);// multiple choice
        else if (code.equals(CodeCategory.CODE_DD))
            return this.questionTypeService.findById(idTypeEmbeddedQuestionDragDrop);
        else if (code.equals(CodeCategory.CODE_FIB) && category.getParentCode().equals(parentCodeListening))
            return this.questionTypeService.findById(idTypeEmbeddedQuestionEmptyBlank);
        else if (code.equals(CodeCategory.CODE_WFD))
            return this.questionTypeService.findById(idTypeShortAnswer);
        else
            return this.questionTypeService.findById(idTypeFileUpload);
    }

    @Override
    public int countWord(String input) {
        if (input == null) {
            return -1;
        }
        int count = 0;
        int size = input.length();
        boolean notCounted = true;
        for (int i = 0; i < size; i++) {
            if (input.charAt(i) != SPACE && input.charAt(i) != TAB && input.charAt(i) != BREAK_LINE) {
                if (notCounted) {
                    count++;
                    notCounted = false;
                }
            } else {
                notCounted = true;
            }
        }
        return count;
    }

    @Override
    public String normalizeString(String st) {
        st = st.trim().toLowerCase();
        st = st.replaceAll("\\s+", " ");
        st = st.replaceAll("[\\\\-\\\\+\\\\.\\\\^:,]", "");
        String[] temp = st.split(" ");
        st = "";
        for (int i = 0; i < temp.length; i++) {
            st += String.valueOf(temp[i].charAt(0)).toUpperCase() + temp[i].substring(1);
            if (i < temp.length - 1)
                st += " ";
        }
        return st;
    }

    @Override
    public List<Text> setListText(String result) {
        List<Text> texts = new ArrayList<Text>();
        String[] results = result.split(" ");
        for (int i = 0; i < results.length; i++) {
            String item = results[i];
            if (texts.size() == 0) {
                Text text = new Text();
                text.setValue(item);
                text.setCount(1);
                texts.add(text);
            } else {
                boolean flag = false;
                for (Text text : texts) {
                    if (text.getValue().equals(item)) {
                        text.setCount(text.getCount() + 1);
                        flag = true;
                    }

                }
                if (!flag) {
                    Text text = new Text();
                    text.setValue(item);
                    text.setCount(1);
                    texts.add(text);
                }

            }
        }
        return texts;
    }

    @Override
    public String getURL(String content) {
        String keyUrl = "https";
        String keyFileExtension = "fileExtension";
        int indexUrl = content.indexOf(keyUrl);
        int indexEndUrl = content.indexOf(keyFileExtension) - 2;
        return content.substring(indexUrl, indexEndUrl);
    }

    @Override
    public String parseLocalDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMAT);
    }

    @Override
    public LocalDateTime parseLocalDateTime(String dateTimeStr) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMAT);
            return dateTime;
        } catch (DateTimeParseException e) {
            throw new ErrorMessageException(
                    String.format(this.messageSource.getMessage("DateTimeFormat", null, null), dateTimeStr)
                    , TypeError.BadRequest);
        }

    }

    @Override
    public Integer randomScore() {
        Random random = new Random();
        int min = 5;
        int max = 50;
        return (int)(Math.random()*(max-min)) + min;
    }

    private final String CHAR_ID = "id";
    private final String CHAR_USER_TYPE = "userType";

    @Override
    public Long getIdUser(String response) {
        int indexId = response.indexOf(CHAR_ID);
        int index = response.indexOf(",", indexId);//find index ',' next
        return Long.valueOf(response.substring(indexId + CHAR_ID.length() + 1, index));
    }

    @Override
    public Long getUserType(String response) {
        int indexId = response.indexOf(CHAR_USER_TYPE);
        int index = response.indexOf(",", indexId);
        return Long.valueOf(response.substring(indexId + CHAR_USER_TYPE.length() + 1, index));
    }

    @Override
    public TokenDTO createToken(RequestUserInfoDTO requestUserInfoDTO) {
        String authEncoder = null;
        try {
            authEncoder = URLEncoder.encode(requestUserInfoDTO.getAuthKey(), StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        Object result = cloudVardyConfig.getUser(requestUserInfoDTO.getUsername(), requestUserInfoDTO.getBrandUrl(), authEncoder);
        Long userId = getIdUser(result.toString());
        Long userType = getUserType(result.toString());
        String token = JWTUtil.generate(userId, userType);
        System.out.println("TEST SUCCESSFULL");
        return new TokenDTO(userId, userType, token, HttpStatus.OK.value());
    }
}
