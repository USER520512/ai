package com.sy.third;


import com.sy.third.request.fish.TextToSpeechRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *FishAudio
 */
@FeignClient(name = "FishAudio", url = "${api.fish.audio}", fallbackFactory = FishAudioFeignFactory.class)
public interface FishAudioRemoteClient {
    String AUTHORIZATION = "AUTHORIZATION=Bearer 26d65b03d13d4861834351b6f84f993b";

    /**
     * HttpResponse<String> response = Unirest.get("https://api.fish.audio/wallet/{user_id}/api-credit")
     * .header("AUTHORIZATION", "Bearer <token>")
     * .asString();
     */
    @GetMapping(value = "/wallet/{user_id}/api-credit", headers = {AUTHORIZATION})
    Object getAPICredit(@PathVariable String user_id);

    /**
     * HttpResponse<String> response = Unirest.get("https://api.fish.audio/wallet/{user_id}/package")
     * .header("AUTHORIZATION", "Bearer <token>")
     * .asString();
     */
    @GetMapping(value = "/{user_id}/package", headers = {AUTHORIZATION})
    Object getUserPackage(@PathVariable String user_id);

    /**
     * HttpResponse<String> response = Unirest.get("https://api.fish.audio/model")
     * .header("AUTHORIZATION", "Bearer <token>")
     * .asString();
     * @return
     */
    @GetMapping(value = "/model", headers = {AUTHORIZATION})
    Object listModels();

    /**
     * HttpResponse<String> response = Unirest.post("https://api.fish.audio/model")
     * .header("AUTHORIZATION", "Bearer <token>")
     * .header("Content-Type", "multipart/form-data")
     * .body("-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"visibility\"\r\n\r\npublic\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"type\"\r\n\r\ntts\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"title\"\r\n\r\n<string>\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"description\"\r\n\r\n<string>\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"train_mode\"\r\n\r\nfast\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"voices\"\r\n\r\n[\n  null\n]\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"texts\"\r\n\r\n[\n  \"<string>\"\n]\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"tags\"\r\n\r\n[\n  \"<string>\"\n]\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"enhance_audio_quality\"\r\n\r\ntrue\r\n-----011000010111000001101001--\r\n\r\n")
     * .asString();
     */
    @PostMapping(value = "/model", headers = {AUTHORIZATION})
    Object createModel(@Param("token") String token,
                       @RequestPart("visibility") String visibility,
                       @RequestPart("type") String type,
                       @RequestPart("title") String title,
                       @RequestPart("description") String description,
                       @RequestPart("train_mode") String trainMode,
                       @RequestPart("voices") MultipartFile voices,
                       @RequestPart("texts") String texts,
                       @RequestPart("tags") String tags,
                       @RequestPart("enhance_audio_quality") boolean enhanceAudioQuality);
    /**
     * HttpResponse<String> response = Unirest.get("https://api.fish.audio/model/{id}")
     * .header("AUTHORIZATION", "Bearer <token>")
     * .asString();
     */
    @GetMapping(value = "/model/{id}", headers = {AUTHORIZATION})
    Object getModel(@PathVariable String id);

    /**
     * HttpResponse<String> response = Unirest.delete("https://api.fish.audio/model/{id}")
     * .header("AUTHORIZATION", "Bearer <token>")
     * .asString();
     */
    @DeleteMapping(value = "/model/{id}", headers = {AUTHORIZATION})
    Object deleteModel(@PathVariable String id);

    /**
     * HttpResponse<String> response = Unirest.patch("https://api.fish.audio/model/{id}")
     * .header("AUTHORIZATION", "Bearer <token>")
     * .header("Content-Type", "application/json")
     * .body("{\n  \"title\": \"<string>\",\n  \"description\": \"<string>\",\n  \"visibility\": \"public\",\n  \"tags\": [\n    \"<string>\"\n  ]\n}")
     * .asString();
     */
    @PatchMapping(value = "/model/{id}", headers = {AUTHORIZATION})
    Object updateModel(@PathVariable String id);

    /**
     * HttpResponse<String> response = Unirest.post("https://api.fish.audio/v1/tts")
     * .header("AUTHORIZATION", "Bearer <token>")
     * .header("Content-Type", "application/json")
     * .body("{\n  \"text\": \"<string>\",\n  \"references\": [\n    {\n      \"text\": \"<string>\"\n    }\n  ],\n  \"reference_id\": \"<string>\",\n  \"chunk_length\": 200,\n  \"normalize\": true,\n  \"format\": \"wav\",\n  \"mp3_bitrate\": 64,\n  \"opus_bitrate\": -1000,\n  \"latency\": \"normal\"\n}")
     * .asString();
     */
    @PostMapping(value = "/v1/tts", headers = {AUTHORIZATION})
    Object textToSpeech(@RequestBody TextToSpeechRequest request);
}
