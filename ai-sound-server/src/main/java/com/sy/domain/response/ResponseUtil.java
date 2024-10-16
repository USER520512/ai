package com.sy.domain.response;

import com.sy.domain.response.language.CommonCodeId;

import javax.servlet.http.HttpServletRequest;

import static com.sy.common.constants.Constants.INDONESIA_LANGUAGE_HEADER_VALUE;
import static com.sy.common.constants.Constants.LANGUAGE_HEADER;

public class ResponseUtil {

    public static ResponseResult<Object> createResponse(CommonCode commonCode, HttpServletRequest request, Object data) {
        if (INDONESIA_LANGUAGE_HEADER_VALUE.equals(request.getHeader(LANGUAGE_HEADER))) {
            return new ResponseResult<>(CommonCodeId.fromCommonCode(commonCode.code()), data);
        } else {
            return new ResponseResult<>(commonCode, data);
        }
    }

    public static ResponseResult<Object> createResponse(CommonCode commonCode, HttpServletRequest request) {
        if (INDONESIA_LANGUAGE_HEADER_VALUE.equals(request.getHeader(LANGUAGE_HEADER))) {
            return new ResponseResult<>(CommonCodeId.fromCommonCode(commonCode.code()));
        } else {
            return new ResponseResult<>(commonCode);
        }
    }
}
