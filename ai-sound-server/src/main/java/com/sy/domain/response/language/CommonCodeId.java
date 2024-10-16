package com.sy.domain.response.language;

import com.sy.domain.response.IResultCode;
import lombok.Data;

@Data
public class CommonCodeId implements IResultCode {

    public static final CommonCodeId SUCCESS = new CommonCodeId(200, "sukses");

    public static final CommonCodeId INTERNAL_SERVER_ERROR = new CommonCodeId(500, "Kesalahan internal server");

    public static final CommonCodeId FAIL = new CommonCodeId(204, "Operasi gagal");

    public static final CommonCodeId PARAM_ERROR = new CommonCodeId(504, "Kesalahan parameter");

    public static final CommonCodeId UNAUTHORIZED_ERROR = new CommonCodeId(401, "Autentikasi gagal dan sumber daya sistem tidak dapat diakses.");

    public static final CommonCodeId CLIENT_ID_ERROR = new CommonCodeId(1000, "Pengidentifikasi unik pengguna kosong.");

    public static final CommonCodeId USER_LOGIN_ERROR = new CommonCodeId(1002, "Pengguna tidak masuk atau telah kedaluwarsa. Silakan masuk ke sistem sebelum mengakses lagi!");

    public static final CommonCodeId INFO_IS_EMPTY = new CommonCodeId(1005, "Informasi yang diperoleh kosong!");

    public static final CommonCodeId BOX_IS_EMPTY = new CommonCodeId(1006, "Kotak harta karun kosong");

    public static final CommonCodeId BOX_IS_OPEN = new CommonCodeId(1016, "Kotak harta karun sudah dibuka");

    public static final CommonCodeId CHECK_IS_EXIST = new CommonCodeId(1007, "Hari ini sudah melakukan tanda tangan");

    public static final CommonCodeId VISITOR_STATUS_ERROR = new CommonCodeId(1008, "Pembatasan status pengunjung");


    public static final CommonCodeId NETWORK_IS_OUT = new CommonCodeId(1015, "Silakan jangan klik berulang kali");

    /**
     * 任务数量已达到上限
     */
    public static final CommonCodeId   TASK_LIMIT_REACHED = new CommonCodeId(1017, "Jumlah tugas sudah penuh");
    /**
     * 任务不存在
     */
    public static final CommonCodeId    TASK_DOES_NOT_EXIST = new CommonCodeId(1018, "Tugas tidak ada");

    /**
     * 任务已经终止
     */
    public static final CommonCodeId    TASK_HAS_BEEN_TERMINATED = new CommonCodeId(1019, "Tugas telah dihentikan");

    /**
     * 任务已经完成过了
     */
    public static final CommonCodeId    TASK_HAS_ALREADY_BEEN_COMPLETED = new CommonCodeId(1020, "Tugas sudah pernah diselesaikan");

    /**
     * 今天已经提现过了
     */
    public static final CommonCodeId   ALREADY_WITHDRAWN_TODAY  = new CommonCodeId(1021, "Sudah menarik hari ini");

    private int code;
    private String message;

    // 根据默认语言的错误码返回
    public static CommonCodeId fromCommonCode(int commonCode) {
        switch (commonCode) {
            case 200:
                return SUCCESS;
            case 500:
                return INTERNAL_SERVER_ERROR;
            case 204:
                return FAIL;
            case 504:
                return PARAM_ERROR;
            case 401:
                return UNAUTHORIZED_ERROR;
            case 1000:
                return CLIENT_ID_ERROR;
            case 1002:
                return USER_LOGIN_ERROR;
            case 1005:
                return INFO_IS_EMPTY;
            case 1006:
                return BOX_IS_EMPTY;
            case 1016:
                return BOX_IS_OPEN;
            case 1007:
                return CHECK_IS_EXIST;
            case 1008:
                return VISITOR_STATUS_ERROR;
            case 1015:
                return NETWORK_IS_OUT;
            case 1017:
                return TASK_LIMIT_REACHED;
            case 1018:
                return TASK_DOES_NOT_EXIST;
            case 1019:
                return TASK_HAS_BEEN_TERMINATED;
            case 1020:
                return TASK_HAS_ALREADY_BEEN_COMPLETED;
            case 1021:
                return ALREADY_WITHDRAWN_TODAY;
            default:
                throw new IllegalArgumentException("Unknown CommonCode: " + commonCode);
        }
    }

    public CommonCodeId(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    public CommonCodeId setMessage(String message) {
        this.message = message;
        return this;
    }
}
