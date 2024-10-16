package com.sy.third.resp.fish;

public class VersionRecordConstant {

    private VersionRecordConstant() {

    }

    public enum LimitField {
        AUTH_ADVERTISER("广告主授权"),UPLOAD_MATERIAL("素材上传"),SYS_ACCOUNT("绑定系统子账号");
        private String desc;

        LimitField(String desc) {
            this.desc = desc;
        }
    }

}
