package com.sy.third.request.fish;

import lombok.Data;

@Data
 public  class TextToSpeechRequest {
        private String text;
        private Object[] references;
        private String reference_id;
        private Integer chunk_length;
        private Boolean normalize;
        private String format;
        private Integer mp3_bitrate;
        private Integer opus_bitrate;
        private String latency;
    }