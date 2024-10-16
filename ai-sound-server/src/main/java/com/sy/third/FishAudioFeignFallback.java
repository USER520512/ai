package com.sy.third;

import com.sy.third.request.fish.TextToSpeechRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Chen ChunJiang
 * date 2022-07-28
 */
@Component
public class FishAudioFeignFallback implements FishAudioRemoteClient {

    @Override
    public Object getAPICredit(String user_id) {
        return null;
    }

    @Override
    public Object getUserPackage(String user_id) {
        return null;
    }

    @Override
    public Object listModels() {
        return null;
    }

    @Override
    public Object createModel(String token, String visibility, String type, String title, String description, String trainMode, MultipartFile voices, String texts, String tags, boolean enhanceAudioQuality) {
        return null;
    }

    @Override
    public Object getModel(String id) {
        return null;
    }

    @Override
    public Object deleteModel(String id) {
        return null;
    }

    @Override
    public Object updateModel(String id) {
        return null;
    }

    @Override
    public Object textToSpeech(TextToSpeechRequest request) {
        return null;
    }
}
